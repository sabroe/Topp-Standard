package com.yelstream.topp.standard.operation.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Predicate;

@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class IdentityFacet<T> {
    public final ObjectInstance<T> instance;

    public int hash() {
        return instance.identityHash();
    }

    public Predicate<T> same() {  //TO-DO: Considersignature!
        return this::isSame;
    }

    public boolean isSame(T value) {  //TO-DO: Considersignature!
        return value == instance.getValue();
    }

    public void ifSame(T value,
                       Consumer<T> consumer) {  //TO-DO: Considersignature!
        if (isSame(value)) {
            consumer.accept(value);
        }
    }
}
