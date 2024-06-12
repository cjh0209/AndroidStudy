package com.example.viewandviewgroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.viewandviewgroup.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class FirstFragment: Fragment() {
    private lateinit var Binding: FragmentAlbumBinding
    private  val information = arrayListOf("수록곡", "상세정보", "영상")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        Binding = FragmentAlbumBinding.inflate(layoutInflater)
        //val title = arguments?.getString("title")
        //val singer = arguments?.getString("singer")
        //val albumPhoto = arguments?.getParcelable("albumPhtoto")

        val albumData = arguments?.getString("album")
        val gson = Gson()
        val album = gson.fromJson(albumData, Album::class.java)

        Binding.title.text = album.title.toString()
        Binding.singer.text = album.singer.toString()
        Binding.albumPhoto.setImageResource(album.coverImg!!)


        //Binding.title.text = title
        //Binding.singer.text = singer
        /*
        이미지도 넘기고 싶었어ㅠㅠ
         
        if (albumPhoto != null) {
            Binding.albumPhoto.setImageResource(albumPhoto)
        }
        */
        Binding.backBtn.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentTest, HomeFragment())
                    .commitAllowingStateLoss()
        }


        val albumAdapter = AlbumVPAdapter(this)
        Binding.albumContentVp.adapter = albumAdapter

        TabLayoutMediator(Binding.albumContents, Binding.albumContentVp){
            tab, position ->
            tab.text = information[position]
        }.attach() //중재자
        return Binding.root
    }





}
