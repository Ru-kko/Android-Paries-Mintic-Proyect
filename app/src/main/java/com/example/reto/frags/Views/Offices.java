package com.example.reto.frags.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reto.BuildConfig;
import com.example.reto.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class Offices extends Fragment {
    MapView map;
    MapController mapController;

    public Offices(){
        super(R.layout.offices_page);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        map = view.findViewById(R.id.offices_map);
        mapController = (MapController) map.getController();

        map.getZoomController().setZoomInEnabled(true);
        map.getZoomController().setZoomOutEnabled(true);
        map.setMultiTouchControls(true);
        mapController.setZoom(16f);

        ArrayList<OverlayItem> points = new ArrayList<>();

        points.add(new OverlayItem("Office 1st Mayo", "", new GeoPoint(4.601050, -74.122368)));
        points.add(new OverlayItem("Office Chapinero", "", new GeoPoint(4.650567, -74.063160)));


        final MyLocationNewOverlay myLocation = new MyLocationNewOverlay(new GpsMyLocationProvider(requireContext()), map);
        map.getOverlays().add(myLocation);
        myLocation.enableMyLocation();

        ItemizedIconOverlay.OnItemGestureListener<OverlayItem> tap = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemLongPress(int arg0, OverlayItem arg1) {
                return false;
            }
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true;
            }
        };


        ItemizedOverlayWithFocus<OverlayItem> cap = new ItemizedOverlayWithFocus<>(requireContext(), points, tap);

        cap.setFocusItemsOnTap(true);
        map.getOverlays().add(cap);

        GeoPoint Bogota = new GeoPoint(4.6482837,-74.2478938);
        mapController.setCenter(Bogota);
    }

}
