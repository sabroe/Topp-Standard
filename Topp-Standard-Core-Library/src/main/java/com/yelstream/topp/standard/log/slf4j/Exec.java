package com.yelstream.topp.standard.log.slf4j;

import lombok.AllArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

@AllArgsConstructor(staticName="of")
public class Exec<C,R> {
    private final Supplier<C> contextSupplier;
    private final R result;

    public R exec() {
        return result;
    }

    public void exec(BiConsumer<C,R> consumer) {
        C context=contextSupplier.get();
        consumer.accept(context,result);
    }

    public void exec(Consumer<R> consumer) {
        consumer.accept(result);
    }
}
