package android.deroid.com.fasteksi.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.deroid.com.fasteksi.Json.JsonRequest;
import android.deroid.com.fasteksi.R;
import android.deroid.com.fasteksi.Util.AppProprties;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NewCustomer extends AppCompatActivity implements View.OnClickListener {


    EditText edtphoneNumber, edtemailid, edtname;
    Button registerBtn;
    CheckBox termCondition;
    String cellNumber, emailId, fullName;
    TextView txtTermAndConditions;
    ProgressDialog progressDialog;
    InputStream inputStream;
    InputStreamReader inputStreamReader;
    BufferedReader bufferedReader;
    String stringTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_customer);
        getSupportActionBar().hide();
        initViews();

        registerBtn.setOnClickListener(this);
        txtTermAndConditions.setOnClickListener(this);
    }

    private void initViews() {
        edtphoneNumber = (EditText) findViewById(R.id.etext_cell_number);
        edtemailid = (EditText) findViewById(R.id.etext_email);
        edtname = (EditText) findViewById(R.id.etext_name);
        registerBtn = (Button) findViewById(R.id.btn_register);
        termCondition = (CheckBox)findViewById(R.id.terms_and_conditions_check_box);
        txtTermAndConditions = (TextView)findViewById(R.id.termsncond_txv);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_register:
                onclickRegisterbutton();
                break;
            case  R.id.termsncond_txv:
                onclicktxtTermAndConditions();
                break;
        }
    }

    private void onclicktxtTermAndConditions() {
        Dialog dialog = new Dialog(NewCustomer.this);
        dialog.setContentView(R.layout.term_and_condition);
        dialog.setTitle("Term and Conditions");
        WebView webViewTermandCondition =(WebView) findViewById(R.id.web_termandcondition);
       /* webViewTermandCondition.getSettings().setJavaScriptEnabled(true);
        String termText = readtext();
        String javaScript = "javascrpit:adddata('"+ termText +"');";
        webViewTermandCondition.loadUrl(javaScript);*/
        dialog.show();
    }

/*    private String readtext() {

        AssetManager assetManager = this.getAssets();
        try {
            inputStream = assetManager.open("Term&Condition.html");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputStream != null) {
            Toast.makeText(getBaseContext(), " Term", Toast.LENGTH_SHORT).show();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();
            try {
                while ((receiveString = bufferedReader.readLine()) != null) {
                    Log.i("receivestring", receiveString + "\n");
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                stringTxt = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getBaseContext(), " No Term", Toast.LENGTH_SHORT).show();
        }
        return stringTxt;
    }*/



    private void onclickRegisterbutton() {
        cellNumber = edtphoneNumber.getText().toString();
        emailId = edtemailid.getText().toString().trim();
        fullName = edtname.getText().toString();
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (cellNumber.isEmpty()){
            edtphoneNumber.setError("Mobile number not be empty");
        }else if(cellNumber.length() <= 9){
            edtphoneNumber.setError("Mobile number must be 10 digit.");
        }else if (emailId.isEmpty()){
            edtemailid.setError("Email id not ne empty");
        }else if (!emailId.matches(EMAIL_PATTERN)){
            edtemailid.setError("give the correct email id.");
        }else if (fullName.isEmpty()){
            edtname.setError("Name not ne empty");
        }else{

            if(!termCondition.isChecked()){
                Toast.makeText(NewCustomer.this, "Please check T&C", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(NewCustomer.this, "OnSign", Toast.LENGTH_SHORT).show();
                onClickRegisterResponse();
            }
        }
    }

    private void onClickRegisterResponse() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonRequest jsonRequest = new JsonRequest();

        String jsonRegistrationRequest = jsonRequest.newCustomerRegistration(cellNumber ,emailId ,fullName);

        progressDialog = new ProgressDialog(NewCustomer.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppProprties.getInstance().getRootUrl(), jsonRegistrationRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext() , response.toString() ,Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext() , error.toString() ,Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}
