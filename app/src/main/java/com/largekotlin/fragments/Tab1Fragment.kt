package com.largekotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.largekotlin.R
import com.largekotlin.databinding.FragmentTab1Binding
import com.largekotlin.viewmodels.Tab1ViewModel


class Tab1Fragment : Fragment() {
    private var _binding:FragmentTab1Binding? = null
    private val binding get() = _binding!!

    private val vm by lazy { ViewModelProvider(this)[Tab1ViewModel::class.java] }

    private var pauseTimerWhenFragmentOnPause:Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTab1Binding.inflate(inflater,container,false)

        // Inflate the layout for this fragment
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtLbl.text = getString(R.string.tab1_article)

        binding.btnStartTimer.setOnClickListener { vm.startTimer() }

        binding.checkboxPauseControl.setOnCheckedChangeListener { compoundButton, isChecked ->
            this.pauseTimerWhenFragmentOnPause = isChecked
            //this.pauseTimerWhenFragmentOnPause = compoundButton.isChecked
        }

        // Работа с ViewModel
        vm.timer.observe(viewLifecycleOwner){
            binding.txtTimer.text = it.toString()
        }
    }

    override fun onPause() {
        super.onPause()
        if (pauseTimerWhenFragmentOnPause) vm.stopTimer()

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