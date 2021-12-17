package com.example.reto.restConsummer;


import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.reto.models.Party;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class PartyRest {
    private final String url = "https://g369d88cd72d64e-parties.adb.sa-saopaulo-1.oraclecloudapps.com/ords/admin/api/parties";
    private final Context ctx;
    private Integer maxPrice;

    public PartyRest(Context ctx) {
        this.ctx = ctx;
    }


    public List<Party> getItems(int max) {

        List<Party> response = new ArrayList<>();
        String request = max == 0 ? url : url + "/" + max;

        RequestQueue queue = Volley.newRequestQueue(this.ctx);

        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                request,
                null,
                _response -> {
                    try {
                        JSONArray items = _response.getJSONArray("items");

                        for (int i = 0; i < items.length(); i++) {
                            Party actual = new Party();

                            actual.setId(items.getJSONObject(i).getInt("id"));
                            actual.setName(items.getJSONObject(i).getString("name"));
                            actual.setDescription(items.getJSONObject(i).getString("description"));
                            actual.setAddress(items.getJSONObject(i).getString("address"));
                            actual.setPrice(items.getJSONObject(i).getInt("price"));

                            response.add(actual);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(ctx, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    System.out.println(error.getMessage());
                }

        );

        queue.add(req);
        return response;
    }

    private void setMaxPrice() {
        RequestQueue queue = Volley.newRequestQueue(this.ctx);

        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                url + "/maxprice",
                null,
                _response -> {
                    try {
                        maxPrice = _response.getJSONArray("items").getJSONObject(0).getInt("price");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(ctx, "Check your internet connection", Toast.LENGTH_SHORT).show()
        );
        queue.add(req);
    }

    public Integer getMaxPrice(){
        setMaxPrice();
        return maxPrice != null ? maxPrice : 1000;
    }
}
