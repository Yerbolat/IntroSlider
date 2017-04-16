package info.androidhive.introslider;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityUserProfile extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener{
    AHBottomNavigation bottomNavigation;
    public static final String SUBJECTS_URL = "http://192.168.100.28:3000/api/subjects";
    public static final String TEST_URL = "http://192.168.100.28:3000/api/test";
    public static final String KEY_UID = "access_token";
    public static final String KEY_TOPIC = "topic_id";
    public static final String THEORY_URL = "http://192.168.100.28:3000/api/theory";
    private DBHelperEquation databaseHelper;
    private DBHelperLogarithm databaseHelperL;
    private DBHelperDerivative databaseHelperD;
    private String uid;
    String str;
    String log_name;
    String log_id,lin_id,lin_name,Root_id,Root_name,data;
    private TextView textView, results;
    boolean clicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        //results = (TextView) findViewById(R.id.textViewUsername);

        bottomNavigation= (AHBottomNavigation) findViewById(R.id.myBottomNavigation_ID);
        bottomNavigation.setOnTabSelectedListener(this);
        this.createNavItems();


        /*  Parsing */
        SharedPreferences sp = getApplicationContext().getSharedPreferences("UID", 0);
        uid = sp.getString("key_name", "nothing");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, SUBJECTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                                str = jsonObject.getString("name");
                                JSONArray topics = jsonObject.getJSONArray("topics");
                                for (int k = 0; k < topics.length(); k++) {
                                    JSONObject jsonObject1 = new JSONObject(topics.get(k).toString());
                                    if(k==0){
                                        log_id = jsonObject1.getString("_id");
                                        log_name = jsonObject1.getString("name");
                                    }
                                    if(k==1){
                                        lin_id = jsonObject1.getString("_id");
                                        lin_name = jsonObject1.getString("name");
                                    }
                                    if(k==2){
                                        Root_id = jsonObject1.getString("_id");
                                        Root_name = jsonObject1.getString("name");
                                    }

                                }
                                SharedPreferences sp = getApplicationContext().getSharedPreferences("topic_id",0);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("log_id",log_id); // key_name is the name through which you can retrieve it later.
                                editor.putString("lin_id",lin_id);
                                editor.putString("Root_id",Root_id);
                                editor.commit();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityUserProfile.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_UID, uid);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    LogarithmOuestions();
    EquationOuestions();
    DerivativeOuestions();
    LogarithmTheory();
    }

    private void LogarithmOuestions() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("UID", 0);
        uid = sp.getString("key_name", "nothing");
        SharedPreferences sp1 = getApplicationContext().getSharedPreferences("topic_id",0);
        log_id = sp1.getString("log_id","nothing");
        databaseHelper=new DBHelperEquation(ActivityUserProfile.this);
        databaseHelper.DeleteAll();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, TEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                                String question = jsonObject.getString("question");
                                String opta = jsonObject.getString("a");
                                String optb = jsonObject.getString("b");
                                String optc = jsonObject.getString("c");
                                String optd = jsonObject.getString("d");
                                String correct = jsonObject.getString("correct");
                                databaseHelper.addQuestion(question,correct,opta,optb,optc,optd);
                                data = opta  + optb + optc + optd + correct;
                            }
//                            results.setText(str);
                            Toast.makeText(getApplicationContext(), data , Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityUserProfile.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_UID, uid);
                map.put(KEY_TOPIC, log_id);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

  private void  EquationOuestions(){
        SharedPreferences sp = getApplicationContext().getSharedPreferences("UID", 0);
        uid = sp.getString("key_name", "nothing");
        SharedPreferences sp1 = getApplicationContext().getSharedPreferences("topic_id",0);
        lin_id = sp1.getString("lin_id","nothing");
        databaseHelperL=new DBHelperLogarithm(ActivityUserProfile.this);
        databaseHelperL.DeleteAll();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, TEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                                String question = jsonObject.getString("question");
                                String opta = jsonObject.getString("a");
                                String optb = jsonObject.getString("b");
                                String optc = jsonObject.getString("c");
                                String optd = jsonObject.getString("d");
                                String correct = jsonObject.getString("correct");
                                databaseHelperL.addQuestion(question,correct,opta,optb,optc,optd);
                                data = opta  + optb + optc + optd + correct;
                            }
//                            results.setText(str);
//                            Toast.makeText(getApplicationContext(), data , Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityUserProfile.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_UID, uid);
                map.put(KEY_TOPIC, lin_id);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void  DerivativeOuestions(){
        SharedPreferences sp = getApplicationContext().getSharedPreferences("UID", 0);
        uid = sp.getString("key_name", "nothing");
        SharedPreferences sp1 = getApplicationContext().getSharedPreferences("topic_id",0);
        Root_id = sp1.getString("Root_id","nothing");
        databaseHelperD=new DBHelperDerivative(ActivityUserProfile.this);
        databaseHelperD.DeleteAll();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, TEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                                String question = jsonObject.getString("question");
                                String opta = jsonObject.getString("a");
                                String optb = jsonObject.getString("b");
                                String optc = jsonObject.getString("c");
                                String optd = jsonObject.getString("d");
                                String correct = jsonObject.getString("correct");
                                databaseHelperD.addQuestion(question,correct,opta,optb,optc,optd);
                                data = opta  + optb + optc + optd + correct;
                            }
//                            results.setText(str);
//                            Toast.makeText(getApplicationContext(), data , Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityUserProfile.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_UID, uid);
                map.put(KEY_TOPIC, Root_id);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /*-----------------------------------------------------------------------------*/

    /*
    * PArsing theory*/
    private void LogarithmTheory(){
        SharedPreferences sp = getApplicationContext().getSharedPreferences("UID", 0);
        uid = sp.getString("key_name", "nothing");
        SharedPreferences sp1 = getApplicationContext().getSharedPreferences("topic_id",0);
        log_id = sp1.getString("log_id","nothing");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, THEORY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            String theory = obj.getString("theory");
                            if(str == null){
                                Toast.makeText(getApplicationContext(),"Error" ,Toast.LENGTH_LONG).show();
                            }
                            else{
                                SharedPreferences sp = getApplicationContext().getSharedPreferences("Theory",0);
                                SharedPreferences.Editor editor1 = sp.edit();
                                editor1.putString("Logarithm",theory); // key_name is the name through which you can retrieve it later.
                                editor1.commit();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityUserProfile.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_UID, uid);
                map.put(KEY_TOPIC, log_id);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    /*-------------------------------------*/
    private void createNavItems()
    {
        //CREATE ITEMS
        AHBottomNavigationItem crimeItem=new AHBottomNavigationItem("Тест",R.drawable.exam);
        AHBottomNavigationItem dramaItem=new AHBottomNavigationItem("Теория",R.drawable.openbook);
        AHBottomNavigationItem docstem=new AHBottomNavigationItem("Профиль",R.drawable.user);
        //ADD THEM to bar
        bottomNavigation.addItem(crimeItem);
        bottomNavigation.addItem(dramaItem);
        bottomNavigation.addItem(docstem);
        //set properties
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        //set current item
        bottomNavigation.setCurrentItem(0);
        // Change colors
        bottomNavigation.setAccentColor(Color.parseColor("#6666ff"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));
    }

    @Override
    public void onTabSelected(int position, boolean wasSelected) {
        //show fragment
        if (position==0)
        {
            ExamFragment examFragment=new ExamFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id,examFragment).commit();
        }else  if (position==1)
        {
            TestFragment testFragment=new TestFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id,testFragment).commit();
        }else  if (position==2)
        {
            ProfileFragment profileFragment=new ProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id,profileFragment).commit();
        }
    }
}
