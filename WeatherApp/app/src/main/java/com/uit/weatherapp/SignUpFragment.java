package com.uit.weatherapp;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uit.weatherapp.model.User;

public class SignUpFragment extends Fragment {
    TextView tvSignUpTitle, tvLookLikes, tvOr, tvPolicy;
    EditText etEmail, etUsername, etPw, etRePw;
    Button btnCreateAcc, btnSignUpGoogle, btnReturn;
    View vHorizontalLeft1, vHorizontalLeft2;
    MainActivity mainActivity;
    FirebaseAuth mAuth;
    boolean flag = false;
    private DatabaseReference mDatabaseReference;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }
    public SignUpFragment(MainActivity activity) {
        this.mainActivity = activity;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        InitViews(view);
        InitEvent();
    }
    private void InitViews(View v)
    {
        btnCreateAcc = v.findViewById(R.id.bt_create_account);
        btnSignUpGoogle = v.findViewById(R.id.bt_signup_google);
        btnReturn = v.findViewById(R.id.bt_return);
        tvOr = v.findViewById(R.id.tv_or);
        tvLookLikes = v.findViewById(R.id.tv_look_like);
        tvSignUpTitle = v.findViewById(R.id.tv_sign_up);
        tvPolicy = v.findViewById(R.id.tv_policy);
        etEmail = v.findViewById(R.id.et_email);
        etUsername = v.findViewById(R.id.et_username);
        etPw = v.findViewById(R.id.et_password);
        etRePw = v.findViewById(R.id.et_enter_the_password);
        vHorizontalLeft1 = v.findViewById(R.id.v_horizontal_left);
        vHorizontalLeft2 = v.findViewById(R.id.v_horizontal_left_1);

    }
    private void InitEvent()
    {
        btnReturn.setOnClickListener(view -> mainActivity.replaceFragment(mainActivity.sign_in));
        btnCreateAcc.setOnClickListener(v -> {
            String email = String.valueOf(etEmail.getText());
            String password = String.valueOf(etPw.getText());
            String username = String.valueOf(etUsername.getText());
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
            {
                Toast.makeText(mainActivity, "Email or Password is Empty", Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                createAccountByEmail(email, password, username);
            }

        });
    }
    private void createAccountByEmail(String email, String password, String username)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userId = user.getUid();
                            writeNewUser(userId,username,email);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("myLogin", "createUserWithEmail:success");
                            Toast.makeText(getContext(), "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            flag = true;

                        } else {
                            flag = false;
                            // If sign in fails, display a message to the user.
                            Log.w("myLogin", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if(flag)
                        {
                            mainActivity.replaceFragment(mainActivity.sign_in);
                        }

                    }
                });
    }

    public void writeNewUser(String userId, String username, String email)
    {
        User user = new User(username, email, userId);
        mDatabaseReference.child("users").child(userId).setValue(user);

    }
}
