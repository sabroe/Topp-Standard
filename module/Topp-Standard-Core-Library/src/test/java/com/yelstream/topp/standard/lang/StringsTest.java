/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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


    /**
     * Test of {@link Comparables#equals(Comparable,Comparable)}.
     */
    @SuppressWarnings({"ConstantValue","StringOperationCanBeSimplified"})
    @Test
    void equals() {
        {
            String a=null;
            String b=null;
            Assertions.assertFalse(Strings.equals(a,b));
        }
        {
            String a="x";
            String b=null;
            Assertions.assertFalse(Strings.equals(a,b));
            Assertions.assertFalse(Strings.equals(b,a));
        }
        {
            String a="x";
            String b="y";
            Assertions.assertFalse(Strings.equals(a,b));
            Assertions.assertFalse(Strings.equals(b,a));
        }
        {
            String a="z";
            String b=new String("z");
            Assertions.assertNotSame(a,b);
            Assertions.assertTrue(Strings.equals(a,b));
            Assertions.assertTrue(Strings.equals(b,a));
        }
    }

}
