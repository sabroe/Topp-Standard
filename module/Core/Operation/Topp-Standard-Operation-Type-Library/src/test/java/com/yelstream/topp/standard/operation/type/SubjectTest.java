package com.yelstream.topp.standard.operation.type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SubjectTest {

    @Test
    void x1() {
        Subject<Integer> s1=
            Subject.of("xxx")
                .map()
                .as(String.class)
                .map()
                .to(String::length);
        Assertions.assertNotNull(s1);
        Assertions.assertEquals(Subject.of(3),s1);

        Subject<String> s2=
            Subject.of("yyy")
                .map()
                .as(String.class)
                .nulls()
                .or("fallback");
        Assertions.assertNotNull(s2);
        Assertions.assertEquals(Subject.of("yyy"),s2);

        Subject<Integer> s3=
            Subject.of("zzz")
                .map()
                .as(String.class)
                .map()
                .to(String::length)
                .nulls()
                .or(0);
        Assertions.assertNotNull(s3);
        Assertions.assertEquals(Subject.of(3),s3);
    }

}
