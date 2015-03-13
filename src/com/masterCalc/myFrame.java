/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.masterCalc;

/**
 *
 * Class used to interact with Graphing utilities.
 *
 * Displays three functions on the graph : G(x),F(x) and H(x).
 */
import android.content.Context;
import android.graphics.Color;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

public class myFrame {

    static GraphViewSeries gSeries, hSeries, ySeries;
    String hxFunction, gxFunction, yxFunction;
    static Evaluator e = new Evaluator();

    public myFrame(Context activityContext) {

        // Initiallization with null.
        
        
        GraphViewSeries.GraphViewSeriesStyle gStyle = new GraphViewSeries.GraphViewSeriesStyle(activityContext.getResources().getColor(R.color.gx_color), 5);
        gSeries = new GraphViewSeries("G(x)",gStyle,new GraphView.GraphViewData[]{new GraphView.GraphViewData(0, 0.0d)});

        
        GraphViewSeries.GraphViewSeriesStyle hStyle = new GraphViewSeries.GraphViewSeriesStyle(activityContext.getResources().getColor(R.color.hx_color), 5);
        hSeries = new GraphViewSeries("H(x)",hStyle,new GraphView.GraphViewData[]{new GraphView.GraphViewData(0, 0.0d)});

        GraphViewSeries.GraphViewSeriesStyle yStyle = new GraphViewSeries.GraphViewSeriesStyle(activityContext.getResources().getColor(R.color.yx_color), 5);
        ySeries = new GraphViewSeries("Y(x)",yStyle,new GraphView.GraphViewData[]{new GraphView.GraphViewData(0, 0.0d)});


        gSeries.appendData(new GraphView.GraphViewData(1.0, 2.0), false);
        gSeries.appendData(new GraphView.GraphViewData(2.0, 3.0), false);
        gSeries.appendData(new GraphView.GraphViewData(4.0, 5.0), false);
        gSeries.appendData(new GraphView.GraphViewData(6.0, 7.0), false);
       // Add the series to your data set


        /**
         * No need of Java charting utilities in Android.
         */
        //        XYSeriesCollection dataset = new XYSeriesCollection();
//        dataset.addSeries(gSeries);
//        dataset.addSeries(hSeries);
//        dataset.addSeries(ySeries);
//        Generate the graph
//        JFreeChart chart = ChartFactory.createXYLineChart(
//                "XY Chart", // Title
//                "x-axis", // x-axis Label
//                "y-axis", // y-axis Label
//                dataset, // Dataset
//                PlotOrientation.VERTICAL, // Plot Orientation
//                true, // Show Legend
//                true, // Use tooltips
//                false // Configure chart to generate URLs?
//                );
        /**
         * Older code for testing as a desktop application.
         */
//        JPanel jPanel1 = new JPanel();
//        jPanel1.setLayout(new java.awt.BorderLayout());
//
//        ChartPanel CP = new ChartPanel(chart);
//
//        jPanel1.add(CP, BorderLayout.CENTER);
//        jPanel1.validate();
////                jPanel1.setVisible(true);
//
//        JLabel jlbHelloWorld = new JLabel("Hello World");
//        add(jlbHelloWorld);
//        add(jPanel1);
//        this.setSize(500, 500);
//        // pack();
//        setVisible(true);
    }

    /**
     * Plot Gx on graph for a particular interval.
     *
     * @param func Expression of Gx Function.
     * @param start
     * @param interval
     * @param end
     * @throws EvaluationException
     */
    public static void addByIntervalToGx(String func, double start, double interval, double end) throws EvaluationException {

        MainActivity.logStr("Computing G(x).");
        MainActivity.logStr("func is "  + func);
        
        e.clearVariables();
        e.putVariable("x", Double.toString(start));
        Double dataValue = Double.parseDouble(e.evaluate(func));
        
        
        
        MainActivity.logStr("resetting data"  + func);
        gSeries.resetData(new GraphView.GraphViewData[]{new GraphView.GraphViewData(start,dataValue)});
        MainActivity.logStr("g(" + (start) + ") = " + dataValue);
        
        for (int i = 1; i <= ((end - start) / interval); i++) {
            e.clearVariables();
            e.putVariable("x", Double.toString(start + (i * interval)));
            dataValue = Double.parseDouble(e.evaluate(func));
            MainActivity.logStr("g(" + (start + (i * interval)) + ") = " + dataValue);
            gSeries.appendData(new GraphViewData(start + (i * interval), dataValue), true);
            
        }
    }

    /**
     * Plot Gx while fetching it from the main class.
     *
     * @param start
     * @param interval
     * @param end
     * @throws EvaluationException
     */
    public void addByIntervalToGx(double start, double interval, double end) throws EvaluationException {
        gSeries.resetData(new GraphView.GraphViewData[]{new GraphView.GraphViewData(0, 0.0d)});

        //  MathFunction gxFunction = SimpleJavaApp.returnGx();
        String gFcn = MainActivity.getgFunction();

        for (int i = 0; i <= ((end - start) / interval); i++) {
            double xforgraph = start + (i * interval);
            e.clearVariables();
            e.putVariable("x", Double.toString(xforgraph));

            System.out.println("gxFunction is " + gFcn);
            double yforgraph = Double.parseDouble(e.evaluate(gFcn));
            System.out.println("g(" + xforgraph + ") =" + yforgraph);


            gSeries.appendData(new GraphViewData(xforgraph, yforgraph), true);


        }
    }
    static Evaluator eH = new Evaluator();

    public static void addByIntervalToHx(String hFunc, double start, double interval, double end) throws EvaluationException {
        MainActivity.logStr("Computing H(x).");
        ;
        
            eH.clearVariables();
            double currentValue = start ;
            eH.putVariable("x", Double.toString(currentValue));
            double yVal = Double.parseDouble(eH.evaluate(hFunc));
            //hSeries.appendData(new GraphViewData(start, yVal), true);
            hSeries.resetData(new GraphView.GraphViewData[]{new GraphView.GraphViewData(currentValue, yVal)});
            
            MainActivity.logStr("h(" + currentValue + ") = " + yVal);
        
        
        
      //  hSeries.resetData(new GraphView.GraphViewData[]{new GraphView.GraphViewData(0, 0.0d)});
        for (int i = 1; i <= ((end - start) / interval); i++) {
            eH.clearVariables();
             currentValue = start + (i * interval);
            eH.putVariable("x", Double.toString(currentValue));

             yVal = Double.parseDouble(eH.evaluate(hFunc));
            hSeries.appendData(new GraphViewData(start + (i * interval), yVal), true);
            MainActivity.logStr("h(" + currentValue + ") = " + yVal);

        }
    }

    public void addByIntervalToHx(double start, double interval, double end) throws EvaluationException {
        hSeries.resetData(new GraphView.GraphViewData[]{new GraphView.GraphViewData(0, 0.0d)});


        String hxFunction = MainActivity.gethFunction();

        for (int i = 0; i <= ((end - start) / interval); i++) {
            eH.clearVariables();
            double currentValue = start + (i * interval);
            eH.putVariable("x", Double.toString(currentValue));


            hSeries.appendData(new GraphViewData(start + (i * interval), Double.parseDouble(eH.evaluate(hxFunction))), true);


        }
    }

    public static void addPointToYx(double x, double y) {

     
        MainActivity.logStr("y(" + x + ") = " + y);
        ySeries.appendData(new GraphViewData(x, y), true);



    }

    public static void addFirstPointToYx(double x1,double y1) {
        ySeries.resetData(new GraphView.GraphViewData[]{new GraphView.GraphViewData(x1,y1)});
        
    }
    
    public void showOnlySolution() {
        gSeries.resetData(new GraphView.GraphViewData[]{new GraphView.GraphViewData(0, 0.0d)});
        hSeries.resetData(new GraphView.GraphViewData[]{new GraphView.GraphViewData(0, 0.0d)});
    }

    public void clearYx() {
        ySeries.resetData(new GraphView.GraphViewData[]{new GraphView.GraphViewData(0, 0.0d)});
    }

    public void plotAll() {
    }

    public static GraphViewSeries[] getSeriesSet() {

        GraphViewSeries[] seriesSet = new GraphViewSeries[3];
        seriesSet[0] = gSeries;
        seriesSet[1] = hSeries;
        seriesSet[2] = ySeries;

        return seriesSet;
    }
}
