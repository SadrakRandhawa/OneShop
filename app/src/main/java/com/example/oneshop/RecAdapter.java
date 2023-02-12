package com.example.oneshop;


import static com.example.oneshop.fetchRecordModel.ItemView_electronics;
import static com.example.oneshop.fetchRecordModel.ItemView_furniture;
import static com.example.oneshop.fetchRecordModel.ItemView_property;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {
    private int LastPosition = -1;
    List<fetchRecordModel> arrayList;
    private final Context context;


    @Override
    public int getItemViewType(int position) {
       // return super.getItemViewType(position);
        switch (arrayList.get(position).getViewType())
        {
            case 0:
                return ItemView_property;
            case 1:
                return ItemView_electronics;
            case 2:
                return ItemView_furniture;
            default:
                return -1;

        }
    }

    public RecAdapter(List<fetchRecordModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override


    public RecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //usint viewtype for multiple listview


        //end viewtype for multiple listviews
        //commented by Sadrak
        View view = LayoutInflater.from(context).inflate(R.layout.singlerow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(arrayList.get(position).getTitle());
        holder.detail.setText(arrayList.get(position).getDetail());//http://192.168.100.6:8080/OneShop/image/
        holder.price.setText(arrayList.get(position).getPrice());//https://heard-leaders.000webhostapp.com/image/
        Glide.with(holder.title.getContext()).load("https://heard-leaders.000webhostapp.com/image/" + arrayList.get(position).getImage()).into(holder.imageView);
//        Glide.with(context).load(arrayList.get(position).getImage()).into(holder.imageView);
        setAnimation(holder.itemView, position);


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //showDialog(view.getContext(),position);
//            }
//        });


    }

    private void showDialog(Context context, int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(arrayList.get(position).getTitle());
        String[] items = {"Update", "Delete"};
        alert.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        Toast.makeText(context, "Update Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(context, "Delete Clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }


    private void setAnimation(View setAnimation, int position) {
        if (position > LastPosition) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f
                    , Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(1500);
            setAnimation.startAnimation(scaleAnimation);

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  {
        ImageView imageView;
        TextView title, detail, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivImage);
            title = itemView.findViewById(R.id.textTitle);
            detail = itemView.findViewById(R.id.textDescription);
            price = itemView.findViewById(R.id.textPrice);
//          itemView.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View view) {
//
//              }
//          });


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    fetchRecordModel frm = arrayList.get(getAdapterPosition());
//                    Intent intent = new Intent(context,UpdateActivity.class);
//                    intent.putExtra("pro",frm);
//                    context.startActivity(intent);
//
//
//
//
//
//
//
//
//                }
//            });
        }
    }
//    class electronics extends RecyclerView.ViewHolder {
//        ImageView imageViewelectronic;
//        TextView titleele, detailele, priceele;
//
//        public electronics(@NonNull View itemView) {
//            super(itemView);
//            imageViewelectronic = itemView.findViewById(R.id.electronicImage);
//            titleele = itemView.findViewById(R.id.textelectronics);
//            detailele = itemView.findViewById(R.id.textDeselectronices);
//            priceele = itemView.findViewById(R.id.textPriceelectronices);
//        }
//    }
//    class furniture extends RecyclerView.ViewHolder {
//        ImageView imageViewfurn;
//        TextView titlefurn, detailfurn, pricefurn;
//
//        public furniture(@NonNull View itemView) {
//            super(itemView);
//            imageViewfurn = itemView.findViewById(R.id.furnitureImage);
//            titlefurn = itemView.findViewById(R.id.textfurniture);
//            detailfurn = itemView.findViewById(R.id.textDesfurniture);
//            pricefurn = itemView.findViewById(R.id.textPricefurniture);
//        }
//    }
}
