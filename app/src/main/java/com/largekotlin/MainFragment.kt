package com.largekotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.largekotlin.databinding.FragmentMainBinding
import com.largekotlin.fragments.Tab1Fragment


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentMap = mutableMapOf<String, Fragment>()

        fragmentMap["A"] = Tab1Fragment()
        fragmentMap["B"] = MainFragment()

        val adapter = FragmentAdapter(requireActivity(), fragmentList = fragmentMap.values.toList())
        binding.viewpagerHolder.adapter = adapter
        TabLayoutMediator(binding.tabLayout,binding.viewpagerHolder){tab,pos ->
            tab.text = fragmentMap.keys.elementAt(pos)
        }.attach()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }


    class FragmentAdapter(private val fragmentActivity: FragmentActivity, private val fragmentList:List<Fragment>):FragmentStateAdapter(fragmentActivity){
        override fun getItemCount() = fragmentList.size
        override fun createFragment(position: Int) = fragmentList.elementAt(position)

    }
}
