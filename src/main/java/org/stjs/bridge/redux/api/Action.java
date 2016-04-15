package org.stjs.bridge.redux.api;

import org.stjs.javascript.annotation.SyntheticType;

/**
 * The action class to represent an action which follows the Flux Standard Action (FSA) standard:
 * An action MUST
 * <ul>
 * <li>be a plain JavaScript object.</li>
 * <li>have a type property.</li>
 * </ul>
 * An action MAY
 * <ul>
 * <li>have a error property.</li>
 * <li>have a payload property.</li>
 * <li>have a meta property.</li>
 * </ul>
 * An action MUST NOT include properties other than type, payload, and error, and meta.
 *
 * @param <P> the type of payload object
 * @param <M> the type of meta object
 */

@SyntheticType
public final class Action<P, M> {
	/**
	 * The type of an action identifies to the consumer the nature of the action that has occurred.
	 * Two actions with the same type MUST be strictly equivalent (using ===)
	 */
	public String type;
	/**
	 * The optional payload property MAY be any type of value. It represents the payload of the action.
	 * Any information about the action that is not the type or status of the action should be part of the payload field.
	 * By convention, if error is true, the payload SHOULD be an error object. This is akin to rejecting a promise with an error object.
	 */
	public P payload;
	/**
	 * he optional error property MAY be set to true if the action represents an error.
	 * An action whose error is true is analogous to a rejected Promise. By convention, the payload SHOULD be an error object.
	 * If error has any other value besides true, including undefined and null, the action MUST NOT be interpreted as an error.
	 */
	public boolean error;
	/**
	 * The optional meta property MAY be any type of value. It is intended for any extra information that is not part of the payload.
	 */
	public M meta;
}
