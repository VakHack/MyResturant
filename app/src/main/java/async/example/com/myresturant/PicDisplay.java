package async.example.com.myresturant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PicDisplay extends AppCompatActivity {

    final int RIGHT = -1;
    final int LEFT = 1;

    private void setAndCallIntent(int direction){

        Intent back = new Intent(PicDisplay.this, MainActivity.class);
        back.putExtra("name", getIntent().getExtras().getString("name"));
        back.putExtra("next", direction);

        startActivity(back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_display);

        ScreenMeasurements sm = new ScreenMeasurements(this.getApplicationContext(), 10);

        final ImageView image = findViewById(R.id.pic);
        TextView name = (TextView)findViewById(R.id.dish);
        name.setTypeface(Typeface.createFromAsset(getAssets(), "CzaristiteBold.ttf"));

        name.setText(getIntent().getExtras().getString("name"));
        String picURL = getIntent().getExtras().getString("picURL");

        Picasso.with(getApplicationContext())
                .load(picURL)
                .resize(sm.getWidth(), sm.getHeight())
                .centerCrop()
                .into(image);


        findViewById(R.id.pic).setOnTouchListener(new OnSwipeTouchListener(this){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return super.onTouch(v, event);
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();

                setAndCallIntent(RIGHT);
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();

                setAndCallIntent(LEFT);
            }

            @Override
            public void onSwipeTop() {
                super.onSwipeTop();
            }

            @Override
            public void onSwipeBottom() {
                super.onSwipeBottom();
            }

        });
    }
}
