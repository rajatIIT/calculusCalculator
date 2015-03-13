/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.masterCalc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.sourceforge.jeval.Evaluator;

/**
 *
 * @author VAIO-1
 */
public class inputFragment extends Fragment {

    boolean initialValueInputPanel;
    EditText gxInput, hxInput, yInitial, yFinal, t0_val, y_t0_val, et_interval;
    //  static TextView progress_view;
    double startval, endval, interval;
    View IVPInputPanel;
    static double[] ySet = new double[5];
    public static boolean gx_OK=false, hx_OK=false;

    public inputFragment(String inputType) {
        if (inputType.equals("IVPInputType")) {
            initialValueInputPanel = true;
        } else {
            // input type is function input panel    

            initialValueInputPanel = false;
        }
    }

    public double[] getRangeOfY() {


//        startval = Double.parseDouble(((EditText) IVPInputPanel.findViewById(R.id.y1_value)).getText().toString());
//        endval = Double.parseDouble(((EditText) IVPInputPanel.findViewById(R.id.y0_value)).getText().toString());
//        interval = Double.parseDouble(((EditText) IVPInputPanel.findViewById(R.id.interval)).getText().toString());
//
//
//        ySet[0] = startval;
//        ySet[1] = interval;
//        ySet[0] = endval;

        return ySet;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (initialValueInputPanel == false) {
            // the layout below should be the functional panel. we should store gx function and hx function.
            LinearLayout functionInputPanel = (LinearLayout)inflater.inflate(R.layout.function_input_complete, container, false);

            // functionInputPanel is a linear layout. Add the adView to this.

            
         //   functionInputPanel.addView(MainActivity.myAdView);



            gxInput = (EditText)(functionInputPanel.findViewById(R.id.gx_text));
            hxInput = (EditText)(functionInputPanel.findViewById(R.id.hx_text));

            gxInput.setBackgroundColor(getResources().getColor(R.color.dark_green));

            gxInput.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //  check();
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String experssionToBeChecked = gText.getText().toString();
//                boolean validExpression = false;
//                gText.setBackgroundColor(getResources().getColor(R.color.dark_green));
//
//                try {
//                    Evaluator e = new Evaluator();
//                    e.putVariable("x", "1.0");
//                    e.evaluate(experssionToBeChecked);
//                } catch (Exception e) {
//                    gText.setBackgroundColor(getResources().getColor(R.color.dark_red));
//
//                }
                    check();
                }

                // add color listener to H(x)
                public void afterTextChanged(Editable s) {
                    //    check();
                }

                public void check() {

                    // TODO add your handling code here:
                    String expressionToBeChecked = convertToJevalForm(gxInput.getText().toString());
                    boolean validExpression = false;
                    gxInput.setBackgroundColor(getResources().getColor(R.color.dark_green));
                    Evaluator e = new Evaluator();
                    e.putVariable("x", "1.0");
                    try {

                        e.evaluate(expressionToBeChecked);
                        MainActivity.setgFunction(expressionToBeChecked);
                        gx_OK=true;
                    } catch (Exception ex) {
                        gxInput.setBackgroundColor(getResources().getColor(R.color.dark_red));
                        gx_OK=false;

                    }
                }
            });


            hxInput.setBackgroundColor(getResources().getColor(R.color.dark_green));



            hxInput.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //  check();
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String experssionToBeChecked = gText.getText().toString();
//                boolean validExpression = false;
//                gText.setBackgroundColor(getResources().getColor(R.color.dark_green));
//
//                try {
//                    Evaluator e = new Evaluator();
//                    e.putVariable("x", "1.0");
//                    e.evaluate(experssionToBeChecked);
//                } catch (Exception e) {
//                    gText.setBackgroundColor(getResources().getColor(R.color.dark_red));
//
//                }
                    check();
                }

                // add color listener to H(x)
                public void afterTextChanged(Editable s) {
                    //    check();
                }

                public void check() {
                    // TODO add your handling code here:
                    String expressionToBeChecked = convertToJevalForm(hxInput.getText().toString());
                    boolean validExpression = false;
                    hxInput.setBackgroundColor(getResources().getColor(R.color.dark_green));
                    Evaluator e = new Evaluator();
                    e.putVariable("x", "1.0");
                    try {
                        e.evaluate(expressionToBeChecked);
                        MainActivity.sethFunction(expressionToBeChecked);
                        hx_OK=true;
                    } catch (Exception ex) {
                        hxInput.setBackgroundColor(getResources().getColor(R.color.dark_red));
                        hx_OK=false;
                    }
                }
            });
            try {
            } catch (Exception e) {
                //Toast.makeText(getActivity(), e.getLocalizedMessage() + e.getMessage() + e.toString(), Toast.LENGTH_SHORT).show();
            }

            return functionInputPanel;
        } else {

            IVPInputPanel = inflater.inflate(R.layout.ivp_input_complete, container, false);

            // progress_view = (TextView)IVPInputPanel.findViewById(R.id.pb);

            // add listeners for the five variables 
            t0_val = ((EditText) IVPInputPanel.findViewById(R.id.t0_value));
            t0_val.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String currentString = t0_val.getText().toString();
                    try {
                        setArray(0, Double.parseDouble(currentString));
                        t0_val.setBackgroundColor(getResources().getColor(R.color.dark_green));
                    } catch (NumberFormatException e) {
                        t0_val.setBackgroundColor(getResources().getColor(R.color.dark_red));
                    }
                }

                public void afterTextChanged(Editable s) {
                }
            });


            y_t0_val = ((EditText) IVPInputPanel.findViewById(R.id.y_t0_value));
            y_t0_val.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String currentYString = y_t0_val.getText().toString();
                    try {
                        setArray(1, Double.parseDouble(currentYString));
                        y_t0_val.setBackgroundColor(getResources().getColor(R.color.dark_green));
                    } catch (NumberFormatException ex) {
                        y_t0_val.setBackgroundColor(getResources().getColor(R.color.dark_red));
                    }

                }

                public void afterTextChanged(Editable s) {
                }
            });


            yInitial = ((EditText) IVPInputPanel.findViewById(R.id.y0_value));
            yInitial.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String currentYInitialString = yInitial.getText().toString();

                    try {
                        setArray(2, Double.parseDouble(currentYInitialString));
                        yInitial.setBackgroundColor(getResources().getColor(R.color.dark_green));
                    } catch (Exception e) {
                        yInitial.setBackgroundColor(getResources().getColor(R.color.dark_red));
                    }

                }

                public void afterTextChanged(Editable s) {
                }
            });


            yFinal = ((EditText) IVPInputPanel.findViewById(R.id.y1_value));
            yFinal.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String currentYInitialString = yFinal.getText().toString();

                    try {
                        setArray(3, Double.parseDouble(currentYInitialString));
                        yFinal.setBackgroundColor(getResources().getColor(R.color.dark_green));
                    } catch (Exception e) {
                        yFinal.setBackgroundColor(getResources().getColor(R.color.dark_red));
                    }

                }

                public void afterTextChanged(Editable s) {
                }
            });

            et_interval = ((EditText) IVPInputPanel.findViewById(R.id.interval));
            et_interval.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String currentYInitialString = et_interval.getText().toString();

                    try {
                        setArray(4, Double.parseDouble(currentYInitialString));
                        et_interval.setBackgroundColor(getResources().getColor(R.color.dark_green));
                    } catch (Exception e) {
                        et_interval.setBackgroundColor(getResources().getColor(R.color.dark_red));
                    }

                }

                public void afterTextChanged(Editable s) {
                }
            });


            return IVPInputPanel;

        }



    }

    public void attachListeners() {
        EditText et = (EditText) getActivity().findViewById(R.id.gx_text);

        et.setBackgroundColor(Color.GREEN);
    }

    private static String convertToJevalForm(String raw) {

        return raw.replace("x", "#{x}");

    }

    public static void setArray(int i, double val) {

        ySet[i] = val;

    }
    
    public boolean everythingAllRight(){
        if(gx_OK && hx_OK) 
            return true;
        else 
            return false;
        
    }
}
