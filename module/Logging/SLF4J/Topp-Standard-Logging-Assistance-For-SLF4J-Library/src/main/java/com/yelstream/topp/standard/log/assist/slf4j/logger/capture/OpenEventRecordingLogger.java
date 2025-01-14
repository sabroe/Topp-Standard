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

package com.yelstream.topp.standard.log.assist.slf4j.logger.capture;

import com.yelstream.topp.standard.log.assist.slf4j.event.Levels;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.event.EventRecordingLogger;
import org.slf4j.event.Level;
import org.slf4j.event.SubstituteLoggingEvent;
import org.slf4j.helpers.SubstituteLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Logger capable of logging-event capture by elaborating upon existing SLF4J recording functionality.
 * <p>
 *     Intended for purposes of testing.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-01-11
 */
@SuppressWarnings({"LombokGetterMayBeUsed"})
public class OpenEventRecordingLogger extends EventRecordingLogger {

    @Getter
    @SuppressWarnings({"java:S1948","java:S2387"})
    private final SubstituteLogger logger;

    @Getter
    @SuppressWarnings({"java:S1948","java:S2387"})
    private final Queue<SubstituteLoggingEvent> eventQueue;

    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    private OpenEventRecordingLogger(SubstituteLogger logger,
                                     Queue<SubstituteLoggingEvent> eventQueue,
                                     boolean traceEnabled,
                                     boolean debugEnabled,
                                     boolean infoEnabled,
                                     boolean warnEnabled,
                                     boolean errorEnabled) {
        super(logger,eventQueue);
        this.logger=logger;
        this.eventQueue=eventQueue;

        this.traceEnabled=traceEnabled;
        this.debugEnabled=debugEnabled;
        this.infoEnabled=infoEnabled;
        this.warnEnabled=warnEnabled;
        this.errorEnabled=errorEnabled;
    }

    public List<SubstituteLoggingEvent> getEvents() {
        return new ArrayList<>(eventQueue);
    }

    @Getter
    @Setter
    private boolean traceEnabled;

    @Getter
    @Setter
    private boolean debugEnabled;

    @Getter
    @Setter
    private boolean infoEnabled;

    @Getter
    @Setter
    private boolean warnEnabled;

    @Getter
    @Setter
    private boolean errorEnabled;

    @SuppressWarnings({"FieldCanBeLocal","FieldMayBeFinal"})
    public static class Builder {
        private boolean traceEnabled=true;
        private boolean debugEnabled=true;
        private boolean infoEnabled=true;
        private boolean warnEnabled=true;
        private boolean errorEnabled=true;

        @SuppressWarnings("java:S1117")  //Another s*** rule made up by an imbecile thinking that all are dumber hence destroying the joy of Java
        public Builder levelEnable(Level level) {
            Levels.enable(level,(traceEnabled, debugEnabled, infoEnabled, warnEnabled, errorEnabled)->{
                traceEnabled(traceEnabled);
                debugEnabled(debugEnabled);
                infoEnabled(infoEnabled);
                warnEnabled(warnEnabled);
                errorEnabled(errorEnabled);
            });
            return this;
        }
        OpenEventRecordingLogger build() {
            if (eventQueue==null) {
                eventQueue=new ConcurrentLinkedQueue<>();
            }
            if (logger==null) {
                logger=new SubstituteLogger("Substitute",eventQueue,true);
            }
            return new OpenEventRecordingLogger(logger,eventQueue,traceEnabled,debugEnabled,infoEnabled,warnEnabled,errorEnabled);
        }
    }

    public static OpenEventRecordingLogger of() {
        return builder().build();
    }
}
