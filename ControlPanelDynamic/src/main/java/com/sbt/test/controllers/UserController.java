package com.sbt.test.controllers;

import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users")
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
    public ResponseEntity<User> setRoles(@RequestParam("username") String username,
                                         @RequestParam("roles") Set<Role> roles) {
        return process(() -> {
                    Optional<User> userOpt = repo.getByUsername(username);
                    if (!userOpt.isPresent()) {
                        return notFound();
                    }
                    User user = userOpt.get();
                    user.setRoles(roles);
                    repo.update(user);
                    return ok();
                }
        );
    }

    @PostMapping("/setAuthorities")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> setPrivileges(@RequestParam("username") String username,
                                              @RequestParam("privileges") Set<Privilege> privileges) {
        return process(() -> {
            Optional<User> userOpt = repo.getByUsername(username);
            if (!userOpt.isPresent()) {
                return notFound();
            }
            User user = userOpt.get();
            user.setPrivileges(privileges);
            repo.update(user);
            return ok();
        });
    }


}
