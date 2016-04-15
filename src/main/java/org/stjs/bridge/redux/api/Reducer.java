package org.stjs.bridge.redux.api;

import org.stjs.javascript.functions.Function2;

/**
 * The reducer interface. The reducer should be a pure function (it shouldn't mutate the objects that it receives as arguments.
 * Reducer should always return a state. If no state mutations required then the original state.
 * If State passed to reducer is null or undefined the initial state for this reducer should be returned.
 *
 * @param <S> the type of state the reducer is expect to receive
 * @param <P> the Payload type of expected action object
 * @param <M> The Metadata type of expected action object
 */
public interface Reducer<S, P, M> extends Function2<S, Action<P, M>, S> {
}