package org.stjs.bridge.redux.api;

import org.stjs.javascript.annotation.STJSBridge;
import org.stjs.javascript.annotation.SyntheticType;
import org.stjs.javascript.annotation.Template;
import org.stjs.javascript.functions.Function2;

/**
 * The Reducer {@link org.stjs.javascript.annotation.JavascriptFunction} to describe the reducer interface of <a
 * href="https://en.wikipedia.org/wiki/Pure_function">a pure function</a> representation.
 * This interface cannot be directly implemented and should be used only for reference variables.
 * Reducer should process only actions of type {@link FluxStandardAction}. All other types should be avoided and use {@linkplain
 * org.stjs.bridge.redux.Redux#applyMiddleware} for process them and map to {@link FluxStandardAction} to be dispatched.
 *
 * @param <S> the type of the State of store passed down for reducer
 * @param <P> the type of payload of the action
 * @param <M> the type of metadata of the action
 */
@SyntheticType
@STJSBridge
public interface Reducer<S, P, M> extends Function2<S, FluxStandardAction<P, M>, S> {
	@Override
	@Template("invoke")
	S $invoke(S s, FluxStandardAction<P, M> pmFluxStandardAction);
}
