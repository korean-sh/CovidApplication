package com.project.mask;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class Sub2Fragment extends Fragment {

    private Typeface typeface;

    public Sub2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_sub2, container, false);

        return rootView;
    }

}
