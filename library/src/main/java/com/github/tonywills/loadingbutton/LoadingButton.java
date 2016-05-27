package com.github.tonywills.loadingbutton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoadingButton extends RelativeLayout {

    // Members

    private int buttonTintColor;
    private int loadingColor;
    private String buttonText;

    private TextView textView;
    private ProgressBar loadingView;
    private LoadingButtonState buttonState;

    // Constructors

    public LoadingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        buttonState = LoadingButtonState.DEFAULT;
        pullXmlAttributes(context, attrs);
        Utils.setBackgroundColor(this, buttonTintColor);
        LayoutInflater.from(context).inflate(R.layout.loading_button, this, true);
    }

    private void pullXmlAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0, 0);
        try {
            buttonTintColor = a.getColor(R.styleable.LoadingButton_buttonBackgroundTint, Color.rgb(255, 193, 7));
            loadingColor = a.getColor(R.styleable.LoadingButton_loadingColor, Color.rgb(255, 193, 7));
            buttonText = a.getString(R.styleable.LoadingButton_buttonText);
        } finally {
            a.recycle();
        }
    }

    // Overrides

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        textView = (TextView) findViewById(R.id.loading_button_text);
        loadingView = (ProgressBar) findViewById(R.id.loading_button_spinner);

        textView.setText(buttonText);
        hideAllLoadingViews();
        setProgressBarColor();
    }

    private void hideAllLoadingViews() {
        loadingView.setVisibility(INVISIBLE);
        loadingView.setAlpha(0f);
        loadingView.setScaleX(0);
        loadingView.setScaleY(0);
    }

    private void setProgressBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loadingView.setIndeterminateTintList(new ColorStateList(new int[][]{new int[] {}}, new int[]{loadingColor}));
        }
    }

    // Accessors

    public TextView getTextView() {
        return textView;
    }

    public ProgressBar getLoadingView() {
        return loadingView;
    }

    public boolean isLoading() {
        return buttonState == LoadingButtonState.LOADING;
    }

    public void setLoading(boolean loading) {
        if (loading && buttonState == LoadingButtonState.LOADING) { return; }
        buttonState = loading ? LoadingButtonState.LOADING : LoadingButtonState.DEFAULT;
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

    public void setButtonText(String text, LoadingButtonState loadingButtonState) {
        buttonText = text;
        textView.setText(buttonText);
    }

    public int getButtonTintColor() {
        return buttonTintColor;
    }

    public void setButtonTintColor(int buttonTintColor) {
        this.buttonTintColor = buttonTintColor;
        Utils.setBackgroundColor(this, buttonTintColor);
    }

    // Animation

    /**
     * Action to perform in order to move the button to the new state. The new state is always different
     * to the current state. Override this to perform your own animations.
     * @param newLoadingButtonState The newLoadingButtonState of the button, guaranteed to be different.
     */
    public void animateToState(LoadingButtonState newLoadingButtonState) {
        if (newLoadingButtonState == LoadingButtonState.LOADING) {
            showLoadingView();
            hideTextView();
        } else {
            hideLoadingView();
            showTextView();
        }
    }

    private void hideTextView() {
        textView.animate().alpha(0).translationY(textView.getMeasuredHeight());
    }

    private void showTextView() {
        textView.animate().alpha(1).translationY(0);
    }

    private void showLoadingView() {
        loadingView.setVisibility(VISIBLE);
        loadingView.setScaleX(0);
        loadingView.setScaleY(0);
        loadingView.animate().alpha(1).scaleX(1).scaleY(1);
    }

    private void hideLoadingView() {
        loadingView.animate().alpha(0).scaleX(3).scaleY(3);
    }

}
