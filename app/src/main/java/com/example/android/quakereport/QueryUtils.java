package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {
    /** Tag for the log messages */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();
    /** Sample JSON response for a USGS query */

   /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Earthquake> extractEarthquakes(String requrl) {

        //creating URL object
        URL url=createUrl(requrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        ArrayList<Earthquake> Earthquake=JSONParse(jsonResponse);
        return Earthquake;
    }
private static String makeHttpRequest(URL url)throws IOException{
String jsonresponse="";
    if (url==null)
        return jsonresponse;
    HttpURLConnection urlConnection=null;
    InputStream inputStream=null;

    try {
        urlConnection=(HttpURLConnection)url.openConnection();
        urlConnection.setReadTimeout(10000);
        urlConnection.setConnectTimeout(15000);
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        //check the response code
        //200-success
        //400-failure
        if (urlConnection.getResponseCode()==200){
        inputStream=urlConnection.getInputStream();
        jsonresponse=getInputStream(inputStream);
        }else {
            Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
        }
    }catch (IOException e){
        Log.e(LOG_TAG,"Error in network connection",e);
    }finally {
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }
    return jsonresponse;
}
   private static String getInputStream(InputStream inputStream)throws IOException{
       StringBuilder builder=new StringBuilder();
       if (inputStream!=null){
           InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
           BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
           String line=bufferedReader.readLine();
           while (line!=null){
               builder.append(line);
               line=bufferedReader.readLine();
           }
       }
       return builder.toString();
   }
    private static ArrayList<Earthquake> JSONParse(String JSONResponse){
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(JSONResponse)) {
            return null;
        }
        ArrayList<Earthquake> earthquakes = new ArrayList<>();
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

            JSONObject root=new JSONObject(JSONResponse);

            JSONArray earthquakeArray=root.getJSONArray("features");

            //creating a for loop to display all the earthquakes
            for (int i=0;i<earthquakeArray.length();i++) {

                JSONObject EarthquakeElement = earthquakeArray.getJSONObject(i);
                JSONObject EarthquakeProperties = EarthquakeElement.getJSONObject("properties");
                double magnitude=EarthquakeProperties.getDouble("mag");
                String location=EarthquakeProperties.getString("place");
                long time=EarthquakeProperties.getLong("time");
                String url=EarthquakeProperties.getString("url");
                //create a new earthquake object and add it to the arraylist

                Earthquake earthquake=new Earthquake(magnitude,location,time,url);
                earthquakes.add(earthquake);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    private static URL createUrl(String url) {
       URL url1=null;
        try {
            url1=new URL(url);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG,"Error creating URL object",e);
        }
        return url1;
    }

}