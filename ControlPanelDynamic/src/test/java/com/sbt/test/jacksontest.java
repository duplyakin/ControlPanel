package com.sbt.test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbt.test.entities.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class jacksontest {
    @Test
  public  void doSerialize(){
        final ObjectMapper mapper = new ObjectMapper().enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.NON_FINAL,"class_");

        EquipmentUnit unit = new EquipmentUnit();
        unit.setId(1);
        EquipmentType type = new EquipmentType();
        type.setName("zzz");
        Parameter parameter = new Parameter();
        parameter.setId(1);
        parameter.setName("ddd");
        parameter.setType(Type.STRING);
        type.getParameters().add(parameter);
        unit.setType(type);
        ParameterValue val = new ParameterValue();
        val.setId(1111);
        val.setParameter(parameter);
        val.setValue("aasdfsdf");
        unit.getValues().add(val);
        try {
            String json = mapper.writeValueAsString(unit);
            EquipmentUnit unit2 = mapper.readerFor(EquipmentUnit.class).readValue(json);

            assert unit.equals(unit2);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
