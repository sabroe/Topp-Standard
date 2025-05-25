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

package com.yelstream.topp.standard.nil.util;

import com.yelstream.topp.standard.nil.Nillable;
import com.yelstream.topp.standard.nil.action.NillableAction;
import lombok.experimental.UtilityClass;

import java.util.function.Consumer;

/**
 * Utility addressing instances of {@link Nillable}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-05-24
 */
@UtilityClass
public final class Nillables {


    public static <T> void apply(Nillable<T> nillable,
                                 NillableAction<T> action) {
        if (nillable.isNull()) {
            action.onNull();
        } else {
            if (nillable.isNil()) {
                action.onNil(nillable.getValue());
            } else {
                action.onPresent(nillable.getValue());
            }
        }
    }

    public static <T> NillableAction<T> action(Runnable onNull,
                                               Consumer<? super T> onNil,
                                               Consumer<? super T> onPresent) {  //TO-DO: Consider moving this to a new 'NillableActions' helper!
        return new NillableAction<>() {
            @Override
            public void onNull() {
                if (onNull!=null) onNull.run();
            }
            @Override
            public void onNil(T value) {
                if (onNil!=null) onNil.accept(value);
            }
            @Override
            public void onPresent(T value) {
                if (onPresent!=null) onPresent.accept(value);
            }
        };
    }
}
