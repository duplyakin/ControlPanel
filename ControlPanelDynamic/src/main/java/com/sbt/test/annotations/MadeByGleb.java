package com.sbt.test.annotations;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Qualifier to resolve possible bean collisions
 * @see com.sbt.test.userLoader.UserDetailsLoader
 */
@Qualifier
public @interface MadeByGleb {
}
