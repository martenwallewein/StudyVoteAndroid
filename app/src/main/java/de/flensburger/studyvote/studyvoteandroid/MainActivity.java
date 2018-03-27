package de.flensburger.studyvote.studyvoteandroid;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.flensburger.studyvote.studyvoteandroid.util.http.HttpUtil;
import de.flensburger.studyvote.studyvoteandroid.util.http.OnHttpResponse;
import de.flensburger.studyvote.studyvoteandroid.util.http.SvHttpResponse;
import de.flensburger.studyvote.studyvoteandroid.web.SvWebView;

public class MainActivity extends AppCompatActivity implements OnHttpResponse {

    SvWebView svWebView;
    ImageView imageViewHourglass;
    TextView textViewStatus;

    LinearLayout linearLayoutMain;
    LinearLayout linearLayoutStatusInfo;
    AnimationDrawable hourglassDrawable;


    /**
     * Aktuell geladene Url
     */
    String currentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initGui();
        svWebView.init(Globals.getDefaultUrl());
        this.loadUrl(Globals.getDefaultUrl());
    }

    public void loadUrl(String url)
    {
        currentUrl = url;
        try {
            hourglassDrawable = (AnimationDrawable)imageViewHourglass.getBackground();
            if(hourglassDrawable != null)
                hourglassDrawable.start();

            // HttpRequest abschicken und gucken, ob die WebApp verfügbar ist
            /*if(!currentUrl.startsWith("http"))
            {
                currentUrl = UriUtil.appendContext(Globals.getDefaultUrl(), currentUrl);
            }*/

            HttpUtil.checkHttpStatus(currentUrl, 0, this, Globals.getDefaultHttpTimeout());
            statusInfoChanged(R.string.action_checkConnection);

        }
        catch(Exception ex)
        {
            statusInfoChanged(R.string.action_errorLoading);
            imageViewHourglass.setVisibility(View.GONE);
            //AlertDialogUtil.showError(this, "CNF ist abgeschissen" ,ex.getMessage());
            // ex.printStackTrace();//TODO: Lang
        }

    }
    /**
     * Callback wenn im CnfWebView hinterlegt und Seite neugeladen
     * @param webView
     * @param url
     */
    @Override
    public void processWebViewRespone(SvWebView webView, String url)
    {
        svWebView.removeOnPageLoadFinished();
        hideLoadingScreen();
    }

    /**
     * Callback des Verbindungs-Test-Requests
     * @param response
     */
    @Override
    public void processAsyncHttpResponse(SvHttpResponse response)
    {
        // Im Erfolgsfall die angefragte Url laden
        if(response.succeeded())
        {
            //errorPageDisplayed = false;
            currentUrl = null;
            statusInfoChanged(R.string.action_finishLoading);
            svWebView.addOnPageLoadFinished(this);
            svWebView.setSavedUrl(response.getRequestUrl());
            svWebView.load();

            //cnfWebView.loadUrl(response.getRequestUrl());
            return;
        }
        else
        {
            statusInfoChanged(R.string.action_errorLoading);
        }

    }

    /**
     * Versteckt den LoadingScreen und blendet den WebView ein
     */
    private void hideLoadingScreen()
    {
        if(hourglassDrawable != null)
            hourglassDrawable.stop();

        imageViewHourglass.setVisibility(View.GONE);
        //textViewStatus.setVisibility(View.GONE);
        linearLayoutStatusInfo.setVisibility(View.GONE);
    }

    /**
     * Zeigt den String aus der übergebenen Ressource an
     * @param stringId
     */
    private void statusInfoChanged(int stringId)
    {
        //textViewStatus.setVisibility(View.VISIBLE);
        //textViewStatus.setText(stringId);
    }

    /**
     * Initialisiert alle nötigen Obferflächenkomponenten
     */
    public void initGui()
    {
        linearLayoutMain = (LinearLayout) findViewById(R.id.linearLayoutMain);
        linearLayoutStatusInfo = (LinearLayout) findViewById(R.id.linearLayoutStatusInfo);
        //if(AppProcess.getAppState() == AppProcess.AppState.CLOSED) {
        imageViewHourglass = (ImageView) findViewById(R.id.imageViewMainHourglass);
        textViewStatus = (TextView) findViewById(R.id.textViewMainStatus);
        //linearLayoutWebView = (LinearLayout) findViewById(R.id.linearLayoutMainWebview);
        //linearLayoutMainNotification = (LinearLayout) findViewById(R.id.linearLayoutMainNotification);
        textViewStatus.setVisibility(View.VISIBLE);
        //textViewNotificationMessage = (TextView) findViewById(R.id.textViewMainNotificationMessage);
        //textViewNotificationTitle = (TextView) findViewById(R.id.textViewMainNotificationTitle);

        // Benutzerinfo
        statusInfoChanged(R.string.action_load_studyvote);

        // Notification OnClick registrieren
//        linearLayoutMainNotification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cnfWebView.loadUrl(currentNotificationUrl);
//                //cnfWebView.setSavedUrl(currentNotificationUrl);
//                //cnfWebView.load();
//
//                hideNotification();
//            }
//        });

        // Webseite laden
        svWebView = (SvWebView) findViewById(R.id.webview);
        WebSettings webSettings = svWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Issue #11 - WebView wird nach längerer Nutzung immer langsamer
        // Testweise Cache deaktiviert
        //webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAppCacheEnabled(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        svWebView.clearCache(true);

        // Ansonsten will er den Link in einem anderen Browser öffnen...
        svWebView.setWebViewClient(new WebViewClient());

        // Swipe Events registrieren
        /*gestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {
                try {
                    if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH) {
                        return false;
                    }

                    float diff = e2.getY() - e1.getY();
                    // right to left swipe
                    if (diff > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                        onDownSwipe();
                    }
                    // left to right swipe
                    // else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                    //        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    //    onRightSwipe();
                    //}
                } catch (Exception ex) {
                    log.error("Fehler beim downSwipe", ex);
                }
                return false;
            }
        });

        cnfWebView.setGestureDetector(gestureDetector);*/

        /*mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // Icon erst beim fertigen neu laden, evtl Callback merken
                        final OnHttpResponse oldResponse = cnfWebView.getOnPageLoadFinished();
                        cnfWebView.addOnPageLoadFinished(new OnHttpResponse() {
                            @Override
                            public void processAsyncHttpResponse(CnfHttpResponse cnfHttpResponse) {

                            }

                            @Override
                            public void processWebViewRespone(CnfWebView cnfWebView, String url) {
                                if (mySwipeRefreshLayout.isRefreshing()) {
                                    mySwipeRefreshLayout.setRefreshing(false);
                                }
                                cnfWebView.removeOnPageLoadFinished();
                                cnfWebView.addOnPageLoadFinished(oldResponse);
                            }
                        });
                        cnfWebView.reload();

                    }
                }
        );*/

    }

}
