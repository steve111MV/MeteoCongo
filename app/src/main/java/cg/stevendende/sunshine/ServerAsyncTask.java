package cg.stevendende.sunshine;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by STEVEN on 03/09/2016.
 */
public class ServerAsyncTask extends AsyncTask<String, Void, String> {

    //For a best use of the device memory and the accecibility of the variables,
    // i encapsulated them and declared global
    private String townId;
    private static HttpURLConnection urlCon;
    private static BufferedReader bufferReader;
    private String weatherForecastJSON;

    final String PARAM_QUERY = "q";
    final String PARAM_UNITS = "units";
    final String PARAM_MODE = "mode";
    final String PARAM_DAYS = "cnt";
    final String PARAM_API_KEY = "appid";
    final String PARAM_APP_KEY = "e62fde5506ccb962cedcdb21a4a08c63";
    final String PARAM_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast/daily";

    @Override
    protected String doInBackground(String... param) {

        if (param.length==0)
            return null;

        townId = param[0];

        String userUnits = "metric";
        String userMode = "json";
        int userNumDays = 7;


        try {

            Uri builder = Uri.parse(PARAM_FORECAST_URL)
                    .buildUpon()
                    .appendQueryParameter(PARAM_QUERY,townId)
                    .appendQueryParameter(PARAM_MODE, userMode)
                    .appendQueryParameter(PARAM_UNITS,userUnits)
                    .appendQueryParameter(PARAM_DAYS, userNumDays+"")
                    .appendQueryParameter(PARAM_API_KEY, PARAM_APP_KEY)
                    .build();

            Log.e("foracast uri", builder.toString());


            //Retrieving the API connexion URL from tthe resources
            URL httpUrl = new URL(builder.toString());

            //Creating the connextion to the API
            urlCon = (HttpURLConnection) httpUrl.openConnection();
            urlCon.setRequestMethod("GET");
            urlCon.connect();

            //Reading the request answer body
            InputStream inputStream = urlCon.getInputStream();
            if(inputStream == null)
                return null;

            StringBuffer stringBuffer = new StringBuffer();
            bufferReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferReader.readLine()) != null){

                stringBuffer.append(line+"\n");
            }

            if(stringBuffer.length() == 0)
                return null;

            weatherForecastJSON = stringBuffer.toString();
            Log.e("forecast_json a", weatherForecastJSON);

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {

            if( null != urlCon)
                urlCon.disconnect();

            if(null != bufferReader)

                try {
                    bufferReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }

        return weatherForecastJSON;

    }
}
