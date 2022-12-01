package com.example.oneshop;

import static com.example.oneshop.MainActivity.fetchRecordModelArrayList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    Button btnUpdate;
    EditText title,detail,price;
    ImageView imageView;
    private int position;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        title = findViewById(R.id.UpdateTitle);
        detail = findViewById(R.id.Updatedetail);
        price = findViewById(R.id.Updateprice);
        btnUpdate = findViewById(R.id.UpdateBtn);
        tv = findViewById(R.id.getUpdateID);
        imageView = findViewById(R.id.uploadImageUpdate);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        //position = intent.getExtras().getInt("position");
        tv.setText(fetchRecordModelArrayList.get(position).getId());
        Glide.with(this).load("http://192.168.100.6:8080/OneShop/image/" + fetchRecordModelArrayList.get(position).getImage()).into(imageView);
        title.setText(fetchRecordModelArrayList.get(position).getTitle());
        detail.setText(fetchRecordModelArrayList.get(position).getDetail());
        price.setText(fetchRecordModelArrayList.get(position).getPrice());
        
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Updaterecord(tv.getText().toString(),title.getText().toString(),detail.getText().toString(),price.getText().toString());
            }
        });
    }

    private void Updaterecord(String id,String title, String detail, String price) {
        Call<responsemessage> call = apiController.getInstance().myapi().updateData(id,title,detail,price);
        call.enqueue(new Callback<responsemessage>() {
            @Override
            public void onResponse(Call<responsemessage> call, Response<responsemessage> response) {
                responsemessage object = response.body();
                if(response.isSuccessful())
                {
                    Toast.makeText(UpdateActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    title.equals("");
                    detail.equals("");
                    price.equals("");
                    finish();
                }
                else
                {
                    Toast.makeText(UpdateActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<responsemessage> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Not Updated! please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UpdateActivity.this,MainActivity.class));
    }
}