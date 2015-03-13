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
 * @author VAIO-1
 */
public class logFragment extends Fragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       View log_view =  inflater.inflate(R.layout.log_layout, container, false);
       TextView log_tv = (TextView)log_view.findViewById(R.id.logv);
       log_tv.setText(MainActivity.logb.toString());
       return log_view;
        
    }
    
    
    
    
}
