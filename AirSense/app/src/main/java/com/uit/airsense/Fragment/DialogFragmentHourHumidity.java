package com.uit.airsense.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.uit.airsense.API.APIManager;
import com.uit.airsense.Interface.DataChartCallback;
import com.uit.airsense.Model.GlobalVars;
import com.uit.airsense.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DialogFragmentHourHumidity extends DialogFragment {
    AppCompatButton btTimePicker;
    long current;
    JsonArray dataChart;
    GraphView graphView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_hour, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitViews(view);
        InitEvent();
    }
    private void InitViews(View v)
    {
        btTimePicker = v.findViewById(R.id.btTimePicker);
        graphView = v.findViewById(R.id.lineChartTemperature);
    }
    private void InitEvent() {
        Calendar timeDefault = Calendar.getInstance();
        timeDefault.set(Calendar.SECOND, 0);
        timeDefault.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String currentTime = dateFormat.format(timeDefault.getTime());
        btTimePicker.setText(currentTime);
        current = timeDefault.getTimeInMillis();
        loadHumidityData(current); // Gọi API ban đầu khi Fragment được khởi tạo

        btTimePicker.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            final int selectedYear = year;
                            final int selectedMonth = monthOfYear;
                            final int selectedDay = dayOfMonth;

                            TimePickerDialog timePickerDialog = new TimePickerDialog(
                                    requireContext(),
                                    new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            final int selectedHour = hourOfDay;
                                            final int selectedMinute = minute;

                                            Calendar selectedCalendar = Calendar.getInstance();
                                            selectedCalendar.set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute);
                                            long selectedTimeInMillis = selectedCalendar.getTimeInMillis();
                                            current = selectedTimeInMillis;
                                            // Update the button text with selected date and time
                                            String selectedDateTime = String.format(Locale.getDefault(), "%02d/%02d/%d %02d:%02d", selectedMonth + 1, selectedDay, selectedYear, selectedHour, selectedMinute);
                                            btTimePicker.setText(selectedDateTime);

                                            loadHumidityData(selectedTimeInMillis); // Gọi API khi người dùng chọn ngày và giờ mới
                                        }
                                    },
                                    hour,
                                    minute,
                                    true // set true if you want 24-hour time, false for AM/PM
                            );
                            timePickerDialog.show(); // Show the TimePickerDialog after selecting the date
                        }
                    },
                    year,
                    month,
                    dayOfMonth
            );

            datePickerDialog.show(); // Show the DatePickerDialog
        });

    }

    // Phương thức để gọi API với thời gian mới và cập nhật dữ liệu
    private void loadHumidityData(long time) {
        JsonObject newRequest = requestChartHour(time);
        APIManager.getHumidityChart(newRequest, new DataChartCallback() {
            @Override
            public void onSuccess(JsonArray data) {
                dataChart = data;
                setupChart();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private JsonObject requestChartHour(long timeCurrent)
    {
        JsonObject request = new JsonObject();
        request.addProperty("type", GlobalVars.typeChart);
        request.addProperty("fromTimestamp", timeCurrent - (3600 * 1000));
        request.addProperty("toTimestamp", timeCurrent + (3600 * 1000));
        request.addProperty("amountOfPoints", GlobalVars.amountOfPointsChart);
        return request;
    }
    private List<Pair<Long, Float>> fnSetTime(JsonArray array) {
        List<Pair<Long, Float>> dataList = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            JsonObject jsonObject = array.get(i).getAsJsonObject();
            long timestamp = jsonObject.get("x").getAsLong();
            float value = jsonObject.get("y").getAsFloat();
            dataList.add(new Pair<>(timestamp, value));
        }

        return dataList;
    }
    private void setupChart() {
        List<Pair<Long, Float>> dataList = fnSetTime(dataChart);
        PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>();
        if (dataList != null && !dataList.isEmpty()) {
            int dataSize = dataList.size();
            for (int i = 0; i < dataSize; i++) {
                Pair<Long, Float> pair = dataList.get(i);
                series.appendData(new DataPoint(pair.first, pair.second), true, dataSize);
            }
        } else {
            System.out.println("dataList is empty or null.");
        }

        // Customize the series appearance
        series.setColor(Color.RED);
        series.setSize(5);
        graphView.removeAllSeries();
        graphView.addSeries(series);
        graphView.setCursorMode(true);
        Animation animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left);
        animation.setDuration(1000);
        graphView.setAnimation(animation);

        graphView.getGridLabelRenderer().setNumHorizontalLabels(12);
        graphView.getGridLabelRenderer().setNumVerticalLabels(10);
        GridLabelRenderer gridLabelRenderer = graphView.getGridLabelRenderer();
        gridLabelRenderer.setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    long unixTimestamp = (long) value;
                    Date date = new Date(unixTimestamp);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    return simpleDateFormat.format(date);
                } else {
                    return super.formatLabel(value, isValueX);
                }
            }
        });

        // Set x-axis boundaries if needed
        if (!dataList.isEmpty()) {
            long minX = current - (3600 + 60) * 1000;
            long maxX = current  + (60 * 1000);
            graphView.getViewport().setXAxisBoundsManual(true);
            graphView.getViewport().setMinX(minX);
            graphView.getViewport().setMaxX(maxX);

        }

        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(100);
        graphView.invalidate();
        gridLabelRenderer.setTextSize(15);
    }
}
