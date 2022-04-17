package com.example.githubuserapp2.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.SearchView
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp2.R
import com.example.githubuserapp2.data.model.User
import com.example.githubuserapp2.databinding.ActivityMainBinding
import com.example.githubuserapp2.detail.DetailUserActivity
import com.example.githubuserapp2.favorite.FavoriteActivity
import com.example.githubuserapp2.ui.darkmode.*
import com.example.githubuserapp2.ui.otheractivity.AboutActivity
import com.example.githubuserapp2.ui.otheractivity.HelpActivity
import com.google.android.material.switchmaterial.SwitchMaterial

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object: UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also{
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.avatarUrl)
                    startActivity(it)
                }
            }
        })

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }

        val pref = SettingPreferences.getInstance(dataStore)
        val darkModeViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            DarkModeViewModel::class.java
        )

        darkModeViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun showLoading(state: Boolean){
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                binding.apply {
                    if (query.isEmpty()) return@apply
                    showLoading(true)
                    viewModel.setSearchUsers(query)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        viewModel.getSearchUser().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.settings -> {
                val i = Intent(this, DarkModeActivity::class.java)
                startActivity(i)
                true
            }
            R.id.menu1 -> {
                val i = Intent(this, AboutActivity::class.java)
                startActivity(i)
                true
            }
            R.id.menu2 -> {
                val i = Intent(this, HelpActivity::class.java)
                startActivity(i)
                true
            }
            R.id.favourite -> {
                val i = Intent(this, FavoriteActivity::class.java)
                startActivity(i)
                true
            }
            else -> true
        }
    }
}