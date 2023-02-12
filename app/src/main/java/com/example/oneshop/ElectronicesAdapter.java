package com.example.oneshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ElectronicesAdapter extends RecyclerView.Adapter<ElectronicesAdapter.ViewHolder> {

    private int LastPosition = -1;
    List<fetchElectronicModel> arrayList;
    private final Context context;

    public ElectronicesAdapter(List<fetchElectronicModel> arrayList,Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ElectronicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainactivity_detailrecyclerviewone,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ElectronicesAdapter.ViewHolder holder, int position) {
        holder.titleele.setText(arrayList.get(position).getName());
        holder.quality.setText(arrayList.get(position).getQuality());
        holder.warrenty.setText(arrayList.get(position).getWarrenty());//http://192.168.100.6:8080/OneShop/image/
        holder.price.setText(arrayList.get(position).getPrice());//https://heard-leaders.000webhostapp.com/image/
        Glide.with(holder.titleele.getContext()).load("https://heard-leaders.000webhostapp.com/image/" + arrayList.get(position).getImage()).into(holder.elecImage);
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
        ImageView elecImage;
        TextView titleele, quality, warrenty, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            elecImage = itemView.findViewById(R.id.electronicImage);
            titleele = itemView.findViewById(R.id.textelectronics);
            quality = itemView.findViewById(R.id.textQuality);
            warrenty = itemView.findViewById(R.id.textwarrenty);
            price = itemView.findViewById(R.id.textElecprice);
        }
    }
}
