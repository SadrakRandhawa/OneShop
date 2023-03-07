package com.example.oneshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;


import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.text.Html;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oneshop.Interface.furnitureInterface;
import com.example.oneshop.models.furnitureModel;
import com.example.oneshop.presenter.furniturePresenter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements furnitureInterface.view {

   // ActivityMainBinding activityMainBinding;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    furniturePresenter  Presenter;

    //Admin work
    EditText adminName,adminPass;
    Button adminButton;

    RecyclerView recyclerView, furnrecyclerview, electronicrecycler;
    RecAdapter recAdapter;
    ElectronicesAdapter electronicesAdapter;
    furnitureAdapter furnitureAdap;
    public static List<furnitureModel> fetchFurnitureRecord;
     public static List<fetchRecordModel> fetchRecordModelArrayList;
     public static List<fetchElectronicModel> fetchElectronicModelList;
    TextView toolbartext,uploadRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
//        View view = activityMainBinding.getRoot();
//        setContentView(view);
        setContentView(R.layout.activity_main);
        Presenter = new furniturePresenter(this);


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
        fetchElectronicModelList = new ArrayList<>();
        fetchFurnitureRecord = new ArrayList<>();
        recyclerView = findViewById(R.id.recView);
        electronicrecycler = findViewById(R.id.recView1);
        furnrecyclerview = findViewById(R.id.recView2);




        recyclerView.setHasFixedSize(true);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this,1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        //recyclerView.setLayoutManager(gridLayoutManager);

        //recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.addItemDecoration(new LinePagerIndicatorDecoration());
        linearLayoutManager.findFirstVisibleItemPosition();
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);




        //recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),1));

        //electronices
        LinearLayoutManager electricManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        electronicrecycler.setLayoutManager(electricManager);
        //electronicrecycler.addItemDecoration(new LinePagerIndicatorDecoration());
        linearLayoutManager.findFirstVisibleItemPosition();
        SnapHelper snapHelperele = new PagerSnapHelper();
        snapHelperele.attachToRecyclerView(electronicrecycler);


        //furniture setting
        furnrecyclerview.setHasFixedSize(true);
        LinearLayoutManager furniturelinearlayout = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        furnrecyclerview.setLayoutManager(furniturelinearlayout);
        //furnrecyclerview.addItemDecoration(new LinePagerIndicatorDecoration());
        furniturelinearlayout.findFirstVisibleItemPosition();
        SnapHelper snapHelperfurn = new PagerSnapHelper();
        snapHelperfurn.attachToRecyclerView(furnrecyclerview);


        //Admin work

        uploadRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
              View layoutinflater = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_input_dialogbox,null);

                adminName = layoutinflater.findViewById(R.id.AdminName);
                adminPass = layoutinflater.findViewById(R.id.AdminPass);
                adminButton = layoutinflater.findViewById(R.id.adminloginBtn);
                ImageView imageView = layoutinflater.findViewById(R.id.passIcon);

                builder.setView(layoutinflater);


                final AlertDialog alertDialog = builder.create();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(adminPass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance()))
                        {
                            adminPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            imageView.setImageResource(R.drawable.visibilityofff);
                        }
                        else
                        {
                            adminPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            imageView.setImageResource(R.drawable.visibilityonn);
                        }
                    }
                });
                adminButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String name = adminName.getText().toString().trim();
                        final String password = adminPass.getText().toString().trim();
                        if(name.isEmpty() || password.isEmpty())
                        {
                            Toast.makeText(MainActivity.this, "Please fill manadatory fields first", Toast.LENGTH_SHORT).show();
                        }
                     else if(name.contentEquals("Sadrak") && password.contentEquals("Sadrak123"))
                     {

                         startActivity(new Intent(MainActivity.this,InsertActivity.class));
                     }
                     else
                     {
                         Toast.makeText(MainActivity.this, "Please Enter correct Name and Password", Toast.LENGTH_SHORT).show();
                     }

                    }
                });
             alertDialog.show();


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
        getElectronicsValue();

        //furniture all functionality
        getFurniture();
        getUpdated();




    }

    private void getUpdated() {
        furnrecyclerview.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), furnrecyclerview, new RecyclerItemClickListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                AlertDialog.Builder alertbuilder = new AlertDialog.Builder(MainActivity.this);
                alertbuilder.setTitle(fetchFurnitureRecord.get(position).getName());
                String[] items = {"update","Delete"};
                alertbuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i)
                        {
                            case 0:
                            startActivity(new Intent(MainActivity.this,updateFurniture.class)
                                    .putExtra("position",position));

                                break;
                            case 1:
                                DeleteFurniture(fetchFurnitureRecord.get(position).getId());
                                break;
                        }
                    }
                });
                alertbuilder.create().show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void DeleteFurniture(String id) {
       Presenter.deleteFurnitureData(id);
       onSucess("Delete Successfully");
       getFurniture();
    }

    private void getFurniture() {
        Call<List<furnitureModel>> call = apiController.getInstance().myapi().getfurnitureData();
        call.enqueue(new Callback<List<furnitureModel>>() {
            @Override
            public void onResponse(Call<List<furnitureModel>> call, Response<List<furnitureModel>> response) {
                fetchFurnitureRecord = response.body();
                furnitureAdap = new furnitureAdapter(fetchFurnitureRecord,getApplicationContext());
                furnrecyclerview.setAdapter(furnitureAdap);
                furnitureAdap.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<furnitureModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getElectronicsValue() {
        Call<List<fetchElectronicModel>> call = apiController.getInstance().myapi().getElectroniclist();
        call.enqueue(new Callback<List<fetchElectronicModel>>() {
            @Override
            public void onResponse(Call<List<fetchElectronicModel>> call, Response<List<fetchElectronicModel>> response) {
                fetchElectronicModelList = response.body();
                electronicesAdapter = new ElectronicesAdapter(fetchElectronicModelList, getApplicationContext());
//                recAdapter = new RecAdapter(md);
                electronicrecycler.setAdapter(electronicesAdapter);
                electronicesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<fetchElectronicModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
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
                recAdapter = new RecAdapter(fetchRecordModelArrayList,getApplicationContext());
                recyclerView.setAdapter(recAdapter);
                recAdapter.notifyDataSetChanged();


//                fetchRecordModelArrayList = response.body();
//                recAdapter = new RecAdapter(fetchRecordModelArrayList, getApplicationContext());
////                recAdapter = new RecAdapter(md);
//                recyclerView.setAdapter(recAdapter);
//                recAdapter.notifyDataSetChanged();
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
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        builder.setTitle("You want to Exist");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        //super.onBackPressed();
    }

    @Override
    public void onSucess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finishAffinity();
//
//    }
}