package com.example.reto.frags.Views;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.example.reto.BuildConfig;
import com.example.reto.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.InfoWindow;


public class Offices extends Fragment {
    MapView map;
    MapController mapController;
    ViewGroup container;

    public Offices(){
        super(R.layout.offices_page);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.container = container;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        requireActivity().setTitle("Offices");
        map = view.findViewById(R.id.offices_map);
        mapController = (MapController) map.getController();

        map.getZoomController().setZoomInEnabled(true);
        map.getZoomController().setZoomOutEnabled(true);
        map.setMultiTouchControls(true);
        mapController.setZoom(14f);


        Inf_window[] cap_ = {
                new Inf_window("1st Mayo", new GeoPoint(4.601050, -74.122368)),
                new Inf_window("Chapinero", new GeoPoint(4.650567, -74.063160))
        };

        for (Inf_window i: cap_) {
            Marker marker = new Marker(map, requireContext());
            Drawable ico = AppCompatResources.getDrawable(requireContext() ,R.drawable.pin_ico);


            assert ico != null;
            ico.setTint(getResources().getColor(R.color.purple_contrast));
            marker.setPosition(i.point);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setInfoWindow(i);

            marker.setIcon(ico);
            map.getOverlays().add(marker);
        }

        GeoPoint Bogota = new GeoPoint(4.6482837,-74.2478938);
        mapController.setCenter(Bogota);
    }

    private class Inf_window extends InfoWindow{
        public GeoPoint point;
        private final String name;

        public Inf_window(String name, GeoPoint point){
            super(R.layout.map_info_win, map);
            this.point = point;
            this.name = name;
        }

        @Override
        public void onOpen(Object item) {
            View view = getView();
            TextView nameView = view.findViewById(R.id.map_inf_txt);
            nameView.setText(name);
            closeAllInfoWindowsOn(map);

        }

        @Override
        public void onClose() {

        }
    }
}
