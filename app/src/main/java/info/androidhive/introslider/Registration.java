package info.androidhive.introslider;

/**
 * Created by NURAKHMET on 08.03.2017.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    private static final String REGISTER_URL = "http://192.168.100.28:3000/api/user/register";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";


    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;

    private Button buttonRegister;
    private Button buttonLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registr);

//        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

//        boolean loggedIn = sharedPref.getBoolean("loggedIn", false);

//        if (loggedIn){
//            Intent intent = new Intent(Registration.this, ActivityUserProfile.class);
//            startActivity(intent);
//        }else{
//            Intent intent = new Intent(Registration.this, MainActivity.class);
//            startActivity(intent);
//        }
        if (CheckNetwork.isInternetAvailable(Registration.this)) //returns true if internet available
        {
            editTextUsername = (EditText) findViewById(R.id.editTextUsername);
            editTextPassword = (EditText) findViewById(R.id.editTextPassword);
            editTextEmail = (EditText) findViewById(R.id.editTextEmail);

            buttonRegister = (Button) findViewById(R.id.buttonRegister);
  //        buttonLogin = (Button) findViewById(R.id.buttonLogin);

            buttonRegister.setOnClickListener(this);
            //      buttonLogin.setOnClickListener(this);

            TextView text = (TextView) findViewById(R.id.link_login);
            text.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(Registration.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Toast.makeText(Registration.this, "восстановите соедения c интернетом", 10000).show();
        }


    }




    private void registerUser(){
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Registration.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Registration.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME,username);
                params.put(KEY_PASSWORD,password);
                params.put(KEY_EMAIL, email);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
//        if(v == buttonLogin){
//            startActivity(new Intent(this,LoginActivity.class));
//        }
    }
}