package async.example.com.myresturant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ArrayList<DishInfo> items;
    ArrayList<DishInfo> selectedItems;

    private int clickCounter;
    private DataItemAdapter adapter;
    RecyclerView recyclerView;

    private String JSONFileToString() {

        InputStream is = getResources().openRawResource(R.raw.menuitems);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];

        try {

            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;

            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return writer.toString();
    }

    private void setAdapter(){

        selectedItems = new ArrayList<>(items.subList(0, clickCounter));

        //setting recycler view
        adapter = new DataItemAdapter(selectedItems, this);
        recyclerView = (RecyclerView)findViewById(R.id.rvItems);
        recyclerView.setAdapter(adapter);

        //setting swipe action result
        ItemTouchHelper.Callback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0 ,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter

                if(direction == ItemTouchHelper.RIGHT){

                    selectedItems.get(viewHolder.getAdapterPosition()).switchPhase();
                    recyclerView.setAdapter(adapter);

                }
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    private int findDishIndexByName(ArrayList<DishInfo> selectedItems, String name){

        int i = 0;

        for(DishInfo dishInfo: selectedItems){

            if(dishInfo.getName().equals(name)){

                return i;
            }

            ++i;
        }

        return -1;
    }

    private void switchToNextDish(){

        DishInfo nextDish;

        String itemName = getIntent().getExtras().getString("name");
        int itemIndex = findDishIndexByName(items, itemName);
        int nextItemDir = getIntent().getExtras().getInt("next");

        int nextItemIndex = itemIndex + nextItemDir;

        if(nextItemIndex < 0 || nextItemIndex >= items.size()){

            nextDish = items.get(itemIndex);

        } else {

            nextDish = items.get(nextItemIndex);
        }

        Intent picZoom = new Intent(this, PicDisplay.class);
        picZoom.putExtra("picURL", nextDish.getPicURL());
        picZoom.putExtra("name", nextDish.getName());

        startActivity(picZoom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.title);
        tv.setTypeface(Typeface.createFromAsset(getAssets(), "Anabelle Script.ttf"));

        MenuItemsJSONParser parser = new MenuItemsJSONParser(JSONFileToString());
        items = parser.getItems();
        Collections.sort(items);

        if(getIntent().getExtras() != null){
            switchToNextDish();
        }

        ImageView plus = findViewById(R.id.plus);
        clickCounter = 0;

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ++clickCounter;

                setAdapter();
            }
        });

    }
}
