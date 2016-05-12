package com.github.tonywills.loadingbutton;

import android.animation.Animator;
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
    private boolean isAnimatingTextView;
    private boolean isAnimatingLoadingView;

    // Constructors

    public LoadingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.compound_view_loading_button, this, true);
        setAnimationDelegate(this);
        loading = false;
    }

    // Behaviours

    // Overrides

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        textView = findViewById(R.id.loading_button_text);
        loadingView = findViewById(R.id.loading_button_spinner);
    }

    // Helpers

    // Accessors

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        showViewAtIndex(loading ? 1 : 0);
        setEnabled(!loading);
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
        if (isAnimatingTextView) {
            textView.setAlpha(1);
            textView.setTranslationX(0);
            textView.setVisibility(VISIBLE);
        }
        isAnimatingTextView = true;
        textView.setVisibility(VISIBLE);
        textView.animate().translationX(0).alpha(1).setListener(new SimpleAnimatorListener() {
            @Override public void onAnimationEnd(Animator animation) {
                isAnimatingTextView = false;
            }
        }).start();
    }

    private void hideTextView() {
        if (isAnimatingTextView) {
            textView.setAlpha(0);
            textView.setTranslationX(-textView.getMeasuredWidth() * 2);
            textView.setVisibility(INVISIBLE);
        }
        isAnimatingTextView = true;
        textView.animate().translationX(-textView.getMeasuredWidth() * 2).alpha(0)
                .setListener(new SimpleAnimatorListener() {
                    @Override public void onAnimationEnd(Animator animation) {
                        textView.setVisibility(INVISIBLE);
                        isAnimatingTextView = false;
                    }
                }).start();
    }

    private void showLoadingView() {
        if (isAnimatingLoadingView) {
            loadingView.setAlpha(1);
            loadingView.setVisibility(VISIBLE);
        }
        isAnimatingLoadingView = true;
        loadingView.setVisibility(VISIBLE);
        loadingView.animate().alpha(1).setListener(new SimpleAnimatorListener() {
            @Override public void onAnimationEnd(Animator animation) {
                isAnimatingLoadingView = false;
            }
        }).start();
    }

    private void hideLoadingView() {
        if (isAnimatingLoadingView) {
            loadingView.setAlpha(0);
            loadingView.setVisibility(INVISIBLE);
        }
        isAnimatingLoadingView = true;
        loadingView.animate().alpha(0).setListener(new SimpleAnimatorListener() {
            @Override public void onAnimationEnd(Animator animation) {
                loadingView.setVisibility(INVISIBLE);
                isAnimatingLoadingView = false;
            }
        }).start();
    }

}

