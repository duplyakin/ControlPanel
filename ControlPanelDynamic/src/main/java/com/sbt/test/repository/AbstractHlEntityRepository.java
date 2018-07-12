package com.sbt.test.repository;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public abstract class AbstractHlEntityRepository<T> implements HLEntityRepository<T> {
}
