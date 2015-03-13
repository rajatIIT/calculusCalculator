/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.masterCalc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * @author VAIO-1
 */
public class buttonFragment extends Fragment {

    boolean plotButton;

    public buttonFragment(String type) {
        if (type.equals("plotButton")) {
            plotButton = true;
        } else {
            plotButton = false;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (plotButton == false) {
            return inflater.inflate(R.layout.borderless_button, container, false);
        } else {
            return inflater.inflate(R.layout.plot_button, container, false);
        }

    }
}
