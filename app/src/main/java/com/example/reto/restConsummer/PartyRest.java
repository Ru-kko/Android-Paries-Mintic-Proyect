package com.example.reto.restConsummer;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.reto.R;
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

    public void getItems(int max, ResponseListener responseVoid) {

        String request = max == 0 ? url : url + "/" + max;

        RequestQueue queue = Volley.newRequestQueue(this.ctx);

        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                request,
                null,
                _response -> {
                    try {
                        List<Party> response = new ArrayList<>();
                        JSONArray items = _response.getJSONArray("items");

                        for (int i = 0; i < items.length(); i++) {
                            Party actual = new Party();

                            actual.setId(items.getJSONObject(i).getInt("id"));
                            actual.setName(items.getJSONObject(i).getString("name"));
                            actual.setDescription(items.getJSONObject(i).getString("description"));
                            actual.setAddress(items.getJSONObject(i).getString("address"));
                            actual.setPrice(items.getJSONObject(i).getInt("price"));

                            RequestQueue innerQueue = Volley.newRequestQueue(ctx);
                            ImageRequest imgReq = new ImageRequest(
                                    items.getJSONObject(i).getString("image"),
                                    actual::setImage,
                                    0 , 0,
                                    ImageView.ScaleType.CENTER,
                                    Bitmap.Config.ARGB_8888,
                                    error -> actual.setImage(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_launcher_foreground))
                            );
                            innerQueue.add(imgReq);

                            response.add(actual);
                        }

                        responseVoid.doSomething(response);
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
        final Integer maxPrice = this.maxPrice;
        setMaxPrice();
        return this.maxPrice != null ? this.maxPrice  : maxPrice != null ? maxPrice : 1000;
    }

    public interface ResponseListener{
        void doSomething(List<Party> content);
    }
}
