package com.sbt.test.hl.storage;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbt.test.entities.User;
import com.sbt.test.hl.HLProvider;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.hyperledger.fabric.sdk.Channel.TransactionOptions.createTransactionOptions;

@Repository
@Slf4j
public abstract class AbstractHlConstEntityRepository<T extends HLEntity> implements HLConstEntityRepository<T> {

    @Getter
    private final JpaRepository<T, Long> jpaRepository;
    @Getter
    private final HLProvider hlProvider;
    @Autowired
    protected AbstractHlConstEntityRepository(JpaRepository<T, Long> repo, HLProvider hlProvider) {
        this.jpaRepository = repo;
        this.hlProvider = hlProvider;
    }

    private ObjectMapper mapper = new ObjectMapper().enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.NON_FINAL,"clazz");
    @Override
    public T getFromHl(String hlEntityId, User user) throws EntityNotFoundException {
        return null;
    }

    @Override
    public T get(Long id) {
        return jpaRepository.getOne(id);
    }

    @Override
    public T addToHl(T entity, User user)   {

        try {
            HFClient hlClient = hlProvider.getClient(user);
            Channel channel = hlProvider.getChannel(hlClient);
            // create chaincode request
            TransactionProposalRequest tpr = hlClient.newTransactionProposalRequest();
            // build cc id providing the chaincode name. Version is omitted here.
            ChaincodeID ccId = ChaincodeID.newBuilder().setName("objects-ledger").build();
            tpr.setChaincodeID(ccId);
            //try {
            //    String txId = hlClient.
            // CC function to be called
            tpr.setFcn("addEvent");
            tpr.setArgs(entity.getHlId(),mapper.writeValueAsString(entity));
            Collection<ProposalResponse> res = channel.sendTransactionProposal(tpr);

            boolean goodProp=false;
            Collection<ProposalResponse> successful = new LinkedList<>();
            // display response
            for (ProposalResponse pres : res) {
                if(pres.getStatus()== ProposalResponse.Status.SUCCESS){
                    successful.add(pres);

                }
                String stringResponse = new String(pres.getChaincodeActionResponsePayload());
                log.info(stringResponse);
            }

            BlockEvent.TransactionEvent event=channel.sendTransaction(successful, createTransactionOptions() //Basically the default options but shows it's usage.
                    .userContext(user) //could be a different user context. this is the default.
                    .shuffleOrders(false) // don't shuffle any orderers the default is true.
                    .orderers(channel.getOrderers()) // specify the orderers we want to try this transaction. Fails once all Orderers are tried.
                  //  .nOfEvents(channel.) // The events to signal the completion of the interest in the transaction
            ).get(30, TimeUnit.SECONDS);
            /*if(event.isValid()){

            }*/

        } catch (Exception e) {
           log.error("bad tx",e);
        }/* catch (ProposalException e) {
            e.printStackTrace();
        } catch (CryptoException e) {
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }*/
        jpaRepository.saveAndFlush(entity);
        return entity;
    }
}
