package de.flensburger.studyvote.studyvoteandroid.util.http;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil extends AsyncTask<Void, Integer, SvHttpResponse> {

    //region - Vars -


    /**
     * Wird gefeuert, wenn die Antwort vom Request kam
     */
    private OnHttpResponse onOnHttpResponse = null;

    /**
     * Gibt das Timeout für den Request an
     */
    private int timeout;

    /**
     * Url merken...
     */
    private String requestUrl;

    /**
     * Content, falls geladen
     */
    private String response;

    /**
     * Im Standardfall wird der Inhalt der Seite geladen, kann aber auch abgestellt werden
     */
    private boolean loadContent = true;

    //endregion

    //region - Init -

    /**
     * Private, damit man nur die statischen Methoden nutzt
     */
    private HttpUtil()
    {

    }

    //endregion

    // region - Background Work -

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected SvHttpResponse doInBackground(Void... params) {
        SvHttpResponse svHttpResponse = null;
        try {

            URL url;
            HttpURLConnection urlConnection = null;

            url = new URL(requestUrl);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setConnectTimeout(timeout);
            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK && loadContent){
                //response = FileUtil.convertStreamToString(urlConnection.getInputStream());
                //Log.v("CatalogClient", server_response);
            }

            svHttpResponse = new SvHttpResponse(response, responseCode);
            return svHttpResponse;

        }
        catch (IOException ex)
        {
            //log.error("Fehler bei der Http Anfrage auf " + requestUrl, ex);
            svHttpResponse = new SvHttpResponse(-1, ex);
            return svHttpResponse;
        }
        finally {
            if(svHttpResponse != null)
                svHttpResponse.setRequestUrl(requestUrl);
        }

    }

    protected void onProgressUpdate(Integer... progress) {

    }


    protected void onPostExecute(SvHttpResponse result) {
        if(onOnHttpResponse != null)
            onOnHttpResponse.processAsyncHttpResponse(result);
    }

    //endregion

    //region - Execute Async -

    /**
     *
     * @param url
     * @param animResId
     */
    public static void executeHttpGetAsync(String url, int animResId, OnHttpResponse callback)
    {
        executeHttpGetAsync(url, animResId, callback, 5000);
    }

    /**
     * Lädt den Inhalt der übergebenen Url falls möglich
     * @param url
     * @param animResId
     */
    public static void executeHttpGetAsync(String url, int animResId, OnHttpResponse callback, int timeout)
    {
        executeHttpGetAsync(url, animResId, callback, timeout, true);
    }

    /**
     * Lädt den Inhalt der übergebenen Url falls möglich
     * @param url
     * @param animResId
     */
    public static void executeHttpGetAsync(String url, int animResId, OnHttpResponse callback, int timeout, boolean loadContent)
    {
        //TODO: Animation laden während der Aufruf durchgeführt wird
        HttpUtil httpUtil = new HttpUtil();
        httpUtil.onOnHttpResponse = callback;
        httpUtil.timeout = timeout;
        httpUtil.requestUrl = url;
        httpUtil.loadContent = loadContent;
        httpUtil.execute();
    }

    /**
     * Ruft den HttpStatusCode der übergebenen Url ab
     * @param url
     * @param animResId
     * @param callback
     * @param timeout
     */
    public static void checkHttpStatus(String url, int animResId, OnHttpResponse callback, int timeout)
    {
        executeHttpGetAsync(url, animResId, callback, timeout, false);
    }

    /**
     * Führt eine Http Post Anfrage synchron durch
     * @param url
     * @param timeout
     * @return
     */
    public static SvHttpResponse executeHttpPost(String url, int timeout)
    {
        SvHttpResponse svHttpResponse = null;
        try {

            URL uri;
            HttpURLConnection urlConnection = null;
            String response = null;

            uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            urlConnection.setRequestMethod("POST");

            urlConnection.setConnectTimeout(timeout);
            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                //response = FileUtil.convertStreamToString(urlConnection.getInputStream());
                //Log.v("CatalogClient", server_response);
            }

            svHttpResponse = new SvHttpResponse(response, responseCode);
            return svHttpResponse;

        }
        catch (IOException ex)
        {
            //log.error("Fehler bei der Http Anfrage auf " + url, ex);
            svHttpResponse = new SvHttpResponse(-1, ex);
            return svHttpResponse;
        }
        finally {
            if(svHttpResponse != null)
                svHttpResponse.setRequestUrl(url);
        }
    }

    //endregion

    //region - Help Methods -

    //endregion

}