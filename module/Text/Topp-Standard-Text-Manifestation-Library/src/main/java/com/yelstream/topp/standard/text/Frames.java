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

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class Frames {


    public static String toDecoratedMessage(List<String> lines) {  //TODO: Fix this! Method kept for inspiration!
        StringBuilder sb=new StringBuilder();
        sb.append("-----BEGIN IATA RP1745 MESSAGE -----");
        sb.append("\n");
        for (String line: lines) {
            sb.append(line);
            sb.append("\n");
        }
        sb.append("-----END IATA RP1745 MESSAGE -----");
        return sb.toString();
    }


}
