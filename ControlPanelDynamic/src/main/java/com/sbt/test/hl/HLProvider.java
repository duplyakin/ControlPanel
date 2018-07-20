package com.sbt.test.hl;


import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Getter

@Slf4j
@Component
public class HLProvider {
    @Getter
    final private CryptoSuite cryptoSuite;
    @Getter
    final private HFCAClient caClient;

    private String peerUrl;
    private String eventHubUrl;
    private String ordererUrl;
    @SneakyThrows
    @Autowired
    public HLProvider(@Value("${hlclient.caUrl}") String caUrl,  @Value("${hlclient.clientFile}") String clientPropertiesFile ) {
        cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        // setup the client

        Properties caClientProperties=null;
        try(FileInputStream fis = new FileInputStream(clientPropertiesFile)){
            caClientProperties = new Properties(caClientProperties);
        }catch(IOException e){
            log.info( "no properties for "+clientPropertiesFile);
        }
        caClient = HFCAClient.createNewInstance(caUrl, caClientProperties);
        caClient.setCryptoSuite(cryptoSuite);
        //client.setUserContext()



    }

    @Value("${hlclient.peerUrl}")
    public void setPeerUrl(String peerUrl){
        this.peerUrl=peerUrl;
    }

    @Value("${hlclient.eventUrl}")
    public void setEventHubUrl(String eventHubUrl){
        this.eventHubUrl=eventHubUrl;

    }

    @Value("${hlclient.ordererUrl}")
    public void setOrdererUrl(String ordererUrl){
        this.ordererUrl=ordererUrl;
    }

    public HFClient getClient(User userContext) throws CryptoException, InvalidArgumentException {
        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(cryptoSuite);
        client.setUserContext(userContext);
        return client;
    }

    public Channel getChannel(HFClient client) throws InvalidArgumentException, TransactionException {
        // initialize channel
        // peer name and endpoint in fabcar network
        Peer peer = client.newPeer("peer0.org1.example.com", peerUrl);
        // eventhub name and endpoint in fabcar network
        EventHub eventHub = client.newEventHub("eventhub01", eventHubUrl);
        // orderer name and endpoint in fabcar network
        Orderer orderer = client.newOrderer("orderer.example.com", ordererUrl);
        // channel name in fabcar network
        Channel channel = client.newChannel("mychannel");
        channel.addPeer(peer);
        channel.addEventHub(eventHub);
        channel.addOrderer(orderer);
        channel.initialize();
        return channel;
    }

}
