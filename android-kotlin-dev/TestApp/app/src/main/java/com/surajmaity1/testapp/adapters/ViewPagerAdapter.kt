package com.surajmaity1.testapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.surajmaity1.testapp.fragments.*

class ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity){
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IndividualFragment()
            1 -> ProfessionalFragment()
            2 -> MerchantFragment()
            else -> IndividualFragment()
        }
    }

}