/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.data.content;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Carrier of encoded content.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-27
 */
@lombok.Builder(builderClassName="Builder")
@AllArgsConstructor(staticName="of")
public final class Carrier {
    /**
     * Content encoding.
     */
    @Getter
    private final Encoding encoding;

    /**
     * Content.
     */
    @Getter
    private final Content content;

    public static final String ENCODING_FIELD_NAME="encoding";
    public static final String TYPE_FIELD_NAME="type";
    public static final String DATA_FIELD_NAME="data";

/*
    public Map<String,String> asMap() {
        Map<String,String> map=new HashMap<>();
        if (encoding!=null) {
            map.put(ENCODING_FIELD_NAME,encoding.getName());
        }
        if (type!=null) {
            map.put(TYPE_FIELD_NAME,type.getName());
        }
        if (data!=null) {
            map.put(DATA_FIELD_NAME,data.getBytes());
        }
    }
*/
}
