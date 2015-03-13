/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.masterCalc;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 *
 * @author VAIO-1
 */
public class DrawerItemClickListener implements ListView.OnItemClickListener {

   MainActivity currentActivity;
   
   public DrawerItemClickListener(MainActivity activity) {
       
       currentActivity=activity;
       
   }
    
    
    
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

   
    selectItem(position);

            
        if (position == 5) {
             //   currentActivity
            
            
                }
    
    
    }

    private void selectItem(int position) {


     //   listenerDrawerLayout.closeDrawers();
        
    }
    
 
    
    
    
    
}
