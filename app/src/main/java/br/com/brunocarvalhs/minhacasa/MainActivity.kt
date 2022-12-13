package br.com.brunocarvalhs.minhacasa

import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import br.com.brunocarvalhs.minhacasa.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.costsFragment))
        binding.topAppBar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}