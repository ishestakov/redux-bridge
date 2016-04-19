package org.stjs.bridge.redux.api;

import org.stjs.javascript.annotation.STJSBridge;
import org.stjs.javascript.functions.Callback0;

/**
 * The callback to be used to un-subscribe from the store change events.
 */
@STJSBridge
@FunctionalInterface
public interface UnsubscribeCallback extends Callback0 {
}
