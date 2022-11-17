package za.ac.ukzn.stu.comp301.Model;

import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.lang.*;
import java.util.*;

import za.ac.ukzn.stu.comp301.Controller.WeatherController;
import za.ac.ukzn.stu.comp301.ThreeDayForecast;
import za.ac.ukzn.stu.comp301.WeatherRVModal;

public class Api {

    public WeatherController weatherController;
    public ArrayList<String> currentWeather = new ArrayList<String>();
    public ArrayList<ThreeDayForecast> threedayForecast = new ArrayList<ThreeDayForecast>();
    public ArrayList<String> weatherTiles = new ArrayList<String>();

   public double todaysTemp =0;
   public double yesterdaysTemp =0;
public String ansTemp;
    public ArrayList<WeatherRVModal> weatherRVModals = new ArrayList<WeatherRVModal>();


    public Api(WeatherController weatherController) {
        this.weatherController = weatherController;

    }

    public void setCurrentWeatherInfo(String cityName) {
        currentWeather.clear();
        weatherTiles.clear();

        weatherRVModals.clear();
        String url = "http://api.weatherapi.com/v1/forecast.json?key=3c4172d86c8c4a91888142039221111&q=" + cityName + "&days=1&aqi=yes&alerts=yes";
        RequestQueue requestQueue = Volley.newRequestQueue(weatherController.mainActivity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {

                    /**Block 1 Current Weather **/
                    String city = response.getJSONObject("location").getString("name");
                    String temperature = response.getJSONObject("current").getString("temp_c");
                    todaysTemp= Double.valueOf(temperature);
                    JSONObject forecastDay = response.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0);
                    double maxTemp = forecastDay.getJSONObject("day").getDouble("maxtemp_c");
                    double minTemp = forecastDay.getJSONObject("day").getDouble("mintemp_c");
                    String maxMinTemp = (maxTemp) + "°C" + " | " + String.valueOf(minTemp) + "°C";
                    String conditionIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    int rainfall = forecastDay.getJSONObject("day").getInt("daily_chance_of_rain");
                    int isDay = response.getJSONObject("current").getInt("is_day");
                    String dateDay = response.getJSONObject("location").getString("localtime");
                    //do prev temp
                    String time = dateDay.substring(dateDay.length() - 5);

                    currentWeather.add(city);
                    currentWeather.add(time);
                    currentWeather.add(temperature);
                    currentWeather.add(maxMinTemp);
                    currentWeather.add(conditionIcon);
                    currentWeather.add(condition);
                    currentWeather.add(String.valueOf(rainfall));
                    currentWeather.add(formatDate(dateDay));
                    setYesterdaysTemp(yesterdayDate(dateDay));
                    currentWeather.add(ansTemp);
                    currentWeather.add(String.valueOf(isDay));
                    weatherController.getWeatherInfo(currentWeather);

                    /**Block 2 Tile Weather Info **/

                    JSONArray hourArray = forecastDay.getJSONArray("hour");
                    for (int i = 0; i < hourArray.length(); i++) {
                        JSONObject hourObj = hourArray.getJSONObject(i);
                        String hourTime = hourObj.getString("time");
                        String temper = hourObj.getString("temp_c");
                        String img = hourObj.getJSONObject("condition").getString("icon");
                        String wind = hourObj.getString("wind_kph");
                        weatherRVModals.add(new WeatherRVModal(hourTime,temper,img,wind));
                    }

                    weatherController.setHourlyWeather(weatherRVModals);


                    /**Block 3 Tile Weather Info **/
                    String sunrise = forecastDay.getJSONObject("astro").getString("sunrise");
                    String sunset = forecastDay.getJSONObject("astro").getString("sunset");
                    String windSpeed = forecastDay.getJSONObject("day").getString("maxwind_kph");
                    String windDir = response.getJSONObject("current").getString("wind_dir");
                    String uvIndex = forecastDay.getJSONObject("day").getString("uv");
                    String airPressure = response.getJSONObject("current").getString("pressure_mb");
                    String humidity = response.getJSONObject("current").getString("humidity");

                    weatherTiles.add(sunrise);
                    weatherTiles.add(sunset);
                    weatherTiles.add(windSpeed+"("+windDir+")");
                    weatherTiles.add(uvIndex);
                    weatherTiles.add(airPressure);
                    weatherTiles.add(humidity);

                    weatherController.setWeatherTiles(weatherTiles);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(weatherController.mainActivity, "Please enter valid city name", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    public void set3DayForecast(String cityName){
        threedayForecast.clear();
        String url = "http://api.weatherapi.com/v1/forecast.json?key=3c4172d86c8c4a91888142039221111&q=" + cityName + "&days=4&aqi=no&alerts=no";
        RequestQueue requestQueue = Volley.newRequestQueue(weatherController.mainActivity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {

                    /**Block 1 Current Weather **/
                    String city = response.getJSONObject("location").getString("name");
                    String temperature = response.getJSONObject("current").getString("temp_c");
                    int count =0;
                    for(int i= 0;i<3;i++)
                    {
                        JSONObject forecastDay = response.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(i);
                        String forecastDayCondition =forecastDay.getJSONObject("day").getJSONObject("condition").getString("icon");
                        String forecastDayTemp =forecastDay.getJSONObject("day").getString("avgtemp_c");
                        String forecastDayRainfall = forecastDay.getJSONObject("day").getString("daily_chance_of_rain");
                        threedayForecast.add(new ThreeDayForecast(forecastDayTemp,forecastDayCondition,forecastDayRainfall));
                    }
                    System.out.println(threedayForecast.get(0).getIcon());
                    System.out.println(threedayForecast.get(1).getRainfall());
                    System.out.println(threedayForecast.get(2).getRainfall());
                    weatherController.setthreeDayForecast(threedayForecast);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(weatherController.mainActivity, "Please enter valid city name", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    public void setYesterdaysTemp(String date){
        String url = "http://api.weatherapi.com/v1/history.json?key=3c4172d86c8c4a91888142039221111&q=London&dt="+date;

        RequestQueue requestQueue = Volley.newRequestQueue(weatherController.mainActivity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject forecastDay = response.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0);
                   yesterdaysTemp = Double.parseDouble(forecastDay.getJSONObject("day").getString("avgtemp_c"));
                    ansTemp=hotOrColder(yesterdaysTemp,todaysTemp);
weatherController.setCompaer(ansTemp);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(weatherController.mainActivity, "Please enter valid city name", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public String  formatDate(String date) {

        String tempDate = date.substring(0, date.length() - 6);
        tempDate = tempDate.replace("-", " ");
        String currentString = tempDate;
        String[] dateValues = currentString.split(" ");
        LocalDate someDay = LocalDate.of(Integer.parseInt(dateValues[0]), Integer.parseInt(dateValues[1]), Integer.parseInt(dateValues[2]));
        String newDate  = dateValues[2]+" "+someDay.getMonth().toString().substring(0,1)+someDay.getMonth().toString().substring(1).toLowerCase()+" , "+someDay.getDayOfWeek().toString().substring(0,1)+someDay.getDayOfWeek().toString().substring(1).toLowerCase();

        return newDate;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public String  yesterdayDate(String date) {

        String tempDate = date.substring(0, date.length() - 6);
        tempDate = tempDate.replace("-", " ");
        String currentString = tempDate;
        String[] dateValues = currentString.split(" ");
        LocalDate someDay = LocalDate.of(Integer.parseInt(dateValues[0]), Integer.parseInt(dateValues[1]), Integer.parseInt(dateValues[2]));
        String yesterday =  someDay.minusDays(1).toString();
        return yesterday;
    }

    public String hotOrColder(double yesterTemp ,double todayTemp)
    {
       String ans;
       double finalTemp = Math.round(todayTemp-yesterTemp);
       if(finalTemp<0)
           ans="Colder";
       else
           ans="Hotter";
       ans= String.valueOf(finalTemp)+"°C "+ans;
       return ans;
    }
}
