package br.com.listadecompras;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    /** Página publicada no GitHub Pages: é ela que manda. */
    private static final String SITE = "https://carlosedugon.github.io/lista-de-compras/";

    /** Cópia guardada dentro do app, usada quando não há internet. */
    private static final String OFFLINE = "file:///android_asset/index.html";

    private WebView web;
    private boolean jaCaiuParaOffline = false;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        web = new WebView(this);
        web.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(web);

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setSupportZoom(false);
        s.setBuiltInZoomControls(false);
        // guarda a página para abrir rápido e continuar funcionando sem sinal
        s.setCacheMode(WebSettings.LOAD_DEFAULT);

        web.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (request.isForMainFrame() && !jaCaiuParaOffline) {
                    jaCaiuParaOffline = true;
                    view.loadUrl(OFFLINE);
                }
            }
        });

        web.loadUrl(SITE);
    }

    @Override
    public void onBackPressed() {
        if (web != null && web.canGoBack()) {
            web.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
