package za.ac.ukzn.stu.comp301;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;

import za.ac.ukzn.stu.comp301.Controller.WeatherController;

public class MainActivity extends AppCompatActivity {

    public ScrollView scrollView;
    public ProgressBar progressBar;
    public LinearLayout homeLayout;
    public TextView cityName, currentTime, currentTemp, maxMinTemp,
            currentCondition, rainPer, dateDay, comparison,
            sunSet, sunRise, wind, uv, pressure, humidity,
            temp0, rain0,
            temp1, rain1,
            temp2, rain2;

    public ImageView search;
    public ImageView conditionIcon, condition0, condition1, condition2;


    public RecyclerView TodayForecast;
    public TextInputEditText cityInput;
    public WeatherController weatherController;

    public ArrayList<WeatherRVModal> weatherRVModalArrayList;
    public WeatherRVAdapter weatherRVAdapter;

    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS); //fullscreen

        setContentView(R.layout.activity_main);
 weatherController= new WeatherController(this);
        scrollView = findViewById(R.id.idScrollView);



        homeLayout = findViewById(R.id.idHomeLayout);   //set home layout
        TodayForecast = findViewById(R.id.idRVWeather);
        cityName = findViewById(R.id.idCityName);
        currentTime = findViewById(R.id.idCurrentTime);
        search = findViewById(R.id.idSearch);   //search image view
        cityInput = findViewById(R.id.idEdtCity);   //city edit input

        currentTemp = findViewById(R.id.idCurrentTemp);
        maxMinTemp = findViewById(R.id.idMaxMin);
        currentCondition = findViewById(R.id.idConditionText);
        conditionIcon = findViewById(R.id.idConditionIcon); //condition icon image view
        rainPer = findViewById(R.id.idRainText);
        dateDay = findViewById(R.id.idDateDay);
        comparison = findViewById(R.id.idComparison);

        sunSet = findViewById(R.id.idSunset);
        sunRise =findViewById(R.id.idSunrise);
        wind = findViewById(R.id.idWind);
        uv = findViewById(R.id.idUV);
        pressure = findViewById(R.id.idPressure);
        humidity = findViewById(R.id.idHumidity);

        //3 day icons
        condition0 = findViewById(R.id.idDay0Condition);
        condition1 = findViewById(R.id.idDay1Condition);
        condition2 = findViewById(R.id.idDay2Condition);
        // 3 day temps
        temp0 = findViewById(R.id.idDay0Temp);
        temp1 = findViewById(R.id.idDay1Temp);
        temp2 = findViewById(R.id.idDay2Temp);
        // 4 day rain
        rain0 = findViewById(R.id.idDay0Rain);
        rain1 = findViewById(R.id.idDay1Rain);
        rain2 = findViewById(R.id.idDay2Rain);


        weatherRVModalArrayList = new ArrayList<>();
        weatherRVAdapter = new WeatherRVAdapter(this, weatherRVModalArrayList);
        TodayForecast.setAdapter(weatherRVAdapter);

        //homeLayout.setVisibility(View.GONE); //set visible when run
       // scrollView.setBackground(getDrawable(R.drawable.morning)); //set background
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = cityInput.getText().toString();
                System.out.println(city);
                if (city.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter city name", Toast.LENGTH_SHORT).show();
                } else {
                    weatherController.sendCityName(city);
                }

            }
        });


    }
}