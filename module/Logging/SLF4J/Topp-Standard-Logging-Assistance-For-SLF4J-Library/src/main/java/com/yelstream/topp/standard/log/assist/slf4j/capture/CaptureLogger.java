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

package com.yelstream.topp.standard.log.assist.slf4j.capture;

import com.yelstream.topp.standard.log.assist.slf4j.event.Levels;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.LegacyAbstractLogger;
import org.slf4j.helpers.MessageFormatter;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

/**
 * Logger capable of logging-event capture in a simple and direct manner.
 * <p>
 *     Intended for purposes of testing.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-01-11
 */
@lombok.Builder(builderClassName="Builder",toBuilder=true)
@AllArgsConstructor
@SuppressWarnings({"LombokGetterMayBeUsed","ClassCanBeRecord"})
public class CaptureLogger extends LegacyAbstractLogger {
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    @AllArgsConstructor
    @Getter
    public static class Entry {
        private final Level level;
        private final Marker marker;
        private final String message;
        private final Object[] arguments;
        private final Throwable throwable;

        public String getFormattedMessage() {
            return MessageFormatter.arrayFormat(message,arguments).getMessage();
        }

        @Override
        public String toString() {
            return getFormattedMessage();
        }
    }

    @Getter
    @lombok.Builder.Default
    private final Queue<Entry> entryQueue=new ConcurrentLinkedQueue<>();

    public List<String> getFormattedMessages() {
        return entryQueue.stream().map(Entry::getFormattedMessage).toList();
    }

    @Getter
    @Setter
    @lombok.Builder.Default
    private boolean traceEnabled=true;

    @Getter
    @Setter
    @lombok.Builder.Default
    private boolean debugEnabled=true;

    @Getter
    @Setter
    @lombok.Builder.Default
    private boolean infoEnabled=true;

    @Getter
    @Setter
    @lombok.Builder.Default
    private boolean warnEnabled=true;

    @Getter
    @Setter
    @lombok.Builder.Default
    private boolean errorEnabled=true;

    @Getter
    @lombok.Builder.Default
    private final transient Supplier<String> callerNameResolver=()->"";

    public static class Builder {
        @SuppressWarnings("java:S1117")  //Another s*** rule made up by an imbecile thinking that all are dumber hence destroying the joy of Java
        public Builder levelEnable(Level level) {
            Levels.enable(level,(traceEnabled,debugEnabled,infoEnabled,warnEnabled,errorEnabled)->{
                traceEnabled(traceEnabled);
                debugEnabled(debugEnabled);
                infoEnabled(infoEnabled);
                warnEnabled(warnEnabled);
                errorEnabled(errorEnabled);
            });
            return this;
        }
    }

    @Override
    protected String getFullyQualifiedCallerName() {
        return callerNameResolver.get();
    }

    @Override
    protected void handleNormalizedLoggingCall(Level level,
                                               Marker marker,
                                               String message,
                                               Object[] arguments,
                                               Throwable throwable) {
        Entry.Builder builder=Entry.builder();
        builder.level(level).marker(marker).message(message).arguments(arguments).throwable(throwable);
        Entry entry=builder.build();
        entryQueue.add(entry);
    }

    public static CaptureLogger of() {
        return builder().build();
    }
}
