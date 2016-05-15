package com.github.tonywills.loadingbutton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * A Simple to include Loading button with nice defaults.
 * Uses a {@link ViewSwitcher} under the hood so you can customise the animations by
 * setting your own delegate.
 * @author Anthony Williams (github.com/92tonywills)
 */
public class LoadingButton extends ViewSwitcher implements ViewSwitcher.AnimationDelegate {

    // Members

    private View textView;
    private View loadingView;
    private boolean loading;

    // Constructors

    public LoadingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.compound_view_loading_button, this, true);
        setAnimationDelegate(this);
        loading = false;
    }

    // Overrides

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        textView = findViewById(R.id.loading_button_text);
        loadingView = findViewById(R.id.loading_button_spinner);
    }

    // Accessors

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        showViewAtIndex(loading ? 1 : 0);
    }

    // Animation Delegate

    @Override public void onHideViewAtIndex(View view, int index) {
        if (index == 0) {
            hideTextView();
        } else {
            hideLoadingView();
        }
    }

    @Override public void onShowViewAtIndex(View view, int index) {
        if (index == 0) {
            showTextView();
        } else {
            showLoadingView();
        }
    }

    private void showTextView() {
        textView.setVisibility(VISIBLE);
        textView.animate().translationX(0).alpha(1);
    }

    private void hideTextView() {
        textView.animate().translationX(-textView.getMeasuredWidth() * 2).alpha(0);
    }

    private void showLoadingView() {
        loadingView.setVisibility(VISIBLE);
        loadingView.animate().alpha(1);
    }

    private void hideLoadingView() {
        loadingView.animate().alpha(0);
    }

}

