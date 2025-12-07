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

package com.yelstream.topp.standard.media.apache.poi.xwpf;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Named standard margins.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-07
 */
@AllArgsConstructor
@SuppressWarnings({"LombokGetterMayBeUsed","java:S115"})
public enum StandardMargin {

    Normal(Margin.of(1440L,1440L,1440L,1440L)),  //{ 2.54 cm, 2.54 cm, 2.54 cm, 2.54 cm }
    Narrow(Margin.of(720L,720L,720L,720L)),  //{ 1.27 cm, 1.27 cm, 1.27 cm, 1.27 cm }
    Moderate(Margin.of(1440L,1440L,1080L,1080L)),  //{ 2.54 cm, 2.54 cm, 1.92 cm, 1.92 cm }
    Wide(Margin.of(1440L,1440L,2880L,2880L));  //{ 2.54 cm, 2.54 cm, 5.08 cm, 5.08 cm }

    @Getter
    private final Margin margin;
}
