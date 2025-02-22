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

package com.yelstream.topp.standard.text;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Indentation of a single line of text.
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-21
 */
@AllArgsConstructor
@SuppressWarnings("LombokGetterMayBeUsed")
public class Indentation {
    /**
     *
     */
    @Getter
    private final BiFunction<Integer,String,String> operator;  //TO-DO: Change this to a label-generator!

    /**
     *
     */
/*
    @Getter
    private final BiFunction<Integer,String,String> operator;  //TO-DO: Change this to a label-generator!
*/

/*
    @Override
    public String apply(Integer index,
                        String line) {
        return operator.apply(index,line);  //TO-DO: Change this to an operation prepending a new label to a line.
    }
*/

/*
    @Override
    public String apply(Integer integer) {
        return null;
    }
*/
}
