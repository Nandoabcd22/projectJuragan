package com.cadenza.bottomnavigation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogAgenActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private Button loginButton;

    private EditText nikEditText;
    private boolean isNikVisible = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_agen);

        // Menghubungkan tombol "Daftar" ke aktivitas pendaftaran
        TextView daftarTextView = findViewById(R.id.btnDaftar);
        daftarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogAgenActivity.this, RegAgenActivity.class);
                startActivity(intent);
            }
        });

        // Menginisialisasi database helper dan tampilan
        databaseHelper = new DatabaseHelper(this);
        loginButton = findViewById(R.id.loginButton);
        nikEditText = findViewById(R.id.password);

        // Menambahkan onTouchListener untuk ikon Show/Hide NIK
        nikEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (nikEditText.getRight() - nikEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        toggleNikVisibility(nikEditText);
                        return true;
                    }
                }
                return false;
            }
        });

        // Menangani klik tombol Login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = nikEditText.getText().toString();

                if (databaseHelper.checkPassword(password)) {
                    // Jika NIK valid, lanjutkan ke activity_home_jamaah
                    Intent intent = new Intent(LogAgenActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(LogAgenActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                } else {
                    // Jika NIK tidak valid, tampilkan pesan kesalahan
                    Toast.makeText(LogAgenActivity.this, "Password anda salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Metode untuk mengganti visibilitas NIK (terlihat atau tersembunyi)
    private void toggleNikVisibility(EditText editText) {
        if (isNikVisible) {
            // NIK sekarang tersembunyi
            editText.setInputType(129); // InputType.TYPE_TEXT_VARIATION_PASSWORD
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off_24, 0);
        } else {
            // NIK sekarang terlihat
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_24, 0);
        }

        // Memperbarui status visibilitas
        isNikVisible = !isNikVisible;
    }
}