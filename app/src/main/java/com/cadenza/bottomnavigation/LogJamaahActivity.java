package com.cadenza.bottomnavigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cadenza.bottomnavigation.API.Db_Contract;

import org.json.JSONException;
import org.json.JSONObject;

public class LogJamaahActivity extends AppCompatActivity {

    private EditText etNik;
    private Button btnLogin;
    private TextView btnRegister, btnLupaNik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_jamaah);

        etNik = findViewById(R.id.etNik);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnLupaNik = findViewById(R.id.btnLupaNik);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegJamaahActivity.class));
            }
        });
        btnLupaNik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LupaNIKActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String NIK = etNik.getText().toString();

        if (!NIK.isEmpty()) {

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            // Ganti urlLogin dengan URL Anda
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Db_Contract.urlLogjamaah + "?NIK=" + NIK,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                int code = jsonResponse.getInt("code");
                                String status = jsonResponse.getString("status");

                                if (code == 200) {
                                    Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "NIK tidak ditemu kan, Login Gagal " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(getApplicationContext(), "NIK tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }
    }
}
