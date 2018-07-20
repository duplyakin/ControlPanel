package com.sbt.test.entities;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

@JsonTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    private JacksonTester<User> serializer;

    private User user;

    @Before
    public void setup() {
        Random rand = ThreadLocalRandom.current();
        String NAME = "test username";
        String PASS = "test password";
        user = User.builder()
                .id(rand.nextLong())
                .username(NAME)
                .password(PASS)
                .springRoles(Collections.singleton(Role.USER))
                .privileges(Collections.singleton(Privilege.READ))
                .credentialsNonExpired(rand.nextBoolean())
                .accountNonLocked(rand.nextBoolean())
                .accountNonExpired(rand.nextBoolean())
                .build();
    }

    @Test
    public void userSerializationTest_roundtrip() throws IOException {
        User userRead = serializer.parseObject(serializer.write(user).getJson());
        assertEquals("User has changed during serialization", user, userRead);
    }

}
