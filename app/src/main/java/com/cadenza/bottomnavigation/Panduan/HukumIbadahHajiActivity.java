package com.cadenza.bottomnavigation.Panduan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.cadenza.bottomnavigation.databinding.ActivityHukumIbadahHajiBinding;

public class HukumIbadahHajiActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityHukumIbadahHajiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHukumIbadahHajiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}