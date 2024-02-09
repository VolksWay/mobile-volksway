package com.example.mobile_volksway


import android.content.Intent
import android.os.Bundle
<<<<<<< HEAD
import android.widget.CheckBox
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
=======

>>>>>>> 4eb77d289bfe0a3880bafe3861b2c93754dd74b2
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController


import com.example.mobile_volksway.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var myCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

<<<<<<< HEAD
        myCheckBox = binding.checkBox

        setupCheckBoxListener()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
=======
        setSupportActionBar(binding.appBarMain.toolbar)

        //desabilita a exibiçao do titulo do nome da tela atual
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations. (R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
>>>>>>> 4eb77d289bfe0a3880bafe3861b2c93754dd74b2
            setOf(
                R.id.nav_home, R.id.nav_checklist, R.id.nav_sos, R.id.nav_perfil
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.menu.findItem(R.id.nav_home).setOnMenuItemClickListener { menu ->
            val mainIntent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(mainIntent)
            finish()
            true
        }


    }

<<<<<<< HEAD
    private fun setupCheckBoxListener() {
        myCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(this@MainActivity, "Caixa de seleção marcada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeTe
=======
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
>>>>>>> 4eb77d289bfe0a3880bafe3861b2c93754dd74b2
