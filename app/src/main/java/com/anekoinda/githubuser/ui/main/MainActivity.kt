package com.anekoinda.githubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anekoinda.githubuser.databinding.ActivityMainBinding
import com.anekoinda.githubuser.model.User
import com.anekoinda.githubuser.ui.detail.DetailUserActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // change title action bar
        supportActionBar?.title = "Github Search User's"

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.USERNAME, data.login)
                    startActivity(it)
                }
            }
        })

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            UserViewModel::class.java
        )

        binding.apply {
            val layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.layoutManager = layoutManager
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            inputSearch.setOnClickListener {
                searchUser()
            }

            inputSearch.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getUser().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
                if (it.isEmpty()) {
                    binding.notFound.visibility = View.VISIBLE
                } else {
                    binding.notFound.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun searchUser() {
        binding.apply {
            val query = inputSearch.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setUser(query)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}