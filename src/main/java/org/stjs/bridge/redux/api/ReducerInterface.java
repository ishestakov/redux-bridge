package org.stjs.bridge.redux.api;

import org.stjs.javascript.annotation.JavascriptFunction;
import org.stjs.javascript.annotation.STJSBridge;
import org.stjs.javascript.functions.Function2;

/**
 * The reducer interface. The reducer should be <a href="https://en.wikipedia.org/wiki/Pure_function">a pure function</a> (it shouldn't mutate
 * the objects that it receives as arguments.
 * Reducer should always return a state. If no state mutations required then the original state.
 * If State passed to reducer is null or undefined the initial state for this reducer should be returned.
 *
 * @param <S> the type of state the reducer is expect to receive
 * @param <P> the Payload type of expected action object
 * @param <M> The Metadata type of expected action object
 */
@STJSBridge
@JavascriptFunction
public interface ReducerInterface<S, P, M> extends Function2<S, FluxStandardAction<P, M>, S> {
}
