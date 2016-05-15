package com.github.tonywills.loadingbutton.sample;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tonywills.loadingbutton.SimpleAnimatorListener;
import com.github.tonywills.loadingbutton.ViewSwitcher;

public class ViewSwitcherExampleFragment extends Fragment {

    private ViewSwitcher viewSwitcher;

    public ViewSwitcherExampleFragment() {
        // Required empty public constructor
    }

    public static ViewSwitcherExampleFragment newInstance() {
        ViewSwitcherExampleFragment fragment = new ViewSwitcherExampleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        return inflater.inflate(R.layout.fragment_view_switcher_example, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewSwitcher = (ViewSwitcher) view.findViewById(R.id.view_switcher);
        viewSwitcher.setAnimationDelegate(animationDelegate);
        setListeners(view);
    }

    private void setListeners(View view) {
        view.findViewById(R.id.state_one_button).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                viewSwitcher.showViewAtIndex(0);
            }
        });
        view.findViewById(R.id.state_two_button).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                viewSwitcher.showViewAtIndex(1);
            }
        });
        view.findViewById(R.id.state_three_button).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                viewSwitcher.showViewAtIndex(2);
            }
        });
        view.findViewById(R.id.state_four_button).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                viewSwitcher.showViewAtIndex(3);
            }
        });
    }

    private final ViewSwitcher.AnimationDelegate animationDelegate = new ViewSwitcher.AnimationDelegate() {
        @Override public void onHideViewAtIndex(View view, int index) {
            view.animate().setStartDelay(0).setDuration(300).alpha(0);
        }

        @Override public void onShowViewAtIndex(final View view, int index) {
            switch (index) {
                case 0:
                    view.setTranslationY(-80);
                    break;
                case 1:
                    view.setTranslationY(80);
                    break;
                case 2:
                    view.setTranslationX(-80);
                    break;
                case 3:
                    view.setTranslationX(80);
                    break;
            }
            view.animate().setStartDelay(500).translationX(0).translationY(0)
                    .setListener(new SimpleAnimatorListener() {
                        @Override public void onAnimationStart(Animator animation) {
                            view.setVisibility(View.VISIBLE);
                            view.setAlpha(1);
                        }
                    });
        }
    };
}
