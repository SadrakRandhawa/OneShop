package com.example.oneshop;

import static com.example.oneshop.MainActivity.fetchFurnitureRecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oneshop.Interface.furnitureInterface;
import com.example.oneshop.presenter.furniturePresenter;

public class updateFurniture extends AppCompatActivity implements furnitureInterface.view {

    TextView tvid;
    EditText name,warrenty,price;
    Button btnUpdate;
    private int position;
    boolean checkStatus;
    String strname,strwarrenty,strprice;
    furniturePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_furniture);

       tvid = findViewById(R.id.getUpdateIDFurniture);
       name = findViewById(R.id.UpdateFurnitureName);
       warrenty = findViewById(R.id.UpdateFurniturewarrenty);
       price = findViewById(R.id.UpdateFurnitureprice);
       btnUpdate = findViewById(R.id.UpdateFurnitureBtn);
       presenter = new furniturePresenter(this);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        tvid.setText(fetchFurnitureRecord.get(position).getId());
        name.setText(fetchFurnitureRecord.get(position).getName());
        warrenty.setText(fetchFurnitureRecord.get(position).getWarrenty());
        price.setText(fetchFurnitureRecord.get(position).getPrice());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            String id = tvid.getText().toString().trim();
            String uname = name.getText().toString().trim();
            String uwarrenty = warrenty.getText().toString().trim();
            String uprice = price.getText().toString().trim();
            @Override
            public void onClick(View view) {
                CheckValidation();
                if(checkStatus)
                {
                    presenter.updateFurniuteData(id,uname,uwarrenty,uprice);
                    onSucess(" ");
                    startActivity(new Intent(updateFurniture.this,MainActivity.class));
                    finish();
                }
                else
                {
                     onFailure("Not Updated");
                }
            }
        });

    }
    private void CheckValidation() {
        strname = name.getText().toString();
        strwarrenty = warrenty.getText().toString();
        strprice = warrenty.getText().toString();

        if (TextUtils.isEmpty(strname) || TextUtils.isEmpty(strwarrenty) || TextUtils.isEmpty(strprice))
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