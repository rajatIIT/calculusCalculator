/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.masterCalc;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 *
 * @author Romil-Dream
 */
public class sympyClient extends WebViewClient{

    
    
    @Override
    public void onPageFinished(WebView view, String url){
        view.loadUrl("javascript:document.getElementsByClassName('menu')[0].innerHTML=''");
        view.loadUrl("javascript:document.getElementsByClassName('gbh')[0].innerHTML=''");
        view.loadUrl("javascript:document.getElementsByClassName('input')[0].innerHTML=''");
        view.loadUrl("javascript:document.getElementsByClassName('card_title')[0].innerHTML=''");
        view.loadUrl("javascript:document.getElementsByClassName('cell_input')[0].innerHTML=''");
        view.loadUrl("javascript:document.getElementsByName('pre')[0].innerHTML=''");
        view.loadUrl("javascript:document.getElementById('footer').innerHTML=''");
        view.loadUrl("javascript:document.getElementsByClassName('foot')[0].innerHTML=''");

    }
    
    
    
    
}
