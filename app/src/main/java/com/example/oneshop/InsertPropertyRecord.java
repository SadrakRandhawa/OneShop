package com.example.oneshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertPropertyRecord extends AppCompatActivity {

    Button insertBtn;
    EditText title,detail,price;
    ImageView imageView;
    String imagedecoded;

    private String edittitle,editdetail,editprice;

    boolean checkCondition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_property_record);
        insertBtn = findViewById(R.id.InsertBtn);
        title = findViewById(R.id.InsertText);
        detail = findViewById(R.id.Insertdetail);
        price = findViewById(R.id.Insertprice);
        imageView = findViewById(R.id.uploadImage);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckValidation();
                if(checkCondition)
                {
                    InsertRecord(edittitle,editdetail,editprice);
                }else
                {
                    Toast.makeText(InsertPropertyRecord.this, "fill manadatory fields first", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void InsertRecord(String edittitle, String editdetail,String edPrice) {
        Call<responsemessage> call = apiController.getInstance().myapi().uploadData(imagedecoded,edittitle,editdetail,edPrice);
        call.enqueue(new Callback<responsemessage>() {
            @Override
            public void onResponse(Call<responsemessage> call, Response<responsemessage> response) {
                responsemessage rm = response.body();
                if(rm == null)
                {
                    Toast.makeText(InsertPropertyRecord.this, "Something missing for uploading", Toast.LENGTH_SHORT).show();
                }
                title.setText("");
                detail.setText("");
                price.setText("");
                imageView.setVisibility(View.GONE);
                startActivity(new Intent(InsertPropertyRecord.this,MainActivity.class));


            }

            @Override
            public void onFailure(Call<responsemessage> call, Throwable t) {
                Toast.makeText(InsertPropertyRecord.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void CheckValidation()
    {
        edittitle = title.getText().toString().trim();
        editdetail = detail.getText().toString().trim();
        editprice = price.getText().toString().trim();
        if(TextUtils.isEmpty(edittitle) || TextUtils.isEmpty(editdetail) || TextUtils.isEmpty(editprice))
        {
            checkCondition = false;
        }
        else
        {
            checkCondition = true;
        }
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
                ImageStore(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void ImageStore(Bitmap bitmap) {

        ByteArrayOutputStream byt = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byt);
        //bitmap.compress(Bitmap.CompressFormat.JPEG,100,byt);

        byte[] imageStore = byt.toByteArray();
        imagedecoded = android.util.Base64.encodeToString(imageStore,Base64.DEFAULT);
        //imagedecoded = android.util.Base64.encodeToString(imageStore, Base64.DEFAULT);
        //imagedecoded = android.util.Base64.encodeToString(imageStore,Base64.DEFAULT);

    }
}