package com.example.oneshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.oneshop.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;
    Boolean checkCondition;
    String email,password,name;
    CheckBox checkBox;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = activityLoginBinding.getRoot();
        setContentView(view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


// Custom Hide/Show Password fields
       activityLoginBinding.passIcon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(activityLoginBinding.loginPass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance()))
               {
                   activityLoginBinding.loginPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                   activityLoginBinding.passIcon.setImageResource(R.drawable.visibilityofff);
               }
               else
               {
                   activityLoginBinding.loginPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                   activityLoginBinding.passIcon.setImageResource(R.drawable.visibilityonn);
               }
           }
       });

//        activityLoginBinding.loginPass.setTransformationMethod(new PasswordTransformationMethod());
//        activityLoginBinding.loginPass.setTransformationMethod(null);

        sharedPreferences = getSharedPreferences("private",MODE_PRIVATE);
        if(sharedPreferences.getBoolean("logged",false))
         {
             MainPage();

         }
        else
        {
            MainPageAfterloggedIn();
        }


        //setContentView(R.layout.activity_login);
         activityLoginBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 checkValidation();
                 if(checkCondition)
                 {
                     Boolean check = activityLoginBinding.checkbox.isChecked();
                     if(check== true)
                     {
                         loginMethod(email,password);
                         sharedPreferences.edit().putBoolean("logged",true).commit();
                     }
                     else
                     {
                         loginMethod(email,password);
                     }
                 }
                 else
                 {
                     Toast.makeText(loginActivity.this, "fill manadatory field first", Toast.LENGTH_SHORT).show();
                 }

             }
         });
         activityLoginBinding.signup.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(loginActivity.this,RegisterationActivity.class));
             }
         });
    }

    private void MainPageAfterloggedIn() {
    }

    private void MainPage() {
        Intent intent = new Intent(this,MainActivity.class);
        Bundle bundle = new Bundle();
//        bundle.putString("name",email);
//        bundle.putString("password", password);
        intent.putExtras(bundle);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void loginMethod(String email, String password) {
        Call<responsemessage> call = apiController.getInstance().myapi().login(name, email,password);
        call.enqueue(new Callback<responsemessage>() {
            @Override
            public void onResponse(Call<responsemessage> call, Response<responsemessage> response) {

                if(response.isSuccessful())
                {
                    responsemessage ms = response.body();
                    if(ms.getMessage().equals("Email Matched"))
                    {
                        Intent intent = new Intent(loginActivity.this,MainActivity.class);
                        intent.putExtra("userName", email);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(loginActivity.this, "Email & Password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(loginActivity.this, "error! try again", Toast.LENGTH_SHORT).show();
                }
//                String check = ms.getMessage();
//                if(!ms.equals("Email Matched")) {
//
//                    Toast.makeText(loginActivity.this, ms.getMessage(), Toast.LENGTH_SHORT).show();
//                    activityLoginBinding.loginEmail.setText("");
//                    activityLoginBinding.loginPass.setText("");
//                    startActivity(new Intent(loginActivity.this, MainActivity.class));
//                }
//                else
//                {
//                    Toast.makeText(loginActivity.this, ms.getMessage(), Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(Call<responsemessage> call, Throwable t) {
                Toast.makeText(loginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                activityLoginBinding.loginEmail.setText("");
                activityLoginBinding.loginPass.setText("");

            }
        });
    }

    public void checkValidation()
    {
        email = activityLoginBinding.loginEmail.getText().toString();
        password = activityLoginBinding.loginPass.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
        {
            checkCondition = false;
        }
        else
        {
            checkCondition = true;
        }

    }
}