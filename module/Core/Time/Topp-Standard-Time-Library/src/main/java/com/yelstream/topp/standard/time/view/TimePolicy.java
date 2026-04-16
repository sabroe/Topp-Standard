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

package com.yelstream.topp.standard.time.view;

import com.yelstream.topp.standard.time.policy.NullPolicy;
import com.yelstream.topp.standard.time.policy.StandardNullPolicy;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TimePolicy {

    private final Time time;
    private final NullPolicy policy;

    public TimePolicy strict() {
        return policy(StandardNullPolicy.Strict.getPolicy());
    }

    public TimePolicy nullable() {
        return policy(StandardNullPolicy.Nullable.getPolicy());
    }

    public TimePolicy nullAware() {
        return policy(StandardNullPolicy.NullAware.getPolicy());
    }

    public TimePolicy policy(NullPolicy policy) {
        return new TimePolicy(time, policy);
    }

    // 🔥 expose SAME helpers — policy-aware

/*
    public TimeMap map() {
        return new TimeMapImpl(time, policy);
    }
*/

/*
    public TimeTo to() {
        return new TimeTo(time, policy);
    }
*/

/*
    public TimeAt at() {
        return new TimeAtImpl(time, policy);
    }
*/

/*
    public TimeCreate create() {
        return new TimeCreateImpl(policy);
    }
*/
}
