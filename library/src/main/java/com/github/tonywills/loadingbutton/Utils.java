package com.github.tonywills.loadingbutton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

public abstract class Utils extends View {

    private Utils(Context context) { super(context); } // HACK: Allows use to access ENABLED_STATE_SET

    public static void setBackgroundColor(View view, int tintColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setBackgroundTintList(new ColorStateList(
                    new int[][] {
                            ENABLED_STATE_SET,
                            new int[] {}
                    }, new int[] {
                    tintColor,
                    Utils.getLessVibrantColor(tintColor, 1.25f)
            }));
        } else {
            view.setBackgroundColor(tintColor);
        }
    }

    public static int getLessVibrantColor(int colour, float factor) {
        float[] hsv = new float[3];
        Color.colorToHSV(colour, hsv);
        hsv[1] *= 1 / factor;
        hsv[2] *= factor;
        return Color.HSVToColor(hsv);
    }

}
