package com.example.mbds.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MasterFragment extends ListFragment implements View.OnClickListener {

    private FloatingActionButton fabContact;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<String> items;

    private OnMessageSelectedListener mListener;

    public MasterFragment() {
    }


    public static MasterFragment newInstance(String param1, String param2) {
        MasterFragment fragment = new MasterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        items = new ArrayList<>();
        items.add("item 1");
        items.add("item 2");
        items.add("item 3");

        ArrayAdapter<String> aa = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);

        setListAdapter(aa);

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
        View view = inflater.inflate(R.layout.fragment_master, container, false);
        fabContact = view.findViewById(R.id.fab_contact);

        fabContact.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
}


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMessageSelectedListener) {
            mListener = (OnMessageSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMessageSelectedListenerOnMessageSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mListener.onMessageSelected(items.get(position));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), Contact.class);
        startActivity(intent);
    }

    public interface OnMessageSelectedListener {
        void onMessageSelected(String itemName);
    }

}
