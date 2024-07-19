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

package com.yelstream.topp.standard.log.resist.slf4j;

import com.yelstream.topp.standard.lang.thread.Threads;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;

@Slf4j
class SlipTest {



    @Test
    void slip() {

        Slip.of(log.atDebug()).nop().use().log("Logging!");
        Slip.of(log.atDebug()).nop().use().setMessage("Logging!").log();
        Slip.of(log.atDebug()).nop().apply(leb->leb.setMessage("Logging!").log());
        Slip.of(log.atDebug()).nop().apply((c, leb)->leb.setMessage("Logging!").log());

        for (int i=0; i<11; i++) {
            int finalI=i;
            Slip.of(log.atInfo()).id("3f35",b->b.limit(5)).apply((c, leb)->leb.log("(1)Logging done; index {}, suppressed {}, accepted {}, rejected {}.", finalI,c.suppressed(),c.accepted(),c.rejected()));
            Threads.sleep(Duration.ofMillis(100));
        }

        for (int i=0; i<11; i++) {
            int finalI=i;
            Slip.of(log.atInfo()).id("12ab",b->b.limit(5)).apply((c, leb)->leb.log("(2)Logging done; index{}, state {}.", finalI,c.state()));
            Threads.sleep(Duration.ofMillis(100));
        }
    }


}
