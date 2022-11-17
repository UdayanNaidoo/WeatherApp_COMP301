package za.ac.ukzn.stu.comp301;

import android.widget.TableLayout;

public class ThreeDayForecast {
    private String temprature;
    private String icon;
    private String rainfall;
    public TableLayout test;

    public ThreeDayForecast(String temprature, String icon, String rainfall) {
        this.temprature = temprature;
        this.icon = icon;
        this.rainfall = rainfall;
    }

    public String getTemprature() {
        return temprature;
    }

    public void setTemprature(String temprature) {
        this.temprature = temprature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRainfall() {
        return rainfall;
    }

    public void setRainfall(String rainfall) {
        this.rainfall = rainfall;
    }


}
