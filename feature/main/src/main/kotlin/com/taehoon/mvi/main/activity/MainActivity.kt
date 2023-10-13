package com.taehoon.mvi.main.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.taehoon.mvi.main.R
import com.taehoon.mvi.main.databinding.ActivityMainBinding
import com.taehoon.mvi.main.fragment.GalleryFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        supportFragmentManager.commit {
            add(R.id.fragmentContainerView, GalleryFragment.newInstance())
        }
    }
}