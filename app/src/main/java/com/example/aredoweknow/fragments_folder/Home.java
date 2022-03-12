package com.example.aredoweknow.fragments_folder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aredoweknow.R;
import com.example.aredoweknow.features_functions.AddItem;
import com.example.aredoweknow.features_functions.Scanner;
import com.example.aredoweknow.features_functions.Web;
import com.example.aredoweknow.other_class.REFRESH;


public class Home extends Fragment {

    RecyclerView rv;
    public static RecyclerView rv_static;
    public static SearchView sv_static;

    AppCompatImageButton barc_search;
    Button browser_btn, scan_btn, add_btn;

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

        //--------------------------------Open Web View
        browser_btn = view.findViewById(R.id.fab1);
        browser_btn.setOnClickListener(v -> {
            Intent intent1 = new Intent(getContext(), Web.class);
            intent1.putExtra("ToSearch", "");
            startActivity(intent1);
        });

        //---------------------------------Open scanner for scan search
        scan_btn = view.findViewById(R.id.fab2);
        scan_btn.setOnClickListener(v -> {
            if (CamPermissionGranted()) {
                Intent intent12 = new Intent(getContext(), Scanner.class);
                intent12.putExtra("update", "seraching_item_web");
                startActivity(intent12);
            }
        });

        //------------------Open Add for adding item
        add_btn = view.findViewById(R.id.fab3);
        add_btn.setOnClickListener(v -> {
            Intent intent13 = new Intent(getContext(), AddItem.class);
            startActivity(intent13);
        });

        //--------------------Open scanner for barcode search
        barc_search = view.findViewById(R.id.barc_searcher);
        barc_search.setOnClickListener(v -> {
            if (CamPermissionGranted()) {
                Intent intent12 = new Intent(getContext(), Scanner.class);
                intent12.putExtra("update", "searching_item");
                startActivity(intent12);
            }
        });

        return view;
    }


    //--------- Check Camera Permission
    private boolean CamPermissionGranted() {
        //--------------CAMERA CODE
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 100);
            return false;
        }else {
            return true;
        }
    }
}