package com.example.mobile_volksway


import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mobile_volksway.databinding.ActivityLoginBinding


import com.example.mobile_volksway.databinding.ActivityMainBinding
import com.example.mobile_volksway.views.EmergenciaSOSFragment
import com.example.mobile_volksway.views.HomeFragment



class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)





    }
}