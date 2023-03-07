package com.example.oneshop.presenter;

import android.content.Context;

import com.example.oneshop.Interface.furnitureInterface;
import com.example.oneshop.apiController;
import com.example.oneshop.fetchElectronicModel;
import com.example.oneshop.furnitureAdapter;
import com.example.oneshop.models.furnitureModel;
import com.example.oneshop.responsemessage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class furniturePresenter implements furnitureInterface.funrniture{
 furnitureInterface.view view;

    public furniturePresenter(furnitureInterface.view view) {
        this.view = view;
    }


    @Override
    public void InsertfurnitureData(String image, String name, String warrenty, String price) {
        Call<responsemessage> call = apiController.getInstance().myapi().insertFurniture(image,name,warrenty,price);
        call.enqueue(new Callback<responsemessage>() {
            @Override
            public void onResponse(Call<responsemessage> call, Response<responsemessage> response) {
                responsemessage ms = response.body();
                view.onSucess(ms.getMessage());
            }

            @Override
            public void onFailure(Call<responsemessage> call, Throwable t) {
               view.onFailure(t.getMessage());
            }
        });

    }

    @Override
    public void updateFurniuteData(String id, String name, String warrenty, String price) {
        Call<responsemessage> call = apiController.getInstance().myapi().updateFurnitureData(id,name,warrenty,price);
        call.enqueue(new Callback<responsemessage>() {
            @Override
            public void onResponse(Call<responsemessage> call, Response<responsemessage> response) {
                if(response.isSuccessful())
                {
                    responsemessage rm = response.body();
                    view.onFailure(rm.getMessage());
                }
            }

            @Override
            public void onFailure(Call<responsemessage> call, Throwable t) {
               view.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void deleteFurnitureData(String id) {
        Call<responsemessage> call = apiController.getInstance().myapi().DeleteFurnitureData(id);
        call.enqueue(new Callback<responsemessage>() {
            @Override
            public void onResponse(Call<responsemessage> call, Response<responsemessage> response) {
                responsemessage rm = response.body();
                view.onSucess(rm.getMessage());
            }

            @Override
            public void onFailure(Call<responsemessage> call, Throwable t) {
view.onFailure(t.getMessage());
            }
        });
    }
}
