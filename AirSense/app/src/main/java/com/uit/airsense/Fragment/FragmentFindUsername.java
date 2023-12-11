package com.uit.airsense.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.uit.airsense.API.APIClient;
import com.uit.airsense.API.APIManager;
import com.uit.airsense.Interface.ListUserCallback;
import com.uit.airsense.Interface.TokenCallback;
import com.uit.airsense.MainActivity;
import com.uit.airsense.Model.GlobalVars;
import com.uit.airsense.Model.Token;
import com.uit.airsense.R;

public class FragmentFindUsername extends Fragment {
    MainActivity loginActivity;
    AppCompatButton btFind, btReturn, btReturn1, btResetPw;
    LinearLayout llReset, llFind;
    TextInputEditText etUsername, etNewPw, etConfirmPw;
    TextInputLayout tilNewPw, tilConfirmNewPw;
    Fragment signInFragment;
    public FragmentFindUsername(MainActivity mainActivity)
    {
        this.loginActivity = mainActivity;
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
        return inflater.inflate(R.layout.fragment_find_user, container, false);

    }
    public void InitViews(View v) {
        btFind = v.findViewById(R.id.btFind);
        etUsername = v.findViewById(R.id.tietUsername);
        btReturn = v.findViewById(R.id.btReturn);
        llFind = v.findViewById(R.id.llFind);
        llReset = v.findViewById(R.id.llReset);
        tilConfirmNewPw = v.findViewById(R.id.tilConfirmNewPw);
        tilNewPw = v.findViewById(R.id.tilNewPw);
        etConfirmPw = v.findViewById(R.id.tietConfirmNewPw);
        etNewPw = v.findViewById(R.id.tietNewPw);
        btReturn1 = v.findViewById(R.id.btReturn1);
        btResetPw = v.findViewById(R.id.btResetpw);
    }

    public void InitEvent() {
        btResetPw.setOnClickListener(v -> {
            String newPw = etNewPw.getText().toString();
            String newConfirmPw = etConfirmPw.getText().toString();
            if(newPw.isEmpty() && newConfirmPw.equals(newPw))
            {
                Toast.makeText(loginActivity, "Please type again", Toast.LENGTH_SHORT).show();
            }
            else
            {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("value", etNewPw.getText().toString());
                APIManager.resetPassword(jsonObject, GlobalVars.uidUser);
                Toast.makeText(loginActivity, "Change password success", Toast.LENGTH_SHORT).show();
                loginActivity.replaceFragment(loginActivity.fmSignIn);
            }
        });
        btReturn1.setOnClickListener(v -> {
            etNewPw.setText(null);
            etConfirmPw.setText(null);
            etUsername.setText(null);
            loginActivity.replaceFragment(loginActivity.fmSignIn);
        });
        btReturn.setOnClickListener(v -> {
            etNewPw.setText(null);
            etConfirmPw.setText(null);
            etUsername.setText(null);
            loginActivity.replaceFragment(loginActivity.fmSignIn);
        });
        btFind.setOnClickListener(v -> {
            String usernameRequest = etUsername.getText().toString();
            APIManager.getToken(GlobalVars.usernameAdmin, GlobalVars.passwordAdmin, new TokenCallback() {
                @Override
                public void onSuccess(String token) {
                    JsonObject request = sendRequestListUser();
                    APIManager.listUserFn(request, new ListUserCallback() {
                        @Override
                        public void onSuccess(JsonArray listUser) {
                            boolean flag = true;
                            for(int i = 0; i < listUser.size(); i++)
                            {
                                JsonObject element = listUser.get(i).getAsJsonObject();
                                String usernameResponse = element.get("username").getAsString();
                                if(usernameRequest.equals(usernameResponse))
                                {
                                    GlobalVars.uidUser = element.get("id").getAsString();
                                    Toast.makeText(loginActivity, "Find username Success", Toast.LENGTH_SHORT).show();
                                    etUsername.setEnabled(false);
                                    llFind.setVisibility(View.GONE);
                                    tilNewPw.setVisibility(View.VISIBLE);
                                    tilConfirmNewPw.setVisibility(View.VISIBLE);
                                    llReset.setVisibility(View.VISIBLE);
                                    flag = false;
                                }
                            }
                            if (flag)
                                Toast.makeText(loginActivity, "Username does not exist", Toast.LENGTH_SHORT).show();
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
    public void  InitVars()
    {
        signInFragment = new FragmentSignIn(loginActivity);
    }
}
