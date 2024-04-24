package com.yelstream.topp.standard.lang;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Test of {@link Strings}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-24
 */
@Slf4j
class StringsTest {
    /**
     * Test of {@link Strings#trim(String)}.
     */
    @SuppressWarnings("ConstantValue")
    @Test
    void trim() {
        Assertions.assertNull(Strings.trim(null));
        Assertions.assertEquals("abc",Strings.trim("abc"));
        Assertions.assertEquals("abc",Strings.trim(" abc "));
    }

    /**
     * Test of {@link Strings#isEmpty(String)}.
     */
    @SuppressWarnings("ConstantValue")
    @Test
    void isEmpty() {
        Assertions.assertTrue(Strings.isEmpty(null));
        Assertions.assertTrue(Strings.isEmpty(""));
        Assertions.assertFalse(Strings.isEmpty(" "));
        Assertions.assertFalse(Strings.isEmpty("abc"));
    }

    /**
     * Test of {@link Strings#isNonEmpty(String)}.
     */
    @SuppressWarnings("ConstantValue")
    @Test
    void isNonEmpty() {
        Assertions.assertFalse(Strings.isNonEmpty(null));
        Assertions.assertFalse(Strings.isNonEmpty(""));
        Assertions.assertTrue(Strings.isNonEmpty(" "));
        Assertions.assertTrue(Strings.isNonEmpty("abc"));
    }

    /**
     * Test of {@link Strings#nonNullOrElse(String,String)}.
     */
    @SuppressWarnings({"ConstantValue","ObviousNullCheck"})
    @Test
    void nonNullOrElseWithValue() {
        Assertions.assertEquals("xyz",Strings.nonNullOrElse(null,"xyz"));
        Assertions.assertNull(Strings.nonNullOrElse(null,(String)null));
        Assertions.assertEquals("",Strings.nonNullOrElse("","xyz"));
        Assertions.assertEquals(" ",Strings.nonNullOrElse(" ","xyz"));
        Assertions.assertEquals("abc",Strings.nonNullOrElse("abc","xyz"));
    }

    /**
     * Test of {@link Strings#nonNullOrElse(String,Supplier)}.
     */
    @Test
    void nonNullOrElseWithSupplier() {
        Assertions.assertEquals("xyz",Strings.nonNullOrElse(null,()->"xyz"));
        Assertions.assertNull(Strings.nonNullOrElse(null,()->null));
        Assertions.assertEquals("",Strings.nonNullOrElse("",()->"xyz"));
        Assertions.assertEquals(" ",Strings.nonNullOrElse(" ",()->"xyz"));
        Assertions.assertEquals("abc",Strings.nonNullOrElse("abc",()->"xyz"));
    }


    /**
     * Test of {@link Strings#nonEmptyOrElse(String,String)}.
     */
    @Test
    void nonEmptyOrElseWithValue() {
        Assertions.assertEquals("xyz",Strings.nonEmptyOrElse(null,"xyz"));
        Assertions.assertNull(Strings.nonEmptyOrElse(null,(String)null));
        Assertions.assertEquals("xyz",Strings.nonEmptyOrElse("","xyz"));
        Assertions.assertEquals(" ",Strings.nonEmptyOrElse(" ","xyz"));
        Assertions.assertEquals("abc",Strings.nonEmptyOrElse("abc","xyz"));
    }

    /**
     * Test of {@link Strings#nonEmptyOrElse(String,Supplier)}.
     */
    @Test
    void nonEmptyOrElseWithSupplier() {
        Assertions.assertEquals("xyz",Strings.nonEmptyOrElse(null,()->"xyz"));
        Assertions.assertNull(Strings.nonEmptyOrElse(null,()->null));
        Assertions.assertEquals("xyz",Strings.nonEmptyOrElse("",()->"xyz"));
        Assertions.assertEquals(" ",Strings.nonEmptyOrElse(" ",()->"xyz"));
        Assertions.assertEquals("abc",Strings.nonEmptyOrElse("abc",()->"xyz"));
    }

    /**
     * Test of {@link Strings#distinct(List)}.
     */
    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    @Test
    void distinct() {
        Assertions.assertNull(Strings.distinct(null));
        Assertions.assertEquals(List.of(),Strings.distinct(Arrays.asList(null,null,"",null," ")));
        Assertions.assertEquals(Arrays.asList("abc"),Strings.distinct(Arrays.asList(null,null,"abc",null)));
        Assertions.assertEquals(Arrays.asList("abc","xyz"),Strings.distinct(Arrays.asList("abc"," ","xyz")));
    }
}
