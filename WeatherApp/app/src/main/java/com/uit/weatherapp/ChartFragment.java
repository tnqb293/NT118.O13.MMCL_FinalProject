package com.uit.weatherapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
import com.mapbox.maps.plugin.attribution.AttributionPlugin;
import com.mapbox.maps.plugin.logo.LogoPlugin;
import com.mapbox.maps.plugin.scalebar.ScaleBarPlugin;
import com.uit.weatherapp.API.APIManager;
import com.uit.weatherapp.model.Device;
import com.uit.weatherapp.model.Map;

import java.util.ArrayList;
import java.util.Objects;

public class ChartFragment extends Fragment {
    HomeActivity homeActivity;
    ImageView ivChart;
    MapView mapView;
    Map mapData;
    static MapboxMap mapboxMap;
    private boolean firstTime = true;

    public ChartFragment() {}
    public ChartFragment(HomeActivity activity)
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
//        APIManager.getMap();

//        tvValueHumidity.setText(APIClient.humidity);
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
        return inflater.inflate(R.layout.fragment_chart, container, false);

    }

    public void InitViews(View v) {
        mapView = v.findViewById(R.id.mapView);
    }
    public void InitEvent() {

    }
    private void setMapView() {
        mapData = Map.getMapObj();
        ScaleBarPlugin scaleBarPlugin = mapView.getPlugin(Plugin.MAPBOX_SCALEBAR_PLUGIN_ID);
        assert scaleBarPlugin != null;
        scaleBarPlugin.setEnabled(false);
//
        LogoPlugin logoPlugin = mapView.getPlugin(Plugin.MAPBOX_LOGO_PLUGIN_ID);
        assert logoPlugin != null;
        logoPlugin.setEnabled(false);
//
        AttributionPlugin attributionPlugin = mapView.getPlugin(Plugin.MAPBOX_ATTRIBUTION_PLUGIN_ID);
        assert attributionPlugin != null;
        attributionPlugin.setEnabled(false);

        mapboxMap = mapView.getMapboxMap();
        mapboxMap.loadStyleJson(Objects.requireNonNull(new Gson().toJson(mapData)), style -> {

            style.removeStyleLayer("poi-level-1");
            style.removeStyleLayer("highway-name-major");

            // Get the annotation plugin instance
            AnnotationPlugin annoPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
            AnnotationConfig annoConfig = new AnnotationConfig("map_annotation");
            PointAnnotationManager pointAnnoManager = (PointAnnotationManager) annoPlugin.createAnnotationManager(AnnotationType.PointAnnotation, annoConfig);

            // Add click listener to the annotation manager
            pointAnnoManager.addClickListener(pointAnnotation -> {
                String id = Objects.requireNonNull(pointAnnotation.getData()).getAsJsonObject().get("id").getAsString();
//                toggleBottomSheet(id);
//                setBottomSheet1(id);

                return true;
            });

            // Add device markers to the map
            ArrayList<PointAnnotationOptions> markerList = new ArrayList<>();



            pointAnnoManager.create(markerList);
        });
        mapboxMap.setCamera(
                new CameraOptions.Builder()
                        .center(mapData.getCenter())
                        .zoom(mapData.getZoom())
                        .build()
        );

        // Set camera bounds
        mapboxMap.setBounds(
                new CameraBoundsOptions.Builder()
                        .minZoom(mapData.getMinZoom())
                        .maxZoom(mapData.getMaxZoom())
                        .bounds(mapData.getBounds())
                        .build()
        );

        // Set camera animation
        CameraAnimationsPlugin cameraAnimationsPlugin = mapView.getPlugin(Plugin.MAPBOX_CAMERA_PLUGIN_ID);
        assert cameraAnimationsPlugin != null;
//        if (!isHidden()) onHiddenChanged(false);
        mapView.setVisibility(View.VISIBLE);
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        if (firstTime && Map.isReady && !hidden) {
            firstTime = false;
            Utils.delayHandler.postDelayed(() -> mapView.setVisibility(View.VISIBLE), 200);
        }
        super.onHiddenChanged(hidden);
    }
}
