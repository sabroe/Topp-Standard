package com.yelstream.topp.aurora.util.stream;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.LongSummaryStatistics;
import java.util.function.LongPredicate;
import java.util.stream.LongStream;

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
public class LongRange {
    /**
     * First index.
     * This is inclusive.
     */
    private final long beginIndex;

    /**
     * Last index.
     * This is exclusive.
     */
    private final long endIndex;

    /**
     * Indices, if an index is contained in this range.
     * @param index Index.
     * @return Indicates, if the index is contained.
     */
    public boolean contains(long index) {
        return beginIndex<=index && index<endIndex;
    }

    /**
     * Expands this range to include an index.
     * @param index Index.
     * @return Expanded range.
     */
    public LongRange expand(long index) {
        return of(Math.min(beginIndex,index),Math.max(endIndex,index+1));
    }

    /**
     * Narrows this range to contain matching indexes only.
     * @param indexMatch Predicate matching indexes.
     * @return Narrowed range.
     */
    public LongRange narrow(LongPredicate indexMatch) {
        return of(stream().filter(indexMatch));
    }

    //overlap? join? split?

    /**
     * Creates the smallest range containing all index in a given stream.
     * @param indexStream Stream of indexes.
     * @return Containing range.
     */
    public static LongRange of(LongStream indexStream) {
        return of(indexStream.summaryStatistics());
    }

    /**
     * Creates the smallest range containing all indexes indicates as a minimum and a maximum.
     * @param summaryStatistics Stream of indexes.
     * @return Containing range.
     */
    public static LongRange of(LongSummaryStatistics summaryStatistics) {
        return of(summaryStatistics.getMin(),summaryStatistics.getMax()+1);
    }

    /**
     * Creates a stream of the values of this range.
     * @return Created stream.
     */
    public LongStream stream() {
        return LongStream.range(beginIndex,endIndex);
    }
}
