package com.example.reto.frags.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto.R;
import com.example.reto.frags.components.ServiceAdapter;
import com.example.reto.models.Service;

import java.util.ArrayList;
import java.util.List;

public class Services extends Fragment {
    private RecyclerView content;

    public Services(){
        super(R.layout.more_services_page);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().setTitle("Services");
        this.content = view.findViewById(R.id.services_rec);
        this.content.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        putInformation();
    }

    private void putInformation(){
        List<Service> cnt = new ArrayList<>();

        cnt.add(new Service(
                "Dj",
                AppCompatResources.getDrawable(requireContext(), R.drawable.dj_ico),
                1500));
        cnt.add(new Service(
                "Chef",
                AppCompatResources.getDrawable(requireContext(), R.drawable.chef_ico),
                3000));
        cnt.add(new Service(
                "Chef",
                AppCompatResources.getDrawable(requireContext(), R.drawable.singer_ico),
                3000));
        cnt.add(new Service(
                "Chef",
                AppCompatResources.getDrawable(requireContext(), R.drawable.waiter_ico),
                1200));

        ServiceAdapter adapter = new ServiceAdapter(cnt, requireView());
        content.setAdapter(adapter);
    }
}
