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

package com.yelstream.topp.standard.dom.ls;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Delegate;
import org.w3c.dom.ls.LSInput;

/**
 * Static proxy for instances of {@link LSInput}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-13
 */
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@lombok.Builder(builderClassName="Builder",toBuilder=true)
@SuppressWarnings("LombokGetterMayBeUsed")
@ToString
@EqualsAndHashCode
public class ProxyLSInput implements LSInput {
    /**
     * Wrapped resource.
     */
    @Getter
    @Delegate(types=LSInput.class)
    private final LSInput input;
}
