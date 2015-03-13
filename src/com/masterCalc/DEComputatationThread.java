/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.masterCalc;

import android.os.Bundle;
import android.os.Message;
import java.util.ArrayList;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;

/**
 *
 * A separate Thread to compute the DE. Increases performance and in some cases
 * prevents the UI from being unresponsive.
 *
 * @author VAIO-1
 */
public class DEComputatationThread implements Runnable {

    private FirstOrderDifferentialEquations ode;
    private FirstOrderIntegrator dp853;
    private double startVal;
    private double endVal;
    private double interval;
    private double[] y0;
    private double t0;
    public static boolean done = false;
    public static ArrayList results;
    public Message m, ms;
    Bundle b, bs;

    public DEComputatationThread(FirstOrderIntegrator foi, FirstOrderDifferentialEquations fode, double mainStartVal, double mainInterval, double mainEndVal, double main_t0, double[] main_y0) {

        ode = fode;
        dp853 = foi;
        startVal = mainStartVal;
        endVal = mainEndVal;
        interval = mainInterval;
        y0 = main_y0;
        t0 = main_t0;
        results = new ArrayList();
        b = new Bundle();
        ms = new Message();
        m = new Message();
        bs = new Bundle();

    }

    /**
     * Core process for the computation of the Differential Equation. Uses the
     * process DE.java to retrieve the values using the equation whenever
     * needed.
     *
     * The process uses a handler mechanism to communicate to the main UI
     * thread. It updates the main thread at every computation of the value of
     * the y function.
     *
     * Care should be taken not to modify any view element directly from this
     * thread. It might result in errors. Proper way is to pass a message to the
     * main UI thread and update Views there.
     */
    public void run() {

        if (startVal != 0.0) {
            dp853.integrate(ode, t0, y0, startVal, y0);
        }

        // The first computation is listed separatly because the object containing data for the function y needs to be initialized.
        bs.putDoubleArray("result_value_first", new double[]{startVal, y0[0]});
        bs.putString("status", "first");
        ms.setData(bs);


        MainActivity.mainHandler.sendMessage(ms);
        MainActivity.updateProgress();

        for (double i = (startVal + interval); i <= endVal; i = i + interval) {
            MainActivity.updateProgress();
            if (i != 0.0) {
                dp853.integrate(ode, t0, y0, i, y0);
            }

            m = new Message();
            b.putDoubleArray("result_value_first", new double[]{i, y0[0]});
            b.putString("status", "not_first");
            m.setData(b);
            MainActivity.mainHandler.sendMessage(m);
        }

    }
}
