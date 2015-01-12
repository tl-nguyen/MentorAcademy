package com.tlnguyen.classdemo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by TL on 1/12/2015.
 */
public class JSONArrayInterable  implements Iterable<JSONObject> {

    private JSONArray employees;

//    @TargetApi(Build.VERSION_CODES.KITKAT)
    public JSONArrayInterable(JSONArray array) throws JSONException {
//        super(array);
        this.employees = (JSONArray) array;
    }

    @Override
    public Iterator<JSONObject> iterator() {
        JSONArrayInterator i = new JSONArrayInterator(employees);
        return i;
    }
}
