package com.uit.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.uit.weatherapp.API.APIManager;
import com.uit.weatherapp.Interface.ListUserCallback;
import com.uit.weatherapp.Interface.TokenCallback;

public class ResetPasswordFindUsernameFragment extends Fragment {
    MainActivity loginActivity;
    Button btConfirm;
    EditText etUsername;
    public ResetPasswordFindUsernameFragment(MainActivity mainActivity)
    {
        this.loginActivity = mainActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        return inflater.inflate(R.layout.fragment_resetpassword_find_username, container, false);

    }
    public void InitViews(View v) {
        btConfirm = v.findViewById(R.id.bt_confirm);
        etUsername = v.findViewById(R.id.et_username_reset_password);
    }
    public void InitEvent() {
        btConfirm.setOnClickListener(v -> {
            String usernameRequest = String.valueOf(etUsername.getText().toString());
            APIManager.getToken(GlobalVars.usernameAdmin, GlobalVars.passwordAdmin, new TokenCallback() {
                @Override
                public void onSuccess(String token) {
                    JsonObject request = sendRequestListUser();
                    APIManager.listUserFn(request, new ListUserCallback() {
                        @Override
                        public void onSuccess(JsonArray listUser) {
                            for(int i = 0; i < listUser.size(); i++)
                            {
                                JsonObject element = listUser.get(i).getAsJsonObject();
                                String usernameResponse = element.get("username").getAsString();
                                if(usernameRequest.equals(usernameResponse))
                                {
//                                    GlobalVars.uidUser = element.get("")
                                }
                            }
                        }

                        @Override
                        public void onFailure(String errorMessage) {

                        }
                    });

                }

                @Override
                public void onFailure(String errorMessage) {

                }
            });
        });
    }
    private JsonObject sendRequestListUser()
    {
        String jsonString = "{\"realmPredicate\":{\"name\":\"master\"}}";
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
        return jsonObject;
    }

}
