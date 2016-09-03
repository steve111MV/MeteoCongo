package cg.stevendende.sunshine;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

/**
 * Created by STEVEN on 03/09/2016.
 */
public class ServerAsynchTask extends AsyncTask {

    //For a best use of the device memory and the accecibility of the variables,
    // i encapsulated them and declared global
    private String url;
    private static HttpURLConnection urlCon;
    private static BufferedReader bufferReader;



    @Override
    protected Object doInBackground(Object[] objects) {


        String forecastUrl = (String) objects[0];
        String weatherForecastJSON ="";


        this.url = forecastUrl;

        try {

            //Retrieving the API connexion URL from tthe resources
            URL httpUrl = new URL(url);

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
            Log.e("my data___", weatherForecastJSON);

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
