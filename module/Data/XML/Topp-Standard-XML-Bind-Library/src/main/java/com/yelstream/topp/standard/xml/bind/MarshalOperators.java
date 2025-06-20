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

package com.yelstream.topp.standard.xml.bind;

import com.yelstream.topp.standard.xml.bind.io.MarshalOutput;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Marshaller;
import lombok.experimental.UtilityClass;

/**
 * Utilities addressing instances of {@link MarshalOperator}.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-13
 */
@UtilityClass
public class MarshalOperators {

    public static <T> MarshalOperator<T> of(Marshaller marshaller,
                                            MarshalOutput output) {
        return (JAXBElement<T> element) -> output.marshal(marshaller,element);
    }
}
