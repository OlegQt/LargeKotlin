package com.largekotlin.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.largekotlin.databinding.FragmentTab2Binding
import com.largekotlin.util.MessageProvider
import com.largekotlin.viewmodels.Tab2ViewModel


class Tab2Fragment : Fragment() {
    private var _binding: FragmentTab2Binding? = null
    private val binding get() = _binding!!

    private val vm by lazy { ViewModelProvider(this)[Tab2ViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTab2Binding.inflate(inflater,container,false)

        binding.btnStandardRetrofit.setOnClickListener {
            (requireActivity() as MessageProvider).showMessage("Standard retrofit")
            vm.StandardRetrofit()
        }

        binding.btnRxjavaRetrofit .setOnClickListener {
            (requireActivity() as MessageProvider).showMessage("JavaRx retrofit")
        }

        binding.btnStandardObservable.setOnClickListener { vm.makeStandardObservable() }

        binding.btnTimerObservable.setOnClickListener { vm.makeTimerObservable() }

        // Inflate the layout for this fragment
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.txtInput.observe(viewLifecycleOwner){
            binding.txtInput.text = it
        }

        vm.txtColor.observe(viewLifecycleOwner){color ->
            binding.txtInput.setBackgroundColor(color)
        }

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    companion object {
        @JvmStatic
        fun newInstance() = Tab2Fragment()
    }
}