package org.stjs.bridge.redux.api;

import org.stjs.javascript.annotation.STJSBridge;
import org.stjs.javascript.annotation.SyntheticType;

/**
 * Is an abstraction for all the possible actions that should be dispatched to the {@link Store}.
 * Mainly the concrete {@link Action} should be a <i>DTO</i> of a specific format in respect to {@link FluxStandardAction}.
 * But for some middlewares like Redux-thunk, etc action can have any form you want.
 */
@SyntheticType
@STJSBridge
public interface Action {
}
