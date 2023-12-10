package com.uit.airsense.Model;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;


public class XAxisTimeFormatter extends ValueFormatter {
    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        long timestamp = (long)value;
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        String formattedDate = dateFormat.format(new Date(timestamp));
        return formattedDate;
    }
}
