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

package com.yelstream.topp.standard.log.resist.slf4j;

import com.yelstream.topp.standard.lang.thread.Threads;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.stream.IntStream;

@Slf4j
class SlipTest {
    @Test
    void slip() {
        Slip.of(log.atDebug()).nop().use().log("Logging!");
        Slip.of(log.atDebug()).nop().use().setMessage("Logging!").log();
        Slip.of(log.atDebug()).nop().apply(leb->leb.setMessage("Logging!").log());
        Slip.of(log.atDebug()).nop().apply((c,leb)->leb.setMessage("Logging!").log());

        Slip.of(log.atInfo()).nop().use().log("Logging!");
        Slip.of(log.atInfo()).nop().use().setMessage("Logging!").log();
        Slip.of(log.atInfo()).nop().apply(leb->leb.setMessage("Logging!").log());
        Slip.of(log.atInfo()).nop().apply((c,leb)->leb.setMessage("Logging!").log());

        IntStream.range(0,11).forEach(index->{
            Slip.of(log.atInfo()).id("3f35",b->b.limit(5)).apply((c,leb)->leb.log("Logging; index {}, suppressed {}, accepted {}, rejected {}.",index,c.suppressed(),c.accepted(),c.rejected()));
            Threads.sleep(Duration.ofMillis(100));
        });

        IntStream.range(0,21).forEach(index->{
            Slip.of(log.atInfo()).id("12ab",b->b.limit(5)).apply((c,leb)->leb.log("Logging; index {}, state {}.",index,c.state()));
            Threads.sleep(Duration.ofMillis(100));
        });
    }

    /* TO-DO: Grab hold of logged output!
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.ArrayList;
import java.util.List;

public class TestAppender extends AppenderBase<ILoggingEvent> {
    private final List<ILoggingEvent> events = new ArrayList<>();

    @Override
    protected void append(ILoggingEvent eventObject) {
        events.add(eventObject);
    }

    public List<ILoggingEvent> getEvents() {
        return events;
    }

    public void clear() {
        events.clear();
    }
}

import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class SlipTest {
    private Logger log;
    private TestAppender testAppender;

    @BeforeEach
    void setUp() {
        log = (Logger) LoggerFactory.getLogger(SlipTest.class);
        testAppender = new TestAppender();
        testAppender.start();
        log.addAppender(testAppender);
    }

    @Test
    void slip() {
        Slip.of(log.atDebug()).nop().use().log("Logging!");
        Slip.of(log.atDebug()).nop().use().setMessage("Logging!").log();
        Slip.of(log.atDebug()).nop().apply(leb -> leb.setMessage("Logging!").log());
        Slip.of(log.atDebug()).nop().apply((c, leb) -> leb.setMessage("Logging!").log());

        IntStream.range(0, 11).forEach(index -> {
            Slip.of(log.atInfo()).id("3f35", b -> b.limit(5)).apply((c, leb) -> leb.log("Logging; index {}, suppressed {}, accepted {}, rejected {}.", index, c.suppressed(), c.accepted(), c.rejected()));
            Threads.sleep(Duration.ofMillis(100));
        });

        IntStream.range(0, 11).forEach(index -> {
            Slip.of(log.atInfo()).id("12ab", b -> b.limit(5)).apply((c, leb) -> leb.log("Logging; index{}, state {}.", index, c.state()));
            Threads.sleep(Duration.ofMillis(100));
        });

        List<ILoggingEvent> logs = testAppender.getEvents();
        assertThat(logs).anyMatch(event -> event.getFormattedMessage().equals("Logging!"));
        assertThat(logs).anyMatch(event -> event.getFormattedMessage().contains("Logging; index 0, suppressed 0, accepted 1, rejected 0."));
    }
}
     */
}
