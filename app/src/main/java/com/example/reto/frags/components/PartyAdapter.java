package com.example.reto.frags.components;


import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.reto.R;
import com.example.reto.models.Party;

import java.util.List;


public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.ViewHolder> {
    private List<Party> content;
    private final View container;

    public PartyAdapter(View container){
        this.container = container;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void change(List<Party> content){
        this.content = content;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vw = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_party_card, parent, false);
        return new ViewHolder(vw);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(
                content.get(position),
                container
        );
    }

    @Override
    public int getItemCount() {
        if (content != null)
            return content.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textName;
        TextView textPrice;
        TextView textAddress;
        ImageButton image;
        Button moreBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moreBtn = itemView.findViewById(R.id.moreInformation);
            textName = itemView.findViewById(R.id.CardPartyName);
            textPrice = itemView.findViewById(R.id.CardPrice);
            textAddress = itemView.findViewById(R.id.CardAddress);
            image = itemView.findViewById(R.id.partyCardImage);
        }
        public void bind(Party party, View container){
            this.textName.setText(party.getName());
            this.textAddress.setText(party.getAddress());
            this.textPrice.setText(String.valueOf(party.getPrice()));
            this.image.setImageBitmap(party.getImage());

            this.moreBtn.setOnClickListener(View -> {
                Bundle info = new Bundle();
                info.putParcelable("info" , party);
                Navigation.findNavController(container).navigate(R.id.action_parties2_to_onePartyPage, info);
            });
        }
    }
}