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

public class InsertElectronicRecord extends AppCompatActivity {

    Button insertBtn;
    EditText title,detail,price,warrenty;
    ImageView imageView;
    String imagedecoded;

    private String edittitle,editdetail,editprice, editwarranty;

    boolean checkCondition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_electronic_record);
        insertBtn = findViewById(R.id.InsertBtn);
        title = findViewById(R.id.InsertElectronictext);
        detail = findViewById(R.id.InsertElectronicquality);
        warrenty = findViewById(R.id.InsertElectronicwarrenty);
        price = findViewById(R.id.InsertElectronicprice);
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
                    InsertRecord(edittitle,editdetail,editwarranty,editprice);
                }else
                {
                    Toast.makeText(InsertElectronicRecord.this, "fill manadatory fields first", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void InsertRecord(String edittitle, String editdetail, String editwarranty, String editprice) {
        Call<responsemessage> call = apiController.getInstance().myapi().uploadElectronicData(imagedecoded,edittitle,editdetail,editwarranty,editprice);
        call.enqueue(new Callback<responsemessage>() {
            @Override
            public void onResponse(Call<responsemessage> call, Response<responsemessage> response) {
                responsemessage ms = response.body();
                if(ms != null)
                {
                  title.setText("");
                  detail.setText("");
                  warrenty.setText("");
                  price.setText("");
                  startActivity(new Intent(InsertElectronicRecord.this,MainActivity.class));
                  finish();
                }
                else
                {
                    Toast.makeText(InsertElectronicRecord.this, "Something missing for upload", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<responsemessage> call, Throwable t) {
                Toast.makeText(InsertElectronicRecord.this, "Data not Received", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void CheckValidation()
    {
        edittitle = title.getText().toString().trim();
        editdetail = detail.getText().toString().trim();
        editprice = price.getText().toString().trim();
        editwarranty = warrenty.getText().toString().trim();
        if(TextUtils.isEmpty(edittitle) || TextUtils.isEmpty(editdetail) || TextUtils.isEmpty(editwarranty) || TextUtils.isEmpty(editprice))
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
        imagedecoded = android.util.Base64.encodeToString(imageStore, Base64.DEFAULT);
        //imagedecoded = android.util.Base64.encodeToString(imageStore,Base64.DEFAULT);

    }
}