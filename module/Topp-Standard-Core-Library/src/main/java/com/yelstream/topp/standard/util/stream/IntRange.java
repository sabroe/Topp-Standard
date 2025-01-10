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

package com.yelstream.topp.standard.util.stream;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.IntSummaryStatistics;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/**
 * Range of integers.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2023-12-17
 */
@ToString
@EqualsAndHashCode
@Getter
@AllArgsConstructor(staticName="of")
public class IntRange {
    /**
     * First index.
     * This is inclusive.
     */
    private final int beginIndex;

    /**
     * Last index.
     * This is exclusive.
     */
    private final int endIndex;

    /**
     * Indices, if an index is contained in this range.
     * @param index Index.
     * @return Indicates, if the index is contained.
     */
    public boolean contains(int index) {
        return beginIndex<=index && index<endIndex;
    }

    /**
     * Expands this range to include an index.
     * @param index Index.
     * @return Expanded range.
     */
    public IntRange expand(int index) {
        return of(Math.min(beginIndex,index),Math.max(endIndex,index+1));
    }

    /**
     * Narrows this range to contain matching indexes only.
     * @param indexMatch Predicate matching indexes.
     * @return Narrowed range.
     */
    public IntRange narrow(IntPredicate indexMatch) {
        return of(stream().filter(indexMatch));
    }

    //overlap? join? split?

    /**
     * Creates the smallest range containing all index in a given stream.
     * @param indexStream Stream of indexes.
     * @return Containing range.
     */
    public static IntRange of(IntStream indexStream) {
        return of(indexStream.summaryStatistics());
    }

    /**
     * Creates the smallest range containing all indexes indicates as a minimum and a maximum.
     * @param summaryStatistics Stream of indexes.
     * @return Containing range.
     */
    public static IntRange of(IntSummaryStatistics summaryStatistics) {
        return of(summaryStatistics.getMin(),summaryStatistics.getMax()+1);
    }

    /**
     * Creates a stream of the values of this range.
     * @return Created stream.
     */
    public IntStream stream() {
        return IntStream.range(beginIndex,endIndex);
    }
}
