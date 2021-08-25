package com.sk.volley_library_viewdata;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String web_url = "https://androidbatch2.000webhostapp.com/view_registered_data.php";

    EditText ename;
    Button viewdatabtn;
    TextView tname,temail,tmobile,taddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ename  =findViewById(R.id.edtname);
        viewdatabtn=findViewById(R.id.view_data);
        tname=findViewById(R.id.txtname);
        temail=findViewById(R.id.txtemail);
        tmobile=findViewById(R.id.txtmob);
        taddress=findViewById(R.id.txtaddress);


        viewdatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            ViewData(ename.getText().toString());
            //Toast.makeText(getApplicationContext(),"data",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void ViewData(String name)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, web_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    int success = jsonObject.getInt("sucess");

                    if(success==1)
                    {
                        tname.setText(jsonObject.getString("name"));
                        temail.setText(jsonObject.getString("email"));
                        tmobile.setText(jsonObject.getString("mobno"));
                        taddress.setText(jsonObject.getString("address"));

                        Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    }
                    else if(success==2)
                    {
                        Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        tname.setText("");
                        temail.setText("");
                        tmobile.setText("");
                        taddress.setText("");
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name",name);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}