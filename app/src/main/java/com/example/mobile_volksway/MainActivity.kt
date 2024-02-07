package com.example.mobile_volksway


import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController

import com.example.mobile_volksway.databinding.ActivityMainBinding
import com.example.mobile_volksway.views.EmergenciaSOSFragment
import com.example.mobile_volksway.views.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNavView.setOnItemSelectedListener {
            when(it.itemId){

                R.id.nav_home -> replaceFragment(HomeFragment())
//                R.id.nav_checklist -> replaceFragment(Checklist())
                R.id.nav_sos -> replaceFragment(EmergenciaSOSFragment())
//                R.id.nav_perfil -> replaceFragment(Perfil())

                else -> {

                }
            }

            true
        }

        }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()



    }
}