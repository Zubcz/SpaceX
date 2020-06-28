package com.zubak.spacex

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.zubak.spacex.api.LaunchesType
import com.zubak.spacex.core.TAG
import com.zubak.spacex.ui.filters.FiltersFragment
import com.zubak.spacex.ui.launches.LaunchesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navView: NavigationView
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.alpha = 0.75F
        fab.setOnClickListener {
            val previousFragment =
                supportFragmentManager.findFragmentByTag(FiltersFragment::class.simpleName)
            if (previousFragment?.isVisible == true) {
                supportFragmentManager.popBackStack()
            } else {
                showSettings()
            }
        }

        drawerLayout = findViewById(R.id.drawer_layout)

        setupDrawerToggle()?.let { drawerToggle = it }
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()

        navView = findViewById(R.id.nav_view)
        setupDrawerContent(navView)

        navController = findNavController(R.id.nav_host_fragment)
    }

    private fun showSettings() {
        val fragment = FiltersFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment, FiltersFragment::class.simpleName)
            .addToBackStack(fragment.TAG)
            .commit()
    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle? {
        return ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        val launchesType = when (menuItem.itemId) {
            R.id.all_launches -> LaunchesType.ALL.name
            R.id.past_launches -> LaunchesType.PAST.name
            R.id.upcoming_launches -> LaunchesType.UPCOMING.name
            else -> LaunchesType.ALL.name
        }

        val fragment = LaunchesFragment()

        val args = Bundle()
        args.putString(getString(R.string.launches_type_bundle), launchesType)
        fragment.arguments = args

        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()

        menuItem.isChecked = true
        title = menuItem.title
        drawerLayout.closeDrawers()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return false
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
