package com.sbt.test.userLoader;

import com.sbt.test.annotations.MadeByGleb;
import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.hl.HLProvider;
import com.sbt.test.repository.exceptions.UserNotExistException;
import com.sbt.test.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Slf4j
@Service
@MadeByGleb
public class UserDetailsLoader implements UserDetailsService {

    private final UserRepository repo;


    @Autowired
    public UserDetailsLoader(UserRepository repo) {
        this.repo = repo;
}





    /*static User getAdmin(HFCAClient caClient) throws Exception {
        User admin = tryDeserialize("admin");
        if (admin == null) {
            Enrollment adminEnrollment = caClient.enroll("admin", "adminpw");
            admin = new AppUser("admin", "org1", "Org1MSP", adminEnrollment);
            serialize(admin);
        }
        return admin;
    }*/




    @PostConstruct
    public void init() {
        try{
            repo.get(UserRepository.ADMIN);
        }catch( UserNotExistException e) {
            log.info("Creating admin user. Again?)");
               // admin = new AppUser("admin", "org1", "Org1MSP", adminEnrollment);
                repo.add(User.builder()
                        .username(UserRepository.ADMIN)
                        .password(new BCryptPasswordEncoder().encode("adminpw"))
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .enabled(true)
                        .springRoles(Arrays.asList(Role.ADMIN, Role.USER))
                        .privileges(Arrays.asList(Privilege.READ, Privilege.WRITE))
                        .build());
        }
        //efgefgefgefg
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return repo.get(username);
        } catch (UserNotExistException ex) {
            throw new UsernameNotFoundException("User " + username + " is missing");
        }
    }
}