package com.sbt.test.controllers;

import com.sbt.test.dto.NameWithAuthorities;
import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController extends AbstractRestController {

    private final UserRepository repo;

    @Autowired
    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/get/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> get(@PathVariable("username") String username) {
        return process(() -> repo.getByUsername(username)
                .map(AbstractRestController::ok)
                .orElse(notFound())
        );
    }

    @PutMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> add(@RequestBody User user) {
        return process(() -> ok(repo.update(user)));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> delete(@RequestAttribute("username") String username) {
        return process(() -> {
                    repo.deleteByUsername(username);
                    return ok();
                }
        );
    }

    @PostMapping("/setRoles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> setRoles(@RequestBody NameWithAuthorities<Role> usernameAndRoles) {
        return process(() -> {
                    Optional<User> userOpt = repo.getByUsername(usernameAndRoles.getName());
                    if (!userOpt.isPresent()) {
                        return notFound();
                    }
                    User user = userOpt.get();
                    user.setRoles(usernameAndRoles.getRoles());
                    return ok(repo.update(user));
                }
        );
    }

    @PostMapping("/setPrivileges")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> setPrivileges(@RequestBody NameWithAuthorities<Privilege> usernameAndPrivileges) {
        return process(() -> {
            Optional<User> userOpt = repo.getByUsername(usernameAndPrivileges.getName());
            if (!userOpt.isPresent()) {
                return notFound();
            }
            User user = userOpt.get();
            user.setPrivileges(usernameAndPrivileges.getRoles());
            return ok(repo.update(user));
        });
    }


}
