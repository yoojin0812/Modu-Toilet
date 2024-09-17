package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QAFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QAFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button VisibleButton;
    private TextView TargetText;

    private Button VisibleButton2;
    private TextView TargetText2;

    private Button VisibleButton3;
    private TextView TargetText3;

    public QAFragment() {
        // Required empty public constructor
    }

    public static QAFragment newInstance(String param1, String param2) {
        QAFragment fragment = new QAFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qa, container, false);

        VisibleButton = view.findViewById(R.id.VisibleButton);
        TargetText = view.findViewById(R.id.TargetText);

        VisibleButton2 = view.findViewById(R.id.VisibleButton2);
        TargetText2 = view.findViewById(R.id.TargetText2);

        VisibleButton3 = view.findViewById(R.id.VisibleButton3);
        TargetText3 = view.findViewById(R.id.TargetText3);

        VisibleButton.setOnClickListener(this);
        TargetText.setOnClickListener(this);

        VisibleButton2.setOnClickListener(this);
        TargetText2.setOnClickListener(this);

        VisibleButton3.setOnClickListener(this);
        TargetText3.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.VisibleButton:
                TargetText.setVisibility(View.VISIBLE);
                break;
            case R.id.TargetText:
                TargetText.setVisibility(View.GONE);
                break;
            case R.id.VisibleButton2:
                TargetText2.setVisibility(View.VISIBLE);
                break;
            case R.id.TargetText2:
                TargetText2.setVisibility(View.GONE);
                break;
            case R.id.VisibleButton3:
                TargetText3.setVisibility(View.VISIBLE);
                break;
            case R.id.TargetText3:
                TargetText3.setVisibility(View.GONE);
                break;
        }
    }
}
