package com.sbt.test.hl.storage;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import com.sbt.test.entities.User;
import com.sbt.test.hl.HLProvider;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.hyperledger.fabric.sdk.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nullable;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class AbstractHlConstEntityRepository<T extends HLEntity> implements HLConstEntityRepository<T> {

    protected final ObjectMapper mapper = new ObjectMapper().enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.NON_FINAL, "clazz");
    @Getter
    private final JpaRepository<T, Long> jpaRepository;
    @Getter
    private final HLProvider hlProvider;
    @Getter
    private final Class<T> entityClass;

    @Autowired
    protected AbstractHlConstEntityRepository(JpaRepository<T, Long> repo, HLProvider hlProvider, Class<T> entityClass) {
        this.jpaRepository = repo;
        this.hlProvider = hlProvider;
        this.entityClass = entityClass;
    }

    @Override
    public @Nullable
    T getFromHl(String hlEntityId, User user) throws EntityNotFoundException {
        try {
            HFClient hlClient = hlProvider.getClient(user);
            Channel channel = hlProvider.getChannel(hlClient);
            // create chaincode request
            QueryByChaincodeRequest qpr = hlClient.newQueryProposalRequest();
            // build cc id providing the chaincode name. Version is omitted here.
            ChaincodeID ccId = ChaincodeID.newBuilder().setName("objects-ledger").build();
            qpr.setChaincodeID(ccId);
            qpr.setFcn("queryItem");
            qpr.setArgs(hlEntityId);
            // CC function to be called
            Collection<ProposalResponse> responses = channel.queryByChaincode(qpr);
            for (ProposalResponse response : responses) {
                if (response.isVerified() && response.getStatus() == ChaincodeResponse.Status.SUCCESS) {
                    String stringResponse = new String(response.getChaincodeActionResponsePayload());
                    log.info(stringResponse);

                    ByteString payload = response.getProposalResponse().getResponse().getPayload();
                    JsonNode rootNode = mapper.readTree(payload.toStringUtf8());
                    JsonNode datanode = rootNode.path("data");
                    Iterator<JsonNode> elements = datanode.elements();
                    List<Byte> data = new ArrayList<>();
                    while (elements.hasNext()) {
                        data.add((byte) elements.next().intValue());
                    }
                    byte[] barr = new byte[data.size()];
                    for (int i = 0; i < data.size(); i++) {
                        barr[i] = data.get(i);
                    }

                    ByteString bufData = ByteString.copyFrom(barr);
                    String out = StringEscapeUtils.unescapeJson(bufData.toStringUtf8());

                    // parse response
                    out = out.substring(1, out.length() - 1);
                    try {
                        return mapper.readerFor(getEntityClass()).readValue(out);
                    } catch (IOException e) {
                        log.error("Type " + getEntityClass().getSimpleName() + ":\nError parsing json :" + out, e);
                        throw e;
                    }

                } else {
                    log.error("response failed. status: " + response.getStatus().getStatus());
                }
            }

        } catch (Exception e) {
            log.error("bad tx", e);
        }
        return null;
    }

    @Override
    public T get(Long id) {
        return jpaRepository.getOne(id);
    }

    @Override
    public T addToHl(T entity, User user) {

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
            tpr.setFcn("addElement");
            tpr.setArgs(entity.getHlId(), mapper.writeValueAsString(entity));
            Collection<ProposalResponse> res = channel.sendTransactionProposal(tpr);

            boolean goodProp = false;
            Collection<ProposalResponse> successful = new LinkedList<>();
            // display response
            for (ProposalResponse pres : res) {
                if (pres.getStatus() == ProposalResponse.Status.SUCCESS) {
                    successful.add(pres);

                }
                String stringResponse = new String(pres.getChaincodeActionResponsePayload());
                log.info(stringResponse);
            }

            BlockEvent.TransactionEvent event = channel.sendTransaction(successful) //could be a different user context. this is the default.
                    .get(30, TimeUnit.SECONDS);
            if (event.isValid()) {
                log.info("object " + entity.getClass().getSimpleName() + " succesfully saved!");
            } else {
                log.error("object " + entity.getClass().getSimpleName() + " not saved!");
            }

        } catch (Exception e) {
            log.error("bad tx", e);
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
