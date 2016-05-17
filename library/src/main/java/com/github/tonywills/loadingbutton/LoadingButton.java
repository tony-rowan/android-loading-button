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
 * A Simple to include Loading button with nice defaults.
 * You can customise the animations by subclassing this class and overriding the animateToState method.
 * @author Anthony Williams (github.com/92tonywills)
 */
public class LoadingButton extends LinearLayout {

    public enum State { DEFAULT, LOADING }

    // Members

    private int loadingColor;
    private String defaultText;
    private String loadingText;

    private TextView textView;
    private ProgressBar loadingView;
    private State buttonState;

    // Constructors

    public LoadingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.loading_button, this, true);
        buttonState = State.DEFAULT;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0, 0);
        try {
            loadingColor = a.getColor(R.styleable.LoadingButton_loadingColor, Color.rgb(255, 193, 7));
            defaultText = a.getString(R.styleable.LoadingButton_buttonTextDefault);
            loadingText = a.getString(R.styleable.LoadingButton_buttonTextLoading);
        } finally {
            a.recycle();
        }
    }

    // Overrides

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        textView = (TextView) findViewById(R.id.loading_button_text);
        loadingView = (ProgressBar) findViewById(R.id.loading_button_spinner);

        textView.setText(defaultText);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loadingView.setIndeterminateTintList(new ColorStateList(new int[][]{}, new int[]{loadingColor}));
        }
    }

    // Accessors

    public boolean isLoading() {
        return buttonState == State.LOADING;
    }

    public void setLoading(boolean loading) {
        this.buttonState = loading ? State.LOADING : State.DEFAULT;
        animateToState(this.buttonState);
    }

    // xml based

    public void setLoadingColor(int color) {
        loadingColor = color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loadingView.setIndeterminateTintList(new ColorStateList(new int[][]{}, new int[]{loadingColor}));
        }
    }

    public void setButtonText(String text, State state) {
        if (state == State.DEFAULT) {
            defaultText = text;
        } else {
            loadingText = text;
        }
        textView.setText(buttonState == State.DEFAULT ? defaultText : loadingText);
    }

    // Animation

    public void animateToState(State newState) {
        if (newState == State.LOADING) {
            showLoadingView();
        } else {
            hideLoadingView();
        }
    }

    private void showLoadingView() {
        textView.setText(loadingText);
        loadingView.setTranslationX(-loadingView.getMeasuredWidth());
        loadingView.animate().alpha(1).translationX(0);
    }

    private void hideLoadingView() {
        textView.setText(defaultText);
        loadingView.animate().alpha(0).translationX(loadingView.getMeasuredWidth() * 3);
    }

}

