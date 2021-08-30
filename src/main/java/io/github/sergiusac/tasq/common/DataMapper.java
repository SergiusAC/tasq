package io.github.sergiusac.tasq.common;

/**
 * Created by Sergey Chin on 8/29/21.
 */
@FunctionalInterface
public interface DataMapper<S, D> {
    D map(final S source);
}
