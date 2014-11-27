package com.tlnguyen.classdemo;

import android.test.InstrumentationTestCase;

/**
 * Created by TL on 11/24/2014.
 */
public class FirstTest extends InstrumentationTestCase {
    public void testSomething() throws Exception {

        int expected = 10;
        int reality = 10;
        assertEquals(expected, reality);

    }

    public void testSomethingElse() throws Exception {

        int expected = 10;
        int reality = 12;
        assertEquals(expected, reality);

    }
}
