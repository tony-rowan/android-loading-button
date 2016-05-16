package com.github.tonywills.loadingbutton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * A Simple to include Loading button with nice defaults.
 * You can customise the animations by subclassing the button and overriding the animateToState method.
 * @author Anthony Williams (github.com/92tonywills)
 */
public class LoadingButton extends LinearLayout {

    public enum State {
        DEFAULT, LOADING
    }

    // Members

    private int buttonColor;
    private int loadingColor;
    private String defaultText;
    private String loadingText;

    private View textView;
    private View loadingView;
    private State buttonState;

    // Constructors

    public LoadingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.compound_view_loading_button, this, true);
        buttonState = State.DEFAULT;
    }

    // Overrides

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        textView = findViewById(R.id.loading_button_text);
        loadingView = findViewById(R.id.loading_button_spinner);

        loadingView.setVisibility(GONE);
    }

    // Accessors

    public boolean isLoading() {
        return buttonState == State.DEFAULT;
    }

    public void setLoading(boolean loading) {
        this.buttonState = loading ? State.LOADING : State.DEFAULT;
        animateToState(this.buttonState);
    }

    // xml based

    public void setButtonColor(int color) {

    }

    public void setLoadingColor(int color) {

    }

    public void setButtonText(String text, State state) {

    }

    // Animation

    public void animateToState(State newState) {
        if (newState == State.DEFAULT) {
            showLoadingView();
        } else {
            hideLoadingView();
        }
    }

    private void showLoadingView() {
        loadingView.setVisibility(VISIBLE);
        loadingView.setTranslationX(0);
        loadingView.animate().alpha(1).translationX(loadingView.getMeasuredWidth());
    }

    private void hideLoadingView() {
        loadingView.animate().alpha(0).translationX(loadingView.getMeasuredWidth() * 2);
    }

}

