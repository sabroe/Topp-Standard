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
}
