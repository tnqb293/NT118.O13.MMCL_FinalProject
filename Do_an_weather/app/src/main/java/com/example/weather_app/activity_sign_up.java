package com.example.weather_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activity_sign_up extends AppCompatActivity {


    private EditText ETName, ETEmail, ETPassword;
    private Button BTNCreateAccount;
    private RelativeLayout RLSignUpEmail;
    private FirebaseAuth Auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Auth=FirebaseAuth.getInstance();
        ETEmail =findViewById(R.id.ETEmail);
        ETPassword =findViewById(R.id.ETPassword);
        BTNCreateAccount =findViewById(R.id.BTCreateAccount);
        BTNCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void register() {
        String  email, password;

        email = ETEmail.getText().toString();
        password = ETPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Vui lòng nhập Email!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Vui lòng nhập Password!",Toast.LENGTH_SHORT).show();
            return;
        }
        Auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(activity_sign_up.this, activity_sign_in.class);
                    startActivity(intent1);
                } else {
                    Toast.makeText(getApplicationContext(),"Đăng ký tài khoản không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}