package com.yelstream.topp.standard.nil;

/**
 * Defines actions for handling the three states of a {@link Nillable} value: null, nil, or present.
 * <p>
 *     Implementations provide specific behavior for each state, enabling action mapping for data transfer
 *     and processing, such as XML serialization or API workflows.
 * </p>
 * @param <T> Type of value.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-05-24
 */
public interface NillableAction<T> {
    /**
     * Handles the null state (unset, no value provided).
     */
    void onNull();

    /**
     * Handles the nil state (explicit null, e.g., xsi:nil in XML).
     */
    void onNil();

    /**
     * Handles the present state (actual non-nil value).
     * @param value The present value.
     */
    void onPresent(T value);
}
