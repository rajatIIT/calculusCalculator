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
import android.widget.LinearLayout;
import com.jjoe64.graphview.GraphView;

/**
 *
 * @author VAIO-1
 */
public class graph_fragment extends Fragment{
    
    GraphView toBeAdded ;
    
    public graph_fragment(GraphView sentView){
    toBeAdded=sentView;
}
    
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        
        
        View inflatedView = inflater.inflate(R.layout.graph_view, container, false);;
      
        LinearLayout layout_graph = (LinearLayout)inflatedView;
        
        layout_graph.addView(toBeAdded);

        return layout_graph;

    }
}
