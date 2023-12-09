package com.uit.airsense.Fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraBoundsOptions;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.plugin.Plugin;
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.AnnotationType;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;

import com.uit.airsense.API.APIManager;
import com.uit.airsense.HomeActivity;
import com.uit.airsense.Interface.DataTemperatureCallback;
import com.uit.airsense.Model.Map;
import com.uit.airsense.Model.TemperatureDataFloat;
import com.uit.airsense.Model.TemperatureDataInt;
import com.uit.airsense.Model.TemperatureDataString;
import com.uit.airsense.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentMap extends Fragment {
    HomeActivity homeActivity;
    MapView mapView;
    Map mapData;
    static MapboxMap mapboxMap;
    private boolean firstTime = true;
    AnnotationPlugin annoPlugin;
    AnnotationConfig annoConfig;
    PointAnnotationManager pointAnnoManager;
    public String lastSelectedId = "";

    public FragmentMap(HomeActivity activity)
    {
        this.homeActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        InitViews(view);
        InitEvent();
        super.onViewCreated(view, savedInstanceState);
        mapView.setVisibility(View.INVISIBLE);
        new Thread(() -> {
            while (!Map.isReady)
            {
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            homeActivity.runOnUiThread(this::setMapView);
        }).start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }
    public void InitViews(View v) {
        mapView = v.findViewById(R.id.mapView);
    }
    public void InitEvent() {

    }
    private void setMapView() {
        Point point = Point.fromLngLat(106.80280655508835, 10.869778736885038);
        Point point1 = Point.fromLngLat(106.80345028525176, 10.869905172970164);
        mapData = Map.getMapObj();
        mapboxMap = mapView.getMapboxMap();

        if (mapboxMap != null) {
            mapboxMap.loadStyleJson(Objects.requireNonNull(new Gson().toJson(mapData)), style -> {
                style.removeStyleLayer("poi-level-1");
                style.removeStyleLayer("highway-name-major");

                annoPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
                annoConfig = new AnnotationConfig("map_annotation");
                pointAnnoManager = (PointAnnotationManager) annoPlugin.createAnnotationManager(AnnotationType.PointAnnotation, annoConfig);
                pointAnnoManager.addClickListener(pointAnnotation -> {
                    String id = Objects.requireNonNull(pointAnnotation.getData()).getAsJsonObject().get("id").getAsString();
                    bottomSheet(id);
                    return true;
                });

                // Create point annotations
                createPointAnnotation(point, "5zI6XqkQVSfdgOrZ1MyWEf", R.drawable.ic_pin_green);
                createPointAnnotation(point1, "6iWtSbgqMQsVq8RPkJJ9vo", R.drawable.ic_pin_green);

                // Set camera values
                setCameraValues();

                CameraAnimationsPlugin cameraAnimationsPlugin = mapView.getPlugin(Plugin.MAPBOX_CAMERA_PLUGIN_ID);
                if (cameraAnimationsPlugin != null) {
                    mapView.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void createPointAnnotation(Point point, String id, int iconResource) {
        ArrayList<PointAnnotationOptions> markerList = new ArrayList<>();
        Drawable iconDrawable = getResources().getDrawable(iconResource);
        Bitmap iconBitmap = drawableToBitmap(iconDrawable);
        JsonObject idDeviceTemperature = new JsonObject();
        idDeviceTemperature.addProperty("id", id);
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(point)
                .withIconImage(iconBitmap)
                .withData(idDeviceTemperature);
        markerList.add(pointAnnotationOptions);
        pointAnnoManager.create(markerList);
    }

    private void setCameraValues() {
        if (mapboxMap != null) {
            mapboxMap.setCamera(
                    new CameraOptions.Builder()
                            .center(mapData.getCenter())
                            .zoom(mapData.getZoom())
                            .build()
            );

            mapboxMap.setBounds(
                    new CameraBoundsOptions.Builder()
                            .minZoom(mapData.getMinZoom())
                            .maxZoom(mapData.getMaxZoom())
                            .bounds(mapData.getBounds())
                            .build()
            );
        }
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
    private void bottomSheet(String id)
    {
        if("5zI6XqkQVSfdgOrZ1MyWEf".equals(id))
        {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireActivity(), R.style.CustomDialogTheme);
            View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_temperature, null);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetView.setBackgroundResource(R.drawable.bg_dialog);
            bottomSheetDialog.setCanceledOnTouchOutside(true);
            bottomSheetDialog.show();

            TextView tvHumidity = bottomSheetDialog.findViewById(R.id.tvValueHumidity);
            TextView tvManufacturer = bottomSheetDialog.findViewById(R.id.tvValueManufacturer);
            TextView tvPlace = bottomSheetDialog.findViewById(R.id.tvValuePlace);
            TextView tvRainfall = bottomSheetDialog.findViewById(R.id.tvValueRainfall);
            TextView tvTemperature = bottomSheetDialog.findViewById(R.id.tvValueTemperature);
            TextView tvWindDirection = bottomSheetDialog.findViewById(R.id.tvValueWindDirection);
            TextView tvWindSpeed = bottomSheetDialog.findViewById(R.id.tvValueWindSpeed);
            APIManager.getTemperature(new DataTemperatureCallback() {
                @Override
                public void onSuccess(float temperature, int humidity, float windSpeed, int windDirection, float rainfall, String place, String manufacture, String nameDevice) {
                    tvHumidity.setText(String.valueOf(humidity));
                    tvPlace.setText(place);
                    tvManufacturer.setText(manufacture);
                    tvTemperature.setText(String.valueOf(temperature));
                    tvWindDirection.setText(String.valueOf(windDirection));
                    tvWindSpeed.setText(String.valueOf(windSpeed));
                    tvRainfall.setText(String.valueOf(rainfall));
                }

                @Override
                public void onFailure(String errorMessage) {

                }
            });
            bottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            bottomSheetDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            bottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        }
    }
}
