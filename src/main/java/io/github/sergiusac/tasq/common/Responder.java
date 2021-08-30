package io.github.sergiusac.tasq.common;

/**
 * Created by Sergey Chin on 8/29/21.
 */
@FunctionalInterface
public interface Responder<S, D> {
    D respond(final S source);
}
