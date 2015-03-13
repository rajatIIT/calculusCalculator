package com.masterCalc;

import android.os.Bundle;

import junit.framework.TestCase;

/**
 * Created by Romil-Dream on 8/31/13.
 */
public class testcase1 extends TestCase {
    public void testDoSomething(){
        MainActivity ma = new MainActivity();
        ma.onCreate(new Bundle());
    }
}
