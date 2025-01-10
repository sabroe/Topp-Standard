/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;

/**
 * Test of {@link Lists}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-27
 */
@SuppressWarnings({"ConstantValue","UnnecessaryLocalVariable"})
@Slf4j
class ListsTest {
    /**
     * Test of {@link Lists#of(Iterable)}
     */
    @Test
    void ofIterable() {
        {  //Test null list
            Iterable<String> i=null;
            List<String> l=Lists.of(i);
            Assertions.assertNull(l);
        }
        {
            Set<String> x=Set.of("a","b","c");
            Iterable<String> i=x;
            List<String> l=Lists.of(i);
            Assertions.assertNotNull(l);
            Assertions.assertNotSame(i,l);
            Assertions.assertEquals(3,l.size());
            Assertions.assertTrue(List.of("a","b","c").containsAll(l));
        }
        {  //Test null elements
            Set<String> x=new java.util.HashSet<>(Set.of("a", "b", "c"));
            x.add(null);
            Iterable<String> i=x;
            List<String> l=Lists.of(i);
            Assertions.assertNotNull(l);
            Assertions.assertNotSame(i,l);
            Assertions.assertEquals(4,l.size());
            List<String> l2=new java.util.ArrayList<>(List.of("a","b","c"));
            l2.add(null);
            Assertions.assertEquals(l2.size(),l.size());
            Assertions.assertTrue(l2.containsAll(l));
        }
        {  //Test mutability
            Set<String> x=Set.of("a","b","c");
            Iterable<String> i=x;
            List<String> l=Lists.of(i);
            l.add("d");
            Assertions.assertNotNull(l);
            Assertions.assertNotSame(i,l);
            Assertions.assertEquals(4,l.size());
            List<String> l2=List.of("a","b","c","d");
            Assertions.assertEquals(l2.size(),l.size());
            Assertions.assertTrue(l2.containsAll(l));
        }
    }

    /**
     * Test of {@link Lists#of(Iterator)}
     */
    @Test
    void ofIterator() {
        {  //Test null list
            Iterator<String> i=null;
            List<String> l=Lists.of(i);
            Assertions.assertNull(l);
        }
        {
            Set<String> x=Set.of("a","b","c");
            Iterator<String> i=x.iterator();
            List<String> l=Lists.of(i);
            Assertions.assertNotNull(l);
            Assertions.assertNotSame(i,l);
            Assertions.assertEquals(3,l.size());
            Assertions.assertTrue(List.of("a","b","c").containsAll(l));
        }
        {  //Test null elements
            Set<String> x=new java.util.HashSet<>(Set.of("a", "b", "c"));
            x.add(null);
            Iterator<String> i=x.iterator();
            List<String> l=Lists.of(i);
            Assertions.assertNotNull(l);
            Assertions.assertNotSame(i,l);
            Assertions.assertEquals(4,l.size());
            List<String> l2=new java.util.ArrayList<>(List.of("a","b","c"));
            l2.add(null);
            Assertions.assertEquals(l2.size(),l.size());
            Assertions.assertTrue(l2.containsAll(l));
        }
        {  //Test mutability
            Set<String> x=Set.of("a","b","c");
            Iterator<String> i=x.iterator();
            List<String> l=Lists.of(i);
            l.add("d");
            Assertions.assertNotNull(l);
            Assertions.assertNotSame(i,l);
            Assertions.assertEquals(4,l.size());
            List<String> l2=List.of("a","b","c","d");
            Assertions.assertEquals(l2.size(),l.size());
            Assertions.assertTrue(l2.containsAll(l));
        }
    }

    /**
     * Test of {@link Lists#of(Spliterator)}
     */
    @Test
    void ofSpliterator() {
        {  //Test null list
            Spliterator<String> i=null;
            List<String> l=Lists.of(i);
            Assertions.assertNull(l);
        }
        {
            Set<String> x=Set.of("a","b","c");
            Spliterator<String> i=x.spliterator();
            List<String> l=Lists.of(i);
            Assertions.assertNotNull(l);
            Assertions.assertNotSame(i,l);
            Assertions.assertEquals(3,l.size());
            Assertions.assertTrue(List.of("a","b","c").containsAll(l));
        }
        {  //Test null elements
            Set<String> x=new java.util.HashSet<>(Set.of("a", "b", "c"));
            x.add(null);
            Spliterator<String> i=x.spliterator();
            List<String> l=Lists.of(i);
            Assertions.assertNotNull(l);
            Assertions.assertNotSame(i,l);
            Assertions.assertEquals(4,l.size());
            List<String> l2=new java.util.ArrayList<>(List.of("a","b","c"));
            l2.add(null);
            Assertions.assertEquals(l2.size(),l.size());
            Assertions.assertTrue(l2.containsAll(l));
        }
        {  //Test mutability
            Set<String> x=Set.of("a","b","c");
            Spliterator<String> i=x.spliterator();
            List<String> l=Lists.of(i);
            l.add("d");
            Assertions.assertNotNull(l);
            Assertions.assertNotSame(i,l);
            Assertions.assertEquals(4,l.size());
            List<String> l2=List.of("a","b","c","d");
            Assertions.assertEquals(l2.size(),l.size());
            Assertions.assertTrue(l2.containsAll(l));
        }
    }

    /**
     * Test of {@link Lists#of(Object[])}
     */
    @Test
    void ofElipsis() {
        {  //Test null list
            List<String> l=Lists.of((String[])null);
            Assertions.assertNull(l);
        }
        {
            List<String> l=Lists.of("a","b","c");
            Assertions.assertNotNull(l);
            Assertions.assertEquals(3,l.size());
            Assertions.assertTrue(List.of("a","b","c").containsAll(l));
        }
        {  //Test null elements
            List<String> l=Lists.of("a","b","c",null);
            Assertions.assertNotNull(l);
            Assertions.assertEquals(4,l.size());
            List<String> l2=new java.util.ArrayList<>(List.of("a","b","c"));
            l2.add(null);
            Assertions.assertEquals(l2.size(),l.size());
            Assertions.assertTrue(l2.containsAll(l));
        }
        {  //Test mutability
            List<String> l=Lists.of("a","b","c");
            l.add("d");
            Assertions.assertNotNull(l);
            Assertions.assertEquals(4,l.size());
            List<String> l2=List.of("a","b","c","d");
            Assertions.assertEquals(l2.size(),l.size());
            Assertions.assertTrue(l2.containsAll(l));
        }
    }

    /**
     * Test of {@link Lists#ofArray(Object[])}
     */
    @Test
    void ofArray() {
        {  //Test null list
            List<String> l=Lists.ofArray(null);
            Assertions.assertNull(l);
        }
        {
            String[] x={"a","b","c"};
            List<String> l=Lists.ofArray(x);
            Assertions.assertNotNull(l);
            Assertions.assertEquals(3,l.size());
            Assertions.assertTrue(List.of("a","b","c").containsAll(l));
        }
        {  //Test null elements
            String[] x={"a","b","c",null};
            List<String> l=Lists.ofArray(x);
            Assertions.assertNotNull(l);
            Assertions.assertEquals(4,l.size());
            List<String> l2=new java.util.ArrayList<>(List.of("a","b","c"));
            l2.add(null);
            Assertions.assertEquals(l2.size(),l.size());
            Assertions.assertTrue(l2.containsAll(l));
        }
        {  //Test mutability
            String[] x={"a","b","c"};
            List<String> l=Lists.ofArray(x);
            l.add("d");
            Assertions.assertNotNull(l);
            Assertions.assertEquals(4,l.size());
            List<String> l2=List.of("a","b","c","d");
            Assertions.assertEquals(l2.size(),l.size());
            Assertions.assertTrue(l2.containsAll(l));
        }
    }

    /**
     * Test of {@link Lists#isEmpty(List)}.
     */
    @Test
    void isEmpty() {
        Assertions.assertTrue(Lists.isEmpty(null));
        Assertions.assertTrue(Lists.isEmpty(List.of()));
        Assertions.assertFalse(Lists.isEmpty(List.of("X","Y")));
    }

    /**
     * Test of {@link Lists#isNonEmpty(List)}.
     */
    @Test
    void isNonEmpty() {
        Assertions.assertFalse(Lists.isNonEmpty(null));
        Assertions.assertFalse(Lists.isNonEmpty(List.of()));
        Assertions.assertTrue(Lists.isNonEmpty(List.of("X","Y")));
    }

    /**
     * Test of {@link Lists#getFirst(List)}.
     */
    @Test
    void getFirst() {
        Assertions.assertNull(Lists.getFirst(null));
        Assertions.assertNull(Lists.getFirst(List.of()));
        Assertions.assertEquals("X",Lists.getFirst(List.of("X","Y")));
    }

    /**
     * Test of {@link Lists#getLast(List)}.
     */
    @Test
    void getLast() {
        Assertions.assertNull(Lists.getLast(null));
        Assertions.assertNull(Lists.getLast(List.of()));
        Assertions.assertEquals("Y",Lists.getLast(List.of("X","Y")));
    }

    /**
     * Test of {@link Lists#removeFirst(List)}.
     */
    @Test
    void removeFirst() {
        Assertions.assertNull(Lists.removeFirst(null));
        Assertions.assertNull(Lists.removeFirst(List.of()));

        List<String> l=Lists.of("X","Y","Z");
        Assertions.assertEquals("X",Lists.removeFirst(l));
        Assertions.assertEquals(2,l.size());
        Assertions.assertEquals(Lists.of("Y","Z"),l);
    }

    /**
     * Test of {@link Lists#removeLast(List)}.
     */
    @Test
    void removeLast() {
        Assertions.assertNull(Lists.removeLast(null));
        Assertions.assertNull(Lists.removeLast(List.of()));

        List<String> l=Lists.of("X","Y","Z");
        Assertions.assertEquals("Z",Lists.removeLast(l));
        Assertions.assertEquals(2,l.size());
        Assertions.assertEquals(Lists.of("X","Y"),l);
    }
}
