package org.stjs.bridge.redux;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.stjs.javascript.JSCollections.$map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.stjs.bridge.redux.api.Action;
import org.stjs.bridge.redux.api.Reducer;
import org.stjs.javascript.JSGlobal;
import org.stjs.javascript.Map;
import org.stjs.testing.annotation.ScriptsBefore;
import org.stjs.testing.driver.STJSTestDriverRunner;

@RunWith(STJSTestDriverRunner.class)
@ScriptsBefore("redux.js")
public class TestCombineReducers {

	private static final String INITIAL_STATE_1 = "Initial State 1";
	private static final String INITIAL_STATE_2 = "Initial State 2";

	private Reducer<String, Object, Void> reducer1;
	private Reducer<String, Object, Void> reducer2;

	@Test
	public void testCombineReducers_checkInitialState() {
		createTestReducers();

		Map<String, Reducer> reducersMap = $map();
		reducersMap.$put("plugin1", reducer1);
		reducersMap.$put("plugin2", reducer2);

		Reducer<Map<String, Object>, Object, Void> combinedReducer = Redux.combineReducers(reducersMap);

		assertNotNull(combinedReducer);
		assertTrue("function".equals(JSGlobal.typeof(combinedReducer)));

		Map<String, Object> initialState = combinedReducer.$invoke((Map<String, Object>) JSGlobal.undefined, createTestAction("INITIAL", null));
		assertNotNull(initialState);
		assertTrue("object".equals(JSGlobal.typeof(initialState)));

		assertEquals(initialState.$get("plugin1"), INITIAL_STATE_1);
		assertEquals(initialState.$get("plugin2"), INITIAL_STATE_2);
	}

	private <T> Action<T, Void> createTestAction(String actionType, T actionPayload) {
		return new Action<T, Void>() {
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
					return "Something created";
				case "DELETE":
					return "Something deleted";
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
					return "Something added";
				case "MOVE":
					return "Something moved";
				default:
					return state;
			}
		};
	}

}
