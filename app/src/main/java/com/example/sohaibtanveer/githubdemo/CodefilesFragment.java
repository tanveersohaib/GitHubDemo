package com.example.sohaibtanveer.githubdemo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CodefilesFragment extends Fragment {

    public CodefilesFragment() {
        // Required empty public constructor
    }

    public static CodefilesFragment newInstance() {
        CodefilesFragment fragment = new CodefilesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_codefiles, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity.bus.post(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
