package info.androidhive.introslider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String LOGIN_URL = "http://192.168.100.28:3000/api/user/login";
    public static final String SUBJECTS_URL = "http://192.168.100.28:3000/api/subjects";

    public static final String KEY_USERNAME="email";
    public static final String KEY_PASSWORD="password";
    public static final String KEY_UID = "access_token";

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;

    private String username;
    private String password;

    String log_id,lin_id,lin_name,Root_id,Root_name,str,log_name,uid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(CheckNetwork.isInternetAvailable(LoginActivity.this)) //returns true if internet available
        {
            setContentView(R.layout.activity_login);
            editTextUsername = (EditText) findViewById(R.id.editTextUsername);
            editTextPassword = (EditText) findViewById(R.id.editTextPassword);

            buttonLogin = (Button) findViewById(R.id.buttonLogin);

            buttonLogin.setOnClickListener(this);

            TextView text =(TextView) findViewById(R.id.link_signup);
            text.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent(LoginActivity.this, Registration.class);
                    startActivity(intent);
                }
            });
        }
        else
        {
            Toast.makeText(LoginActivity.this,"восстановите соедения c интернетом",Toast.LENGTH_LONG).show();
        }



    }


    private void userLogin() {
        username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            String uid = obj.getString("uid");

                            SharedPreferences sp = getApplicationContext().getSharedPreferences("UID",0);
                            SharedPreferences.Editor editor1 = sp.edit();
                            editor1.putString("key_name",uid); // key_name is the name through which you can retrieve it later.
                            editor1.commit();


                            if (obj.getBoolean("success")) {
                                // user successfully logged in
                                // JSONObject userObj = obj.getJSONObject("user");
//                                String uid=userObj.getString("requestor_id");
//                                String nam= userObj.getString("name");
//                                String ema=userObj.getString("email");
//                                User user = new User(id,nam,ema);
                                // storing user in shared preferences
//                                MyApplication.getInstance().getPrefManager().storeUser(user);
//                                if(myPreferenceManager.isFirstLaunch()){
//                                    reRegisterGCM(nam,ema);
//                                    myPreferenceManager.setIsFirstLaunch(false);
//                                }
//                                startActivity(new Intent(getApplicationContext(), ActivityUserProfile.class));
                                SharedPreferences sharedPref = LoginActivity.this.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putBoolean("loggedIn", true);
//                                Subjects();
//                                SharedPreferences sharedUid = LoginActivity.this.getSharedPreferences("UID", Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor1 = sharedUid.edit();
//                                editor1.putInt("id",uid);
//                                editor1.commit();

                                SharedPreferences userDetails = LoginActivity.this.getSharedPreferences("userdetails", MODE_PRIVATE);
                                SharedPreferences.Editor edit = userDetails.edit();
                                edit.clear();
                                edit.putString("username",username);
                                edit.commit();
                                editor.commit();

                                openProfile();
                                finish();

                            } else {
                                // login error - simply toast the message
                                Toast.makeText(getApplicationContext(), "" + obj.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,error.toString(), Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_USERNAME,username);
                map.put(KEY_PASSWORD,password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void openProfile(){
        Intent intent = new Intent(this, ActivityUserProfile.class);
        intent.putExtra(KEY_USERNAME, username);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        userLogin();
    }

//    public void Subjects(){
//        SharedPreferences sp = getApplicationContext().getSharedPreferences("UID", 0);
//        uid = sp.getString("key_name", "nothing");
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, SUBJECTS_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//                            JSONArray jsonArray = new JSONArray(response);
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
//                                str = jsonObject.getString("name");
//                                JSONArray topics = jsonObject.getJSONArray("topics");
//                                for (int k = 0; k < topics.length(); k++) {
//                                    JSONObject jsonObject1 = new JSONObject(topics.get(k).toString());
//                                    if(k==0){
//                                        log_id = jsonObject1.getString("_id");
//                                        log_name = jsonObject1.getString("name");
//                                    }
//                                    if(k==1){
//                                        lin_id = jsonObject1.getString("_id");
//                                        lin_name = jsonObject1.getString("name");
//                                    }
//                                    if(k==2){
//                                        Root_id = jsonObject1.getString("_id");
//                                        Root_name = jsonObject1.getString("name");
//                                    }
//
//                                }
//                                SharedPreferences sp = getApplicationContext().getSharedPreferences("topic_id",0);
//                                SharedPreferences.Editor editor = sp.edit();
//                                editor.putString("log_id",log_id); // key_name is the name through which you can retrieve it later.
//                                editor.putString("lin_id",lin_id);
//                                editor.putString("Root_id",Root_id);
//                                editor.commit();
//                            }
//                            String sub_name = obj.getString("name");
//                            JSONObject topics = obj.getJSONObject("topics");
//                            for (int i = 0; i < topics.length(); i++) {
//                                String id=topics.getString("_id");
//                                String nam= topics.getString("name");
//
//                                data += "Number " + (i + 1) + "\nTopics id: " + id +
//                                        "\nName : " + nam + "\n\n\n";
//
//                            }
//                            results.setText(log_name);

//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<String, String>();
//                map.put(KEY_UID, uid);
//                return map;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//
//    }
}