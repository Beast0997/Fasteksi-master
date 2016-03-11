package android.deroid.com.fasteksi.Activity;

import android.app.ProgressDialog;
import android.deroid.com.fasteksi.Json.JsonRequest;
import android.deroid.com.fasteksi.R;
import android.deroid.com.fasteksi.Util.AppProprties;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ForgetPassword extends AppCompatActivity implements View.OnClickListener {

    EditText edtmobileNumber;
    String mobileNumber;
    Button btnregister;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        getSupportActionBar().hide();
        initViews();
        btnregister.setOnClickListener(this);
    }

    private void initViews() {
        edtmobileNumber = (EditText)findViewById(R.id.etext_mobile_number);
        btnregister =(Button)findViewById(R.id.btn_register);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_register:
                onForgetPassword();
                break;
        }
    }

    private void onForgetPassword() {
        mobileNumber = edtmobileNumber.getText().toString();
        if (mobileNumber.isEmpty()){
            edtmobileNumber.setError("Mobile number is be empty");
        }else if (mobileNumber.length() <= 9){
            edtmobileNumber.setError("Mobile number must be 10 digit.");
        }else {
            Toast.makeText(ForgetPassword.this, "Send Sms", Toast.LENGTH_SHORT).show();
            onClickForgetPassword();
        }
    }

    private void onClickForgetPassword() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonRequest jsonRequest = new JsonRequest();

        String JsonStringResponse = jsonRequest.forgetPasswordJsonRequest(mobileNumber);
        progressDialog = new ProgressDialog(ForgetPassword.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, AppProprties.getInstance().getRootUrl(), JsonStringResponse, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext() ,"Successfull" + response.toString() ,Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext() ,"Error" + error.toString() ,Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        requestQueue.add(objectRequest);
    }
}
