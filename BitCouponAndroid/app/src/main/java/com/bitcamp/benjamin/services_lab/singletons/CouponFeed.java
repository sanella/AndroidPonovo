package com.bitcamp.benjamin.services_lab.singletons;

import android.util.Log;

import com.bitcamp.benjamin.services_lab.Coupon;
import com.bitcamp.benjamin.services_lab.Post;
import com.bitcamp.benjamin.services_lab.service.ServiceRequest;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by benjamin on 14/04/15.
 */
public class CouponFeed {
    private static CouponFeed ourInstance = new CouponFeed();

    public static CouponFeed getInstance() {
        return ourInstance;
    }

    private ArrayList<Coupon> mFeed;

    private CouponFeed() {
        mFeed = new ArrayList<Coupon>();
    }

    public void getFeed(String url){
        ServiceRequest.get(url, parseResponse());
    }

    public ArrayList<Coupon> getFeed(){

        return mFeed;
    }

    private Callback parseResponse(){
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("RESPONSE", e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    for(int i = 0; i < array.length(); i++){
                        JSONObject postObj = array.getJSONObject(i);
                        int id = postObj.getInt("id");
                        String name = postObj.getString("nema");
                        double price = postObj.getDouble("price");
                        String dateCreated = postObj.getString("dateCreated");
                        String dateExpire = postObj.getString("dateExpire");
                        String picture = postObj.getString("picture");
                        String category = postObj.getString("category");
                        String description = postObj.getString("description");
                        String remark = postObj.getString("remark");
                        int minOrder = postObj.getInt("minOrder");
                        int maxOrder = postObj.getInt("maxOrder");
                        String usage = postObj.getString("usage");
                        String seller = postObj.getString("seller");

                        mFeed.add(new Coupon(id, name, price, dateCreated, dateExpire, picture, category, description, remark, minOrder, maxOrder, usage, seller ));




                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }
}
