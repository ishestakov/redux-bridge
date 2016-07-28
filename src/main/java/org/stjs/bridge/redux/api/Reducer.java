package org.stjs.bridge.redux.api;

import org.stjs.javascript.annotation.STJSBridge;
import org.stjs.javascript.annotation.SyntheticType;
import org.stjs.javascript.annotation.Template;
import org.stjs.javascript.functions.Function2;

@SyntheticType
@STJSBridge
public interface Reducer<S, P, M> extends Function2<S, FluxStandardAction<P, M>, S> {
	@Override
	@Template("invoke")
	S $invoke(S s, FluxStandardAction<P, M> pmFluxStandardAction);
}
