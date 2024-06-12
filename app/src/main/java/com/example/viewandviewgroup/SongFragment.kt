package com.example.viewandviewgroup

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.viewandviewgroup.databinding.FragmentSongBinding

class SongFragment: Fragment() {
    lateinit var binding: FragmentSongBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSongBinding.inflate(inflater,container,false)

        var i = 0
        binding.songMixoffTg.setOnClickListener {
            if(i==0){
                binding.songMixoffTg.setImageResource(R.drawable.btn_toggle_on)
                i = 1
            }
            else{
                binding.songMixoffTg.setImageResource(R.drawable.btn_toggle_off)
                i = 0;
            }

        }

        return binding.root
    }
}