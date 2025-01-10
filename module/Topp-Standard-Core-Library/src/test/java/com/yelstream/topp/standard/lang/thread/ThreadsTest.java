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

package com.yelstream.topp.standard.lang.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Test of {@link Threads}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-28
 */
@Slf4j
@SuppressWarnings({"java:S2925","BusyWait","ResultOfMethodCallIgnored"})
class ThreadsTest {
    /**
     * Test of {@link Threads#sleep(long)}.
     */
    @RepeatedTest(value=10,name="{displayName}: Repeated {currentRepetition}/{totalRepetitions}")
    @Timeout(value=3,unit=TimeUnit.SECONDS)
    void sleepInMillis() throws InterruptedException {
        {
            long sleepDuration=5L;
            long maxMeasuredSleepDuration=sleepDuration+100L;

            long t0=System.currentTimeMillis();
            boolean b=Threads.sleep(sleepDuration);  //Tested method!
            long t1=System.currentTimeMillis();
            long t=t1-t0;
            Assertions.assertTrue(sleepDuration<=t,String.format("Expected the measured sleep-duration %d to be bigger than the scheduled sleep-duration %d!",t,sleepDuration));
            Assertions.assertTrue(t<=maxMeasuredSleepDuration,String.format("Expected the measured sleep-duration %d to be less than the set maximum measured sleep-duration %d!",t,maxMeasuredSleepDuration));
            Assertions.assertTrue(b,"Expected the sleep to occur with success!");
        }
        {
            long sleepDuration=200L;
            Thread mainThread=Thread.currentThread();

            AtomicBoolean stopThreads=new AtomicBoolean();
            Thread interruptingThread=null;

            try {
                interruptingThread=new Thread(()->{
                    try {
                        while (!stopThreads.get() && mainThread.getState()!=Thread.State.TIMED_WAITING) {
                            Thread.sleep(5L);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    mainThread.interrupt();
                });
                interruptingThread.start();

                long t0=System.currentTimeMillis();
                boolean b=Threads.sleep(sleepDuration);  //Tested method!
                long t1=System.currentTimeMillis();
                long t=t1-t0;
                Assertions.assertTrue(t<sleepDuration,String.format("Expected the measured sleep-duration' %d to be less than the maximum sleep-duration %d!",t,sleepDuration));
                Assertions.assertFalse(b,"Expected the sleep to occur with failure, hence interrupted!");
            } finally {
                Thread.interrupted();  //Clear interruption!
                stopThreads.set(true);
                assert interruptingThread!=null;
                interruptingThread.join(1000L);
            }
        }
    }

    /**
     * Test of {@link Threads#sleep(long,int)}.
     */
    @RepeatedTest(value=10,name="{displayName}: Repeated {currentRepetition}/{totalRepetitions}")
    @Timeout(value=3,unit=TimeUnit.SECONDS)
    void sleepInMillisAndNanos() throws InterruptedException {
        {
            long sleepDuration=5L;
            long maxMeasuredSleepDuration=sleepDuration+100L;

            long t0=System.currentTimeMillis();
            boolean b=Threads.sleep(sleepDuration-1,999_999);  //Tested method!
            long t1=System.currentTimeMillis();
            long t=t1-t0;
            Assertions.assertTrue(sleepDuration<=t,String.format("Expected the measured sleep-duration %d to be bigger than the scheduled sleep-duration %d!",t,sleepDuration));
            Assertions.assertTrue(t<=maxMeasuredSleepDuration,String.format("Expected the measured sleep-duration %d to be less than the set maximum measured sleep-duration %d!",t,maxMeasuredSleepDuration));
            Assertions.assertTrue(b,"Expected the sleep to occur with success!");
        }
        {
            long sleepDuration=200L;
            Thread mainThread=Thread.currentThread();

            AtomicBoolean stopThreads=new AtomicBoolean();
            Thread interruptingThread=null;

            try {
                interruptingThread=new Thread(()->{
                    try {
                        while (!stopThreads.get() && mainThread.getState()!=Thread.State.TIMED_WAITING) {
                            Thread.sleep(5L);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    mainThread.interrupt();
                });
                interruptingThread.start();

                long t0=System.currentTimeMillis();
                boolean b=Threads.sleep(sleepDuration-1,999_999);  //Tested method!
                long t1=System.currentTimeMillis();
                long t=t1-t0;
                Assertions.assertTrue(t<sleepDuration,String.format("Expected the measured sleep-duration' %d to be less than the maximum sleep-duration %d!",t,sleepDuration));
                Assertions.assertFalse(b,"Expected the sleep to occur with failure, hence interrupted!");
            } finally {
                Thread.interrupted();  //Clear interruption!
                stopThreads.set(true);
                assert interruptingThread!=null;
                interruptingThread.join(1000L);
            }
        }
    }

    /**
     * Test of {@link Threads#sleep(Duration)}.
     */
    @RepeatedTest(value=10,name="{displayName}: Repeated {currentRepetition}/{totalRepetitions}")
    @Timeout(value=3,unit=TimeUnit.SECONDS)
    void sleepForDuration() throws InterruptedException {
        {
            long sleepDuration=5L;
            long maxMeasuredSleepDuration=sleepDuration+100L;

            long t0=System.currentTimeMillis();
            Threads.sleep(Duration.ofMillis(sleepDuration));  //Tested method!
            long t1=System.currentTimeMillis();
            long t=t1-t0;
            Assertions.assertTrue(sleepDuration<=t,String.format("Expected the measured sleep-duration %d to be bigger than the scheduled sleep-duration %d!",t,sleepDuration));
            Assertions.assertTrue(t<=maxMeasuredSleepDuration,String.format("Expected the measured sleep-duration %d to be less than the set maximum measured sleep-duration %d!",t,maxMeasuredSleepDuration));
        }
        {
            long sleepDuration=200L;
            Thread mainThread=Thread.currentThread();

            AtomicBoolean stopThreads=new AtomicBoolean();
            Thread interruptingThread=null;

            try {
                interruptingThread=new Thread(()->{
                    try {
                        while (!stopThreads.get() && mainThread.getState()!=Thread.State.TIMED_WAITING) {
                            Thread.sleep(5L);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    mainThread.interrupt();
                });
                interruptingThread.start();

                long t0=System.currentTimeMillis();
                Threads.sleep(Duration.ofMillis(sleepDuration));  //Tested method!
                long t1=System.currentTimeMillis();
                long t=t1-t0;
                Assertions.assertTrue(t<sleepDuration,String.format("Expected the measured sleep-duration' %d to be less than the maximum sleep-duration %d!",t,sleepDuration));
            } finally {
                Thread.interrupted();  //Clear interruption!
                stopThreads.set(true);
                assert interruptingThread!=null;
                interruptingThread.join(1000L);
            }
        }
    }

    /**
     * Test of {@link Threads#join(Thread,long)}.
     */
    @RepeatedTest(value=10,name="{displayName}: Repeated {currentRepetition}/{totalRepetitions}")
    @Timeout(value=3,unit=TimeUnit.SECONDS)
    void joinInMillis() throws InterruptedException {
        {
            long joinDuration=20L;

            AtomicBoolean stopThreads=new AtomicBoolean();
            Thread workingThread = null;

            try {
                workingThread = new Thread(() -> {
                    try {
                        while (!stopThreads.get()) {
                            Thread.sleep(5L);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                workingThread.start();

                long t0 = System.currentTimeMillis();
                boolean b = Threads.join(workingThread, joinDuration);  //Tested method!
                long t1 = System.currentTimeMillis();
                long t = t1 - t0;
                Assertions.assertTrue(t >= joinDuration, String.format("Expected the measured join-duration %d to be greater than the maximum join-duration %d!", t, joinDuration));
                Assertions.assertFalse(b,"Expected the join operation to be a failure, but it is not!");
            } finally {
                stopThreads.set(true);
                assert workingThread!=null;
                workingThread.join(1000L);
            }
        }
        {
            long joinDuration=200L;
            Thread mainThread=Thread.currentThread();

            AtomicBoolean stopThreads=new AtomicBoolean();
            Thread workingThread = null;

            try {
                workingThread = new Thread(() -> {
                    try {
                        while (!stopThreads.get() && mainThread.getState()!=Thread.State.TIMED_WAITING) {
                            Thread.sleep(5L);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                workingThread.start();

                long t0 = System.currentTimeMillis();
                boolean b = Threads.join(workingThread, joinDuration);  //Tested method!
                long t1 = System.currentTimeMillis();
                long t = t1 - t0;
                Assertions.assertTrue(t <= joinDuration, String.format("Expected the measured join-duration %d to be less than the maximum join-duration %d!", t, joinDuration));
                Assertions.assertTrue(b, "Expected the join operation to be a success, but it is not!");
            } finally {
                stopThreads.set(true);
                assert workingThread!=null;
                workingThread.join(1000L);
            }
        }
        {
            long joinDuration=200L;
            Thread mainThread=Thread.currentThread();

            AtomicBoolean stopThreads=new AtomicBoolean();
            Thread workingThread = null;
            Thread interruptingThread=null;

            try {
                workingThread = new Thread(() -> {
                    try {
                        while (!stopThreads.get()) {
                            Thread.sleep(5L);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                workingThread.start();

                interruptingThread=new Thread(()->{
                    try {
                        while (!stopThreads.get() && mainThread.getState()!=Thread.State.TIMED_WAITING) {  //Note: State 'TIMED_WAITING' matching Thread#join(Thread,Duration)!
                            Thread.sleep(5L);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    mainThread.interrupt();
                });
                interruptingThread.start();

                long t0 = System.currentTimeMillis();
                boolean b = Threads.join(workingThread, joinDuration);  //Tested method!
                long t1 = System.currentTimeMillis();
                long t = t1 - t0;
                Assertions.assertTrue(t <= joinDuration, String.format("Expected the measured join-duration %d to be less than the maximum join-duration %d!", t, joinDuration));
                Assertions.assertFalse(b,"Expected the join operation to be a failure, but it is not!");
            } finally {
                Thread.interrupted();  //Clear interruption!
                stopThreads.set(true);
                assert workingThread!=null;
                workingThread.join(1000L);
                assert interruptingThread!=null;
                interruptingThread.join(1000L);
            }
        }
    }


    /**
     * Test of {@link Threads#join(Thread,long,int)}.
     */
    @RepeatedTest(value=10,name="{displayName}: Repeated {currentRepetition}/{totalRepetitions}")
    @Timeout(value=3,unit=TimeUnit.SECONDS)
    void joinInMillisAndNanos() throws InterruptedException {
        {
            long joinDuration=20L;

            AtomicBoolean stopThreads=new AtomicBoolean();
            Thread workingThread = null;

            try {
                workingThread = new Thread(() -> {
                    try {
                        while (!stopThreads.get()) {
                            Thread.sleep(5L);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                workingThread.start();

                long t0 = System.currentTimeMillis();
                boolean b = Threads.join(workingThread, joinDuration-1,999_999);  //Tested method!
                long t1 = System.currentTimeMillis();
                long t = t1 - t0;
                Assertions.assertTrue(t >= joinDuration, String.format("Expected the measured join-duration %d to be greater than the maximum join-duration %d!", t, joinDuration));
                Assertions.assertFalse(b,"Expected the join operation to be a failure, but it is not!");
            } finally {
                stopThreads.set(true);
                assert workingThread!=null;
                workingThread.join(1000L);
            }
        }
        {
            long joinDuration=800L;
            Thread mainThread=Thread.currentThread();

            AtomicBoolean stopThreads=new AtomicBoolean();
            Thread workingThread = null;

            try {
                workingThread = new Thread(() -> {
                    try {
                        while (!stopThreads.get() && mainThread.getState()!=Thread.State.TIMED_WAITING) {
                            Thread.sleep(5L);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                workingThread.start();

                long t0 = System.currentTimeMillis();
                boolean b = Threads.join(workingThread, joinDuration-1,999_999);  //Tested method!
                long t1 = System.currentTimeMillis();
                long t = t1 - t0;
                Assertions.assertTrue(t <= joinDuration, String.format("Expected the measured join-duration %d to be less than the maximum join-duration %d!", t, joinDuration));
                Assertions.assertTrue(b, "Expected the join operation to be a success, but it is not!");
            } finally {
                stopThreads.set(true);
                assert workingThread!=null;
                workingThread.join(1000L);
            }
        }
        {
            long joinDuration=800L;
            Thread mainThread=Thread.currentThread();

            AtomicBoolean stopThreads=new AtomicBoolean();
            Thread workingThread = null;
            Thread interruptingThread=null;

            try {
                workingThread = new Thread(() -> {
                    try {
                        while (!stopThreads.get()) {
                            Thread.sleep(5L);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                workingThread.start();

                interruptingThread=new Thread(()->{
                    try {
                        while (!stopThreads.get() && mainThread.getState()!=Thread.State.TIMED_WAITING) {  //Note: State 'TIMED_WAITING' matching Thread#join(Thread,Duration)!
                            Thread.sleep(5L);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    mainThread.interrupt();
                });
                interruptingThread.start();

                long t0 = System.currentTimeMillis();
                boolean b = Threads.join(workingThread, joinDuration-1,999_999);  //Tested method!
                long t1 = System.currentTimeMillis();
                long t = t1 - t0;
                Assertions.assertTrue(t <= joinDuration, String.format("Expected the measured join-duration %d to be less than the maximum join-duration %d!", t, joinDuration));
                Assertions.assertFalse(b,"Expected the join operation to be a failure, but it is not!");
            } finally {
                Thread.interrupted();  //Clear interruption!
                stopThreads.set(true);
                assert workingThread!=null;
                workingThread.join(1000L);
                assert interruptingThread!=null;
                interruptingThread.join(1000L);
            }
        }
    }

    /**
     * Test of {@link Threads#join(Thread, Duration)}.
     */
    @RepeatedTest(value=10,name="{displayName}: Repeated {currentRepetition}/{totalRepetitions}")
    @Timeout(value=3,unit=TimeUnit.SECONDS)
    void joinForDuration() throws InterruptedException {
        {
            long joinDuration=20L;

            AtomicBoolean stopThreads=new AtomicBoolean();
            Thread workingThread = null;

            try {
                workingThread = new Thread(() -> {
                    try {
                        while (!stopThreads.get()) {
                            Thread.sleep(5L);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                workingThread.start();

                long t0 = System.currentTimeMillis();
                boolean b = Threads.join(workingThread, Duration.ofMillis(joinDuration));  //Tested method!
                long t1 = System.currentTimeMillis();
                long t = t1 - t0;
                Assertions.assertTrue(t >= joinDuration, String.format("Expected the measured join-duration %d to be greater than the maximum join-duration %d!", t, joinDuration));
                Assertions.assertFalse(b,"Expected the join operation to be a failure, but it is not!");
            } finally {
                stopThreads.set(true);
                assert workingThread!=null;
                workingThread.join(1000L);
            }
        }
        {
            long joinDuration=800L;
            Thread mainThread=Thread.currentThread();

            AtomicBoolean stopThreads=new AtomicBoolean();
            Thread workingThread = null;

            try {
                workingThread = new Thread(() -> {
                    try {
                        while (!stopThreads.get() && mainThread.getState()!=Thread.State.TIMED_WAITING) {  //Note: State 'TIMED_WAITING' matching Thread#join(Thread,Duration)!
                            Thread.sleep(5L);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                workingThread.start();

                long t0 = System.currentTimeMillis();
                boolean b = Threads.join(workingThread, Duration.ofMillis(joinDuration));  //Tested method!
                long t1 = System.currentTimeMillis();
                long t = t1 - t0;
                Assertions.assertTrue(t <= joinDuration, String.format("Expected the measured join-duration %d to be less than the maximum join-duration %d!", t, joinDuration));
                Assertions.assertTrue(b, "Expected the join operation to be a success, but it is not!");
            } finally {
                stopThreads.set(true);
                assert workingThread!=null;
                workingThread.join(1000L);
            }
        }
        {
            long joinDuration=800L;
            Thread mainThread=Thread.currentThread();

            AtomicBoolean stopThreads=new AtomicBoolean();
            Thread workingThread = null;
            Thread interruptingThread=null;

            try {
                workingThread = new Thread(() -> {
                    try {
                        while (!stopThreads.get()) {
                            Thread.sleep(5L);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                workingThread.start();

                interruptingThread=new Thread(()->{
                    try {
                        while (!stopThreads.get() && mainThread.getState()!=Thread.State.TIMED_WAITING) {  //Note: State 'TIMED_WAITING' matching Thread#join(Thread,Duration)!
                            Thread.sleep(5L);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    mainThread.interrupt();
                });
                interruptingThread.start();

                long t0 = System.currentTimeMillis();
                boolean b = Threads.join(workingThread, Duration.ofMillis(joinDuration));  //Tested method!
                long t1 = System.currentTimeMillis();
                long t = t1 - t0;
                Assertions.assertTrue(t <= joinDuration, String.format("Expected the measured join-duration %d to be less than the maximum join-duration %d!", t, joinDuration));
                Assertions.assertFalse(b,"Expected the join operation to be a failure, but it is not!");
            } finally {
                Thread.interrupted();  //Clear interruption!
                stopThreads.set(true);
                assert workingThread!=null;
                workingThread.join(1000L);
                assert interruptingThread!=null;
                interruptingThread.join(1000L);
            }
        }
    }

    /**
     * Test of {@link Threads#join(Thread)}.
     */
    @RepeatedTest(value=10,name="{displayName}: Repeated {currentRepetition}/{totalRepetitions}")
    @Timeout(value=3,unit=TimeUnit.SECONDS)
    void joinIndefinetly() throws InterruptedException {
        {
            long joinDuration=800L;
            Thread mainThread=Thread.currentThread();

            AtomicBoolean stopThreads=new AtomicBoolean();
            Thread workingThread = null;

            try {
                workingThread = new Thread(() -> {
                    try {
                        while (!stopThreads.get() && mainThread.getState()!=Thread.State.WAITING) {  //Note: State 'WAITING' matching Thread#join(Thread) with no additional arguments!
                            Thread.sleep(5L);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                workingThread.start();

                long t0 = System.currentTimeMillis();
                boolean b = Threads.join(workingThread);  //Tested method! Note that this brings the calling thread into state 'WAITING', not 'TIMED_WAITING'!
                long t1 = System.currentTimeMillis();
                long t = t1 - t0;
                Assertions.assertTrue(t <= joinDuration, String.format("Expected the measured join-duration %d to be less than the maximum join-duration %d!", t, joinDuration));
                Assertions.assertTrue(b, "Expected the join operation to be a success, but it is not!");
            } finally {
                stopThreads.set(true);
                assert workingThread!=null;
                workingThread.join(1000L);
            }
        }
        {
            long joinDuration=800L;
            Thread mainThread=Thread.currentThread();

            AtomicBoolean stopThreads=new AtomicBoolean();
            Thread workingThread = null;
            Thread interruptingThread=null;

            try {
                workingThread = new Thread(() -> {
                    try {
                        while (!stopThreads.get()) {
                            Thread.sleep(5L);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                workingThread.start();

                interruptingThread=new Thread(()->{
                    try {
                        while (!stopThreads.get() && mainThread.getState()!=Thread.State.WAITING) {  //Note: State 'WAITING' matching Thread#join(Thread) with no additional arguments!
                            Thread.sleep(5L);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    mainThread.interrupt();
                });
                interruptingThread.start();

                long t0 = System.currentTimeMillis();
                boolean b = Threads.join(workingThread);  //Tested method! Note that this brings the calling thread into state 'WAITING', not 'TIMED_WAITING'!
                long t1 = System.currentTimeMillis();
                long t = t1 - t0;
                Assertions.assertTrue(t <= joinDuration, String.format("Expected the measured join-duration %d to be less than the maximum join-duration %d!", t, joinDuration));
                Assertions.assertFalse(b,"Expected the join operation to be a failure, but it is not!");
            } finally {
                Thread.interrupted();  //Clear interruption!
                stopThreads.set(true);
                assert workingThread!=null;
                workingThread.join(1000L);
                assert interruptingThread!=null;
                interruptingThread.join(1000L);
            }
        }
    }
}
