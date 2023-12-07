package com.uit.weatherapp;

import android.os.Bundle;
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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.uit.weatherapp.API.APIManager;
import com.uit.weatherapp.Interface.TokenCallback;
import com.uit.weatherapp.Interface.UidCallback;
import com.uit.weatherapp.model.RequestPostRealmUser;

public class SignUpFragment extends Fragment {
    JsonArray bodyUserRoles, bodyRealmRoles;
    JsonObject bodyUpdatePassword;
    TextView tvSignUpTitle, tvLookLikes, tvOr, tvPolicy;
    EditText etEmail, etUsername, etPw, etRePw;
    Button btnCreateAcc, btnSignUpGoogle, btnReturn;
    View vHorizontalLeft1, vHorizontalLeft2;
    MainActivity mainActivity;
    RequestPostRealmUser requestPostRealmUser;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username))
            {
                Toast.makeText(mainActivity, "Email or Password is Empty", Toast.LENGTH_SHORT).show();
                return;
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
                                Log.d("Retrofit", "Đăng ký thành công");
                                mainActivity.runOnUiThread(() -> {
                                    Toast.makeText(mainActivity, R.string.signup_success, Toast.LENGTH_SHORT).show();
                                });
                            }

                            @Override
                            public void onFailure(String errorMessage) {
                                Log.e("Retrofit", errorMessage);
                                mainActivity.runOnUiThread(() -> {
                                    Toast.makeText(mainActivity, R.string.signup_fail, Toast.LENGTH_SHORT).show();
                                });
                            }
                        });
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(mainActivity, "Tên tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
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
