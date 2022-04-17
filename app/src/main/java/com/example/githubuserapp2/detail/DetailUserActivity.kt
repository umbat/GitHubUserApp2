package com.example.githubuserapp2.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuserapp2.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailUserBinding

    private lateinit var viewModel : DetailUserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(EXTRA_ID, 0)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        viewModel.setUserDetail(username.toString())
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    tvDetailName.text = it.name
                    tvDetailUsename.text = it.username
                    tvDetailFollowing.text = StringBuilder().append(it.following).append(" Following")
                    tvDetailFollowers.text = StringBuilder().append(it.followers).append(" Followers")
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatarUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivDetailProfile)
                }
            }
        }

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count>0){
                        binding.favouriteToggle.isChecked = true
                        _isChecked = true
                    } else {
                        binding.favouriteToggle.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.favouriteToggle.setOnClickListener {
            _isChecked = ! _isChecked
            if (_isChecked){
                viewModel.addToFavorite(id, username.toString(), avatarUrl.toString())
            } else {
                viewModel.removeFavoriteUser(id)
            }
            binding.favouriteToggle.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this,supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }
}