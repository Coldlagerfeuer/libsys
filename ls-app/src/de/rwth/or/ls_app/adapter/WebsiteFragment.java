package de.rwth.or.ls_app.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import de.rwth.or.ls_app.R;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public class WebsiteFragment extends Fragment {
	
	private WebView webView;

	private static final String URL = "http://10.0.2.2/pages/index.html";
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_website, container, false);
         
        webView = (WebView) rootView.findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient());
		
		webView.loadUrl(URL);
        
        return rootView;
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	    // Check if the key event was the Back button and if there's history
	    if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
	    	webView.goBack();
	        return true;
	    }
	    // If it wasn't the Back key or there's no web page history, bubble up to the default
	    // system behavior (probably exit the activity)
	    return false;
	}
}