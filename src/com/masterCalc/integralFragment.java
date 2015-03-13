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

/**
 *
 * @author Romil-Dream
 */
public class integralFragment extends Fragment {

    public EditText et_integral_input;
    public static String sourceIntegralFunction;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View integralPanel = inflater.inflate(R.layout.integration_fragment, container, false);

        et_integral_input = (EditText)integralPanel.findViewById(R.id.input_int_funcn);

        et_integral_input.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sourceIntegralFunction = et_integral_input.getText().toString();
            }

            public void afterTextChanged(Editable s) {
            }
        });


        return integralPanel;

    }
    
    public static String getFunctionToBeIntegrated(){
        return sourceIntegralFunction;
    }
    
}
