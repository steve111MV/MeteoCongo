package cg.stevendende.sunshine.tools;

import android.text.format.Time;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by @steve111MV on 06/09/2016.
 *
 * The content of this parser is originaly from Github & tailored to my needs
 * https://gist.github.com/udacityandroid/4ee49df1694da9129af9
 */
public class WeatherDataParser {

    //make unix server timestamp readable(by humans) as normal date
    private String getReadableDate(long timeStamp){

        SimpleDateFormat shortenedDate = new SimpleDateFormat("EEE dd MMM");
        return shortenedDate.format(timeStamp);
    }

    private String formatMaxMinTemp(double min, double max){

        long roundedMin = Math.round(min);
        long roundedMax = Math.round(max);

        String highLowStr = roundedMax +"/"+ roundedMin;
        return highLowStr;
    }

    public String[] getWeatherDataFromJson(String weatherForecastJsonStr, int numDays)
            throws JSONException {

        //JSON extraction usefull constants
        final String MY_LIST = "list";
            final String MY_TEMPERATURE = "temp";
                final String MY_MAX = "max";
                final String MY_MIN = "min";
            final String MY_WEATHER = "weather";
                final String MY_DESCRIPTION = "main";

        JSONObject forecastJson = new JSONObject(weatherForecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(MY_LIST);


        //The aim here is to get a normalized UTC(GMT) date for the weather
        Time dayTime = new Time();
        dayTime.setToNow();

        // we start at the day returned by local time. (obligation)
        int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

        // Our dayTime variable is in UTC(GMT) now
        dayTime = new Time();


        //We prepare the result of getWeatherDataFromJson()
        //we give the array the size corresponding to the user DaysNumber query parameter
        String[] resultStrArray = new String[numDays];

        for(int i = 0; i< weatherArray.length(); i++) {
            //For now, using the format "Day, description, max/min"
            String day;
            String description;
            String maxAndMin;

            //Get the JSON object representing the day
            JSONObject dayForecast = weatherArray.getJSONObject(i);

            //The date/time is returned as a long.  We need to convert that
            //into something human-readable, since most people won't read "1400356800" as
            //"this saturday".
            long dateTime;
            // Cheating to convert this to UTC time, which is what we want anyhow
            dateTime = dayTime.setJulianDay(julianStartDay+i);
            day = getReadableDate(dateTime);

            //description is in a child array called "weather", which is 1 element long.
            JSONObject weatherObject = dayForecast.getJSONArray(MY_WEATHER).getJSONObject(0);
            description = weatherObject.getString(MY_DESCRIPTION);

            //Temperatures are in a child object called "temp".  Try not to name variables
            //"temp" when working with temperature.  It confuses everybody.
            JSONObject temperatureObject = dayForecast.getJSONObject(MY_TEMPERATURE);
            double max = temperatureObject.getDouble(MY_MAX);
            double min = temperatureObject.getDouble(MY_MIN);

            maxAndMin = formatMaxMinTemp(max, min);
            resultStrArray[i] = day + " - " + description + " - " + maxAndMin;
        }

        return resultStrArray;
    }

}
