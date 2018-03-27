package de.flensburger.studyvote.studyvoteandroid.web;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import de.flensburger.studyvote.studyvoteandroid.util.http.OnHttpResponse;

public class SvWebViewClient extends WebViewClient {

    //region - Vars -

    /**
     * Delegate
     */
    private OnHttpResponse onHttpResponse;

    private Context context;

    //endregion

    //region - Init -

    public SvWebViewClient(Context context)
    {
        this.context = context;
    }

    //endregion

    //region - Events -

    /**
     * Event einfach durchreichen
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        if(onHttpResponse != null && view instanceof SvWebView)
            onHttpResponse.processWebViewRespone((SvWebView)view, url);
    }

    //endregion

    //region - Getters & Setters -

    public OnHttpResponse getOnHttpResponse() {
        return onHttpResponse;
    }

    public void setOnHttpResponse(OnHttpResponse onHttpResponse) {
        this.onHttpResponse = onHttpResponse;
    }

    //endregion
}