/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.masterCalc;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *
 * @author Romil-Dream
 */
public class outputWebviewFragment extends Fragment {
    
    static String sourceFunction, parameter;
    WebView simpleWebview;
    LinearLayout webViewLinearLayout;
    TextView loading;

    
    public outputWebviewFragment(String functionForDiffOrInt) {
        sourceFunction = functionForDiffOrInt;
      
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        
        webViewLinearLayout = (LinearLayout)inflater.inflate(R.layout.simple_webview, container, false);

        // Read the already created view and add webview to it.
        simpleWebview = (WebView) webViewLinearLayout.findViewById(R.id.webview);
        loading = (TextView) webViewLinearLayout.findViewById(R.id.loading_tv);
        loading.setVisibility(View.GONE);
        simpleWebview.setVisibility(View.GONE);
        
        
        WebSettings settings = simpleWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        simpleWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loading.setVisibility(View.VISIBLE);
                
            }
            
            @Override
            public void onPageFinished(WebView view, String url) {
                
                view.loadUrl("javascript:document.getElementsByClassName('menu')[0].innerHTML=''");
                view.loadUrl("javascript:document.getElementsByClassName('gbh')[0].innerHTML=''");
                view.loadUrl("javascript:document.getElementsByClassName('input')[0].innerHTML=''");
                view.loadUrl("javascript:document.getElementsByClassName('card_title')[0].innerHTML=''");
                view.loadUrl("javascript:document.getElementsByClassName('cell_input')[0].innerHTML=''");
                view.loadUrl("javascript:document.getElementsByName('pre')[0].innerHTML=''");
                view.loadUrl("javascript:document.getElementById('footer').innerHTML=''");
                view.loadUrl("javascript:document.getElementsByClassName('foot')[0].innerHTML=''");
                
                loading.setVisibility(View.GONE);
                simpleWebview.setVisibility(View.VISIBLE);
                
            }
        });
        
        
        
        
        
        
        
        
        
            simpleWebview.loadUrl("http://gamma.sympy.org/input/?i=" + Uri.encode("diff( " + sourceFunction + ", x)"));
        
        // use simply a webView as already created View instead of a formal LinearLayout. 
        return webViewLinearLayout;
        
    }
    
    public static void setFunctionToBeDifferentiatedOrIntegrated(String functionForDifferentiation) {
        sourceFunction = functionForDifferentiation;
    }
}
