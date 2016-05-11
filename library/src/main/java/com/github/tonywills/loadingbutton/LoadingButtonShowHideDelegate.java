package com.github.tonywills.loadingbutton;

import android.view.View;

public interface LoadingButtonShowHideDelegate {
    void hideLoadingView(View view);
    void showLoadingView(View view);
    void showTextView(View view);
    void hideTextView(View view);
}
