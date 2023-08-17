package com.largekotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.largekotlin.databinding.FragmentTab1Binding


class Tab1Fragment : Fragment() {
    private var _binding:FragmentTab1Binding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTab1Binding.inflate(inflater,container,false)

        // Inflate the layout for this fragment
        return _binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    companion object {
        @JvmStatic
        fun newInstance() = Tab1Fragment()
    }
}