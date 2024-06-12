package com.example.viewandviewgroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.viewandviewgroup.databinding.*

class SearchFragment: Fragment() {
    private lateinit var Binding: FragmentSearchBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        Binding = FragmentSearchBinding.inflate(layoutInflater)
        return Binding.root
    }
}