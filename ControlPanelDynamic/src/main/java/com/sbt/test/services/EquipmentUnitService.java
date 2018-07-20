package com.sbt.test.services;

import com.sbt.test.entities.EquipmentUnit;
import com.sbt.test.entities.User;
import com.sbt.test.hl.HLProvider;
import com.sbt.test.repository.EquipmentUnitRepository;
import com.sbt.test.repository.SpringDataEquipmentUnitRepository;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
public class EquipmentUnitService extends AbstractUserService {

   // private final HLProvider provider;

    private final EquipmentUnitRepository repo;

    @Autowired
    public EquipmentUnitService(/*HLProvider provider, */EquipmentUnitRepository repo) {
      //  this.provider = provider;
        this.repo = repo;
    }


    public EquipmentUnit addUnit(EquipmentUnit eq, User user) {

        EquipmentUnit unit = repo.addToHl(eq,user);
        EquipmentUnit unit2 =repo.getFromHl(unit.getHlId(),user);
            return unit;
        }


    public EquipmentUnit updateUnit(EquipmentUnit eq) {
        return repo.saveAndFlush(eq);
    }

    public EquipmentUnit getById(long id) {
        return repo.getById(id);
    }

    public EquipmentUnit getByhlId(User user, String hlId) {
        return repo.getFromHl(hlId,user);
    }
}
