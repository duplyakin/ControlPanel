package com.sbt.test.repository;

import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.repository.exceptions.UserNotExistException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositorySmokeTest {

    private User MOCK_USER_1 = User.builder()
            .username("user 1")
            .password("test pass 1")
            .springRoles(Collections.singleton(Role.USER))
            .privileges(Collections.singleton(Privilege.READ))
            .accountNonExpired(true)
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .enabled(true)
            .build();

    private User MOCK_USER_2 = User.builder()
            .username("user 2")
            .password("test pass 2")
            .springRoles(Collections.singleton(Role.USER))
            .privileges(Collections.singleton(Privilege.READ))
            .accountNonExpired(true)
            .accountNonLocked(true)
            .enabled(true)
            .credentialsNonExpired(true)
            .build();

    @Autowired
    private UserRepository repo;

    // tests on get
    @Test(expected = UserNotExistException.class)
    public void getsNoUser_ifItIsAbsent() {
        repo.get("absent username");
    }

    @Test
    @DirtiesContext
    public void getsUser_ifItIsPresent() {
        repo.add(MOCK_USER_1);
        User user = repo.get(MOCK_USER_1.getUsername());
        assertNotNull("Fails to find existing user:", user);
        assertEquals("Users are not equal:", user, MOCK_USER_1);
    }

    // tests on add

    @Test
    @DirtiesContext
    public void addUserSuccessfully() {
        assertEquals("User has changed", MOCK_USER_1, repo.add(MOCK_USER_1));
    }

    @Test
    @DirtiesContext
    public void addIsIdempotent() {
        User savedUser1 = repo.add(MOCK_USER_1);
        User savedUser2 = repo.add(MOCK_USER_1);
        assertEquals("Operation is no more idempotent", savedUser1, savedUser2);
        assertEquals("Ids are different: usernames in db are not unique!", savedUser1.getId(), savedUser2.getId());
    }

    @Test
    @DirtiesContext
    public void addTwoUsersSuccessfully_differentUsername() {
        User savedUser1 = repo.add(MOCK_USER_1);
        User savedUser2 = repo.add(MOCK_USER_2);
        assertTrue("Ids are equal: save is corrupted", savedUser1.getId() != savedUser2.getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DirtiesContext
    public void failsToAddTwoUsers_sameUsernames() {
        repo.add(MOCK_USER_1);
        User userWithSameUsername = User.builder().username(MOCK_USER_1.getUsername()).build();
        assertNotEquals("Equals contract violated", userWithSameUsername, MOCK_USER_1);
        repo.add(userWithSameUsername);
    }

    // update tests
    @Test
    @DirtiesContext
    public void updateSuccessfully_existingUser() {
        User added = repo.add(MOCK_USER_1);
        User emptyUserWithAppropriateUsernameAndId = User.builder()
                .id(added.getId())
                .username(added.getUsername())
                .build();
        repo.update(emptyUserWithAppropriateUsernameAndId);
        User user2 = repo.get(added.getUsername());
        assertNotEquals("User has not changed", user2, added);
        assertEquals("User is not equal!", user2, emptyUserWithAppropriateUsernameAndId);
    }


    // delete tests
    @Test
    @DirtiesContext
    public void deleteSuccessfully_existingUser() {
        repo.add(MOCK_USER_1);
        assertNotNull("Add fails!", repo.get(MOCK_USER_1.getUsername()));
        assertEquals("Deletes wrong client!", MOCK_USER_1, repo.delete(MOCK_USER_1.getUsername()));
    }
}
