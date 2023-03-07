package com.example.oneshop;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.oneshop.Interface.furnitureInterface;
import com.example.oneshop.presenter.furniturePresenter;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.internal.Util;

public class furnitureActivity extends AppCompatActivity implements furnitureInterface.view {

    Button btn;
    ImageView imageView;
    EditText name,warrenty,price;
    boolean checkStatus;
    String edname,edwarrenty,edprice;
    String imageencoded;
    furniturePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture);
        btn = findViewById(R.id.InsertfurnitureBtn);
        imageView = findViewById(R.id.uploadImagefurniture);
        name = findViewById(R.id.Insertfurnituretext);
        warrenty = findViewById(R.id.Insertfurniturewarrenty);
        price = findViewById(R.id.Insertfurnitureprice);
        presenter = new furniturePresenter(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
                if(checkStatus)
                {
                    presenter.InsertfurnitureData(imageencoded,edname,edwarrenty,edprice);
                    onSucess("Submit Furniture Data Sucessfully");
                    startActivity(new Intent(furnitureActivity.this,MainActivity.class));
                    finish();
                }
                else
                {
                    onFailure("Not Submit Furniture Data");
                   // Toast.makeText(furnitureActivity.this, "please manadatory fields first", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                imageStore(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void imageStore(Bitmap bitmap) {
        ByteArrayOutputStream byt = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byt);

        byte[] value = byt.toByteArray();
        imageencoded = android.util.Base64.encodeToString(value, Base64.DEFAULT);
    }

    public void validation()
    {
        edname = name.getText().toString();
        edwarrenty = warrenty.getText().toString();
        edprice = price.getText().toString();
        if(TextUtils.isEmpty(edname) || TextUtils.isEmpty(edwarrenty) || TextUtils.isEmpty(edprice))
        {
            checkStatus = false;
        }
        else
        {
            checkStatus = true;
        }
    }

    @Override
    public void onSucess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}