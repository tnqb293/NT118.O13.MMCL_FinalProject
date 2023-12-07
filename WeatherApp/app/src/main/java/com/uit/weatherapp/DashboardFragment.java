package com.uit.weatherapp;

import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.uit.weatherapp.API.APIManager;
import com.uit.weatherapp.Interface.DataLoadedCallback;
import com.uit.weatherapp.Interface.TokenCallback;
import com.uit.weatherapp.model.WeatherData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DashboardFragment extends Fragment implements DataLoadedCallback {
    private View rootView;
    private float xDelta, yDelta;
    private float startX = 0;
    private Fragment chartFragment;
    private static final int MIN_SWIPE_DISTANCE = 150;
    HomeActivity homeActivity;
    CardView cvImageUser;
    ImageView ivImageUser, ivImageSunny, ivImageCalendar, ivLocation, ivImageSunrise;
    ImageView ivImageSunset, ivImageRainfall, ivImageWindSpeed, ivImageHumidity;
    TextView tvHelloUser, tvStatusWeather, tvTemperature, tvDayofWeek, tvMonthYear;
    TextView tvLocation, tvLocationUser, tvWeather, tvSunrise, tvTimeSunrise;
    TextView tvSunset, tvTimeSunset, tvRainfall, tvValueRainfall, tvWindSpeed;
    TextView tvValueWindSpeed, tvHumidity, tvValueHumidity;
    View vItemTemperature, vItemDayofWeek, vItemLocation, vItemSunrise, vItemSunset;
    View vItemRainfall, vItemWindSpeed, vItemHumidity;
    ViewFlipper viewFlipper;
    Calendar calendar;
    @Override
    public void onDataLoaded(WeatherData weatherData) {
        // Use the humidity and temperature values from weatherData
        String humidity = String.join(" ", weatherData.getHumidity(), getResources().getString(R.string.percent));
        String temperature = String.join(" ", weatherData.getTemperature(), getResources().getString(R.string.celsius));
        String rainfall = String.join(" ", weatherData.getRainfall(), getResources().getString(R.string.mm));
        String windSpeed = String.join(" ", weatherData.getWindSpeed(), getResources().getString(R.string.km_h));
        // Update your fragment's UI with the values
        tvValueHumidity.setText(humidity);
        tvTemperature.setText(temperature);
        tvValueRainfall.setText(rainfall);
        tvValueWindSpeed.setText(windSpeed);
        calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy", Locale.forLanguageTag(GlobalVars.day));
        SimpleDateFormat days = new SimpleDateFormat("EEEE", Locale.forLanguageTag(GlobalVars.day));
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        String formattedDate = dateFormat.format(calendar.getTime());
        String formattedDateOfWeek = days.format(calendar.getTime());
        tvMonthYear.setText(formattedDate);
        tvDayofWeek.setText(formattedDateOfWeek);
    }

    public DashboardFragment(HomeActivity activity)
    {
        this.homeActivity = activity;
    }
    public DashboardFragment() {

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        APIManager.getToken(GlobalVars.username, GlobalVars.password, new TokenCallback() {
            @Override
            public void onSuccess(String token) {
                APIManager.getData(DashboardFragment.this);

                new Thread(() -> {
//                    APIManager.getMap();
                }).start();
                String welcomeUser = String.join(" ", getResources().getString(R.string.username_default), GlobalVars.username);
                tvHelloUser.setText(welcomeUser);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
//        APIManager.getData(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        InitVars();
        InitViews(view);
        InitEvent();
//        tvValueHumidity.setText(APIClient.humidity);

        super.onViewCreated(view, savedInstanceState);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Xử lý logic dựa trên sự kiện chạm tại đây
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Xử lý khi người dùng chạm vào màn hình
                        Log.d("Action", String.valueOf(event.getAction()));
                        startX = event.getX(); // Lưu vị trí X khi bắt đầu swipe
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // Xử lý khi người dùng di chuyển trên màn hình
                        float endX = event.getX(); // Vị trí X hiện tại trong lúc di chuyển
                        float deltaX = endX - startX;
                        if (Math.abs(deltaX) > MIN_SWIPE_DISTANCE) {
                            if (deltaX < 0) {
                                // Xử lý khi swipe sang trái
                                homeActivity.replaceFragmentRightToLeft(chartFragment);
                                homeActivity.navBar.selectTabAt(1, true);
                            } else {
                                // Xử lý khi swipe sang phải
//                                homeActivity.replaceFragmentLeftToRight(chartFragment);

                            }
                            startX = endX; // Cập nhật vị trí bắt đầu mới
                            return true; // Xử lý sự kiện swipe
                        }
                        Log.d("Action", String.valueOf(event.getAction()));

                        break;
                    case MotionEvent.ACTION_UP:
                        // Xử lý khi người dùng nhấc ngón tay khỏi màn hình
                        Log.d("Action", String.valueOf(event.getAction()));

                        break;
                }
                // Trả về true nếu bạn đã xử lý sự kiện, false nếu không xử lý
                return true;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_dashboard, container, false);
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        viewFlipper = view.findViewById(R.id.vf_item_sunrise);
        LayoutInflater fragmentInflater = LayoutInflater.from(getActivity());
        viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);

// Bắt đầu hoặc dừng tự động chuyển đổi giữa các views
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.startFlipping();
        return view;
    }

    private void InitViews(View v) {
        vItemHumidity = v.findViewById(R.id.v_item_humidity);
        vItemDayofWeek = v.findViewById(R.id.v_item_dayOfWeek);
        vItemLocation = v.findViewById(R.id.v_item_location);
        vItemRainfall = v.findViewById(R.id.v_item_rainfall);
//        vItemSunrise = v.findViewById(R.id.v_item_sunrise);
        vItemTemperature = v.findViewById(R.id.v_item_temperature);
        vItemSunset = v.findViewById(R.id.v_item_sunset);
        vItemWindSpeed = v.findViewById(R.id.v_item_wind_speed);
        tvDayofWeek = v.findViewById(R.id.tv_dayOfWeek);
        tvHumidity = v.findViewById(R.id.tv_humidity);
        tvHelloUser = v.findViewById(R.id.tv_hello_user);
        tvLocation = v.findViewById(R.id.tv_location);
        tvRainfall = v.findViewById(R.id.tv_rainfall);
        tvLocationUser = v.findViewById(R.id.tv_location_user);
        tvMonthYear = v.findViewById(R.id.tv_month_year);
        tvStatusWeather = v.findViewById(R.id.tv_status_weather);
//        tvSunrise = v.findViewById(R.id.tv_sunrise);
        tvSunset = v.findViewById(R.id.tv_sunset);
        tvTemperature = v.findViewById(R.id.tv_temperature);
//        tvTimeSunrise = v.findViewById(R.id.tv_time_sunrise);
        tvTimeSunset = v.findViewById(R.id.tv_time_sunset);
        tvDayofWeek = v.findViewById(R.id.tv_dayOfWeek);
        tvValueHumidity = v.findViewById(R.id.tv_value_humidity);
        tvValueRainfall = v.findViewById(R.id.tv_value_rainfall);
        tvValueWindSpeed = v.findViewById(R.id.tv_value_windspeed);
        tvWeather = v.findViewById(R.id.tv_weather);
        tvWindSpeed = v.findViewById(R.id.tv_windspeed);
        ivImageCalendar = v.findViewById(R.id.iv_image_calendar);
        ivImageHumidity = v.findViewById(R.id.iv_image_humidity);
        ivImageRainfall = v.findViewById(R.id.iv_image_rainfall);
        ivImageSunny = v.findViewById(R.id.iv_image_sunny);
        ivImageSunrise = v.findViewById(R.id.iv_image_sunrise);
        ivImageSunset = v.findViewById(R.id.iv_image_sunset);
        ivImageUser = v.findViewById(R.id.iv_image_user);
        ivImageWindSpeed = v.findViewById(R.id.iv_image_windspeed);
        ivLocation = v.findViewById(R.id.iv_location);
        cvImageUser = v.findViewById(R.id.cv_image_user);
//        viewFlipper = v.findViewById(R.id.vf_item_sunrise);
    }
    private void InitEvent() {
    }
    private void InitVars() {
        chartFragment = new ChartFragment(homeActivity);
    }
}
