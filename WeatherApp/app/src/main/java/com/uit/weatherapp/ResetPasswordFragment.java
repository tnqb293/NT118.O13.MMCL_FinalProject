package com.uit.weatherapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.uit.weatherapp.API.APIManager;

public class ResetPasswordFragment extends Fragment {
    MainActivity loginActivity;
    EditText etPassword, etConfirmPassword;
    Fragment signUpFragment;
    Button btReset, btReturn;
    public  ResetPasswordFragment(MainActivity activity)
    {
        this.loginActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitVars();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        InitViews(view);
        InitEvent();
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_resetpassword, container, false);

    }
    private void InitViews(View v)
    {
        btReset = v.findViewById(R.id.bt_reset);
        btReturn = v.findViewById(R.id.bt_return);
        etPassword = v.findViewById(R.id.et_new_password);
        etConfirmPassword = v.findViewById(R.id.et_confirm_new_password);
    }
    private void InitEvent()
    {
        btReturn.setOnClickListener(v -> {
            loginActivity.replaceFragment(signUpFragment);
        });
        btReset.setOnClickListener(v -> {
           String password = String.valueOf(etPassword.getText());
           String passwordConfirm = String.valueOf(etConfirmPassword.getText());
           if(TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfirm))
           {
               Toast.makeText(loginActivity, "Vui long dien du thong tin", Toast.LENGTH_SHORT).show();
           }
           else
           {
               JsonObject jsonObject = new JsonObject();
               jsonObject.addProperty("value", etPassword.getText().toString());
               APIManager.resetPassword(jsonObject, GlobalVars.uidUser);
               Toast.makeText(loginActivity, "Change password success", Toast.LENGTH_SHORT).show();
               loginActivity.replaceFragment(signUpFragment);

           }
        });
    }
    public void  InitVars()
    {
        signUpFragment = new SignInFragment(loginActivity);

    }
}
