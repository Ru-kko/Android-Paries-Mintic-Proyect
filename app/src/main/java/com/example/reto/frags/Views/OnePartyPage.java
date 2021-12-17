package com.example.reto.frags.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reto.R;
import com.example.reto.crud.Favorites.FavoritesCrud;
import com.example.reto.models.Party;


public class OnePartyPage extends Fragment {
    TextView name;
    TextView price;
    TextView description;
    TextView address;
    ImageButton fav_btn;
    ImageView image;

    Boolean favorite;
    Party info;
    public OnePartyPage(){
        super(R.layout.one_party_page);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.info = (Party) (getArguments() != null ? getArguments().getParcelable("info") : null);

        this.name = view.findViewById(R.id.party_pg_name);
        this.price = view.findViewById(R.id.party_pg_price);
        this.address = view.findViewById(R.id.party_pg_address);
        this.description = view.findViewById(R.id.party_pg_desc);
        this.image = view.findViewById(R.id.partyImage);

        fav_btn = view.findViewById(R.id.favorite_btn);
        fav_btn.setOnClickListener(this::favorite);

        assert info != null;
        name.setText(info.getName());
        price.setText(String.valueOf(info.getPrice()));
        address.setText(info.getAddress());
        image.setImageBitmap(info.getImage());
        description.setText(info.getDescription());


        /*  _________________________________ QUERY _________________________________  */

        FavoritesCrud query = new FavoritesCrud(this.getActivity());
        favorite = query.exist(info.getId());

        if(favorite)
            fav_btn.setColorFilter(getResources().getColor(R.color.yellow));
        else
            fav_btn.setColorFilter(getResources().getColor(R.color.white));
    }
    public void favorite(View view){
        if (favorite){
            new FavoritesCrud(this.getActivity()).remove(info.getId());
            fav_btn.setColorFilter(getResources().getColor(R.color.white));
        } else {
            new FavoritesCrud(this.getActivity()).add(info);
            fav_btn.setColorFilter(getResources().getColor(R.color.yellow));
        }

        favorite = !favorite;
    }
}
