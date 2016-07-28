package org.stjs.bridge.redux;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.stjs.bridge.redux.api.FluxStandardAction;
import org.stjs.bridge.redux.api.ReducerInterface;
import org.stjs.bridge.redux.api.Store;
import org.stjs.bridge.redux.api.UnsubscribeCallback;
import org.stjs.javascript.Error;
import org.stjs.javascript.annotation.SyntheticType;
import org.stjs.javascript.functions.Callback0;
import org.stjs.testing.driver.STJSTestDriverRunner;

@RunWith(STJSTestDriverRunner.class)
public class TestRedux {

	public static final String TEST = "TEST";
	public static final String HELLO = "Hello";

	@Test(expected = Error.class)
	public void testCreateStore() {
		Store store = Redux.createStore(null, null);
	}

	@Test
	public void testCreateStore_success() {
		Object initialState = new Object();
		Store store = Redux.createStore((state, action) -> state, initialState);
		assertNotNull(store);
		assertEquals(store.getState(), initialState);
	}

	@Test
	public void testSubscribeUnsubscribe() {
		Object initialState = new Object();
		Store store = Redux.createStore((state, action) -> state, initialState);
		UnsubscribeCallback unsubscribeCallback = store.subscribe((Callback0) () -> {
		});
		unsubscribeCallback.$invoke();
	}

	@Test
	public void testReducers() {
		Object initialState = new Object();
		ReducerInterface<TestState, TestActionPayload, Void> testReducer = new TestReducer()::$invoke;
		Store store = Redux.createStore(testReducer, initialState);
		store.dispatch(new TestAction() {
			{
				this.type = TEST;
				this.payload = new TestActionPayload() {
					{
						this.GREETING = HELLO;
					}
				};
			}
		});
		TestState state = store.getState();
		assertEquals(state.value, HELLO);
	}

	private static class TestReducer {

		public TestState $invoke(TestState state, FluxStandardAction<TestActionPayload, Void> action) {
			switch (action.type) {
				case TEST:
					return new TestState() {
						{
							this.value = action.payload.GREETING;
						}
					};
				default:
					return state;
			}
		}
	}

	@SyntheticType
	private static class TestActionPayload {
		public String GREETING;
	}

	private static class TestAction extends FluxStandardAction<TestActionPayload, Void> {

	}

	private static class TestState {
		public String value;
	}

}