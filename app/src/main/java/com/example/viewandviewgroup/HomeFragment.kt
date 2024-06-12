package com.example.viewandviewgroup

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.viewandviewgroup.databinding.FragmentHomeBinding
import com.google.gson.Gson
import java.util.*


class HomeFragment: Fragment(), CommunicationInterface {
    private lateinit var Binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()

    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())
    //private var currentPage = 0 //현재 페이지 번호

    override fun sendData(album: Album) {
        if (activity is MainActivity) {
            val activity = activity as MainActivity
            activity.updateMainPlayerCl(album)
        }
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        Binding = FragmentHomeBinding.inflate(inflater,container, false)

        albumDatas.apply {
            add(Album("Super Real Me", "아일릿(Illit)", R.drawable.magnetic))
            add(Album("2", "(여자)아이들", R.drawable.idle_2))
            add(Album("MANITO", "QWER", R.drawable.manito))
            add(Album("IVE SWITCH", "IVE(아이브)", R.drawable.ive_switch))
            add(Album("The Book of Us:Gravity", "Day6(데이식스)", R.drawable.thebookofusgravity))
            add(Album("SPOT!", "지코 (ZICO)", R.drawable.spot_zico))
        }

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        Binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        Binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                //var firstFragment = FirstFragment()
                //var bundle = Bundle()

                //bundle.putString("title","Super Real Me".toString())
               // bundle.putString("singer","아일릿(Illit)".toString())
                //bundle.putParcelable("albumPhoto",  )
                //firstFragment.arguments = bundle

                (context as MainActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentTest, FirstFragment().apply {
                            arguments = Bundle().apply {
                                val gson = Gson()
                                val albumJson = gson.toJson(album)
                                putString("album", albumJson)
                            }
                        })
                        .addToBackStack(null)
                        .commitAllowingStateLoss()

                 /*
                parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentTest, firstFragment)
                        .addToBackStack(null)
                        .commit()

                  */

                }
            override fun onPlayAlbum(album: Album){
                sendData(album)

            }
            /*
            override fun onRemoveAlbum(position: Int){
                albumRVAdapter.removeItem(position)
            }
             */
        })

        /*

        Binding.albumBtn.setOnClickListener {

            var firstfragment = FirstFragment()
            var bundle = Bundle()

            var albumPh = Binding.albumBtn.drawable

            bundle.putString("title",Binding.text11.text.toString())
            bundle.putString("singer",Binding.text12.text.toString())
            bundle.putParcelable("albumPhoto", albumPh.toBitmap() )
            firstfragment.arguments = bundle
            parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentTest, firstfragment)
                    .addToBackStack(null)
                    .commit()
            //.commitAllowingStateLoss()
        }
        */


        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        Binding.homeBannerVp.adapter = bannerAdapter
        Binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        Binding.bannerIndicator.setViewPager(Binding.homeBannerVp)

        autoSlide(bannerAdapter)

        return Binding.root
    }

    private fun autoSlide(bannerAdapter: BannerVPAdapter) {
        timer.scheduleAtFixedRate(object : TimerTask(){
            override fun run(){
                handler.post{
                    val nextItem = Binding.homeBannerVp.currentItem + 1
                    if(nextItem < bannerAdapter.itemCount){
                        Binding.homeBannerVp.currentItem = nextItem
                    }else{
                        Binding.homeBannerVp.currentItem = 0
                    }
                }
            }
        }, 3000, 3000)
    }



}
