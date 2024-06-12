package com.example.viewandviewgroup

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.viewandviewgroup.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    // ViewBinding 인스턴스
    private lateinit var binding: ActivityMainBinding
    private var isPlaying: Boolean = false
    private var song: Song = Song()
    private var gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 테마 설정
        setTheme(R.style.Theme_Flo)
        // ViewBinding 초기화

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // ActionBar가 null이 아닌지 확인 후 숨김
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        // Handler 초기화
        val handler = Handler(mainLooper)
        var total = 0

        // Song 인스턴스 초기화 (textTest 및 textTest2가 null일 경우를 대비)
        //val songTitle = binding.textTest?.text?.toString() ?: ""
       // val songSinger = binding.textTest2?.text?.toString() ?: ""
        //val song = Song(songTitle, songSinger)

        // Fragment 설정
        setFragment()

        // MiniPlayer 클릭 리스너 설정
        binding.miniplayer.setOnClickListener {
            val teat = binding.textTest.text.toString()
            val teat2 = binding.textTest2.text.toString()
            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra("text", teat)
                putExtra("text2", teat2)
            }
            startActivity(intent)
        }


        // 플레이/일시정지 버튼 리스너 설정
        binding.mainMiniPlayerBtn.setOnClickListener {
            if (!isPlaying) {
                isPlaying = true
                binding.mainMiniPlayerBtn.setImageResource(R.drawable.btn_miniplay_pause)
                // 타이머 업데이트를 위한 쓰레드 시작
                thread(start = true) {
                    while (isPlaying) {
                        Thread.sleep(1000)
                        total += 1
                        handler.post {
                            binding.progressSb.progress = total * 1500
                        }
                    }
                }
            } else {
                isPlaying = false
                binding.mainMiniPlayerBtn.setImageResource(R.drawable.btn_miniplayer_play)
            }
        }

    }

    // 초기 Fragment 설정 및 Bottom Navigation 리스너 설정
    private fun setFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentTest, HomeFragment())
                .commitAllowingStateLoss()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentTest, HomeFragment())
                            .commitAllowingStateLoss()
                    true
                }

                R.id.fragment_around -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentTest, LookFragment())
                            .commitAllowingStateLoss()
                    true
                }

                R.id.fragment_search -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentTest, SearchFragment())
                            .commitAllowingStateLoss()
                    true
                }
                R.id.fragment_content -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentTest, ContentsFragment())
                            .commitAllowingStateLoss()
                    true
                }
                else -> false
            }
        }
    }

    // 플레이어 UI 업데이트
    fun updateMainPlayerCl(album: Album) {
        binding.textTest.text = album.title
        binding.textTest2.text = album.singer
        binding.progressSb.progress = 0
    }
}
