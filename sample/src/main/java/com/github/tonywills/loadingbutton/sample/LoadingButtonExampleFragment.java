package com.github.tonywills.loadingbutton.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tonywills.loadingbutton.LoadingButton;

public class LoadingButtonExampleFragment extends Fragment {

    private LoadingButton loadingButton;

    public LoadingButtonExampleFragment() {
        // Required empty public constructor
    }

    public static LoadingButtonExampleFragment newInstance() {
        LoadingButtonExampleFragment fragment = new LoadingButtonExampleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        return inflater.inflate(R.layout.fragment_loading_button_example, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingButton = (LoadingButton) view.findViewById(R.id.loading_button);
        loadingButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                loadingButton.setLoading(!loadingButton.isLoading());
            }
        });
    }
}
