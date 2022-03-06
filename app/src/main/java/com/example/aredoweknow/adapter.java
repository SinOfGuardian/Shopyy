package com.example.aredoweknow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.Myclass> {
    Context context;
    ArrayList<GetterSetter> al;


    public adapter(Context context, ArrayList<GetterSetter> al) {
        this.context = context;
        this.al = al;
        //  this.listener = listener;
    }

    @NonNull
    @Override
    public adapter.Myclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycler_grid, parent, false);
        return new Myclass(v);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Myclass holder, int position) {
//        holder.setIsRecyclable(false); //Disable recycling
        System.out.println("Position ------------------------[ " + position + " ]");
        GetterSetter g1 = al.get(position);

        holder.tvName.setText(g1.getName());
        holder.tvprice.setText("₱" + g1.getPrice());
        holder.tvQuantity.setText(g1.getQuantity());
        holder.im.setImageBitmap(g1.getImage());

        holder.panel.setTag(g1.getId());

        holder.panel.setOnLongClickListener(v -> {
            String temp = String.valueOf(v.getTag());
            Toast.makeText(context, "LONG CLICKED ID: " + temp, Toast.LENGTH_SHORT).show();
            return false;
        });

        holder.panel.setOnClickListener(v -> {
            int position1 = holder.getAdapterPosition();
            Intent intent = new Intent(context, VieweditActivity.class);
            intent.putExtra("id", al.get(position1).getId());
            intent.putExtra("name", al.get(position1).getName());
            intent.putExtra("price", al.get(position1).getPrice());
            intent.putExtra("quant", al.get(position1).getQuantity());

            intent.putExtra("image", al.get(position1).getImage());
            intent.putExtra("descr", al.get(position1).getDescription());
            intent.putExtra("barcode", al.get(position1).getBarcode());

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            Toast.makeText(v.getContext(), v.getTag().toString(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public static class Myclass extends RecyclerView.ViewHolder {
        ImageView im;
        TextView tvName, tvprice, tvQuantity;
        ConstraintLayout panel;

        public Myclass(@NonNull View view) {
            super(view);
            tvName = view.findViewById(R.id.nameITEM);
            tvprice = view.findViewById(R.id.priceItem);
            im = view.findViewById(R.id.imageITEM);
            tvQuantity = view.findViewById(R.id.quantItem);
            panel = view.findViewById(R.id.theItem);

        }
    }
}


