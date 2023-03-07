package com.example.oneshop.Interface;

public interface furnitureInterface {
     interface view{
         void onSucess(String message);
         void onFailure(String message);
     }
     interface funrniture {
        // void getfurnitureData();
         void InsertfurnitureData(String name,String warrenty,String price,String image);
         void updateFurniuteData(String id,String name,String warrenty,String price);
         void deleteFurnitureData(String id);
     }
}
