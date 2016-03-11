package android.deroid.com.fasteksi.Json;

import android.deroid.com.fasteksi.Util.AppProprties;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gulshank on 18-02-2016.
 */
public class JsonRequest {


    private String getUrl() {

        return AppProprties.getInstance().getRootUrl();
    }

    private String getAppSecretId(String app_secret_id) {

        return AppProprties.getInstance().getEndPoint(app_secret_id);

    }

    private String getAppId(String app_id) {
        return AppProprties.getInstance().getEndPoint(app_id);
    }

    private String getMethodName(String methodName) {
        return AppProprties.getInstance().getEndPoint(methodName);
    }


    public String customerLoginJsonRequest(String phoneNumber , String password){

        JSONObject rootObject = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        JSONObject response = null;
        String requestBody = "";
        try {
            jsonObject.put("mobile", phoneNumber);
            jsonObject.put("password" ,password);
            rootObject.put("app_id", getAppId("app_id"));
            rootObject.put("app_secret", getAppSecretId("app_secret_id"));
            rootObject.put("method", getMethodName("customerLogin"));
            rootObject.put("params", jsonObject);
            requestBody = rootObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return requestBody;
    }


    public String newCustomerRegistration(String cellNumber, String emailId, String fullName) {

        JSONObject rootJsonObject = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        String registrationRequest = "";

        try {
            jsonObject.put("name", fullName);
            jsonObject.put("email", emailId);
            jsonObject.put("mobile", cellNumber);
            rootJsonObject.put("app_id", getAppId("app_id"));
            rootJsonObject.put("app_secret", getAppSecretId("app_secret_id"));
            rootJsonObject.put("method", getMethodName("customerRegistration"));
            rootJsonObject.put("params", jsonObject);
            registrationRequest = rootJsonObject.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return registrationRequest;
    }

    public String forgetPasswordJsonRequest(String mobileNumber) {
        JSONObject rootJsonObject = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        String forgetPasswordRequest ="";

        try {
            jsonObject.put("mobile" ,mobileNumber);
            rootJsonObject.put("app_id", getAppId("app_id"));
            rootJsonObject.put("app_secret", getAppSecretId("app_secret_id"));
            rootJsonObject.put("method", getMethodName("forgetPassword"));
            rootJsonObject.put("params", jsonObject);
            forgetPasswordRequest = rootJsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return forgetPasswordRequest;
    }
}
