package com.sbt.test.repository;

import com.sbt.test.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositorySmokeTest {

    private User MOCK_USER_1 = User.builder()
            .username("user 1")
            .password("test pass 1")
            .accountNonExpired(true)
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .enabled(true)
            .build();

    private User MOCK_USER_2 = User.builder()
            .username("user 2")
            .password("test pass 2")
            .accountNonExpired(true)
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .enabled(true)
            .build();

    @Autowired
    private UserRepository repo;

    @Test
    public void findsNoClient_ifItIsAbsent() {
        assertFalse("Absent user exists", repo.getByUsername("absent username").isPresent());
    }

    @Test
    @DirtiesContext
    public void putsClientSuccessfully() {
        assertEquals("User has changed", MOCK_USER_1, repo.update(MOCK_USER_1));
    }

    @Test
    @DirtiesContext
    public void putsTwoClientSuccessfully_differentUsername() {
        User savedUser1 = repo.update(MOCK_USER_1);
        User savedUser2 = repo.update(MOCK_USER_2);
        assertTrue("Ids are equal: save is corrupted", savedUser1.getId() != savedUser2.getId());
    }

    @Test
    @DirtiesContext
    public void putIsIdempotent() {
        User savedUser1 = repo.update(MOCK_USER_1);
        User savedUser2 = repo.update(MOCK_USER_1);
        assertEquals("Operation is no more idempotent", savedUser1, savedUser2);
        assertEquals("Ids are different: usernames in db are not unique!", savedUser1.getId(), savedUser2.getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DirtiesContext
    public void failsToSaveTwoUsers_sameUsernames() {
        User savedUser1 = repo.update(MOCK_USER_1);
        User userWithSameUsername = User.builder().username(MOCK_USER_1.getUsername()).build();
        assertNotEquals("Equals contract violated", userWithSameUsername, MOCK_USER_1);
        User savedUser2 = repo.update(userWithSameUsername);
    }

    @Test
    public void findsAClient_ifItIsPresent() {
        repo.update(MOCK_USER_1);
        assertTrue("Fails to find existing user", repo.getByUsername(MOCK_USER_1.getUsername()).isPresent());
    }

}
