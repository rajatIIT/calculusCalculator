/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.masterCalc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import net.sourceforge.jeval.Evaluator;

/**
 *
 * @author Romil-Dream
 */
public class drawGraphFragment extends Fragment {

    // create a simple array to store the initial, final and interval of the graph to be plotted.
    static double[] plot_param = new double[3];
   public static boolean inputFunctionOk = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View drawInputPanel = inflater.inflate(R.layout.graph_draw_complete, container, false);

        final EditText draw_panel = (EditText)drawInputPanel.findViewById(R.id.draw_fn_text);
        final EditText from_et = (EditText)drawInputPanel.findViewById(R.id.draw_from);
        final EditText to_et = (EditText)drawInputPanel.findViewById(R.id.draw_to);
        final EditText interval_et = (EditText)drawInputPanel.findViewById(R.id.draw_interval);


        draw_panel.setBackgroundColor(getResources().getColor(R.color.dark_green));

        draw_panel.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                check();
            }

            // add color listener to H(x)
            public void afterTextChanged(Editable s) {
            }

            public void check() {

                // TODO add your handling code here:
                String expressionToBeChecked = convertToJevalForm(draw_panel.getText().toString());
                boolean validExpression = false;
                draw_panel.setBackgroundColor(getResources().getColor(R.color.dark_green));
                Evaluator e = new Evaluator();
                e.putVariable("x", "1.0");
                try {
                    e.evaluate(expressionToBeChecked);
                    MainActivity.setPlottingFunction(expressionToBeChecked);
                    inputFunctionOk=true;
                } catch (Exception ex) {
                    draw_panel.setBackgroundColor(getResources().getColor(R.color.dark_red));
                    inputFunctionOk=false;

                }
            }
        });

        from_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                plot_param[0] = Double.parseDouble(from_et.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            public void check() {
            }
        });
        to_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                plot_param[2] = Double.parseDouble(to_et.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            public void check() {
            }
        });

        interval_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                plot_param[1] = Double.parseDouble(interval_et.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        // attach evaluator listeners



        return drawInputPanel;


    }

    private static String convertToJevalForm(String raw) {

        return raw.replace("x", "#{x}");

    }

    public static double[] getPlotParam() {
        return plot_param;
    }
    
    public boolean functionInputProper(){
        return inputFunctionOk;
    }
}
