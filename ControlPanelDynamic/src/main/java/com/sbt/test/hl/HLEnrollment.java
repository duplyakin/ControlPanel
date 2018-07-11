package com.sbt.test.hl;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;
import org.hyperledger.fabric.sdk.Enrollment;

import javax.persistence.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

@Entity
@Table(name="ENROLLMENT")
@Data
@NoArgsConstructor
@ToString(exclude = {"privateKeyBytes","privateKey","id"})
public class HLEnrollment implements Enrollment{


    @Id
    @GeneratedValue
    private Long id;

    private byte[] privateKeyBytes;
    private String algorithm;
    private String format;
    transient private PrivateKey privateKey=null;
    @Column(length = 2048)
    private String cert;


    public HLEnrollment(Enrollment other){
        cert = other.getCert();
        privateKey=other.getKey();
        if(privateKey!=null) {
            privateKeyBytes = privateKey.getEncoded();
            algorithm = privateKey.getAlgorithm();
            format = privateKey.getFormat();
        }
    }

    @SneakyThrows
    @Override
    public PrivateKey getKey() {

        if(privateKey==null&& algorithm!=null && format!=null && privateKeyBytes!=null){
            synchronized (this){
                if(privateKey==null){
                    KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
                    EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                    PrivateKey localPrivate = keyFactory.generatePrivate(privateKeySpec);
                    privateKey=localPrivate;
                }
            }
        }
        return privateKey;
    }

    @Override
    public String getCert() {
        return cert;
    }
}
