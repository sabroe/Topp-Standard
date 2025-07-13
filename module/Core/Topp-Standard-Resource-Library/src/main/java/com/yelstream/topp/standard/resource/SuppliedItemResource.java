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

import com.yelstream.topp.standard.resource.item.Item;
import com.yelstream.topp.standard.system.load.name.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

/**
 * Resource with on-demand creation of the contained item.
 * <p>
 *     This allows for implementations of resource providers to send out locations as resources and
 *     without creating expensive items unless actually asked for.
 *     <br/>
 *     Not only that, but the internal item-supplier may be the same for all resource objects.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-02
 */
@lombok.Builder(builderClassName="Builder")
@AllArgsConstructor(staticName="of")
final class SuppliedItemResource implements Resource {
    /**
     * Location.
     */
    @Getter
    private final Location location;

    /**
     * Item-supplier.
     * <p>
     *     Note that this construction allows multiple resources from the same provider to use the same item-supplier.
     * </p>
     */
    private final Function<Location,Item> itemSupplier;

    /**
     * Item.
     */
    @Getter(lazy=true)
    private final Item item=itemSupplier.apply(location);
}
