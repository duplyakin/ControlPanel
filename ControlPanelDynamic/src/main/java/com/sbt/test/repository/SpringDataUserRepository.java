package com.sbt.test.repository;

import com.sbt.test.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface SpringDataUserRepository extends JpaRepository<User, Long> {

    User getByUsername(String userName);

    @Transactional
    void deleteByUsername(String username);

}
