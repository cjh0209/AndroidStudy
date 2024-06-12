package com.example.viewandviewgroup

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerVPAdapter(fragment:Fragment) :FragmentStateAdapter(fragment){
    private val fragmentlist : ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        return fragmentlist.size
    }

    override fun createFragment(position: Int): Fragment = fragmentlist[position]

    fun addFragment(fragment: Fragment){
        fragmentlist.add(fragment)
        notifyItemInserted(fragmentlist.size-1)
    } //실행시 아무것도 없기 때문에 homefragment에 써주기 위함.

}