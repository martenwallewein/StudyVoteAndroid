package de.flensburger.studyvote.studyvoteandroid.util.http;

/**
 * Created by Marten on 26.10.2016.
 */
public class SvHttpResponse {

    //region - Vars -

    /**
     * Die eigentliche response wrappen für bessere Nutzung
     */
    private String response;

    /**
     * HttpStatusCode
     */
    private int statusCode = -1;

    /**
     * Gibt einen evtl aufgetretenen Fehler an
     */
    private Exception requestException;

    /**
     * Url merken...
     */
    private String requestUrl;

    //endregion

    //region - Init -

    /**
     * Wrapped die HttpResponse Klasse
     * @param response
     */
    public SvHttpResponse(String response, int httpCode)
    {
        this.response = response;
        this.statusCode = httpCode;
    }

    /**
     * Konstruktor im Fehlerfall
     * @param statusCode
     * @param requestException
     */
    public SvHttpResponse(int statusCode, Exception requestException)
    {
        this.statusCode = statusCode;
        this.requestException = requestException;
    }

    //endregion

    //region - Wrapped Methods -

    /**
     * Gibt an, ob der request erfolgreich war
     * @return
     */
    public boolean succeeded()
    {
        return statusCode < 400 && statusCode > 0;
    }

    //endregion

    //region - Getters & Setters -

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Exception getRequestException() {
        return requestException;
    }

    public void setRequestException(Exception requestException) {
        this.requestException = requestException;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }


    //endregion
}
