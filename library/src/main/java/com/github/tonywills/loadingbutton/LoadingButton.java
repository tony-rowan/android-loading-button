package com.github.tonywills.loadingbutton;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoadingButton extends RelativeLayout
        implements LoadingButtonFindViewsDelegate, LoadingButtonShowHideDelegate {

    // Members

    @NonNull private LoadingButtonFindViewsDelegate findViewsDelegate;
    @NonNull private LoadingButtonShowHideDelegate showHideDelegate;
    @Nullable private View textView;
    @Nullable private View progressBar;
    private boolean loading;

    // Constructors

    public LoadingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        loading = false;
        findViewsDelegate = this;
        showHideDelegate = this;
    }

    // Behaviours

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        if (loading) {
            showHideDelegate.hideTextView(textView);
            showHideDelegate.showLoadingView(progressBar);
        } else {
            showHideDelegate.showTextView(textView);
            showHideDelegate.hideLoadingView(progressBar);
        }
    }

    // Overrides

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        pullViewsFromDelegate();
    }

    // Helpers

    private void pullViewsFromDelegate() {
        textView = findViewsDelegate.getTextView();
        progressBar = findViewsDelegate.getLoadingView();
        makeViewsMatchLoadingState();
    }

    private void makeViewsMatchLoadingState() {
        if (textView != null) {
            textView.setAlpha(loading ? 0 : 1);
        }
        if (progressBar != null) {
            progressBar.setAlpha(loading ? 1 : 0);
        }
    }

    // Accessors

    public void setFindViewsDelegate(@NonNull LoadingButtonFindViewsDelegate findViewsDelegate) {
        this.findViewsDelegate = findViewsDelegate;
        pullViewsFromDelegate();
    }

    public void setShowHideDelegate(@NonNull LoadingButtonShowHideDelegate showHideDelegate) {
        this.showHideDelegate = showHideDelegate;
    }

    public void unsetFindDelegate() {
        this.findViewsDelegate = this;
        pullViewsFromDelegate();
    }

    public void unsetShowHideDelegate() {
        this.showHideDelegate = this;
    }

    // LoadingButtonFindViews Delegate

    @Override public View getTextView() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof TextView) {
                return child;
            }
        }

        return null;
    }

    @Override public View getLoadingView() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof ProgressBar) {
                return child;
            }
        }

        return null;
    }

    // LoadingButtonShowHideDelegate

    @Override public void hideLoadingView(View view) {
        if (view != null) {
            view.animate().alpha(0).start();
        }
    }

    @Override public void showLoadingView(View view) {
        if (view != null) {
            view.animate().alpha(1).start();
        }
    }

    @Override public void showTextView(View view) {
        if (view != null) {
            view.setTranslationX(view.getMeasuredWidth() * 2);
            view.animate().translationX(0).alpha(1).start();
        }
    }

    @Override public void hideTextView(View view) {
        if (view != null) {
            view.animate().translationX(-view.getMeasuredWidth() * 2).alpha(0).start();
        }
    }

}

