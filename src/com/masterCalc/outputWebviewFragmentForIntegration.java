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
public class outputWebviewFragmentForIntegration extends Fragment {
static String int_sourceFunction, int_parameter;
    WebView int_simpleWebview;
    LinearLayout int_webViewLinearLayout;
    TextView int_loading;
    
    public outputWebviewFragmentForIntegration(String functionForDiffOrInt) {
        int_sourceFunction = functionForDiffOrInt;
       
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        
        int_webViewLinearLayout = (LinearLayout)inflater.inflate(R.layout.int_simple_webview, container, false);

        // Read the already created view and add webview to it.
        int_simpleWebview = (WebView) int_webViewLinearLayout.findViewById(R.id.int_webview);
        int_loading = (TextView) int_webViewLinearLayout.findViewById(R.id.int_loading_tv);
        int_loading.setVisibility(View.GONE);
        int_simpleWebview.setVisibility(View.GONE);
        
        
        WebSettings settings = int_simpleWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        int_simpleWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
    
                int_loading.setVisibility(View.VISIBLE);
                
    
                
            }
            
            @Override
            public void onPageFinished(WebView view, String url) {
                
                MainActivity.logStr("Finished Loading. Executing js commands.");
                
                view.loadUrl("javascript:document.getElementsByClassName('menu')[0].innerHTML=''");
                MainActivity.logStr("Executed js command 1");
                view.loadUrl("javascript:document.getElementsByClassName('gbh')[0].innerHTML=''");
                MainActivity.logStr("Executed js command 2");
                view.loadUrl("javascript:document.getElementsByClassName('input')[0].innerHTML=''");
                  MainActivity.logStr("Executed js command 3");
                view.loadUrl("javascript:document.getElementsByClassName('card_title')[0].innerHTML=''");
                  MainActivity.logStr("Executed js command 4");
                view.loadUrl("javascript:document.getElementsByClassName('cell_input')[0].innerHTML=''");
                  MainActivity.logStr("Executed js command 5");
                view.loadUrl("javascript:document.getElementsByName('pre')[0].innerHTML=''");
                  MainActivity.logStr("Executed js command 6");
                view.loadUrl("javascript:document.getElementById('footer').innerHTML=''");
                  MainActivity.logStr("Executed js command 7");
                view.loadUrl("javascript:document.getElementsByClassName('foot')[0].innerHTML=''");
                  MainActivity.logStr("Executed js command 8");
                
                
                int_loading.setVisibility(View.GONE);
                int_simpleWebview.setVisibility(View.VISIBLE);
                
            }
        });
        
        
        
        
        
        
        
        
        
                int_simpleWebview.loadUrl("http://gamma.sympy.org/input/?i=" + Uri.encode("integrate( " + int_sourceFunction + ", x)"));
    

        // use simply a webView as already created View instead of a formal LinearLayout. 
        return int_webViewLinearLayout;
        
    }
    
    public static void setFunctionToBeDifferentiatedOrIntegrated(String functionForDifferentiation) {
        int_sourceFunction = functionForDifferentiation;
    }
}
