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

import com.yelstream.topp.standard.annotation.mark.lifecycle.Provisional;

/**
 * Topp Standard JSON Library addressing selected elements of JSON data handling.
 *
 * @author Morten Sabroe Mortensen
 * @since 2026-03-02
 */
@Provisional
module com.yelstream.topp.standard.data.json {
    requires static lombok;
    requires org.slf4j;
    requires json.path;
    requires com.yelstream.topp.standard.annotation.intention;
    exports com.yelstream.topp.standard.data.json.path;
}
