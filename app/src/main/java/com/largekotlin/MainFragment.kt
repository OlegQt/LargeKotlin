package com.largekotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.largekotlin.databinding.FragmentMainBinding
import com.largekotlin.fragments.Tab1Fragment
import com.largekotlin.fragments.Tab2Fragment


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

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

        // Создание списка фрагментов
        val fragmentMap = mutableMapOf<String, Fragment>()

        fragmentMap["Tab1"] = Tab1Fragment()
        fragmentMap["Tab2"] = Tab2Fragment()

        // Инициализация адаптера для TabLayout и связь со списком фрагментов
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


    class FragmentAdapter(fragmentActivity: FragmentActivity, private val fragmentList:List<Fragment>):FragmentStateAdapter(fragmentActivity){
        override fun getItemCount() = fragmentList.size
        override fun createFragment(position: Int) = fragmentList.elementAt(position)

    }
}
