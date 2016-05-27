package com.github.tonywills.loadingbutton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * A Simple to include Loading button that auto disables on loading, comes with nice defaults.
 * You can customise the animations by subclassing this class and overriding the animateToState method.
 * @author Anthony Williams (github.com/92tonywills)
 */
public class HorizontalLoadingButton extends LinearLayout {

    public enum State { DEFAULT, LOADING }
    public enum LoadingPosition { LEFT, RIGHT }

    // Members

    private LoadingPosition loadingPosition;
    private int buttonTintColor;
    private int loadingColor;
    private String defaultText;
    private String loadingText;

    private TextView textView;
    private ProgressBar loadingViewLeft;
    private ProgressBar loadingViewRight;
    private State buttonState;

    // Constructors

    public HorizontalLoadingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        buttonState = State.DEFAULT;
        pullXmlAttributes(context, attrs);
        setOrientation(HORIZONTAL);
        setBackgroundColor();
        LayoutInflater.from(context).inflate(R.layout.horizontal_loading_button, this, true);
    }

    private void setBackgroundColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setBackgroundTintList(new ColorStateList(
                    new int[][] {
                            ENABLED_STATE_SET,
                            new int[] {}
                    }, new int[] {
                    buttonTintColor,
                    getLessVibrantColor(buttonTintColor, 1.25f)
            }));
        } else {
            setBackgroundColor(buttonTintColor);
        }
    }

    private void pullXmlAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HorizontalLoadingButton, 0, 0);
        try {
            loadingPosition = a.getInt(R.styleable.HorizontalLoadingButton_loadingPosition, 0) == 0 ?
                    LoadingPosition.LEFT : LoadingPosition.RIGHT;
            buttonTintColor = a.getColor(R.styleable.HorizontalLoadingButton_buttonBackgroundTint, Color.rgb(255, 193, 7));
            loadingColor = a.getColor(R.styleable.HorizontalLoadingButton_loadingColor, Color.rgb(255, 193, 7));
            defaultText = a.getString(R.styleable.HorizontalLoadingButton_buttonTextDefault);
            loadingText = a.getString(R.styleable.HorizontalLoadingButton_buttonTextLoading);
        } finally {
            a.recycle();
        }
    }

    // Overrides

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        textView = (TextView) findViewById(R.id.loading_button_text);
        loadingViewLeft = (ProgressBar) findViewById(R.id.loading_button_spinner_left);
        loadingViewRight = (ProgressBar) findViewById(R.id.loading_button_spinner_right);

        textView.setText(defaultText);
        hideAllLoadingViews();
        setProgressBarColor();
    }

    private void hideAllLoadingViews() {
        loadingViewLeft.setVisibility(INVISIBLE);
        loadingViewRight.setVisibility(INVISIBLE);
        loadingViewLeft.setAlpha(0f);
        loadingViewRight.setAlpha(0f);
    }

    private void setProgressBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loadingViewLeft.setIndeterminateTintList(new ColorStateList(new int[][]{new int[] {}}, new int[]{loadingColor}));
            loadingViewRight.setIndeterminateTintList(new ColorStateList(new int[][]{new int[] {}}, new int[]{loadingColor}));
        }
    }

    // Accessors

    public TextView getTextView() {
        return textView;
    }

    public ProgressBar getLoadingView() {
        return loadingPosition == LoadingPosition.LEFT ? loadingViewLeft : loadingViewRight;
    }

    public boolean isLoading() {
        return buttonState == State.LOADING;
    }

    public void setLoading(boolean loading) {
        if (loading && buttonState == State.LOADING) { return; }
        buttonState = loading ? State.LOADING : State.DEFAULT;
        setEnabled(!loading);
        animateToState(buttonState);
    }

    public int getLoadingColor() {
        return loadingColor;
    }

    public void setLoadingColor(int color) {
        loadingColor = color;
        setProgressBarColor();
    }

    public void setButtonText(String text, State state) {
        if (state == State.DEFAULT) {
            defaultText = text;
        } else {
            loadingText = text;
        }
        textView.setText(buttonState == State.DEFAULT ? defaultText : loadingText);
    }

    public LoadingPosition getLoadingPosition() {
        return loadingPosition;
    }

    public void setLoadingPosition(LoadingPosition loadingPosition) {
        this.loadingPosition = loadingPosition;
        if (buttonState == State.LOADING) {
            hideAllLoadingViews();
            getLoadingView().setAlpha(1f);
            getLoadingView().setVisibility(VISIBLE);
        }
    }

    public int getButtonTintColor() {
        return buttonTintColor;
    }

    public void setButtonTintColor(int buttonTintColor) {
        this.buttonTintColor = buttonTintColor;
        setBackgroundColor();
    }

    // Animation

    /**
     * Action to perform in order to move the button to the new state. The new state is always different
     * to the current state.
     * Override this to perform your own animations. You can get the currently shown loading view
     * by calling getLoadingView().
     * @param newState The newState of the button, guaranteed to be different.
     */
    public void animateToState(State newState) {
        if (newState == State.LOADING) {
            showLoadingView();
        } else {
            hideLoadingView();
        }
    }

    private void showLoadingView() {
        textView.setText(loadingText);
        ProgressBar loadingView = getLoadingView();
        loadingView.setVisibility(VISIBLE);
        loadingView.setTranslationX(-loadingView.getMeasuredWidth());
        loadingView.animate().alpha(1).translationX(0);
    }

    private void hideLoadingView() {
        textView.setText(defaultText);
        ProgressBar loadingView = getLoadingView();
        loadingView.animate().alpha(0).translationX(loadingView.getMeasuredWidth() * 3);
    }

    // Static Helpers

    private static int getLessVibrantColor(int colour, float factor) {
        float[] hsv = new float[3];
        Color.colorToHSV(colour, hsv);
        hsv[1] *= 1 / factor;
        hsv[2] *= factor;
        return Color.HSVToColor(hsv);
    }

}

