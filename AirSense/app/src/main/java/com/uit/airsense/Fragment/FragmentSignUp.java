package com.uit.airsense.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.uit.airsense.API.APIManager;
import com.uit.airsense.Interface.TokenCallback;
import com.uit.airsense.Interface.UidCallback;
import com.uit.airsense.MainActivity;
import com.uit.airsense.Model.GlobalVars;
import com.uit.airsense.Model.RequestPostRealmUser;
import com.uit.airsense.R;

public class FragmentSignUp extends Fragment {
    MainActivity loginActivity;
    LinearLayout layoutSignUp;
    AppCompatButton btLogin, btCreateAccount;
    JsonArray bodyUserRoles, bodyRealmRoles;
    JsonObject bodyUpdatePassword;
    TextInputEditText etEmail, etUsername, etPw, etRePw;
    RequestPostRealmUser requestPostRealmUser;
    public FragmentSignUp(MainActivity activity)
    {
        this.loginActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);
        layoutSignUp = view.findViewById(R.id.llSignUp);
        int childCount = layoutSignUp.getChildCount();
        long delayPerItem = 300 / childCount; // Tính toán thời gian delay cho mỗi item
        for (int i = 0; i < childCount; i++) {
            final View child = layoutSignUp.getChildAt(i);
            child.setVisibility(View.INVISIBLE); // Ẩn item ban đầu

            // Load Animation từ tệp tin XML
            Animation animation = AnimationUtils.loadAnimation(loginActivity.getApplicationContext(), R.anim.slide_in_right);

            // Đặt thời gian delay cho mỗi item
            animation.setStartOffset(delayPerItem * i);

            // Hiển thị item và áp dụng Animation
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    child.setVisibility(View.VISIBLE); // Hiển thị item
                    child.startAnimation(animation); // Áp dụng Animation
                }
            }, delayPerItem * i); // Hiển thị mỗi item sau 1 thời gian delay
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitViews(view);
        InitEvent();
    }
    void InitViews(View v)
    {
        layoutSignUp = v.findViewById(R.id.llSignUp);
        btLogin = v.findViewById(R.id.btLogIn);
        btCreateAccount = v.findViewById(R.id.btCreateAccount);
        etEmail = v.findViewById(R.id.tietEmail);
        etPw = v.findViewById(R.id.tietPassword);
        etRePw = v.findViewById(R.id.tietConfirmPassword);
        etUsername = v.findViewById(R.id.tietUsername);
    }
    void InitEvent()
    {
//        loginActivity.changeTitleFromFragment(getResources().getString(R.string.sign_up));

        btCreateAccount.setOnClickListener(v -> {
            String email = String.valueOf(etEmail.getText());
            String password = String.valueOf(etPw.getText());
            String username = String.valueOf(etUsername.getText());
            String confirmPw = String.valueOf(etRePw.getText());
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username) || TextUtils.isEmpty(confirmPw))
            {
                Toast.makeText(loginActivity, "Email or Password is Empty", Toast.LENGTH_SHORT).show();
            }
            else
            {
                APIManager.getToken(GlobalVars.usernameAdmin, GlobalVars.passwordAdmin, new TokenCallback() {
                    @Override
                    public void onSuccess(String token) {
                        requestPostRealmUser = new RequestPostRealmUser(email, username);
                        APIManager.createIdUser(requestPostRealmUser, new UidCallback() {
                            @Override
                            public void onSuccess(String uid) {
                                bodyUserRoles = new JsonArray();
                                bodyRealmRoles = new JsonArray();
                                bodyUpdatePassword = new JsonObject();
                                bodyUserRoles = convertJsonArraySetRoles();
                                bodyRealmRoles = convertJsonArrayRealmRoles();
                                bodyUpdatePassword = convertJsonObjectUpdatePassword();
                                APIManager.setRoles(bodyUserRoles, uid);
                                APIManager.setRealmRoles(bodyRealmRoles, uid);
                                APIManager.resetPassword(bodyUpdatePassword, uid);
                                loginActivity.runOnUiThread(() -> {
                                    Toast.makeText(loginActivity, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                });
                                animationToLogin();
                            }

                            @Override
                            public void onFailure(String errorMessage) {
                                loginActivity.runOnUiThread(() -> {
                                    Toast.makeText(loginActivity, errorMessage, Toast.LENGTH_SHORT).show();
                                });
                            }
                        });
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        loginActivity.runOnUiThread(() -> {
                            Toast.makeText(loginActivity, errorMessage, Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }
        });
        btLogin.setOnClickListener(v -> {
            animationToLogin();
        });

    }
    public void animationToLogin()
    {
        int childCount = layoutSignUp.getChildCount();
        long delayPerItem = 300 / childCount; // Tính toán thời gian delay cho mỗi item
        final int[] completedAnimations = {0};

        // Sắp xếp các item theo thứ tự ngược lại
        for (int i = childCount - 1; i >= 0; i--) {
            final View child = layoutSignUp.getChildAt(i);
            Animation animation = AnimationUtils.loadAnimation(loginActivity.getApplicationContext(), R.anim.slide_out_right);
            animation.setStartOffset(delayPerItem * (childCount - i - 1)); // Đảo ngược thứ tự delay

            // Sử dụng Listener để kiểm tra khi animation hoàn thành
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // Không cần làm gì khi animation bắt đầu
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    child.setVisibility(View.INVISIBLE); // Ẩn item sau khi animation kết thúc
                    completedAnimations[0]++;

                    // Kiểm tra nếu tất cả animation đã hoàn thành trước khi chuyển Fragment
                    if (completedAnimations[0] == childCount) {
                        loginActivity.replaceFragment(loginActivity.fmSignIn); // Thay thế Fragment khi tất cả các animation đã ẩn
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // Không cần làm gì khi animation lặp lại
                }
            });

            // Áp dụng Animation cho từng item
            child.startAnimation(animation);
        }
    }

    public JsonArray convertJsonArraySetRoles()
    {
        JsonArray jsonArray = new JsonArray();

        JsonObject readObject = new JsonObject();
        readObject.addProperty("id", "c698ac49-58ce-4a1a-9f0b-114571234f54");
        readObject.addProperty("name", "read");
        readObject.addProperty("description", "Read all data");
        readObject.addProperty("composite", true);

        JsonArray readCompositeRoleIds = new JsonArray();
        readCompositeRoleIds.add("97069b78-fc54-46ab-9e2f-da232f4be396");
        readCompositeRoleIds.add("597df7da-f389-4e17-90bf-8da7117dae4a");
        readCompositeRoleIds.add("e3a471a0-b5d2-4ad2-ab92-ea03b86800c8");
        readCompositeRoleIds.add("92ce7735-e0a1-48bd-a9a0-1207a57432dd");
        readCompositeRoleIds.add("d8f99961-decb-427c-b943-66fb6dd03e12");
        readCompositeRoleIds.add("ae8e898c-562d-45d7-a267-c6f89f19dfe1");
        readCompositeRoleIds.add("5e87d283-af22-45fa-9f0b-34db57872b4e");
        readCompositeRoleIds.add("c68e4d31-4452-4464-80da-813f9df75932");
        readCompositeRoleIds.add("992ad3da-46b9-4acb-b7fd-e786b30b74bf");
        readCompositeRoleIds.add("61075af1-407f-491e-b325-e3c1b4c10351");
        readCompositeRoleIds.add("29962812-abef-4726-b3df-a03aaa34b77b");
        readCompositeRoleIds.add("0a1c56a0-0ace-48c5-80a1-7a3953d44f19");
        readCompositeRoleIds.add("40eb5826-d99a-40b4-83d5-088b789bfa41");
        readCompositeRoleIds.add("eb066551-f084-48c3-8d64-2a8e32ee7627");
        readObject.add("compositeRoleIds", readCompositeRoleIds);
        readObject.addProperty("assigned", true);

        jsonArray.add(readObject);

        JsonObject writeObject = new JsonObject();
        writeObject.addProperty("id", "27737d27-3151-4f54-8efd-61dfdeabd673");
        writeObject.addProperty("name", "write");
        writeObject.addProperty("description", "Write all data");
        writeObject.addProperty("composite", true);

        JsonArray writeCompositeRoleIds = new JsonArray();
        writeCompositeRoleIds.add("97069b78-fc54-46ab-9e2f-da232f4be396");
        writeCompositeRoleIds.add("597df7da-f389-4e17-90bf-8da7117dae4a");
        writeCompositeRoleIds.add("e3a471a0-b5d2-4ad2-ab92-ea03b86800c8");
        writeCompositeRoleIds.add("92ce7735-e0a1-48bd-a9a0-1207a57432dd");
        writeCompositeRoleIds.add("d8f99961-decb-427c-b943-66fb6dd03e12");
        writeCompositeRoleIds.add("ae8e898c-562d-45d7-a267-c6f89f19dfe1");
        writeCompositeRoleIds.add("5e87d283-af22-45fa-9f0b-34db57872b4e");
        writeCompositeRoleIds.add("c68e4d31-4452-4464-80da-813f9df75932");
        writeCompositeRoleIds.add("992ad3da-46b9-4acb-b7fd-e786b30b74bf");
        writeCompositeRoleIds.add("61075af1-407f-491e-b325-e3c1b4c10351");
        writeCompositeRoleIds.add("29962812-abef-4726-b3df-a03aaa34b77b");
        writeCompositeRoleIds.add("0a1c56a0-0ace-48c5-80a1-7a3953d44f19");
        writeCompositeRoleIds.add("40eb5826-d99a-40b4-83d5-088b789bfa41");
        writeCompositeRoleIds.add("eb066551-f084-48c3-8d64-2a8e32ee7627");

        writeObject.add("compositeRoleIds", writeCompositeRoleIds);
        writeObject.addProperty("assigned", true);

        jsonArray.add(writeObject);
        return jsonArray;
    }
    public JsonArray convertJsonArrayRealmRoles()
    {
        JsonArray jsonArray = new JsonArray();

        JsonObject adminObject = new JsonObject();
        adminObject.addProperty("id", "b2e14977-151c-4900-a896-6ac913e59980");
        adminObject.addProperty("name", "admin");
        adminObject.addProperty("description", "${role_admin}");
        adminObject.addProperty("composite", true);
        adminObject.addProperty("assigned", true);

        jsonArray.add(adminObject);
        return jsonArray;
    }
    public JsonObject convertJsonObjectUpdatePassword()
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("value", etPw.getText().toString());
        return jsonObject;
    }
}
