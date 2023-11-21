package com.cadenza.bottomnavigation.AGEN;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cadenza.bottomnavigation.API.Db_Contract;
import com.cadenza.bottomnavigation.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class RegisterAgenActivity extends AppCompatActivity {

    private EditText etNamalengkap, etEmail, etPassword, etNo_hp;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_agen);

        etNamalengkap = findViewById(R.id.etNamalengkap);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etNo_hp = findViewById(R.id.etNo_hp);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = etNamalengkap.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String no_hp = etNo_hp.getText().toString();

                if (!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(no_hp)) {
                    registerUser(nama, email, password, no_hp);
                } else {
                    Toast.makeText(getApplicationContext(), "Ada Data Yang Masih Kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser(String nama, String email, String password, String no_hp) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urlRegister,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return createRequestParams(nama, email, password, no_hp);
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void handleResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.getInt("code");

            if (code == 201) {
                Toast.makeText(getApplicationContext(), "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                // tambahkan logika atau navigasi ke halaman berikutnya setelah registrasi berhasil
            } else if (code == 406) {
                Toast.makeText(getApplicationContext(), "Email sudah terdaftar", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Registrasi Gagal", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleError(VolleyError error) {
        Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
    }

    private Map<String, String> createRequestParams(String nama, String email, String password, String no_hp) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("nama", nama);
        requestParams.put("email", email);
        requestParams.put("password", password);
        requestParams.put("nohp", no_hp);
        return requestParams;
    }
}
