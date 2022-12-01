package com.example.oneshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;


import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oneshop.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

   // ActivityMainBinding activityMainBinding;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    RecyclerView recyclerView;
    RecAdapter recAdapter;
     public static List<fetchRecordModel> fetchRecordModelArrayList;
    TextView toolbartext,uploadRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
//        View view = activityMainBinding.getRoot();
//        setContentView(view);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        uploadRecord = findViewById(R.id.InsertRecord);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.drawerlayout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setTitle("One Click Shop");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //get HeaderView text
        View headerview = navigationView.getHeaderView(0);

        toolbartext = headerview.findViewById(R.id.headertxt);
        Intent intent = getIntent();
        String value = intent.getStringExtra("userName");
        toolbartext.setText(value);
        toolbartext.setTextColor(Color.parseColor("#bdbdbd"));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int value = item.getItemId();
                switch (value)
                {
                    case R.id.dashboard:

                        startActivity(new Intent(MainActivity.this,MainActivity.class));
                        finish();
                        break;
                    case R.id.aboutus:
                        Toast.makeText(MainActivity.this, "About Us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.contactus:
                        Toast.makeText(MainActivity.this, "Contact Us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:

                        SharedPreferences sharedPreferences = getSharedPreferences("private",MODE_PRIVATE);
                        sharedPreferences.edit().remove("logged").commit();
                        Intent intent = new Intent(MainActivity.this,loginActivity.class);
                        startActivity(intent);
//                        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
                        finish();
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }

        });

        fetchRecordModelArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recView);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));


        uploadRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,InsertActivity.class));
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                android.app.AlertDialog.Builder alertdialog = new AlertDialog.Builder(MainActivity.this);
                String[] items = {"Update","Delete"};
                alertdialog.setTitle("Choose");
                alertdialog.setTitle(fetchRecordModelArrayList.get(position).getTitle());
                alertdialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case 0:
                                startActivity(new Intent(MainActivity.this,UpdateActivity.class)
                                        .putExtra("position",position));
//                               startActivity(new Intent(MainActivity.this,Update_Activity.class)
//                                       .putExtra("position",position));
                                break;
                            case 1:
                                Delete(fetchRecordModelArrayList.get(position).getId());
//                                recAdapter.notifyItemRemoved(position+1);
//                                recAdapter.notifyItemRangeChanged(position,recAdapter.getItemCount());
                                break;

                            default:
                                break;
                        }
                    }
                });
                alertdialog.create().show();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        getListValue();




    }

    private void Delete(String id)
    {
        Call<responsemessage> call = apiController.getInstance().myapi().DeleteData(id);
        call.enqueue(new Callback<responsemessage>() {
            @Override
            public void onResponse(Call<responsemessage> call, Response<responsemessage> response) {
                responsemessage ms = response.body();
                getListValue();
                if(response.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<responsemessage> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getListValue() {
        Call<List<fetchRecordModel>> call = apiController.getInstance().myapi().getlist();
        call.enqueue(new Callback<List<fetchRecordModel>>() {
            @Override
            public void onResponse(Call<List<fetchRecordModel>> call, Response<List<fetchRecordModel>> response) {
                fetchRecordModelArrayList = response.body();
                recAdapter = new RecAdapter(fetchRecordModelArrayList, getApplicationContext());
//                recAdapter = new RecAdapter(md);
                recyclerView.setAdapter(recAdapter);
                recAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<fetchRecordModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //
        if(actionBarDrawerToggle.onOptionsItemSelected(item))

        return super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}