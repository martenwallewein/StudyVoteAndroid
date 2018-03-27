package de.flensburger.studyvote.studyvoteandroid;

public class Globals {

    // region - Vars -

    /**
     * Hauptversion der App
     */
    private static String version = "1.0.0-1";

    /**
     * Standard-Url der App
     */
    private static String defaultUrl = "http://192.168.0.3:3000/";//"https://app.whatsright.de/WR/modules/mobileclient/"; //"http://192.168.0.8:8180/WR/modules/mobileclient/";//"http://192.168.178.97:8080/WR/modules/mobileclient/"; //"http://codethys.de:8080/CNF/modules/mobileclient/";//"http://192.168.2.113:8080/CNF/modules/mobileclient/";

    /**
     * Debug-Modus
     */
    private static boolean isDebugMode = false;


    /**
     * Definiert das Standard Timeout für Httpanfragen
     */
    private static int defaultHttpTimeout = 3000;

    // endregion

    // region - Getters & Setters -

    public static String getVersion() {
        return version;
    }

    public static void setVersion(String rootVersion) {
        Globals.version = rootVersion;
    }

    public static String getDefaultUrl() {
        return defaultUrl;
    }

    public static void setDefaultUrl(String defaultUrl) {
        Globals.defaultUrl = defaultUrl;
    }

    public static boolean isIsDebugMode() {
        return isDebugMode;
    }

    public static void setIsDebugMode(boolean isDebugMode) {
        Globals.isDebugMode = isDebugMode;
    }

    public static int getDefaultHttpTimeout() {
        return defaultHttpTimeout;
    }

    public static void setDefaultHttpTimeout(int defaultHttpTimeout) {
        Globals.defaultHttpTimeout = defaultHttpTimeout;
    }
// endregion

}