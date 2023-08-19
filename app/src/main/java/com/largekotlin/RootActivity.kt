package com.largekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.largekotlin.databinding.ActivityRootBinding
import com.largekotlin.fragments.Tab1Fragment

class RootActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRootBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.root_fragment_container, MainFragment.newInstance())
                .commit()
        }

        binding.navigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_settings -> openAnotherFragment(Tab1Fragment.newInstance())
                else -> openAnotherFragment(MainFragment.newInstance())
            }
        }
    }

    private fun openAnotherFragment(fragmentToOpen: Fragment): Boolean {
        supportFragmentManager.commit {
            replace(R.id.root_fragment_container, fragmentToOpen)
        }
        return true
    }
}