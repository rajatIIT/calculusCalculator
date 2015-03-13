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
import android.widget.TextView;

/**
 *
 * @author Romil-Dream
 */
public class samplesFragment extends Fragment{
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savendInstanceState){
        View sample_view = inflater.inflate(R.layout.samples_layout, container, false);
        TextView samples_tv = (TextView)sample_view.findViewById(R.id.samplev);
        samples_tv.setText(MainActivity.samplesText.toString());
        return sample_view;
        
        
    }
    
}
