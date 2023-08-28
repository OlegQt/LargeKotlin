package com.largekotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.largekotlin.databinding.FragmentTab2Binding
import com.largekotlin.databinding.FragmentTab3Binding
import com.largekotlin.util.MessageProvider
import com.largekotlin.viewmodels.Tab2ViewModel
import com.largekotlin.viewmodels.Tab3ViewModel


class Tab3Fragment : Fragment() {
    private var _binding: FragmentTab3Binding? = null
    private val binding get() = _binding!!

    private val vm by lazy { ViewModelProvider(this)[Tab3ViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTab3Binding.inflate(inflater, container, false)

        binding.btnThreadStart.setOnClickListener {
            vm.startLongFun()
        }

        binding.btnCoroutines.setOnClickListener {
            vm.coroutinesLaunch()
        }

        // Inflate the layout for this fragment
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.threadTxt.observe(viewLifecycleOwner){binding.txtThreads.setText(it)}

        vm.dialogAlertMessage.observe(viewLifecycleOwner){messageString ->
            (requireActivity() as MessageProvider).showMessage(messageString)
        }

        binding.txtDebounce.doOnTextChanged { text, start, before, count ->
            //vm.debounceSearchText(text.toString())
            vm.debounceSearchTextCoroutine(text.toString())
        }

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = Tab3Fragment()
    }
}