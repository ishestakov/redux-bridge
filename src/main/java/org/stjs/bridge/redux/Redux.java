package org.stjs.bridge.redux;

import org.stjs.bridge.redux.api.Reducer;
import org.stjs.bridge.redux.api.Store;
import org.stjs.javascript.Map;
import org.stjs.javascript.annotation.STJSBridge;
import org.stjs.javascript.annotation.SyntheticType;
import org.stjs.javascript.functions.Function;

@STJSBridge(sources = "webjar:/redux.js")
@SyntheticType
/**
 * The main entry point to work with reducers.
 */
public final class Redux {

	/**
	 * Creates a Redux store that holds the state tree.
	 * The only way to change the data in the store is to call `dispatch()` on it.
	 * There should only be a single store in your app. To specify how different
	 * parts of the state tree respond to actions, you may combine several reducers
	 * into a single reducer function by using `combineReducers`.
	 *
	 * @param reducer      A function that returns the next state tree, given
	 *                     the current state tree and the action to handle.
	 * @param initialState The initial state. You may optionally specify it
	 *                     to hydrate the state from the server in universal apps, or to restore a
	 *                     previously serialized user session.
	 *                     If you use `combineReducers` to produce the root reducer function, this must be
	 *                     an object with the same shape as `combineReducers` keys.
	 * @param enhancer     The store enhancer. You may optionally specify it
	 *                     to enhance the store with third-party capabilities such as middleware,
	 *                     time travel, persistence, etc. The only store enhancer that ships with Redux
	 *                     is `applyMiddleware()`.
	 * @return A Redux store that lets you read the state, dispatch actions
	 * and subscribe to changes.
	 */
	public static native Store createStore(Reducer<?, ?, ?> reducer, Object initialState, Function enhancer);

	public static native Store createStore(Reducer<?, ?, ?> reducer, Object initialState);

	/**
	 * Turns an object whose values are different reducer functions, into a single
	 * reducer function. It will call every child reducer, and gather their results
	 * into a single state object, whose keys correspond to the keys of the passed
	 * reducer functions.
	 *
	 * @param reducers An object whose values correspond to different
	 *                 reducer functions that need to be combined into one. One handy way to obtain
	 *                 it is to use ES6 `import * as reducers` syntax. The reducers may never return
	 *                 undefined for any action. Instead, they should return their initial state
	 *                 if the state passed to them was undefined, and the current state for any
	 *                 unrecognized action.
	 * @return A reducer function that invokes every reducer inside the
	 * passed object, and builds a state object with the same shape.
	 */

	public static native <S, P, M> Reducer<Map<String, S>, P, M> combineReducers(Map<String, Reducer> reducers);

	/**
	 * Turns an object whose values are action creators, into an object with the
	 * same keys, but with every function wrapped into a `dispatch` call so they
	 * may be invoked directly. This is just a convenience method, as you can call
	 * `store.dispatch(MyActionCreators.doSomething())` yourself just fine.
	 * For convenience, you can also pass a single function as the first argument,
	 * and get a function in return.
	 *
	 * @param actionCreators An object whose values are action
	 *                       creator functions. One handy way to obtain it is to use ES6 `import * as`
	 *                       syntax. You may also pass a single function.
	 * @param dispatch       The `dispatch` function available on your Redux
	 *                       store.
	 * @return The object mimicking the original object, but with
	 * every action creator wrapped into the `dispatch` call. If you passed a
	 * function as `actionCreators`, the return value will also be a single
	 * function.
	 */
	public static native <T, A> T bindActionCreators(A actionCreators, Function<?> dispatch);

	/**
	 * Creates a store enhancer that applies middleware to the dispatch method
	 * of the Redux store. This is handy for a variety of tasks, such as expressing
	 * asynchronous actions in a concise manner, or logging every action payload.
	 * See `redux-thunk` package as an example of the Redux middleware.
	 * Because middleware is potentially asynchronous, this should be the first
	 * store enhancer in the composition chain.
	 * Note that each middleware will be given the `dispatch` and `getState` functions
	 * as named arguments.
	 *
	 * @param middlewares The middleware chain to be applied.
	 * @return A store enhancer applying the middleware.
	 */
	public static native <T, A> Function<T> applyMiddleware(Function<A>... middlewares);

	/**
	 * Composes single-argument functions from right to left. The rightmost
	 * function can take multiple arguments as it provides the signature for
	 * the resulting composite function.
	 *
	 * @param funcs The functions to compose.
	 * @return A function obtained by composing the argument functions
	 * from right to left. For example, compose(f, g, h) is identical to doing
	 * (...args) => f(g(h(...args))).
	 */
	public static native <T, A> Function<T> compose(Function<A>... funcs);
}
