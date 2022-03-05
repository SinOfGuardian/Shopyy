package com.example.aredoweknow;

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
        ArrayList<GetterSetter>al;


        public adapter(Context context, ArrayList<GetterSetter> al){
         this.context = context;
            this.al = al;
          //  this.listener = listener;
        }

    @NonNull
    @Override
    public adapter.Myclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycler_grid,parent,false);
        return new Myclass(v);

    }

    @Override
    public void onBindViewHolder(@NonNull Myclass holder, int position) {
//        holder.setIsRecyclable(false); //Disable recycling
        System.out.println("Position ------------------------[ "+position+" ]");
        GetterSetter g1 = al.get(position);

        holder.tvName.setText(g1.getName());
        holder.tvprice.setText("₱" + g1.getPrice());
        holder.tvQuantity.setText(g1.getQuantity());
        holder.im.setImageBitmap(g1.getImage());

        holder.panel.setTag(String.valueOf(g1.getId()));


        holder.panel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String temp = String.valueOf(v.getTag());
                Toast.makeText(context, "TAG-------------- "+temp, Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        holder.panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(context, VieweditActivity.class);
                    intent.putExtra("name",al.get(position).getName());
                    intent.putExtra("price",al.get(position).getPrice());
                //intent.putExtra("image",g1.getImage());
                    intent.putExtra("quan",al.get(position).getQuantity());
//                intent.putExtra("description", g1.getDescription());
//                intent.putExtra("barcode", g1.getBarcode());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
               Toast.makeText(v.getContext(), "Clicked me"+position, Toast.LENGTH_SHORT).show();
            }
        });


    }



    @Override
    public int getItemCount() {
        return al.size();
    }



    public class Myclass extends RecyclerView.ViewHolder {
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


