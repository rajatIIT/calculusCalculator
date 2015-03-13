package com.masterCalc;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.gyesa.keanp176500.AdView;


import com.gyesa.keanp176500.MA;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewStyle;
import com.jjoe64.graphview.LineGraphView;
import java.util.ArrayList;
import java.util.Iterator;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;

/**
 * Plays the central role in handling all the activities of the application.
 * Initialized in the beginning, this activity lasts till the application comes
 * to a stop.
 */
public class MainActivity extends FragmentActivity implements AdapterView.OnItemClickListener {

    //Declerations
    inputFragment inputfragment;
    private static String gFunction, hFunction;
    myFrame graphFrame;
    static DrawerLayout drawer;
    static ListView drawer_listview;
    static View drawer_view;
    static FragmentManager sfm;
    static FragmentTransaction sft;
    ListView lv;
    ArrayList al;
    static String lastAddedFragment = "";
    static StringBuilder logb, samplesText;
    static ProgressBar pb;
    static Handler mainHandler;
    ActionBar actionbar;
    final int TAB_DIFFERENTIAL = 1, TAB_INTEGRAL = 2, TAB_DE = 3, TAB_LOG = 4, TAB_GRAPH = 5;
    int current_tab = 1;
     boolean showDialog = true;
     boolean appStarted = false;
     private AdView myAdView;
    AdView airPushAdView; 
     private MA ma;
   
    private String calculus_AD_UNIT_ID = "a152cb9feb15336";

    @Override
    public void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        // MMSDK.initialize(this);

        //  setup a new adView and initialize an adRequest to it.


// setupMMAdView();

        

// setupAirPushAdView();
        
        
        // onResume moved to onCreate
        if ((getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);


        }


        try {

            if (!appStarted) {
                setContentView(R.layout.main);
                
                
                
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage() + "\n" + e.getClass().toString() + "\n" + e.getLocalizedMessage() + "\n", Toast.LENGTH_LONG).show();
        }


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            public void run() {
                perform();
            }
        }, 700);


        
        
        

    }

    private void setupAdView() {

        
//        AdMob Stuff         
//        myAdView = new AdView(this);
//        myAdView.setAdUnitId(calculus_AD_UNIT_ID);
//        myAdView.setAdSize(AdSize.BANNER);
//
//        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("0149BDCB17010006").build();
//
//        myAdView.loadAd(adRequest);
//      
        
// from here goes airpush
        
                ma = new MA(this, null, true);

        airPushAdView = new AdView(this, AdView.BANNER_TYPE_IN_APP_AD, AdView.PLACEMENT_TYPE_INTERSTITIAL, false, false,
                AdView.ANIMATION_TYPE_LEFT_TO_RIGHT);

        
    }
    @Override
    public void onResume() {

        super.onResume();
        

    }

    private void perform() {


        setContentView(R.layout.app_home);

        
        
        
        if (!appStarted) {

            setupAdView();
            
             attachAdView();

 //           attachAirPushAdView();
            appStarted = true;
        }
        // attachMMAdView();        
//
        graphFrame = new myFrame(this);
        gFunction = "";
        hFunction = "";
        sfm = getSupportFragmentManager();
        logb = new StringBuilder();
        samplesText = new StringBuilder();

        logStr("Started app.");



        commitFunctionPanel();

        setupProgressBar();


        logStr("Function input panel displayed. Progress Bar initialized");

        setupDrawer();

        logStr("Drawer created without any problem.");

        setupHandler();

        logStr("Handler setup complete for messages.");

        setupSamples();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    /**
     * Changes a common expression to one suitable for evaluation by JEval (by
     * replacing the variables in the proper way).
     *
     * @param raw input string
     * @return converted string
     */
    private static String convertToJevalForm(String raw) {

        return raw.replace("x", "#{x}");

    }

    /**
     * Method executed on the click of the next button (located on the default
     * UI after clicking DE(IVP) in the drawer)
     *
     * @param v
     */
    public void goToNextPanel(View v) {

        if (inputfragment.everythingAllRight()) {
            // add another UI and remove the older one. 
            logStr("Next panel input received.");
            FragmentTransaction inputPanelTransaction = getSupportFragmentManager().beginTransaction();
            inputPanelTransaction.remove(getSupportFragmentManager().findFragmentByTag(lastAddedFragment));
            Fragment IVPPanelFragment = new inputFragment("IVPInputType");
            inputPanelTransaction.add(R.id.content_frame, IVPPanelFragment, "IVPPanelFragment");
            lastAddedFragment = "IVPPanelFragment";
            inputPanelTransaction.addToBackStack(null);
            inputPanelTransaction.commit();
        } else {
            new AlertDialog.Builder(this).setTitle("Correct your input").setMessage("Looks like you did not enter the functions correctly. Backgrounds must be green. Refer samples if you want to.").setNegativeButton("Okay", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();
        }

    }

    /**
     * Method executed on click of Plot button (found in the second UI while
     * solving the DE)
     *
     * Handles various edittexts to read values required for solving the DE,
     * solves the DE and plots the results on a graph.
     *
     * @param v
     * @throws EvaluationException
     */
    public void readValuesAndPlotSolution(View v) throws EvaluationException {


// Read the values required for computing the DE (like starting point, ending point and interval of the function to be determined)
        logStr("Starting to plot solution for a DE.");
        double[] input = inputfragment.getRangeOfY();
        String g_Function, h_Function;
        logStr("g(x) : " + MainActivity.getgFunction());
        logStr("h(x) : " + MainActivity.gethFunction());
        g_Function = MainActivity.getgFunction();
        h_Function = MainActivity.gethFunction();
        Toast.makeText(this, "" + input[0] + input[1] + input[2] + input[3] + input[4] + "\n gFnc" + g_Function + "\n hFnc" + h_Function, Toast.LENGTH_LONG).show();
        logStr("t0 : " + input[0]);
        logStr("y(t0) : " + input[1]);
        logStr("y1 =  : " + input[2]);
        logStr("y2 =  : " + input[3]);
        logStr("Interval =  : " + input[4]);
        double startVal = input[2];
        double interval = input[4];
        double endVal = input[3];


// Plot G(x) and H(x)
        String convertedExpression = g_Function;
        myFrame.addByIntervalToGx(convertedExpression, startVal, interval, endVal);
        String hconvertedExpression = h_Function;
        myFrame.addByIntervalToHx(hconvertedExpression, startVal, interval, endVal);
        DE.setGFunction(g_Function);
        DE.setHFunction(h_Function);


// Compute the solutions using the other thread and add them to the GraphView
        double[] y0 = new double[1];
        Double t0 = input[0];
        y0[0] = input[1];
        MainActivity.logStr("Started Computation of y(x).");
        pb.setMax((int) ((endVal - startVal) / (interval)));
        FirstOrderIntegrator dp853 = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);
        FirstOrderDifferentialEquations ode = new DE();
        new Thread(new DEComputatationThread(dp853, ode, startVal, interval, endVal, t0, y0)).start();



// setup the graphView 
        GraphViewStyle graphStyle = new GraphViewStyle(Color.BLACK, Color.BLACK, Color.BLACK);
        GraphView DEGraphView = new LineGraphView(this, "DE Solution");
        DEGraphView.setGraphViewStyle(graphStyle);
        DEGraphView.setScrollable(true);
        DEGraphView.setScalable(true);
        DEGraphView.setShowLegend(true);
        DEGraphView.setLegendAlign(GraphView.LegendAlign.BOTTOM);
        DEGraphView.setLegendWidth(200);
        DEGraphView.setViewPort(startVal, endVal);
        GraphViewSeries[] all_three_functions = myFrame.getSeriesSet();
        for (int i = 0; i < all_three_functions.length; i++) {
            DEGraphView.addSeries(all_three_functions[i]);
        }


// commit the transaction to show the graphView
        FragmentTransaction graphTransaction = getSupportFragmentManager().beginTransaction();
        graph_fragment graphFragment = new graph_fragment(DEGraphView);
        graphTransaction.remove(getSupportFragmentManager().findFragmentByTag(lastAddedFragment));
        graphTransaction.add(R.id.content_frame, graphFragment, "GraphLayoutFragment");
        lastAddedFragment = "GraphLayoutFragment";
        graphTransaction.addToBackStack(null);
        graphTransaction.commit();


    }

    public void plotGraph(View v) throws EvaluationException {
        //  read the values of initial, final and intervals.

        // global plotFunction us the function to be evaluated.

        if (drawGraphFragment.inputFunctionOk) {
            logStr("Starting Plotting");
            double[] plotParam = drawGraphFragment.getPlotParam();

            Evaluator plotEvaluator = new Evaluator();


            // create a GraphView and a GraphViewSeries.
            GraphView plotFunctionGraphView = new LineGraphView(this, "Plot a Function");
            GraphViewStyle plotGraphStyle = new GraphViewStyle(Color.BLACK, Color.BLACK, Color.BLACK);
            plotFunctionGraphView.setGraphViewStyle(plotGraphStyle);
            plotFunctionGraphView.setScrollable(true);
            plotFunctionGraphView.setScalable(true);
            plotFunctionGraphView.setShowLegend(true);
            plotFunctionGraphView.setLegendAlign(GraphView.LegendAlign.BOTTOM);
            plotFunctionGraphView.setLegendWidth(200);
            plotFunctionGraphView.setViewPort(plotParam[0], plotParam[2]);


            int no_dataPoints = (int) ((plotParam[2] - plotParam[0]) / (plotParam[1]));

            GraphView.GraphViewData[] plotValues = new GraphView.GraphViewData[no_dataPoints];


            // create a graphViewSeries. to create it , first create the textWatchers.
            // create a dataset for computation.

            GraphViewSeries.GraphViewSeriesStyle drawGraphStyle = new GraphViewSeries.GraphViewSeriesStyle(this.getResources().getColor(R.color.gx_color), 5);


            GraphViewSeries plotGraphSeries = new GraphViewSeries("G(x)", drawGraphStyle, new GraphView.GraphViewData[]{new GraphView.GraphViewData(0, 0.0d)});

            plotFunctionGraphView.addSeries(plotGraphSeries);

            //   int num = 0;
            for (double i = plotParam[0]; i < plotParam[2]; i = i + plotParam[1]) {
                plotEvaluator.clearVariables();
                plotEvaluator.putVariable("x", Double.toString(i));
                try {
                    double resultDrawGraph = Double.parseDouble(plotEvaluator.evaluate(plotFunction));
                    plotGraphSeries.appendData(new GraphView.GraphViewData(i, resultDrawGraph), true);
                    logStr("g(" + i + ") = " + resultDrawGraph);
                } catch (EvaluationException e) {
                    //    new AlertDialog.Builder
                    new AlertDialog.Builder(this).setTitle("Error Message").setMessage("Unable to evaluate !! Probably the function is undefined at some point in the domain or it is entered incorrectly.").setNegativeButton("Okay", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
                    logStr("Evaluation error occured at " + i);
                }
                //    plotValues[num] = new GraphView.GraphViewData(i, resultDrawGraph);
                //     num++;


            }


            // use the given evaluator to compute the values of the datasets and simaltaneously plot the graphs

            FragmentTransaction drawTransaction = getSupportFragmentManager().beginTransaction();
            graph_fragment drawFragment = new graph_fragment(plotFunctionGraphView);
            drawTransaction.remove(getSupportFragmentManager().findFragmentByTag(lastAddedFragment));
            drawTransaction.add(R.id.content_frame, drawFragment, "drawGraphFragment");
            lastAddedFragment = "drawGraphFragment";
            drawTransaction.addToBackStack(null);
            drawTransaction.commit();
            logStr("Commited without fail");
        } else {
            new AlertDialog.Builder(this).setTitle("Correct your input").setMessage("Looks like you did not enter the function correctly. Background must be green. Refer to samples in the drawer if you want to.").setNegativeButton("Okay", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();

        }

    }

    public void differentiate(View v) {
        if (isNetworkAvailable()) {
            FragmentTransaction differentialTransaction = getSupportFragmentManager().beginTransaction();
            outputWebviewFragment webviewFragment = new outputWebviewFragment(differentiation_fragment.getFunctionToBeDifferentiated());
            outputWebviewFragment.setFunctionToBeDifferentiatedOrIntegrated(differentiation_fragment.getFunctionToBeDifferentiated());
            // add a textchange listener to the editText and then uncomment the command above.

            Fragment current_diff = getSupportFragmentManager().findFragmentByTag(lastAddedFragment);
            differentialTransaction.remove(current_diff);

            if (current_diff != null) {
                differentialTransaction.replace(R.id.content_frame, webviewFragment, "WebviewDifferential");
            }

            lastAddedFragment = "WebviewDifferential";
            differentialTransaction.addToBackStack(null);
            differentialTransaction.commit();
            logStr("Differentiated " + differentiation_fragment.getFunctionToBeDifferentiated());
        } else {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Internet Access");
            builder1.setMessage("This feature requires internet access. Please connect to the internet first.");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder1.show();
        }

    }

    public void integrate(View v) {
        if (isNetworkAvailable()) {

            Toast.makeText(this, "Network Available!", Toast.LENGTH_SHORT).show();


            FragmentTransaction integrationWebTransaction = getSupportFragmentManager().beginTransaction();


            outputWebviewFragmentForIntegration integralWebFragment = new outputWebviewFragmentForIntegration(integralFragment.getFunctionToBeIntegrated());



            outputWebviewFragmentForIntegration.setFunctionToBeDifferentiatedOrIntegrated(integralFragment.getFunctionToBeIntegrated());

            Fragment current_integrate = getSupportFragmentManager().findFragmentByTag(lastAddedFragment);

            if (current_integrate != null) {
                integrationWebTransaction.remove(current_integrate);
            }

            integrationWebTransaction.replace(R.id.content_frame, integralWebFragment, "WebviewIntegral");
            lastAddedFragment = "WebViewIntegral";
            integrationWebTransaction.addToBackStack(null);
            integrationWebTransaction.commit();
            logStr("Integrated " + integralFragment.getFunctionToBeIntegrated());


        } else {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Internet Access");
            builder1.setMessage("This feature requires internet access. Please connect to the internet first.");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder1.show();
        }
    }

    public static String getgFunction() {
        return gFunction;
    }

    public static void setgFunction(String gFunc) {
        gFunction = gFunc;
    }

    public static String gethFunction() {
        return hFunction;
    }

    public static void sethFunction(String hFunc) {
        hFunction = hFunc;
    }

    public static void displayLog() {
        //  FragmentTransaction logTransaction = getSupportFragmentManager().beginTransaction();

        sft = sfm.beginTransaction();

        //   logFragment log_fragment = new logFragment();

        sft.remove(sfm.findFragmentByTag("functionInputfragment"));
        sft.remove(sfm.findFragmentByTag("functionButtonfragment"));
        //  sft.replace(R.id.content_frame, log_fragment, "LogFragment");
        //    sft.add(drawer.getId(),log_fragment);

        sft.addToBackStack(null);
        sft.commit();
    }

    public void commitFunctionPanel() {
        // Committing the initial panel
//        FragmentTransaction buttonTransaction = getSupportFragmentManager().beginTransaction();
//        buttonFragment buttonfragment = new buttonFragment("otherButton");
//        buttonTransaction.replace(R.id.content_frame, buttonfragment, "functionButtonFragment");
//        buttonTransaction.commit();
//        getSupportFragmentManager().executePendingTransactions();


        try {
            current_tab = TAB_DE;
            FragmentTransaction inputTransaction = getSupportFragmentManager().beginTransaction();
            inputfragment = new inputFragment("functionInputLayout");
            if (lastAddedFragment != "") {
                inputTransaction.remove(getSupportFragmentManager().findFragmentByTag(lastAddedFragment));
            }
            inputTransaction.replace(R.id.content_frame, inputfragment, "functionInputFragment");
            lastAddedFragment = "functionInputFragment";
            inputTransaction.addToBackStack(null);
            inputTransaction.commit();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage() + "\n" + e.getClass().toString() + "\n" + e.getLocalizedMessage() + "\n", Toast.LENGTH_LONG).show();
        }
        // create one layout and commit it. 



    }

    public void setupActionBar() {
        actionbar = getActionBar();
        actionbar.show();
        actionbar.setDisplayShowHomeEnabled(false);

        actionbar.setDisplayUseLogoEnabled(false);
        actionbar.setHomeButtonEnabled(false);




        actionbar.addTab(actionbar.newTab().setText("Input").setTabListener(new ActionBar.TabListener() {

            public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
                //remove all panels and add an examples layout
            }

            public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
            }

            public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
            }
        }));

        actionbar.addTab(actionbar.newTab().setText("Example").setTabListener(new ActionBar.TabListener() {

            public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
                // display a dialog that contains examples
            }

            public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
            }

            public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
            }
        }));

        actionbar.setHomeButtonEnabled(false);;
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);



    }

    public void setupDrawer() {

        //  setContentView(R.layout.app_home);
        //  setContentView(R.layout.app_home);


        //  drawer_view = getLayoutInflater().inflate(R.layout.app_home,null);
        //   drawer_view = getLayoutInflater().inflate(R.layout.app_home, null);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        al = new ArrayList();


        al.add("Differential");
        al.add("Integral");
        al.add("DE(IVP)");

        al.add("Application log");
        al.add("Draw a graph");
        al.add("About");
        al.add("Samples");


        lv = (ListView) findViewById(R.id.drawer_list);

        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.simple_drawer_item, al));

        lv.setOnItemClickListener(this);

        Handler simpleHandler = new Handler();
        
        
        simpleHandler.postDelayed(new Runnable() {
        @Override
        public void run() {
            drawer.openDrawer(lv);
        }
    }, 1000);
        
        
        




//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer_listview = (ListView) findViewById(R.id.drawer_list);
//
//
//        //       String[] drawer_contents = {"Differential", "Integral", "DE (IVP)", "Interpolation", "Developer Log"};
//
//        ArrayList drawer_contents = new ArrayList();
//
//        drawer_contents.add("Differential");
//        drawer_contents.add("Integral");
//        drawer_contents.add("DE(IVP)");
//        drawer_contents.add("Interpolation");
//        drawer_contents.add("Developer Log");
//
//
//        drawer_listview.setAdapter(new ArrayAdapter<String>(this, R.layout.simple_drawer_item, drawer_contents));


//        drawer_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                {
//
//                    MainActivity.drawer.closeDrawer(MainActivity.drawer_listview);
//                    MainActivity.this.displayLog();
//                }
//
//            }
//        });
//
//        // add a listener to the drawer 

//        drawer_listview.setOnItemClickListener(this);

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


//        FragmentTransaction logTransaction  = getSupportFragmentManager().beginTransaction();
//
//        // logFragment log_fragment = new logFragment();
//        buttonFragment log_fragment = new buttonFragment("BorderlessButton");
//        logTransaction.remove(getSupportFragmentManager().findFragmentByTag("functionInputfragment"));
//        logTransaction.remove(getSupportFragmentManager().findFragmentByTag("functionButtonfragment"));
//        //  sft.replace(R.id.content_frame, log_fragment, "LogFragment");
//        logTransaction.add(R.id.content_frame,log_fragment);
//        logTransaction.commit();
//        

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

//        Toast.makeText(MainActivity.this, position + "",Toast.LENGTH_SHORT).show();

        lv.setItemChecked(position, true);
        //  sampleFragment sf = new sampleFragment();
        setTitle((String) al.get(position));


        if (position == 3) {

            logStr("Displaying log");
            current_tab = TAB_LOG;

            drawer.closeDrawer(lv);

            logFragment lf = new logFragment();

            Fragment current_4 = getSupportFragmentManager().findFragmentByTag(lastAddedFragment);
            if (current_4 != null) {
                ft.remove(current_4);
            }

            ft.replace(R.id.content_frame, lf, "logFragment");

            lastAddedFragment = "logFragment";
            ft.addToBackStack(null);
            ft.commit();

        } else if (position == 6) {

            logStr("Displaying Samples");
            drawer.closeDrawer(lv);
            samplesFragment sf = new samplesFragment();
            Fragment current_frr = getSupportFragmentManager().findFragmentByTag(lastAddedFragment);
            if (current_frr != null) {
                ft.remove(current_frr);
            }
            ft.replace(R.id.content_frame, sf, "samplesFragment");
            lastAddedFragment = "samplesFragment";
            ft.addToBackStack(null);
            ft.commit();

        } else if (position == 2) {


            current_tab = TAB_DE;
            //     setupExamples();
            logStr("Initializing an IVP calculation.");
            drawer.closeDrawer(lv);

            commitFunctionPanel();
        } else if (position == 4) {
            current_tab = TAB_GRAPH;
            //   setupExamples();
            logStr("Initializing a plot graph calculation");
            drawer.closeDrawer(lv);



            Fragment current_5 = getSupportFragmentManager().findFragmentByTag(lastAddedFragment);
            if (current_5 != null) {
                ft.remove(getSupportFragmentManager().findFragmentByTag(lastAddedFragment));
            }

            drawGraphFragment dgf = new drawGraphFragment();
            ft.replace(R.id.content_frame, dgf, "drawGraphFragment");

            lastAddedFragment = "drawGraphFragment";
            ft.addToBackStack(null);
            ft.commit();
        } else if (position == 0) {
            current_tab = TAB_DIFFERENTIAL;
            //       setupExamples();
            // perform a differential
            logStr("Initializing a Differentiation");
            drawer.closeDrawer(lv);

            FragmentTransaction diffTransaction = getSupportFragmentManager().beginTransaction();

            Fragment current = getSupportFragmentManager().findFragmentByTag(lastAddedFragment);

            if (current != null) {
                diffTransaction.remove(current);
            }
            differentiation_fragment diff_fragment = new differentiation_fragment();
            diffTransaction.replace(R.id.content_frame, diff_fragment, "differentialFragment");
            lastAddedFragment = "differentialFragment";
            diffTransaction.addToBackStack(null);
            diffTransaction.commit();


            // create a fragment for differential
            // use a simple linearlayout and get it packaged in a differential fragment 
            // commit the transaction
            // create a onClick method for the plot that commits a webview which shows the sympy result.
        } else if (position == 1) {
            // integration

            logStr("Initializing an integration computation.");
            drawer.closeDrawer(lv);
            current_tab = TAB_INTEGRAL;
            //    setupExamples();
            FragmentTransaction integrationTransaction = getSupportFragmentManager().beginTransaction();
            Fragment current = getSupportFragmentManager().findFragmentByTag(lastAddedFragment);

            if (current != null) {
                integrationTransaction.remove(getSupportFragmentManager().findFragmentByTag(lastAddedFragment));
            }

            integralFragment integral_fragment = new integralFragment();
            integrationTransaction.replace(R.id.content_frame, integral_fragment, "integrationFragment");
            lastAddedFragment = "integrationFragment";
            integrationTransaction.addToBackStack(null);
            integrationTransaction.commit();
        } else if (position == 5) {
            logStr("Displaying information about the App.");
            drawer.closeDrawer(lv);

            new AlertDialog.Builder(this).setPositiveButton("Close", new AlertDialog.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).setMessage("\n\n Slide open the drawer in the left if stuck up \n\n Look up website on the play store page for instructions.\n\n Integrals and differentials are computed using the Risch Algorithm available in SymPy. For the computation of solutions to differential equations, Apache Commons Math is used. For plotting graphs, GraphView library of jjoe64.com is used. We express sincere thanks to all for their contribution to the open source community.").setTitle("About").show();
        }

    }

    public static void logStr(String str) {
        logb.append("\n" + str + " ");

    }

    public static void updateProgress() {
        pb.incrementProgressBy(1);
    }

    public static void addPointFromMainThread(double i, double yo_0) {
        try {
            myFrame.addPointToYx(i, yo_0);
        } catch (Exception e) {
        }

    }

    private void plotYGraph() {
        Iterator resultsIterator = DEComputatationThread.results.iterator();


        double[] currentArray = (double[]) resultsIterator.next();
        myFrame.addFirstPointToYx(currentArray[0], currentArray[1]);


        while (resultsIterator.hasNext()) {
            currentArray = (double[]) resultsIterator.next();



            myFrame.addPointToYx(currentArray[0], currentArray[1]);
        }


    }

    private void setupHandler() {
        mainHandler = new Handler(getMainLooper()) {

            @Override
            public void handleMessage(Message inputMessage) {

                Bundle inputBundle = inputMessage.getData();

                double[] input_array = inputBundle.getDoubleArray("result_value_first");
                String status_string = inputBundle.getString("status");


                if (status_string.equals("first")) {
                    myFrame.addFirstPointToYx(input_array[0], input_array[1]);
                } else if (status_string.equals("not_first")) {
                    myFrame.addPointToYx(input_array[0], input_array[1]);
                }

            }
        };
    }

    private void setupProgressBar() {
        // setup progress bar 
        pb = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);

        LayoutParams progressParams;

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            int progressBarHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
            progressParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, progressBarHeight);
        } else {
            progressParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }




        progressParams.gravity = android.view.Gravity.BOTTOM;
        pb.setLayoutParams(progressParams);
        LinearLayout insideLayout = (LinearLayout) findViewById(R.id.content_frame);
        insideLayout.addView(pb);
    }
    private static String plotFunction;

    public static void setPlottingFunction(String plotFunc) {
        plotFunction = plotFunc;
    }

    public static String getPlottingFunction() {
        return plotFunction;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    AlertDialog.Builder sample_message;

    private void setupExamples() {

        createDialog();
        showDialog();

    }

    private void createDialog() {

        sample_message = new AlertDialog.Builder(MainActivity.this);
        sample_message.setTitle("Examples");
        sample_message.setPositiveButton("Close", new AlertDialog.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        sample_message.setNegativeButton("Don't show again.", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                showDialog = false;
            }
        });


    }

    private void showDialog() {

        if (showDialog == true) {
            if (current_tab == TAB_DIFFERENTIAL) {
                sample_message.setMessage("\nNOTE THAT THE SCHEME USED TO ENTER MATHEMATICAL FUNCTIONS WHILE DIFFERENTIATING OR INTEGRATING IS DIFFERENT THAN THE ONE FOR SOLVING A DE OR PLOTTING A GRAPH. USE x^2 FOR THE FORMER WHILE pow(x,2.0) FOR THE LATTER \n Use 'x' and not 'X'\nSome of the examples follow :\n\n 3 * x**2 + 2");
            } else if (current_tab == TAB_GRAPH) {
                sample_message.setMessage("\nNOTE THAT THE SCHEME USED TO ENTER MATHEMATICAL FUNCTIONS WHILE DIFFERENTIATING OR INTEGRATING IS DIFFERENT THAN THE ONE FOR SOLVING A DE OR PLOTTING A GRAPH. USE x^2 FOR THE FORMER WHILE pow(x,2.0) FOR THE LATTER \n Use 'x' and not 'X'\n3 * pow(x,2.0) + 2" + "\n\n2*pow(x,2.0)+2*pow(x,2.0)+4 \n\n(x)*sin(x)+(x)*cos(x)");
            } else if (current_tab == TAB_DE) {
                sample_message.setMessage("\nNOTE THAT THE SCHEME USED TO ENTER MATHEMATICAL FUNCTIONS WHILE DIFFERENTIATING OR INTEGRATING IS DIFFERENT THAN THE ONE FOR SOLVING A DE OR PLOTTING A GRAPH. USE x^2 FOR THE FORMER WHILE pow(x,2.0) FOR THE LATTER \n Use 'x' and not 'X'\nThe Differential equation that we solve here would be an initial value problem.\nIt is expected to be in the following format: \ndy/dx + y*g(x) = h(x) \nEnter the value of g(x) and h(x) in the first part while that of others after clicking the button next.The output would be the the values of y for the given range. Some examples on how to enter a mathematical function are given. \n3 * pow(x,2.0) + 2" + "\n\n2*pow(x,2.0)+2*pow(x,2.0)+4 \n\n(x)*sin(x)+(x)*cos(x)");
            } else if (current_tab == TAB_INTEGRAL) {
                sample_message.setMessage("\nNOTE THAT THE SCHEME USED TO ENTER MATHEMATICAL FUNCTIONS WHILE DIFFERENTIATING OR INTEGRATING IS DIFFERENT THAN THE ONE FOR SOLVING A DE OR PLOTTING A GRAPH. USE x^2 FOR THE FORMER WHILE pow(x,2.0) FOR THE LATTER \n Use 'x' and not 'X'\nSome of the examples follow :\n\n3 * x^2 + 2");
            }

            sample_message.show();
        }

    }

    private void setupSamples() {

        addSample("IMPORTANT");
        addSample("This app solved a DE of the form : ");
        addSample("dy/dx + (g(x))*y = h(x) ");
        addSample("t0 , y(t0)");
        addSample("");
        addSample("You have to input the following values :");
        addSample("g(x)");
        addSample("h(x)");
        addSample("t0");
        addSample("y(t0)");
        addSample("y(1)");
        addSample("y(2) [Range of Solution]");
        addSample("Interval [ app computes value of y(x) at y and y+interval and joins it for the period between y1 and y2 ]");
        addSample("");
        addSample("");
        addSample("");



        addSample("Integration and differentiation :");
        addSample("2*(x^2) + 3*x + 5");
        addSample("3*(x^5) + 4");
        addSample("cos(x^3 + 5)");
        addSample("log(x^2 + 5)");
        addSample("e^x");

        addSample("");
        addSample("");
        addSample("");
        //log(x^3 + cos(x)) Error

        addSample("Draw a Graph :");
        addSample("sin(cos(tan(x)))");
        addSample("sin(3*log(x))");
        addSample("3*pow(x,3.0) + 2*pow(x,2.0)");

        addSample("");
        addSample("");
        addSample("");
        addSample("Solve an IVP :");
        addSample("sin(x);cos(x)");
        addSample("3*pow(x,3.0) + 2*pow(x,1.0) + 3 ; 3*x + 5 [with some time] ");
        addSample("log(x);sin(x)");

    }

    private void addSample(String toBeAdded) {
        samplesText.append(toBeAdded + "\n");
    }
    
    
    private void attachAdView() {
    
        LinearLayout outerAdLayout = (LinearLayout)findViewById(R.id.externalAdId);
       // outerAdLayout.addView(myAdView);

        // Here goes Airpush
        outerAdLayout.addView(airPushAdView);
        
        
    }
    
//
//    private void setupAirPushAdView() {
//
//        ma = new MA(this, null, false);
//
//        airPushAdView = new AdView(this, AdView.BANNER_TYPE_IN_APP_AD, AdView.PLACEMENT_TYPE_INTERSTITIAL, false, false,
//                AdView.ANIMATION_TYPE_LEFT_TO_RIGHT);
//
//    }
//
//    private void attachAirPushAdView() {
//
//        LinearLayout outerAdLayout = (LinearLayout) findViewById(R.id.externalAdId);
//        outerAdLayout.addView(airPushAdView);
//
//    }
}
