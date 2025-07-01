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

package com.yelstream.topp.standard.resource;

import com.yelstream.topp.standard.resource.content.Item;
import com.yelstream.topp.standard.resource.name.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-01
 */
@Getter
@lombok.Builder(builderClassName="Builder")
@AllArgsConstructor(staticName="of")
final class SimpleResource implements Resource {
    /**
     *
     */
    private final Location location;

    /**
     *
     */
    private final Item item;
}
