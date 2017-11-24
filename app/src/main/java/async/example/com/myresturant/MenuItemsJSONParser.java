package async.example.com.myresturant;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 22/11/2017.
 */

public class MenuItemsJSONParser {

    private String json;
    private ArrayList<DishInfo> items;

    MenuItemsJSONParser(String json){

        this.json = json;
        this.items = new ArrayList<>();

        try {
            parse();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parse() throws JSONException {

        JSONArray arr = new JSONArray(json);

        for(int i = 0; i < arr.length(); ++i){

            JSONObject object = arr.getJSONObject(i);
            DishInfo item = new DishInfo();

            item.setName(object.getString("name"));
            item.setPicURL(object.getString("picURL"));
            item.setPrice(object.getInt("price"));
            item.setCalories(object.getInt("calories"));

            items.add(item);
        }
    }

    public ArrayList<DishInfo> getItems() {
        return items;
    }
}
