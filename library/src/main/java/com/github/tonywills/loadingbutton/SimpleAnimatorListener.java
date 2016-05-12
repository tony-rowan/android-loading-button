package com.github.tonywills.loadingbutton;

import android.animation.Animator;

/**
 * A _very_ simple implementation of an animator listener.
 *
 * Subclassing this in place of implementing the {@link Animator.AnimatorListener} interface can
 * make your definitions smaller if you only need a few (or one) of the callbacks.
 */
public class SimpleAnimatorListener implements Animator.AnimatorListener {
    @Override
    public void onAnimationStart(Animator animation) {
    }

    @Override
    public void onAnimationEnd(Animator animation) {
    }

    @Override
    public void onAnimationCancel(Animator animation) {
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
    }
}
