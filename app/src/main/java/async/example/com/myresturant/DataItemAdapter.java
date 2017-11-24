package async.example.com.myresturant;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 22/11/2017.
 */

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.ViewHolder> {

    private List<DishInfo> items;
    private Context context;

    public DataItemAdapter(List<DishInfo> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public DataItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataItemAdapter.ViewHolder holder, int position) {

        final DishInfo item = items.get(position);

        holder.itemName.setTypeface(Typeface.createFromAsset(context.getAssets(), "CzaristiteBold.ttf"));
        holder.itemPrice.setTypeface(Typeface.createFromAsset(context.getAssets(), "CzaristiteBold.ttf"));
        holder.calories.setTypeface(Typeface.createFromAsset(context.getAssets(), "CzaristiteBold.ttf"));

        ScreenMeasurements sm = new ScreenMeasurements(context, 0);
        Picasso.with(context)
                .load(item.getPicURL())
                .resize(sm.getWidth(), 300)
                .centerCrop()
                .into(holder.itemPic);

        holder.itemName.setText(item.getName());
        holder.itemPrice.setText(String.valueOf(item.getPrice()) + " NIS");
        holder.calories.setText(String.valueOf(item.getCalories()) + " calories");

        if(!item.isBasePhase()){

            holder.itemName.setVisibility(View.INVISIBLE);
            holder.itemPrice.setVisibility(View.INVISIBLE);

            holder.calories.setVisibility(View.VISIBLE);
        }

        holder.itemPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent picZoom = new Intent(context, PicDisplay.class);
                picZoom.putExtra("picURL", item.getPicURL());
                picZoom.putExtra("name", item.getName());

                context.startActivity(picZoom);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView itemPrice;
        TextView calories;
        ImageView itemPic;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);

            itemName = (TextView)itemView.findViewById(R.id.dishName);
            itemPrice = (TextView)itemView.findViewById(R.id.dishPrice);
            itemPic = (ImageView)itemView.findViewById(R.id.dishPic);
            calories = (TextView)itemView.findViewById(R.id.calories);

            view = itemView;
        }
    }

}
