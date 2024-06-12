package com.example.viewandviewgroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viewandviewgroup.databinding.FragmentSavedsongBinding
import com.google.gson.Gson

class SavedSongFragment : Fragment() {
    lateinit var binding: FragmentSavedsongBinding
    private  var albumDatas = ArrayList<Album>()
    //lateinit var songDB: SongDatabase

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedsongBinding.inflate(inflater, container, false)

        //songDB = SongDatabase.getInstance(requireContext())!!


        albumDatas.apply {
            add(Album("Magnetic", "아일릿(Illit)", R.drawable.magnetic))
            add(Album("나는 아픈 건 딱 질색이니까", "(여자)아이들", R.drawable.idle_2))
            add(Album("고민중독", "QWER", R.drawable.manito))
            add(Album("해야 (HEYA)", "IVE(아이브)", R.drawable.ive_switch))
            add(Album("한 페이지가 될 수 있게", "Day6(데이식스)", R.drawable.thebookofusgravity))
            add(Album("SPOT!", "지코 (ZICO)", R.drawable.spot_zico))
        }

        val lockerAlbumRVAdapter = LockerAlbumRVAdapter(albumDatas)
        binding.lockerSavedSongRecyclerView.adapter = lockerAlbumRVAdapter
        binding.lockerSavedSongRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        lockerAlbumRVAdapter.setItemClickListener(object : LockerAlbumRVAdapter.OnItemClickListener {
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onRemoveAlbum(position: Int) {
                lockerAlbumRVAdapter.removeItem(position)
            }
        })

        return binding.root


    }
    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentTest, FirstFragment().apply {
                    arguments = Bundle().apply {
                        val gson = Gson()
                        val albumToJson = gson.toJson(album)
                        putString("album", albumToJson)
                    }
                })
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }

    /*
    좋아요 표시한 음악만 나타내기

    class SavedSongFragment : Fragment() {

    lateinit var songDB: SongDatabase
    lateinit var binding : FragmentSavedSongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedSongBinding.inflate(inflater, container, false)

        songDB = SongDatabase.getInstance(requireContext())!!

        return binding.root

    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.lockerMusicAlbumRv.layoutManager = LinearLayoutManager(requireActivity())
        val lockerAlbumRVAdapter = LockerAlbumRVAdapter()

        lockerAlbumRVAdapter.setItemClickListener(object : LockerAlbumRVAdapter.OnItemClickListener {

            override fun onRemoveAlbum(songId: Int) {
                songDB.songDao().updateIsLikeById(false, songId)
            }
        })
        binding.lockerMusicAlbumRv.adapter = lockerAlbumRVAdapter
        lockerAlbumRVAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
    }
}
     */


    /*

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.lockerSavedSongRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val songRVAdapter = SavedSongRVAdapter(albumDatas)

        songRVAdapter.setMyItemClickListener(object : SavedSongRVAdapter.MyItemClickListener{
            override fun onRemoveSong(songId: Song) {
                //songDB.songDao().updateIsLikeById(false,songId)
            }

        })

        binding.lockerSavedSongRecyclerView.adapter = songRVAdapter

        //songRVAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
    }

     */
}