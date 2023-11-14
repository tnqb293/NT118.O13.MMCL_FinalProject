package com.uit.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.uit.weatherapp.API.APIManager;
import com.uit.weatherapp.Interface.TokenCallback;

public class SignInFragment extends Fragment {
    FirebaseAuth mAuth;
    static final int RC_SIGN_IN = 1;
    static final String TAG = "GOOGLEAUTH";
    GoogleSignInClient mGoogleSignInClient;
    Button btnLogin, btnSignInGoogle, btnSignUpEmail;
    TextView tvSignIn, tvNowYou, tvOr;
    View vHorizontalLineLeft, vHorizontalLineRight;
    EditText etEmail, etPassword;
    MainActivity loginActivity;
    public SignInFragment() {

    }
    public SignInFragment(MainActivity activity)
    {
        this.loginActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitViews(view);
        InitEvent();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    private void InitViews(View v)
    {
        btnLogin = v.findViewById(R.id.bt_login);
        btnSignInGoogle = v.findViewById(R.id.bt_signin_google);
        btnSignUpEmail = v.findViewById(R.id.bt_signup_email);
        tvOr = v.findViewById(R.id.tv_or);
        tvSignIn = v.findViewById(R.id.tv_sign_in);
        tvNowYou = v.findViewById(R.id.tv_now_you);
        etEmail =  v.findViewById(R.id.et_email);
        etPassword = v.findViewById(R.id.et_password);
        vHorizontalLineLeft = v.findViewById(R.id.v_horizontal_left);
        vHorizontalLineRight = v.findViewById(R.id.v_horizontal_right);
    }
    private void InitEvent()
    {
        btnSignUpEmail.setOnClickListener(view -> loginActivity.replaceFragment(loginActivity.sign_up));
        btnLogin.setOnClickListener(v -> {
            String email = String.valueOf(etEmail.getText());
            String password = String.valueOf(etPassword.getText());
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
            {
                Toast.makeText(loginActivity, "Please fill in your account", Toast.LENGTH_SHORT).show();
                etEmail.setText("");
                etPassword.setText("");
            }
            else if(password.length() <= 4)
            {
                Toast.makeText(loginActivity, "Password must have 4 characters or more", Toast.LENGTH_SHORT).show();
                etPassword.setText("");
            }
            else
            {
//                APIManager.getToken(email, password);
                APIManager.getToken(email, password, new TokenCallback() {
                    @Override
                    public void onSuccess(String token) {
                        GlobalVars.username = email;
                        GlobalVars.password = password;
                        loginActivity.runOnUiThread(() -> {
                            Toast.makeText(loginActivity, "Login Successful", Toast.LENGTH_SHORT).show();
                        });
                        startActivity(new Intent(loginActivity, HomeActivity.class));
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        loginActivity.runOnUiThread(() -> {
                            Toast.makeText(loginActivity, "Login Failed. Incorrect username or password", Toast.LENGTH_SHORT).show();
                        });
                    }
                });

//                APIManager.getData();
            }
        });

    }


}
