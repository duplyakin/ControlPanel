package com.sbt.test.hl.storage;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@NoArgsConstructor

public class AbstractHlEntity implements HLEntity {

    @Getter
    @Setter
    private String hlId= UUID.randomUUID().toString();

}
