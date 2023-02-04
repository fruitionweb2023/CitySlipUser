package com.direct2web.citysipuser.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Doctor.DoctorAccountActivity;
import com.direct2web.citysipuser.activities.Doctor.DoctorDashboardActivity;
import com.direct2web.citysipuser.activities.Doctor.DoctorSearchActivity;
import com.direct2web.citysipuser.activities.Doctor.DoctorCartActivity;
import com.direct2web.citysipuser.activities.Insurance.InsurenceAccountActivity;
import com.direct2web.citysipuser.activities.Insurance.InsurenceCartActivity;
import com.direct2web.citysipuser.activities.Insurance.InsurenceDashboardActivity;
import com.direct2web.citysipuser.activities.Insurance.InsurenceSearchActivity;
import com.direct2web.citysipuser.activities.Lawyer.LawyerAccountActivity;
import com.direct2web.citysipuser.activities.Lawyer.LawyerCartActivity;
import com.direct2web.citysipuser.activities.Lawyer.LawyerDashboardActivity;
import com.direct2web.citysipuser.activities.Lawyer.LawyerSearchActivity;
import com.direct2web.citysipuser.activities.Restaurent.AccountActivity;
import com.direct2web.citysipuser.activities.Restaurent.CartActivity;
import com.direct2web.citysipuser.activities.Restaurent.MainActivity;
import com.direct2web.citysipuser.activities.Restaurent.SearchActivity;

public class BottomButtonClickListner implements View.OnClickListener {


    Context context;
    SessionManager sessionManager;

    public BottomButtonClickListner(Context context, SessionManager sessionManager) {
        this.context = context;
        this.sessionManager = sessionManager;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.bb_home) {

            if (sessionManager.getBusinessType().equals("6")){
                context.startActivity(new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }else if (sessionManager.getBusinessType().equals("4")){
                context.startActivity(new Intent(context, DoctorDashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else if (sessionManager.getBusinessType().equals("1")){
                context.startActivity(new Intent(context, LawyerDashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }  else if (sessionManager.getBusinessType().equals("3")){
                context.startActivity(new Intent(context, InsurenceDashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }

        } else if (id == R.id.bb_my_business) {

            if (sessionManager.getBusinessType().equals("6")){
                context.startActivity(new Intent(context, SearchActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }else if (sessionManager.getBusinessType().equals("4")){
                context.startActivity(new Intent(context, DoctorSearchActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else if (sessionManager.getBusinessType().equals("1")){
                context.startActivity(new Intent(context, LawyerSearchActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else if (sessionManager.getBusinessType().equals("3")){
                context.startActivity(new Intent(context, InsurenceSearchActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }


        } else if (id == R.id.bb_order) {

            if (sessionManager.getBusinessType().equals("6")){
                context.startActivity(new Intent(context, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }else if (sessionManager.getBusinessType().equals("4")){
                context.startActivity(new Intent(context, DoctorCartActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }  else if (sessionManager.getBusinessType().equals("1")){
                context.startActivity(new Intent(context, LawyerCartActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else if (sessionManager.getBusinessType().equals("3")){
                context.startActivity(new Intent(context, InsurenceCartActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }

        } else if (id == R.id.bb_menu) {

            if (sessionManager.getBusinessType().equals("6")){
                context.startActivity(new Intent(context, AccountActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }else if (sessionManager.getBusinessType().equals("4")){
               // Toast.makeText(context, "Clicked...", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, DoctorAccountActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else if (sessionManager.getBusinessType().equals("1")){
                context.startActivity(new Intent(context, LawyerAccountActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else if (sessionManager.getBusinessType().equals("3")){
                context.startActivity(new Intent(context, InsurenceAccountActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }
    }
}
