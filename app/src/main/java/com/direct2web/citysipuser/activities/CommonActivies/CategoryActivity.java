package com.direct2web.citysipuser.activities.CommonActivies;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.CategoryItem.CategoryAdapter;
import com.direct2web.citysipuser.databinding.ActivityCategoryBinding;
import com.direct2web.citysipuser.model.RestaurentModels.Category.Category;
import com.direct2web.citysipuser.model.RestaurentModels.Category.ResponseCategoryList;
import com.direct2web.citysipuser.utils.Api;
import com.direct2web.citysipuser.utils.RetrofitClient;
import com.direct2web.citysipuser.utils.WS_URL_PARAMS;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    ActivityCategoryBinding binding;
    String city_id = "1";

    List<Category> categoryList = new ArrayList<>();
    CategoryAdapter adapter;

    String[] allPermitions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE};

    private static final int PERMISSIONS_REQUEST_CODE = 1240;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category);

        checkAndRequestPermissions();

        getCategory(city_id);


    }

    private void getCategory(String city_id) {

        Api api = RetrofitClient.getClient().create(Api.class);
        Call<ResponseCategoryList> call = api.getCategory("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject), WS_URL_PARAMS.access_key, city_id);
        call.enqueue(new Callback<ResponseCategoryList>() {
            @Override
            public void onResponse(Call<ResponseCategoryList> call, Response<ResponseCategoryList> response) {

                Log.e("response_category", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    categoryList = response.body().getCategoryList();

                    if (categoryList != null) {

                        adapter = new CategoryAdapter(CategoryActivity.this,categoryList);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(CategoryActivity.this,3);
                        binding.rclrCategory.setLayoutManager(gridLayoutManager);
                        binding.rclrCategory.setAdapter(adapter);


                    } else {

                        binding.rclrCategory.setVisibility(View.GONE);

                    }


                } else {

                    binding.rclrCategory.setVisibility(View.GONE);

                }


            }

            @Override
            public void onFailure(Call<ResponseCategoryList> call, Throwable t) {

            }
        });


    }

    private void checkAndRequestPermissions() {

        List<String> listpermissionneeded = new ArrayList<>();

        for (String prm : allPermitions) {

            if (ContextCompat.checkSelfPermission(this, prm) != PackageManager.PERMISSION_GRANTED) {
                listpermissionneeded.add(prm);
            }
        }

        if (!listpermissionneeded.isEmpty()) {

            ActivityCompat.requestPermissions(this, listpermissionneeded.toArray(new String[listpermissionneeded.size()]), PERMISSIONS_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            HashMap<String, Integer> permissionresult = new HashMap<>();
            int deniedcount = 0;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionresult.put(permissions[i], grantResults[i]);
                    deniedcount++;
                }
            }

            if (deniedcount != 0) {
                for (Map.Entry<String, Integer> entry : permissionresult.entrySet()) {
                    String permname = entry.getKey();
                    int permvalue = entry.getValue();

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permname)) {
                        showdialog("", "This App needs Location and Storage permission to work without any problems.",
                                "Yes , Grant permission",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                        checkAndRequestPermissions();
                                    }
                                },
                                "No, Exit App", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }, false);
                        break;
                    } else {
                        showdialog("", "You have denied some permission, Allow all permission at [Setting] > [App] > [Permission]",
                                "Go to Setting",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts("package", getPackageName(), null));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                },
                                "No, Exit App", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }, false);
                        break;
                    }
                }
            }

        }

    }

    public void showdialog(String title, String msg, String positivelabel, DialogInterface.OnClickListener positiveonclick,
                           String negitivelabel, DialogInterface.OnClickListener negitiveonclick,
                           boolean iscancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(iscancelable);
        builder.setMessage(msg);
        builder.setPositiveButton(positivelabel, positiveonclick);
        builder.setNegativeButton(negitivelabel, negitiveonclick);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


}