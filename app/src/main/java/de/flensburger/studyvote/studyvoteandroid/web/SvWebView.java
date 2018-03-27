package de.flensburger.studyvote.studyvoteandroid.web;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.WebView;

import de.flensburger.studyvote.studyvoteandroid.util.http.OnHttpResponse;

/**
 * Created by Marten on 10.01.2018.
 */

public class SvWebView extends WebView {
    // region - Vars -

    /**
     * Zugrundeliegender WebView
     */
    //private WebView webView;

    /**
     * Aufrufender Context
     */
    private Context context;

    /**
     * Aufzurufende Url
     */
    private String url;

    /**
     * Zugrundeliegende Url, zu der hinterher die Kontexte hinzugefügt werden
     */
    private String baseUrl;

    private SvWebViewClient svWebViewClient;

    // endregion

    // region - Init -

    public SvWebView(Context context)
    {
        super(context);
        this.context = context;
    }

    public SvWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public SvWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    /**
     * Initialisiert alles nötige für den CnfWebView
     * @param url
     */
    public void init(String url)
    {
        this.svWebViewClient = new SvWebViewClient(this.context);
        this.setWebViewClient(this.svWebViewClient);
        this.baseUrl = url;
    }

    // endregion

    // region - Wrapped Methods -

    public void loadUrl(String url)
    {
        super.loadUrl(url);
    }

    /**
     * Lädt die hinterlegte Url
     */
    public void load()
    {
        this.loadUrl(url);
    }

    /**
     * Fügt den übergebenenen Kontext an die BaseUrl an und lädt dann die zusammengebaute
     * Url
     * @param context
     */
    public void loadUrlByContext(String context)
    {
        if(!baseUrl.endsWith("/"))
            baseUrl += "/";

        loadUrl(baseUrl + context);
    }

    /**
     * Fügt einen Context an die BaseUrl an
     * @param context
     * @return
     */
    public String addContext(String context)
    {

        if(!baseUrl.endsWith("/"))
            baseUrl += "/";

        return  baseUrl + context;
    }

    // endregion

    //region - Events -

    /**
     * Reqistriert den callback für onPageFinished Events
     * @param callback
     */
    public void addOnPageLoadFinished(OnHttpResponse callback)
    {
        this.svWebViewClient.setOnHttpResponse(callback);
    }

    /**
     * Entfernt den vorher gesetzten callback für onPageFinished Events wieder
     */
    public void removeOnPageLoadFinished()
    {
        this.svWebViewClient.setOnHttpResponse(null);
    }

    /*
     * @see android.webkit.WebView#onScrollChanged(int, int, int, int)
     */
    //@Override
    //protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    //    super.onScrollChanged(l, t, oldl, oldt);
    //}

    /*
     * @see android.webkit.WebView#onTouchEvent(android.view.MotionEvent)
     */
    //@Override
    //public boolean onTouchEvent(MotionEvent ev) {
    //    return gestureDetector.onTouchEvent(ev) || super.onTouchEvent(ev);
    //}

    //endregion

    //region - KeyBoard -

    /**
     * Next und Previous Buttons in der Tastatur einblenden
     * TODO: Bei Previous auf Samsung Galaxy S4 wird auch der Next Button wieder ausgeblendet
     * @param outAttrs
     * @return
     */
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection inputConnection = super.onCreateInputConnection(outAttrs);
        if (outAttrs != null) {
            // remove other IME_ACTION_*
            outAttrs.imeOptions &= ~EditorInfo.IME_ACTION_GO;
            outAttrs.imeOptions &= ~EditorInfo.IME_ACTION_SEARCH;
            outAttrs.imeOptions &= ~EditorInfo.IME_ACTION_SEND;
            outAttrs.imeOptions &= ~EditorInfo.IME_ACTION_DONE;
            outAttrs.imeOptions &= ~EditorInfo.IME_ACTION_NONE;
            // add IME_ACTION_NEXT instead
            outAttrs.imeOptions |= EditorInfo.IME_FLAG_NAVIGATE_NEXT;
            outAttrs.imeOptions |= EditorInfo.IME_FLAG_NAVIGATE_PREVIOUS;
            outAttrs.imeOptions |= EditorInfo.IME_ACTION_NEXT;
        }
        return inputConnection;
    }
    //endregion

    // region - Getters & Setters -

    public void setContext(Context context) {
        this.context = context;
    }

    public String getSavedUrl() {
        return url;
    }

    public void setSavedUrl(String url) {
        this.url = url;
    }

    public OnHttpResponse getOnPageLoadFinished()
    {
        return this.svWebViewClient.getOnHttpResponse();
    }

    // endregion
}
