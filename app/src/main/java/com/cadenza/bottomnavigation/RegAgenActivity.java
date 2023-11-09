package com.cadenza.bottomnavigation;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegAgenActivity extends AppCompatActivity {


    private EditText namalengkapEditText, emailEditText, PassEditText, nohpEditText;
    private DatabaseHelper databaseHelper;
    private boolean isNikVisible = false; // Variabel ini digunakan untuk mengatur visibilitas NIK

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_agen);

        // Inisialisasi komponen EditText
        namalengkapEditText = findViewById(R.id.namalengkap);
        emailEditText = findViewById(R.id.email);
        PassEditText = findViewById(R.id.password);
        nohpEditText = findViewById(R.id.nohp);

        // Inisialisasi database helper
        databaseHelper = new DatabaseHelper(this);

        // Tambahkan listener untuk tombol "Daftar"
        Button signupButton = findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mengambil data dari formulir
                String USERNAME = namalengkapEditText.getText().toString();
                String EMAIL = emailEditText.getText().toString();
                String PASSWORD = PassEditText.getText().toString();
                String PHONE = nohpEditText.getText().toString();

                // Validasi kolom
                boolean isValid = true;
                if (USERNAME.isEmpty()) {
                    namalengkapEditText.setError("Nama lengkap harus diisi");
                    isValid = false;
                }
                if (EMAIL.isEmpty()) {
                    emailEditText.setError("Email harus diisi");
                    isValid = false;
                }
                if (PASSWORD.isEmpty()) {
                    PassEditText.setError("Password harus diisi");
                    isValid = false;
                } else if (PASSWORD.length() == 8) {
                    PassEditText.setError("Password Minimal dari 8 karakter");
                    isValid = false;
                }
                if (PHONE.isEmpty()) {
                    nohpEditText.setError("Nomor HP harus diisi");
                    isValid = false;
                }

                if (isValid) {
                    // Simpan data ke SQLite
                    SQLiteDatabase insertAgen = databaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(DatabaseHelper.USERNAME, USERNAME);
                    values.put(DatabaseHelper.EMAIL, EMAIL);
                    values.put(DatabaseHelper.PASSWORD, PASSWORD);
                    values.put(DatabaseHelper.PHONE, PHONE);

                    long RegAgen = insertAgen.insert(DatabaseHelper.AGEN, null, values);

                    if (RegAgen != -1) {
                        // Data berhasil disimpan
                        Toast.makeText(RegAgenActivity.this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show();

                        // Navigasi ke aktivitas activity_log_agen
                        Intent intent = new Intent(RegAgenActivity.this, LogAgenActivity.class);
                        startActivity(intent);
                    } else {
                        // Gagal mendaftar
                        Toast.makeText(RegAgenActivity.this, "Gagal mendaftar", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Menambahkan onTouchListener untuk ikon Show/Hide NIK
        PassEditText.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (PassEditText.getRight() - PassEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        toggleNikVisibility(PassEditText);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    // Metode untuk mengganti visibilitas NIK (terlihat atau tersembunyi)
    private void toggleNikVisibility(EditText editText) {
        if (isNikVisible) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off_24, 0);
        } else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_24, 0);
        }

        // Memperbarui status visibilitas
        isNikVisible = !isNikVisible;
    }
}