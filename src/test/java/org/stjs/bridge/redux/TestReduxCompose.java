package org.stjs.bridge.redux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.stjs.javascript.JSFunctionAdapter;
import org.stjs.javascript.functions.Function;
import org.stjs.javascript.functions.Function0;
import org.stjs.javascript.functions.Function1;
import org.stjs.testing.driver.STJSTestDriverRunner;

import static org.junit.Assert.assertEquals;

@RunWith(STJSTestDriverRunner.class)
public class TestReduxCompose {

    @Test
    public void testCompose() {
        Function1<String, String> fnc1 = val -> "Fnk1 [" + val + "]";
        Function1<String, String> fnc2 = val -> "Fnk2 [" + val + "]";
        Function1<String, String> fnc3 = val -> "Fnk3 [" + val + "]";

        Function compose = Redux.compose(fnc1, fnc2, fnc3);

        Object context = "context";
        Object result = JSFunctionAdapter.call((Function0<String>) compose, context, "param");

        assertEquals("Fnk1 [Fnk2 [Fnk3 [param]]]", result.toString());
    }
}
