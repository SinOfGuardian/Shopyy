package com.example.aredoweknow.fragments_folder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aredoweknow.R;
import com.example.aredoweknow.other_class.REFRESH;


public class Home extends Fragment {

    RecyclerView rv;
    public static RecyclerView rv_static;
    public static SearchView sv_static;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rv = view.findViewById(R.id.recycleViewLinear);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager gridLayoutManager = new LinearLayoutManager(getContext());
        //gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(gridLayoutManager);

        rv_static = rv;
        sv_static = view.findViewById(R.id.searcher);

//            Handler handler = new Handler();
//            handler.postAtTime(new Runnable() {
//                @Override
//                public void run() {
//                    updateArrayList(view);
//                }
//            }, 1);

        // We now use the REFRESH as if it contains the search function
        REFRESH r = new REFRESH();
        r.updateArrayList2(getContext());


        return view;
    }
}