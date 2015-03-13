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
public class differentiation_fragment extends Fragment {

    public static EditText et_diff_input;
    public static String diff_function;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View diff_view = inflater.inflate(R.layout.differential_input_complete, container, false);
        et_diff_input = (EditText) diff_view.findViewById(R.id.input_diff_funcn);
        et_diff_input.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                diff_function = et_diff_input.getText().toString();
            }

            public void afterTextChanged(Editable s) {
            }
        });



        return diff_view;

    }

    public static String getFunctionToBeDifferentiated() {
        return diff_function;
    }
}
