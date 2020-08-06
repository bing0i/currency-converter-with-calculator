package com.example.currencyconverter;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class GetRatesTask {
    private static Context context;
    JSONObject ratesObj = null;
    private static String jsonString = "";
    private static String baseCurrency = "";
    private static String date = "";
    private static double result = 0;

    public GetRatesTask(Context ct) {
        context = ct;
    }

    public void getJSON () {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://api.exchangeratesapi.io/latest";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        jsonString = String.valueOf(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Fail to get JSON", Toast.LENGTH_SHORT);
                    }
                });

        queue.add(jsonObjectRequest);
    }

    public void getRates(String currency) {
        try {
            ratesObj = new JSONObject(jsonString);
            baseCurrency = ratesObj.getString("base");
            date = ratesObj.getString("date");
            ratesObj = ratesObj.getJSONObject("rates");
        } catch (JSONException e) {
            Toast.makeText(context, "Fail to parse JSON", Toast.LENGTH_SHORT);
        }
    }
}
