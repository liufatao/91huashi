package com.huashi.app.util;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/23.
 */
public class JsonArrayPostRequest extends Request<JSONArray> {
    private Map<String,String> mMap;
    private Response.Listener<JSONArray> mListener;

    public JsonArrayPostRequest(String url,Response.Listener<JSONArray> listener, Response.ErrorListener errorListener,Map map) {
        super(Request.Method.POST, url, errorListener);
        mListener=listener;
        mMap=map;

        // TODO Auto-generated constructor stub
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        // TODO Auto-generated method stub
        return mMap;
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONArray(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONArray response) {
        mListener.onResponse(response);

    }
}
