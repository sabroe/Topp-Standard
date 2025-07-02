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

/**
 * Access to resource content.
 * <p>
 *     This is usually, but not necessarily, associated with a classloader.
 * </p>
 * <p>
 *     There are two ways to obtain {@link com.yelstream.topp.standard.resource.Resource} instances:
 * </p>
 * <ol>
 *     <li>
 *         Use the {@link com.yelstream.topp.standard.resource.Resources} helper utility as a fast short-cut:
 *         <br/>
 *         Call {@link com.yelstream.topp.standard.resource.Resources#getResource(java.lang.String)} or
 *         {@link com.yelstream.topp.standard.resource.Resources#getResource(com.yelstream.topp.standard.resource.name.Location)}.
 *     </li>
 *     <li>
 *         Collections of resources are maintained by a resource resolver {@link com.yelstream.topp.standard.resource.resolve.ResourceResolver}.
 *         <br/>
 *         The allocation of resource resolvers is managed by resource providers {@link com.yelstream.topp.standard.resource.provider.ResourceProvider}.
 *         <br/>
 *         A resource provider may be allocated locally or be a result of being introduced by a service-locator.
 *         <br/>
 *         Resource providers in general, a global resource provider specifically, can be access through
 *         the utility {@link com.yelstream.topp.standard.resource.provider.ResourceProviders}.
 *     </li>
 * </ol>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
package com.yelstream.topp.standard.resource;
