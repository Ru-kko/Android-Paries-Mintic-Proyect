package com.example.reto.frags.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto.R;
import com.example.reto.crud.Favorites.FavoritesCrud;
import com.example.reto.frags.components.PartyAdapter;
import com.example.reto.models.Party;
import com.example.reto.restConsummer.PartyRest;

import java.util.List;

public class Parties extends Fragment {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch toggleFavorites;
    private RecyclerView content;
    private LinearLayout filter_options;
    private TextView maxPrice;

    private Integer productMaxPriceFavorite;
    private Integer productMaxPriceRest = 100;
    private Integer max_price_selected = 0;
    private FavoritesCrud favoriteDb;
    private PartyRest rest;
    private Thread restThread;
    private PartyAdapter adapter;

    public Parties(){
        super(R.layout.parties_page);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.parties_page, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.favoriteDb = new FavoritesCrud(this.getActivity());
        this.rest = new PartyRest(requireContext());
        this.adapter = new PartyAdapter(view);

        this.productMaxPriceRest = rest.getMaxPrice();
        this.productMaxPriceFavorite = favoriteDb.getMaxPrice();

        this.content = view.findViewById(R.id.parties_list);
        this.filter_options = view.findViewById(R.id.filter_options);
        this.toggleFavorites = view.findViewById(R.id.show_favorites_toggle_btn);
        this.maxPrice = view.findViewById(R.id.max_price_count);

        ImageButton filter_show = view.findViewById(R.id.show_filter);
        SeekBar maxPriceLine = view.findViewById(R.id.max_price);
        Button fill = view.findViewById(R.id.confirm_fill);

        this.toggleFavorites.setOnClickListener(this::checkButtonListener);
        this.content.setLayoutManager(new LinearLayoutManager(getContext()));
        this.content.setAdapter(this.adapter);
        filter_show.setOnClickListener(this::setFilterVisibility);
        fill.setOnClickListener(this::confirmFill);


        requireActivity().setTitle("Parties");
        maxPriceLine.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int price;
                if (toggleFavorites.isChecked())
                    price = productMaxPriceFavorite / 100;
                else
                    price = productMaxPriceRest / 100;
                max_price_selected = price * i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}

            @SuppressLint("SetTextI18n")
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(max_price_selected != 0)
                    maxPrice.setText(String.valueOf(max_price_selected));
                else
                    maxPrice.setText("All");
            }
        });

        checkButtonListener(null);
        confirmFill(null);
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
        checkButtonListener(null);
        this.productMaxPriceFavorite = favoriteDb.getMaxPrice();
    }

    private void setFilterVisibility(View view){
        if(filter_options.getVisibility() == View.VISIBLE)
            filter_options.setVisibility(View.GONE);
        else
            filter_options.setVisibility(View.VISIBLE);
    }
    private void confirmFill(@Nullable View view){
        if(restThread != null) this.restThread.interrupt();

        if(toggleFavorites.isChecked()) {
            adapter.change(favoriteDb.getPartiesWhen(max_price_selected));
            content.setAdapter(adapter);
            productMaxPriceFavorite = favoriteDb.getMaxPrice();
        } else {

            restThread = new Thread(){
                @Override
                public void run() {
                    adapter.clear();
                    List<Party> newContent = rest.getItems(max_price_selected);

                    requireActivity().runOnUiThread(() -> adapter.change(newContent));
                }
            };
            restThread.start();
        }
        filter_options.setVisibility(View.GONE);
    }
    private void checkButtonListener(@Nullable View view){

        ConnectivityManager cm = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (!isConnected && !toggleFavorites.isChecked()) {
            toggleFavorites.setChecked(true);

            if (view != null)
                Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT).show();
        }
        if (!toggleFavorites.isChecked())
            this.productMaxPriceRest = rest.getMaxPrice();
    }
}
