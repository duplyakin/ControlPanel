package com.sbt.test.dto;

import lombok.Builder;
import lombok.Data;

/**
 * made because unmarshalled request body is expected to contain only 1 object.
 */
@Data
@Builder
public class OldAndNewPass {
    private final String oldPassword;
    private final String newPassword;
}
