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

import java.util.ArrayList;
import java.util.List;

public class Parties extends Fragment {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch toggleFavorites;
    private RecyclerView content;
    private LinearLayout filter_options;
    private TextView maxPrice;
    private View this_fragment;

    private Integer productMaxPrice;
    private Integer max_price_selected = 0;
    private FavoritesCrud favoriteDb;

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

        this.this_fragment = view;
        this.content = view.findViewById(R.id.parties_list);
        this.filter_options = view.findViewById(R.id.filter_options);
        this.toggleFavorites = view.findViewById(R.id.show_favorites_toggle_btn);
        this.maxPrice = view.findViewById(R.id.max_price_count);
        this.productMaxPrice = favoriteDb.getMaxPrice();

        ImageButton filter_show = view.findViewById(R.id.show_filter);
        SeekBar maxPriceLine = view.findViewById(R.id.max_price);
        Button fill = view.findViewById(R.id.confirm_fill);

        this.toggleFavorites.setOnClickListener(this::checkButtonListener);
        this.content.setLayoutManager(new LinearLayoutManager(getContext()));
        filter_show.setOnClickListener(this::setFilterVisibility);
        fill.setOnClickListener(this::confirmFill);


        maxPriceLine.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int price = productMaxPrice / 100;
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
        productMaxPrice = favoriteDb.getMaxPrice();
        checkButtonListener(null);
        super.onResume();
    }

    private void setFilterVisibility(View view){
        if(filter_options.getVisibility() == View.VISIBLE)
            filter_options.setVisibility(View.GONE);
        else
            filter_options.setVisibility(View.VISIBLE);
    }
    private void  confirmFill(@Nullable View view){
        if(toggleFavorites.isChecked()) {
            PartyAdapter adapter;
            adapter = new PartyAdapter(favoriteDb.getPartiesWhen(max_price_selected), this_fragment);
            content.setAdapter(adapter);
            productMaxPrice = favoriteDb.getMaxPrice();
        } else {
            // TODO consume rest api
            testInfo();
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
    }
    /**
     *  Simulate rest consumer
     */
    private void testInfo(){
        List<Party> cnt = new ArrayList<>();

        cnt.add(new Party(0, 10000, "Test Name", "nan", "desc"));
        cnt.add(new Party(1, 1000, "Test Name 2", "nan", "desc"));
        cnt.add(new Party(2, 200, "Test Name 3", "nan", "desc"));
        cnt.add(new Party(3, 100, "Test Name 5", "nan", "desc"));
        cnt.add(new Party(3, 500, "Test Name 6", "nan", "desc"));
        cnt.add(new Party(3, 100, "Test Name 7", "nan", getResources().getString(R.string.large_text)));

        PartyAdapter adapter = new PartyAdapter(cnt, this_fragment);
        content.setAdapter(adapter);
    }
}
