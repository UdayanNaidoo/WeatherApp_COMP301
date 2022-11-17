package za.ac.ukzn.stu.comp301.Controller;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import za.ac.ukzn.stu.comp301.MainActivity;
import za.ac.ukzn.stu.comp301.Model.Api;
import za.ac.ukzn.stu.comp301.ThreeDayForecast;
import za.ac.ukzn.stu.comp301.WeatherRVAdapter;
import za.ac.ukzn.stu.comp301.WeatherRVModal;

public class WeatherController extends AppCompatActivity {

    public MainActivity mainActivity;//View
    public Api model; //Model

    public WeatherController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        model = new Api(this);
    }

    public void sendCityName(String cityName) {
        this.mainActivity.weatherRVModalArrayList.clear();
        model.setCurrentWeatherInfo(cityName);
        model.set3DayForecast(cityName);
    }

    public void getWeatherInfo(ArrayList<String> currentWeather) {
      this.mainActivity.cityName.setText(currentWeather.get(0));
      this.mainActivity.currentTime.setText(currentWeather.get(1));
      this.mainActivity.currentTemp.setText(currentWeather.get(2)+"°C");
        this.mainActivity.maxMinTemp.setText(currentWeather.get(3));
        Picasso.get().load("http:".concat(currentWeather.get(4))).into(this.mainActivity.conditionIcon);
        this.mainActivity.currentCondition.setText(currentWeather.get(5));
        this.mainActivity.rainPer.setText(currentWeather.get(6)+"%");
        this.mainActivity.dateDay.setText(currentWeather.get(7));



    }

    public void setHourlyWeather(ArrayList<WeatherRVModal> weatherRVModals){
        this.mainActivity.weatherRVAdapter = new WeatherRVAdapter(this.mainActivity, weatherRVModals);
        this.mainActivity.TodayForecast.setAdapter(this.mainActivity.weatherRVAdapter);
    }

    public void setWeatherTiles(ArrayList<String> weatherTiles){
        this.mainActivity.sunRise.setText(weatherTiles.get(0));
        this.mainActivity.sunSet.setText(weatherTiles.get(1));
        this.mainActivity.wind.setText(weatherTiles.get(2));
        this.mainActivity.uv.setText(weatherTiles.get(3));
        this.mainActivity.pressure.setText(weatherTiles.get(4));
        this.mainActivity.humidity.setText(weatherTiles.get(5));
    }

    public void setthreeDayForecast(ArrayList<ThreeDayForecast> threeDayForecasts){
        Picasso.get().load("http:".concat(threeDayForecasts.get(0).getIcon())).into(this.mainActivity.condition0);
        this.mainActivity.temp0.setText(threeDayForecasts.get(0).getTemprature());
        this.mainActivity.rain0.setText(threeDayForecasts.get(0).getRainfall());
        Picasso.get().load("http:".concat(threeDayForecasts.get(1).getIcon())).into(this.mainActivity.condition1);
        this.mainActivity.temp1.setText(threeDayForecasts.get(1).getTemprature());
        this.mainActivity.rain1.setText(threeDayForecasts.get(1).getRainfall());
        Picasso.get().load("http:".concat(threeDayForecasts.get(2).getIcon())).into(this.mainActivity.condition2);
        this.mainActivity.temp2.setText(threeDayForecasts.get(2).getTemprature());
        this.mainActivity.rain2.setText(threeDayForecasts.get(2).getRainfall());
    }
    public void setCompaer(String compare){
        this.mainActivity.comparison.setText(compare);
    }
}
