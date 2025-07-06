package com.yelstream.topp.standard.resource.util.let;

import com.yelstream.topp.standard.resource.util.let.in.ListInlet;
import com.yelstream.topp.standard.resource.util.let.in.factory.ListInlets;
import com.yelstream.topp.standard.resource.util.let.out.ListOutlet;
import com.yelstream.topp.standard.resource.util.let.out.factory.ListOutlets;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class DualAccessTest {


    /**
     * Simpler list provider.
     * @param <E> Type of element.
     */
    @AllArgsConstructor(staticName="of")
    public static class SimpleListProvider<E> {
        @Getter
        private List<E> x;

        public ListOutlet<E> resources() {
            return ListOutlets.of(()->x.stream(),()->x);
        }

        public void resources(Consumer<ListInlet<E>> resources) {  //TO-DO: Make method content a one-liner!
            resources.accept(ListInlets.byList(l->x=l));
        }
    }

    /**
     * Immutable list provider; the list reference cannot be updated.
     * @param <E> Type of element.
     */
    @AllArgsConstructor(staticName="of")
    public static class ImmutableListProvider<E> {
        @Getter
        private final List<E> x;  //Reference fixed, list itself is modifiable!

        public ListOutlet<E> resources() {
            return ListOutlets.of(x::stream,()->x);
        }

        public void resources(Consumer<ListInlet<E>> resources) {  //TO-DO: Make method content a one-liner!
            resources.accept(ListInlets.byList(l->{ x.clear(); x.addAll(l); }));
        }
    }

    /**
     * Immutable list provider; the list reference cannot be updated, the list content cannot be updated.
     * @param <E> Type of element.
     */
    @AllArgsConstructor(access=AccessLevel.PRIVATE)
    public static class UnmodifiableListProvider<E> {
        @Getter
        private final List<E> x;  //Reference fixed, list itself is unmodifiable!

        public ListOutlet<E> resources() {
            return ListOutlets.of(x::stream,()->x);
        }

        public void resources(Consumer<ListInlet<E>> resources) {  //TO-DO: Make method content a one-liner!
            resources.accept(ListInlets.byList(l->{ x.clear(); x.addAll(l); }));
        }

        public static <E> UnmodifiableListProvider<E> of(List<E> x) {
            return new UnmodifiableListProvider<>(Collections.unmodifiableList(x));
        }
    }

    public static boolean isLikelyImmutable(Collection<?> collection) {
        String className = collection.getClass().getName();
        return className.contains("Unmodifiable") || className.contains("Immutable") ||
               className.contains("java.util.ImmutableCollections");
    }

    /**
     * Tests the static interface of reading from an outlet and writing to an inlet -- using a list.
     * This means that we are essentially happy if this compiles.
     */
    @Test
    void staticInterfaceByList() {
        SimpleListProvider<String> provider=SimpleListProvider.of(List.of());

        List<String> list=provider.resources().get();  //Note: Read as a list!
        provider.resources(in->in.set(list));  //Note: Write as a list!

        Assertions.assertTrue(true);
    }

    /**
     * Tests the static interface of reading from an outlet and writing to an inlet -- using a stream.
     * This means that we are essentially happy if this compiles.
     */
    @Test
    void staticInterfaceByStream() {
        SimpleListProvider<String> provider=SimpleListProvider.of(List.of());

        Stream<String> stream=provider.resources().stream();  //Note: Read as a stream!
        provider.resources(in->in.stream(stream));  //Note: Write as a stream!

        Assertions.assertTrue(true);
    }

    /**
     * Tests basic reading from an outlet and writing to an inlet, using a list in both directions.
     * Verifies that the held list is updated.
     */
    @Test
    void readAndWriteWithList() {
        List<String> x0=List.of("1","2","3");
        List<String> x1=List.of("A","B","C","D","E","F");

        SimpleListProvider<String> provider=SimpleListProvider.of(x0);

        List<String> x=provider.resources().get();  //Note: Read as a list!
        Assertions.assertEquals(x0,x);

        provider.resources(in->in.set(x1));  //Note: Write as a list!

        Assertions.assertEquals(x1,provider.getX());
    }

    /**
     * Tests basic reading from an outlet and writing to an inlet, using a stream in both directions.
     * Verifies that the held list is updated.
     */
    @Test
    void readAndWriteWithStream() {
        List<String> x0=List.of("1","2","3");
        List<String> x1=List.of("A","B","C","D","E","F");

        Assertions.assertTrue(isLikelyImmutable(x0));

        SimpleListProvider<String> provider=SimpleListProvider.of(x0);

        try (Stream<String> stream=provider.resources().stream()) {  //Note: Read as a stream!
            List<String> x=stream.toList();
            Assertions.assertEquals(x0,x);

            provider.resources(in -> in.stream(x1.stream()));  //Note: Write as a stream!
        }

        Assertions.assertEquals(x1,provider.getX());
    }
}
