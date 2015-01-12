package com.tlnguyen.classdemo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by TL on 1/12/2015.
 */
public class JSONArrayInterator implements Iterator<JSONObject> {

    private final JSONArray array;
    private int current;

    public JSONArrayInterator(JSONArray array) {
        this.array = array;
        this.current = 0;
    }

    @Override
    public boolean hasNext() {
        return current < array.length();
    }

    @Override
    public JSONObject next() {
        if (hasNext()) {
            try {
                return array.getJSONObject(current++);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public void remove() {

    }
}
