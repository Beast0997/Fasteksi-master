package android.deroid.com.fasteksi.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.deroid.com.fasteksi.Json.JsonRequest;
import android.deroid.com.fasteksi.Preferences.SavedPreferences;
import android.deroid.com.fasteksi.R;
import android.deroid.com.fasteksi.Util.AppProprties;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerLogin extends AppCompatActivity implements View.OnClickListener {

    EditText edtPhoneNumer, edtPassword;
    LinearLayout layoutforgetPassword, layoutnewUser;
    CheckBox chkbsaveCredential;
    Button btnsign;
    String phone, password;
    ProgressDialog progressDialog;
    SavedPreferences sf = null;
    boolean isLogIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerlogin);
        getSupportActionBar().hide();
        initViews();

        layoutforgetPassword.setOnClickListener(this);
        layoutnewUser.setOnClickListener(this);
        btnsign.setOnClickListener(this);
        sf = new SavedPreferences();
        isLogIn = sf.isUserLoggedIn(this);
        if(isLogIn){
            Intent intent = new Intent(CustomerLogin.this, BookMyCar.class);
            startActivity(intent);
            finish();
        }
    }

    private void initViews() {
        edtPhoneNumer = (EditText) findViewById(R.id.etext_phone_number);
        edtPassword = (EditText) findViewById(R.id.etext_password);
        btnsign = (Button) findViewById(R.id.btn_sign_in);
        layoutforgetPassword = (LinearLayout) findViewById(R.id.layout_forgetpassword);
        layoutnewUser = (LinearLayout) findViewById(R.id.layout_new_user);
        chkbsaveCredential = (CheckBox) findViewById(R.id.checkbox_credentialsave);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.layout_forgetpassword:
                Intent forgetpasswordIntent = new Intent(CustomerLogin.this, ForgetPassword.class);
                startActivity(forgetpasswordIntent);
                break;
            case R.id.layout_new_user:
                Intent newUserIntent = new Intent(CustomerLogin.this, NewCustomer.class);
                startActivity(newUserIntent);
                break;
            case R.id.btn_sign_in:
                onSign();
                break;
        }
    }

    private void onSign() {
        phone = edtPhoneNumer.getText().toString();
        password = edtPassword.getText().toString();
        if (phone.isEmpty()) {
            edtPhoneNumer.setError("Please enter phone number");
        } else if (phone.length() <= 9) {
            edtPhoneNumer.setError("Please enter 10 digits number");
        } else if (password.isEmpty()) {
            edtPassword.setError("Please enter pwd number");
        }else {
            if(chkbsaveCredential.isChecked()){
                sf.setUserLoggedIn(CustomerLogin.this);
            }Toast.makeText(CustomerLogin.this, "OnSign", Toast.LENGTH_SHORT).show();
            onClickSignInResponse();
        }
    }

    private void onClickSignInResponse() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonRequest jsonRequest = new JsonRequest();
            String jsonRqstString = jsonRequest.customerLoginJsonRequest(phone,password);
            progressDialog = new ProgressDialog(CustomerLogin.this);
            progressDialog.setMessage("Loading....");
            progressDialog.show();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppProprties.getInstance().getRootUrl(), jsonRqstString, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(CustomerLogin.this, response.toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    if (response.toString() != null) {
                        try {
                            String message = response.getString("msg");
                            if (response.getInt("error") == 0 && message.equals("SUCCESS")) {
                               Intent intent = new Intent(CustomerLogin.this, BookMyCar.class);
                                startActivity(intent);
                                Toast.makeText(CustomerLogin.this, "BookMyCar", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.hide();
                }
            });
            requestQueue.add(jsonObjectRequest);
    }
}
