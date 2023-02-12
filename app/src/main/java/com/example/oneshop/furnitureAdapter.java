package com.example.oneshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class furnitureAdapter extends RecyclerView.Adapter<furnitureAdapter.ViewHolder> {

    private int LastPosition = -1;
    List<fetchRecordModel> arrayList;
    private final Context context;

    public furnitureAdapter(List<fetchRecordModel> arrayList,Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public furnitureAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainactivity_detailrecyclerviewone,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull furnitureAdapter.ViewHolder holder, int position) {

        holder.titlefurniture.setText(arrayList.get(position).getTitle());
        holder.detailfurniture.setText(arrayList.get(position).getDetail());//http://192.168.100.6:8080/OneShop/image/
        holder.pricefurniture.setText(arrayList.get(position).getPrice());//https://heard-leaders.000webhostapp.com/image/
        Glide.with(holder.titlefurniture.getContext()).load("https://heard-leaders.000webhostapp.com/image/" + arrayList.get(position).getImage()).into(holder.furnitureImage);
//        Glide.with(context).load(arrayList.get(position).getImage()).into(holder.imageView);
        setAnimation(holder.itemView, position);

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
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView furnitureImage;
        TextView titlefurniture, detailfurniture, pricefurniture;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            furnitureImage = itemView.findViewById(R.id.furnitureImage);
            titlefurniture = itemView.findViewById(R.id.textfurniture);
            detailfurniture = itemView.findViewById(R.id.textDesfurniture);
            pricefurniture = itemView.findViewById(R.id.textPricefurniture);
        }
    }
}
