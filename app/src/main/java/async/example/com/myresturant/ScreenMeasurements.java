package async.example.com.myresturant;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Administrator on 23/11/2017.
 */

public class ScreenMeasurements {

    private int height;
    private int width;

    public ScreenMeasurements(Context context, int margins){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        this.height = displayMetrics.heightPixels - margins * 2;
        this.width = displayMetrics.widthPixels - margins * 2;
    }

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
}
