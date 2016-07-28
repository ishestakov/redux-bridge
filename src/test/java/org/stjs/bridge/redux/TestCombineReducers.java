package org.stjs.bridge.redux;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.stjs.javascript.JSCollections.$map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.stjs.bridge.redux.api.FluxStandardAction;
import org.stjs.bridge.redux.api.Reducer;
import org.stjs.bridge.redux.api.ReducerInterface;
import org.stjs.javascript.Error;
import org.stjs.javascript.JSGlobal;
import org.stjs.javascript.Map;
import org.stjs.testing.driver.STJSTestDriverRunner;

@RunWith(STJSTestDriverRunner.class)
public class TestCombineReducers {

	private static final String REDUCER_1_PREFIX = "Reducer1: ";
	private static final String REDUCER_2_PREFIX = "Reducer2: ";
	private static final String INITIAL_STATE_1 = "Initial State 1";
	private static final String INITIAL_STATE_2 = "Initial State 2";

	private ReducerInterface<String, Object, Void> reducer1;
	private ReducerInterface<String, Object, Void> reducer2;

	@Test
	public void testCombineReducers_checkInitialState() {
		createTestReducers();

		Map<String, ReducerInterface> reducersMap = $map();
		reducersMap.$put("plugin1", reducer1);
		reducersMap.$put("plugin2", reducer2);
		reducersMap.$put("plugin3", null);

		ReducerInterface<Map<String, Object>, Object, Void> combinedReducer = Redux.combineReducers(reducersMap);

		assertNotNull(combinedReducer);
		assertTrue("function".equals(JSGlobal.typeof(combinedReducer)));

		Map<String, Object> initialState = combinedReducer.$invoke((Map<String, Object>) JSGlobal.undefined, createTestAction("INITIAL", null));
		assertNotNull(initialState);
		assertTrue("object".equals(JSGlobal.typeof(initialState)));
		assertEquals(getKeysNumber(initialState), 2);

		assertEquals(initialState.$get("plugin1"), INITIAL_STATE_1);
		assertEquals(initialState.$get("plugin2"), INITIAL_STATE_2);
		assertEquals(initialState.$get("plugin3"), JSGlobal.undefined);
		assertEquals(initialState.$get("plugin4"), JSGlobal.undefined);
	}

	@Test(expected = Error.class)
	public void testCombineReducers_initialStateIsUndefined() {
		createTestReducers();

		Map<String, ReducerInterface> reducersMap = $map();
		reducersMap.$put("plugin1", (state, action) -> JSGlobal.undefined);
		reducersMap.$put("plugin2", (state, action) -> "Not empty state");

		ReducerInterface<Map<String, Object>, Object, Void> combinedReducer = Redux.combineReducers(reducersMap);
		combinedReducer.$invoke($map(), createTestAction("INIT", null));
	}

	@Test(expected = Error.class)
	public void testCombineReducers_reducerProducesUndefined() {
		createTestReducers();

		Map<String, ReducerInterface> reducersMap = $map();
		reducersMap.$put("plugin1", (state, action) -> "Not empty state");
		reducersMap.$put("plugin2", new ReducerInterface<Object, Object, Void>() {
			@Override
			public Object $invoke(Object s, FluxStandardAction<Object, Void> action) {
				if ("TEST_EVENT".equals(action.type)) {
					return "Not empty";
				}

				return JSGlobal.undefined;
			}
		});

		ReducerInterface<Map<String, Object>, Object, Void> combinedReducer = Redux.combineReducers(reducersMap);
		combinedReducer.$invoke($map(), createTestAction("INIT", null));
	}

	@Test(expected = Error.class)
	public void testCombineReducers_nextStateProducesUndefined() {
		createTestReducers();

		Map<String, ReducerInterface> reducersMap = $map();
		ReducerInterface<?, ?, ?> reducerInterface = (state, action) -> {
			if ("NEXT_STATE".equals(action.type)) {
				return "Not empty";
			}
			return JSGlobal.undefined;
		};
		reducersMap.$put("plugin2", reducerInterface);

		ReducerInterface<Map<String, Object>, Object, Void> combinedReducer = Redux.combineReducers(reducersMap);
		combinedReducer.$invoke($map(), createTestAction("NEXT_STATE", null));
	}

	@Test
	public void testCombineReducers_noReducers() {
		createTestReducers();

		Map<String, ReducerInterface> reducersMap = $map();
		reducersMap.$put("plugin1", null);

		ReducerInterface<Map<String, Object>, Object, Void> combinedReducer = Redux.combineReducers(reducersMap);
		combinedReducer.$invoke($map(), createTestAction("INIT", null));
		// Produces only log: Store does not have a valid reducer. Make sure the argument passed to combineReducers is an object whose values are reducers.
	}

	@Test
	public void testCombineReducers_invokeEventForOneOfTwoReducers() {
		createTestReducers();

		Map<String, ReducerInterface> reducersMap = $map();
		reducersMap.$put("plugin1", reducer1);
		reducersMap.$put("plugin2", reducer2);

		ReducerInterface<Map<String, Object>, Object, Void> combinedReducer = Redux.combineReducers(reducersMap);

		Map<String, Object> initialState = combinedReducer.$invoke($map(), createTestAction("INIT", null));
		Map<String, Object> newState = combinedReducer.$invoke(initialState, createTestAction("CREATE", null));

		assertEquals(newState.$get("plugin1"), REDUCER_1_PREFIX + "Something created");
		assertEquals(newState.$get("plugin2"), INITIAL_STATE_2);
	}

	@Test
	public void testCombineReducers_invokeCommonEventForAllReducers() {
		createTestReducers();

		Map<String, ReducerInterface> reducersMap = $map();
		reducersMap.$put("plugin1", reducer1);
		reducersMap.$put("plugin2", reducer2);

		ReducerInterface<Map<String, Object>, Object, Void> combinedReducer = Redux.combineReducers(reducersMap);

		Map<String, Object> initialState = combinedReducer.$invoke($map(), createTestAction("INIT", null));
		Map<String, Object> newState = combinedReducer.$invoke(initialState, createTestAction("COMMON_EVENT", null));

		assertEquals(newState.$get("plugin1"), REDUCER_1_PREFIX + "Applies for common event");
		assertEquals(newState.$get("plugin2"), REDUCER_2_PREFIX + "Applies for common event");
	}

	private int getKeysNumber(Map map) {
		int cnt = 0;
		for (Object s : map) {
			cnt++;
		}

		return cnt;
	}

	private <T> FluxStandardAction<T, Void> createTestAction(String actionType, T actionPayload) {
		return new FluxStandardAction<T, Void>() {
			{
				this.type = actionType;
				this.payload = actionPayload;
			}
		};
	}

	private void createTestReducers() {
		reducer1 = (state, action) -> {
			if (state == null) {
				return INITIAL_STATE_1;
			}

			switch (action.type) {
				case "CREATE":
					return REDUCER_1_PREFIX + "Something created";
				case "DELETE":
					return REDUCER_1_PREFIX + "Something deleted";
				case "COMMON_EVENT":
					return REDUCER_1_PREFIX + "Applies for common event";
				default:
					return state;
			}
		};

		reducer2 = (state, action) -> {
			if (state == null) {
				return INITIAL_STATE_2;
			}

			switch (action.type) {
				case "ADD":
					return REDUCER_2_PREFIX + "Something added";
				case "MOVE":
					return REDUCER_2_PREFIX + "Something moved";
				case "COMMON_EVENT":
					return REDUCER_2_PREFIX + "Applies for common event";
				default:
					return state;
			}
		};
	}

}
