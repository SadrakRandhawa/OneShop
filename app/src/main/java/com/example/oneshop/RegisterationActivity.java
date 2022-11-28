package com.example.oneshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oneshop.databinding.ActivityRegisterationBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterationActivity extends AppCompatActivity {


    EditText regname,regemail,regpassword;
    Button regbtn;
    ActivityRegisterationBinding activityRegisterationBinding;
    Boolean checkCondition;
    String name,email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_registeration);
        activityRegisterationBinding = ActivityRegisterationBinding.inflate(getLayoutInflater());
        View view = activityRegisterationBinding.getRoot();
         setContentView(view);

         activityRegisterationBinding.regBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 checkvalidation();
                 if(checkCondition)
                 {
                     RegisterRecord(name,email,password);
                 }
                 else
                 {
                     Toast.makeText(RegisterationActivity.this, "Please fill Manadatory fields first", Toast.LENGTH_SHORT).show();
                 }

             }
         });
    }

    private void RegisterRecord(String name,String email,String password) {

        Call<responsemessage> responsemessageCall = apiController.getInstance().myapi().Register(name,email,password);
        responsemessageCall.enqueue(new Callback<responsemessage>() {
            @Override
            public void onResponse(Call<responsemessage> call, Response<responsemessage> response) {
                responsemessage ms = response.body();
                String value = ms.getMessage();
                Toast.makeText(RegisterationActivity.this, value, Toast.LENGTH_SHORT).show();
                activityRegisterationBinding.regName.setText("");
                activityRegisterationBinding.regEmail.setText("");
                activityRegisterationBinding.regPass.setText("");
                startActivity(new Intent(RegisterationActivity.this,loginActivity.class));
            }

            @Override
            public void onFailure(Call<responsemessage> call, Throwable t) {
                Toast.makeText(RegisterationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                activityRegisterationBinding.regName.setText("");
                activityRegisterationBinding.regEmail.setText("");
                activityRegisterationBinding.regPass.setText("");
            }
        });
    }

    public void checkvalidation()
    {
        name = activityRegisterationBinding.regName.getText().toString();
        email = activityRegisterationBinding.regEmail.getText().toString();
        password = activityRegisterationBinding.regPass.getText().toString();

       if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
       {
            checkCondition = false;
       }
       else
       {
           checkCondition = true;
       }
    }


}