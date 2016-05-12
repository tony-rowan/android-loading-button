package com.github.tonywills.loadingbutton;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/**
 * A Simple to use Layout that can switch between its children.
 * Provides a delegate customise transitions.
 * @author Anthony Williams (github.com/92tonywills)
 */
public class ViewSwitcher extends FrameLayout {

    private static final String TAG = "ViewSwitcher";

    @Nullable private AnimationDelegate animationDelegate;
    private int childOnShowIndex;

    public ViewSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 0) { Log.d(TAG, "onFinishInflate: No children attached yet"); }
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(i == 0 ? VISIBLE : INVISIBLE);
        }
    }

    /**
     * Calls the animation delegate to animate in the child view at the given index and animate out
     * the currently shown view. If no animation delegate is attached, they are simply set to VISIBLE / INVISIBLE.
     * @param index The index of the child - the order in the xml layout or the order in which they were added.
     */
    public void showViewAtIndex(int index) {
        hideCurrentlyShowView();
        childOnShowIndex = index;
        showCurrentlyShownView();
    }

    private void hideCurrentlyShowView() {
        if (animationDelegate == null) {
            getChildAt(childOnShowIndex).setVisibility(INVISIBLE);
        } else {
            animationDelegate.onHideViewAtIndex(getChildAt(childOnShowIndex), childOnShowIndex);
        }
    }

    private void showCurrentlyShownView() {
        if (animationDelegate == null) {
            getChildAt(childOnShowIndex).setVisibility(VISIBLE);
        } else {
            animationDelegate.onShowViewAtIndex(getChildAt(childOnShowIndex), childOnShowIndex);
        }
    }

    /**
     * Use this to provide custom animations through the delegate.
     * Animations are called at the same time, this way staggering is optional and left up to you.
     * @param animationDelegate The delegate providing animations.
     */
    public void setAnimationDelegate(@Nullable AnimationDelegate animationDelegate) {
        this.animationDelegate = animationDelegate;
    }

    public interface AnimationDelegate {
        void onHideViewAtIndex(View view, int index);
        void onShowViewAtIndex(View view, int index);
    }

}
