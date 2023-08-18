package com.largekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.largekotlin.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRootBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction()
                .add(R.id.root_fragment_container,MainFragment.newInstance())
                .commit()
        }
    }
}