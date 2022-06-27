package com.example.jpmcnycschools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jpmcnycschools.databinding.ActivityMainBinding
import com.example.jpmcnycschools.view.fragment.SchoolListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SchoolListFragment()).commit()
    }
}