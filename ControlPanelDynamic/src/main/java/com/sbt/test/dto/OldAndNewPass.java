package com.sbt.test.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OldAndNewPass {
    private final String oldPassword;
    private final String newPassword;
}
