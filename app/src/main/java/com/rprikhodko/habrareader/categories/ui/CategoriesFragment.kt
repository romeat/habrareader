package com.rprikhodko.habrareader.categories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.rprikhodko.habrareader.R
import com.rprikhodko.habrareader.categories.AUTHORS_PAGE_INDEX
import com.rprikhodko.habrareader.categories.COMPANIES_PAGE_INDEX
import com.rprikhodko.habrareader.categories.CategoriesPagerAdapter
import com.rprikhodko.habrareader.categories.HUBS_PAGE_INDEX
import com.rprikhodko.habrareader.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val tabLayout = binding.tabLayout
        val viewPager = binding.pager

        viewPager.adapter = CategoriesPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            HUBS_PAGE_INDEX -> "ХАБЫ"
            AUTHORS_PAGE_INDEX -> "АВТОРЫ"
            COMPANIES_PAGE_INDEX -> "КОМПАНИИ"
            else -> null
        }
    }
}