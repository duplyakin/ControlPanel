package com.sbt.test.entities;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {
    final String name;
    final String login;
    final String password;
    final List<Role> role;
    final List<Right> rights;
}
