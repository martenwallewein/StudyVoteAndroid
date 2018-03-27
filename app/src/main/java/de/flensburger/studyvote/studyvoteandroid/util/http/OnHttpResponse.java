package de.flensburger.studyvote.studyvoteandroid.util.http;

import android.webkit.WebView;

import de.flensburger.studyvote.studyvoteandroid.web.SvWebView;

/**
 * Definiert das Interface, welches implementiert werden muss,
 * wenn man die Antwort vom HttpUtil verarbeiten will
 */
public interface OnHttpResponse {

    /**
     * Wird als callback bei einem HttpAufruf aus dem HttpUtil verwendet
     * @param svHttpResponse
     */
    void processAsyncHttpResponse(SvHttpResponse svHttpResponse);

    /**
     *
     * @param cnfWebView
     * @param url
     */
    void processWebViewRespone(SvWebView cnfWebView, String url);
}
