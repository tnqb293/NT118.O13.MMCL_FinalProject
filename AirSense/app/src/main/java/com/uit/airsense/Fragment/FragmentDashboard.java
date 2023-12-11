package com.uit.airsense.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.uit.airsense.API.APIManager;
import com.uit.airsense.HomeActivity;
import com.uit.airsense.Interface.DataChartCallback;
import com.uit.airsense.Interface.DataTemperatureCallback;
import com.uit.airsense.Interface.DataUserCallback;
import com.uit.airsense.Model.GlobalVars;
import com.uit.airsense.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentDashboard extends Fragment {
    HomeActivity homeActivity;
    ImageView ivTemperature, ivHumidity, ivWind, ivRainfall;
    TextView tvMainTemp, tvMinTemp, tvMaxTemp, tvItemTemp;
    TextView tvMainHumidity, tvMinHumidity, tvMaxHumidity, tvItemHumidity;
    TextView tvMainWind, tvMinWind, tvMaxWind, tvItemWind;
    TextView tvHelloUser;
    TextView tvMainRainfall, tvMinRainfall, tvMaxRainfall, tvItemRainfall;
    long previousTimeDay, nextTimeDay;
    JsonArray dataApi;
    public FragmentDashboard(HomeActivity activity)
    {
        this.homeActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitEvent();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ViewFlipper viewFlipper = view.findViewById(R.id.vfMainInfo1);
        InitViews(view);
        getDataMinMaxTemperature();
        getDataMinMaxHumidity();
        getDataMinMaxWindSpeed();
        getDataMinMaxRainfall();
        getDataWeather();
        if (viewFlipper != null) {
            viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
            viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);

            viewFlipper.setAutoStart(true);
            viewFlipper.setFlipInterval(3000);
            viewFlipper.startFlipping();
        } else {
            Log.e("ViewFlipper", "ViewFlipper not found");
        }

        return view;
    }
    public void InitViews(View view)
    {
        tvHelloUser = view.findViewById(R.id.tvHiUsername);
        // Temp
        ivTemperature = view.findViewById(R.id.ivTemperature);
        tvMainTemp = view.findViewById(R.id.tvMainTemperature);
        tvMaxTemp = view.findViewById(R.id.tvMaxTemp);
        tvMinTemp = view.findViewById(R.id.tvMinTemp);
        tvItemTemp = view.findViewById(R.id.tvItemTemp);
        // Humidity
        ivHumidity = view.findViewById(R.id.ivHumidity);
        tvMainHumidity = view.findViewById(R.id.tvMainHumidity);
        tvMaxHumidity = view.findViewById(R.id.tvMaxHumidity);
        tvMinHumidity = view.findViewById(R.id.tvMinHumidity);
        tvItemHumidity = view.findViewById(R.id.tvItemHumidity);
        // Wind speed
        ivWind = view.findViewById(R.id.ivWindSpeed);
        tvMainWind = view.findViewById(R.id.tvMainWindSpeed);
        tvMaxWind = view.findViewById(R.id.tvMaxWindSpeed);
        tvMinWind = view.findViewById(R.id.tvMinWindSpeed);
        tvItemWind = view.findViewById(R.id.tvItemWindSpeed);
        // Rainfall
        ivRainfall = view.findViewById(R.id.ivRainfall);
        tvMainRainfall = view.findViewById(R.id.tvMainRainfall);
        tvMaxRainfall = view.findViewById(R.id.tvMaxRainfall);
        tvMinRainfall = view.findViewById(R.id.tvMinRainfall);
        tvItemRainfall = view.findViewById(R.id.tvItemRainfall);

    }
    public void InitEvent()
    {
        APIManager.getInfoUser(new DataUserCallback() {
            @Override
            public void onSuccess(String id, String username, String email, long createOn) {
                tvHelloUser.setText(username);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
        ivTemperature.setOnClickListener(v -> {
            showDialogBarTemperature();
        });

        ivHumidity.setOnClickListener(v -> {
            showDialogBarHumidity();
        });
        ivWind.setOnClickListener(v -> {
            showDialogBarWindSpeed();
        });
        ivRainfall.setOnClickListener(v -> {
            showDialogBarRainfall();
        });
    }
    public void showDialogBarRainfall()
    {
        DialogFragmentRainfall rainfallDialogFragment = DialogFragmentRainfall.newInstance();
        rainfallDialogFragment.show(getFragmentManager(), "rainfall_dialog");
    }
    public void showDialogBarWindSpeed()
    {
        DialogFragmentWindSpeed windSpeedDialogFragment = DialogFragmentWindSpeed.newInstance();
        windSpeedDialogFragment.show(getFragmentManager(), "windSpeed_dialog");
    }
    public void showDialogBarTemperature() {
        DialogFragmentTemperature temperatureDialogFragment = DialogFragmentTemperature.newInstance();
        temperatureDialogFragment.show(getFragmentManager(), "temperature_dialog");
    }
    public void showDialogBarHumidity() {
        DialogFragmentHumidity humidityDialogFragment = DialogFragmentHumidity.newInstance();
        humidityDialogFragment.show(getFragmentManager(), "humidity_dialog");
    }
    private JsonObject requestChartHour()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.getTimeInMillis();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        previousTimeDay = calendar.getTimeInMillis();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.getTimeInMillis();
        calendar1.set(Calendar.MINUTE, 59);
        calendar1.set(Calendar.HOUR_OF_DAY, 23);
        calendar1.set(Calendar.SECOND, 59);
        nextTimeDay = calendar1.getTimeInMillis();
        JsonObject request = new JsonObject();
        request.addProperty("type", GlobalVars.typeChart);
        request.addProperty("fromTimestamp", previousTimeDay);
        request.addProperty("toTimestamp", nextTimeDay);
        request.addProperty("amountOfPoints", GlobalVars.amountOfPointsChart);
        return request;
    }
    private JsonObject requestRainfall()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.getTimeInMillis();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        previousTimeDay = calendar.getTimeInMillis();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.getTimeInMillis();
        calendar1.set(Calendar.MINUTE, 59);
        calendar1.set(Calendar.HOUR_OF_DAY, 23);
        calendar1.set(Calendar.SECOND, 59);
        nextTimeDay = calendar1.getTimeInMillis();
        JsonObject request = new JsonObject();
        request.addProperty("type", GlobalVars.typeChart);
        request.addProperty("fromTimestamp", previousTimeDay);
        request.addProperty("toTimestamp", nextTimeDay);
        request.addProperty("amountOfPoints", GlobalVars.amountOfPointsChart);
        return request;
    }
    private List<Integer> findMinAndMaxValues(JsonArray array) {
        List<Pair<Long, Integer>> dataList = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            JsonObject jsonObject = array.get(i).getAsJsonObject();
            long timestamp = jsonObject.get("x").getAsLong();
            int value = jsonObject.get("y").getAsInt();
            dataList.add(new Pair<>(timestamp, value));
        }

        List<Integer> result = new ArrayList<>();

        if (!dataList.isEmpty()) {
            int minValue = dataList.get(0).second;
            int maxValue = dataList.get(0).second;

            for (int i = 1; i < dataList.size(); i++) {
                int currentValue = dataList.get(i).second;

                if (currentValue < minValue) {
                    minValue = currentValue;
                }

                if (currentValue > maxValue) {
                    maxValue = currentValue;
                }
            }
            result.add(minValue);
            result.add(maxValue);
        }
        return result;
    }
    private List<Float> findMinAndMaxValuesFloat(JsonArray array) {
        List<Pair<Long, Float>> dataList = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            JsonObject jsonObject = array.get(i).getAsJsonObject();
            long timestamp = jsonObject.get("x").getAsLong();
            float value = jsonObject.get("y").getAsFloat();
            dataList.add(new Pair<>(timestamp, value));
        }

        List<Float> result = new ArrayList<>();

        if (!dataList.isEmpty()) {
            float minValue = dataList.get(0).second;
            float maxValue = dataList.get(0).second;

            for (int i = 1; i < dataList.size(); i++) {
                float currentValue = dataList.get(i).second;

                if (currentValue < minValue) {
                    minValue = currentValue;
                }

                if (currentValue > maxValue) {
                    maxValue = currentValue;
                }
            }
            result.add(minValue);
            result.add(maxValue);
        }
        return result;
    }
    private void getDataMinMaxRainfall() {
        JsonObject request = requestRainfall();
        APIManager.getRainfallChart(request, new DataChartCallback() {
            @Override
            public void onSuccess(JsonArray data) {
                dataApi = data;
                List<Float> findMinMax = findMinAndMaxValuesFloat(dataApi);
                String minRainfall = String.join("", getString(R.string.min), String.valueOf(findMinMax.get(0)),
                        getString(R.string.mm));
                String maxRainfall = String.join("", getString(R.string.max), String.valueOf(findMinMax.get(1)),
                        getString(R.string.mm));
                tvMinRainfall.setText(minRainfall);
                tvMaxRainfall.setText(maxRainfall);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }
    private void getDataMinMaxWindSpeed() {
        JsonObject request = requestChartHour();
        APIManager.getWindSpeedChart(request, new DataChartCallback() {
            @Override
            public void onSuccess(JsonArray data) {
                dataApi = data;
                List<Float> findMinMax = findMinAndMaxValuesFloat(dataApi);
                String minWindSpeed = String.join("", getString(R.string.min), String.valueOf(findMinMax.get(0)),
                        getString(R.string.km_h));
                String maxWindSpeed = String.join("", getString(R.string.max), String.valueOf(findMinMax.get(1)),
                        getString(R.string.km_h));
                tvMinWind.setText(minWindSpeed);
                tvMaxWind.setText(maxWindSpeed);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }
    private void getDataMinMaxTemperature() {
        JsonObject request = requestChartHour();
        APIManager.getTemperatureChart(request, new DataChartCallback() {
            @Override
            public void onSuccess(JsonArray data) {
                dataApi = data;
                List<Integer> findMinMax = findMinAndMaxValues(dataApi);
                String minTemp = String.join("",getResources().getString(R.string.min),
                        String.valueOf(findMinMax.get(0)), getResources().getString(R.string.celsius));
                String maxTemp = String.join("", getResources().getString(R.string.max),
                        String.valueOf(findMinMax.get(1)), getResources().getString(R.string.celsius));
                tvMaxTemp.setText(minTemp);
                tvMinTemp.setText(maxTemp);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }
    private void getDataMinMaxHumidity() {
        JsonObject request = requestChartHour();
        APIManager.getHumidityChart(request, new DataChartCallback() {
            @Override
            public void onSuccess(JsonArray data) {
                dataApi = data;
                List<Integer> findMinMax = findMinAndMaxValues(dataApi);
                String minHumidity = String.join("", getString(R.string.min),
                        String.valueOf(findMinMax.get(0)), getString(R.string.percent));
                String maxHumidity = String.join("", getString(R.string.max),
                        String.valueOf(findMinMax.get(1)), getString(R.string.percent));
                tvMinHumidity.setText(minHumidity);
                tvMaxHumidity.setText(maxHumidity);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }
    private void getDataWeather()
    {
        APIManager.getTemperature(new DataTemperatureCallback() {
            @Override
            public void onSuccess(float temperature, int humidity, float windSpeed, int windDirection, float rainfall, String place, String manufacture, String nameDevice) {
                String temp = String.join("", String.valueOf((int)Math.floor(temperature)), getString(R.string.celsius));
                String humi = String.join("", String.valueOf(humidity), getString(R.string.percent));
                String wind = String.join("", String.valueOf(windSpeed), getString(R.string.km_h));
                String rain = String.join("", String.valueOf(rainfall), getString(R.string.mm));
                tvItemTemp.setText(temp);
                tvMainTemp.setText(temp);
                tvMainHumidity.setText(humi);
                tvItemHumidity.setText(humi);
                tvItemWind.setText(wind);
                tvMainWind.setText(wind);
                tvItemRainfall.setText(rain);
                tvMainRainfall.setText(rain);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }

}
