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
import lombok.experimental.UtilityClass;

/**
 * Utility addressing instances of {@link Nillable}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-05-24
 */
@UtilityClass
public final class Nillables {


/*
    public void apply(NillableAction<T> action) {
        if (isNull()) {
            action.onNull();
        } else if (isNil()) {
            action.onNil();
        } else {
            action.onPresent(value);
        }
    }
*/

/*
    public static <T> NillableAction<T> action(Runnable onNull, Runnable onNil, Consumer<? super T> onPresent) {
        return new NillableAction<>() {
            @Override public void onNull() { if (onNull != null) onNull.run(); }
            @Override public void onNil() { if (onNil != null) onNil.run(); }
            @Override public void onPresent(T value) { if (onPresent != null) onPresent.accept(value); }
        };
    }
*/
}
