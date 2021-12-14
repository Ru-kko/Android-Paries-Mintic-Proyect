package com.example.reto.frags.components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto.R;
import com.example.reto.models.Party;
import com.example.reto.models.Service;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.viewHolder> {
    private final List<Service> content;
    private final View container;

    public ServiceAdapter(List<Service> content, View container) {
        this.content = content;
        this.container = container;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vw = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_services_card, parent, false);
        return new viewHolder(vw);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.bind(content.get(position));
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private final ImageView image;
        private final TextView name;
        private final TextView price;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.service_image);
            this.name = itemView.findViewById(R.id.service_name);
            this.price = itemView.findViewById(R.id.service_price);
        }
        public void bind(Service content){
            this.name.setText(content.getName());
            this.price.setText(String.valueOf(content.getPrice()));
            this.image.setImageDrawable(content.getImage());
        }
    }
}
