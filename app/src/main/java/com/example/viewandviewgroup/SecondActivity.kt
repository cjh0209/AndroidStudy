package com.example.viewandviewgroup

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.viewandviewgroup.databinding.ActivitySecondBinding
import java.lang.Thread.sleep
import java.util.*
import kotlin.concurrent.thread


class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    private var timer: Timer? = null
    private var isPlaying: Boolean = false
    private var song: Song = Song()
    //private var gson : Gson = Gson()
    //private var mediaPlayer : MediaPlayer? = null

    val songs = arrayListOf<Song>()
    //lateinit var songDB: SongDatabase
    //var nowPos = 0


    /*
    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer != null) { //앱을 끌 때 제대로 초기화를 해줌
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val actionBar = supportActionBar
        actionBar!!.hide()
        setContentView(binding.root)

        //var m1: MediaPlayer = MediaPlayer.create(this, R)


        val handler = Handler(mainLooper)
        //var minute: String = ""
        //var second: String = ""
        var total = 0



        if (intent.hasExtra("text") && intent.hasExtra("text2")) {
            val getText = intent.getStringExtra("text")
            val getText2 = intent.getStringExtra("text2")
            binding.textReturn.text = getText
            binding.textReturn2.text = getText2
        }


        binding.finishBtn.setOnClickListener {
            finish()
        }

        //setPlayer 구현되어잇음.
        binding.songMiniPlayerIv.setOnClickListener {
            if (!isPlaying) {
                startStopService()
                //val music = resources.getIdentifier()//song.music, "raw", this.packageName
                //startTimer()
                isPlaying=true

                binding.songMiniPlayerIv.setImageResource(R.drawable.btn_miniplay_pause)


                //mediaPlayer = MediaPlayer.create(this, R.raw.music_bboom)

                // 쓰레드 실행 - 타이머를 위한 쓰레드
                thread(start = true) {

                    //var mills: Float = 0f
                    while(isPlaying){

                        // 1초 지연
                        sleep(1000)
                        //mills += 1000
                        total += 1

                        val minute = String.format("%02d", total / 60) // 분
                        val second = String.format("%02d", total % 60) // 초
                        handler.post{
                            binding.songStartTimeTv.text = "$minute:$second"
                            binding.songProgressSb.progress = total*1500
                        }
                    }

                }
            } else {
                startStopService()
                isPlaying = false
                binding.songMiniPlayerIv.setImageResource(R.drawable.btn_miniplayer_play)
                /*
                song.second = second
                song.minute = minute
                val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                val songToJson = gson.toJson(song)
                editor.putString("songData", songToJson)
                editor.apply()

                 */

            }
        }

        /*
        binding.songLikeIv.setOnClickListener {
            setLike(songs[nowPos].isLike)
        }
        */

    }

    /*

    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike, songs[nowPos].id)

        if (!isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

     */





    private fun startStopService(){
        if(isServiceRunning(MyService::class.java)){
            Toast.makeText(this, "포그라운드 서비스 멈춤", Toast.LENGTH_SHORT).show()
            stopService(Intent(this, MyService::class.java))
        }
        else {
            Toast.makeText(this, "Foreground Service Started", Toast.LENGTH_SHORT).show()
            startService(Intent(this, MyService::class.java))
        }
    }

    private fun isServiceRunning(inputClass: Class<MyService>) : Boolean {
        val manager : ActivityManager = getSystemService(
                Context.ACTIVITY_SERVICE
        ) as ActivityManager

        for (service : ActivityManager.RunningServiceInfo in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (inputClass.name.equals(service.service.className)) {
                return true
            }

        }
        return false
    }



/*
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("message", "뒤로가기 버튼 클릭")

        setResult(RESULT_OK, intent)
        finish()
    }


 */


    /*

    override fun onPause() {
        super.onPause()
        songs[nowPos].second = (songs[nowPos].playTime * binding.songProgressSb.progress) / 100000
        songs[nowPos].isPlaying = false
        setPlayerStatus(false)
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("songId", songs[nowPos].id)
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() // 미디어 플레이어가 갖고 있던 리소스를 해제한다.
        mediaPlayer = null // 미디어 플레이어를 해제한다.
    }

    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }
    private fun initClickListener(){
        binding.songDownIb.setOnClickListener {
        	val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("message", songs[nowPos].title + "_" + songs[nowPos].singer)
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }
    }

    private fun initSong() { // intent 방식 사용 안함
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)

        Log.d("now Song ID", songs[nowPos].id.toString())

        startTimer()
        setPlayer(songs[nowPos])
    }

	// songId로 position을 얻는 메서드
    private fun getPlayingSongPosition(songId: Int): Int{
        for (i in 0 until songs.size){
            if (songs[i].id == songId){
                return i
            }
        }
        return 0
    }

    private fun setPlayer(song : Song) {
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songAlbumIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)
        setPlayerStatus(song.isPlaying)
    }

    fun setPlayerStatus (isPlaying : Boolean){
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){ // 재생중
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        } else { // 일시정지
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if(mediaPlayer?.isPlaying == true) { // 재생 중이 아닐 때에 pause를 하면 에러가 나기 때문에 이를 방지
                mediaPlayer?.pause()
            }
        }
    }
     private fun startTimer() {
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true) : Thread() {
        private var second : Int = 0
        private var mills : Float = 0F

        override fun run() {
            super.run()

            try {
                while(true) {
                    if(second >= playTime) {
                        break
                    }

                    while (!isPlaying) {
                        sleep(200) // 0.2초 대기
                    }

                    if(isPlaying) {
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            // binding.songProgressSb.progress = ((mills/playTime*1000) * 100).toInt()
                            binding.songProgressSb.progress = ((mills/playTime) * 100).toInt()
                        }

                        if(mills % 1000 == 0F) { // 1초
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("SongActivity", "Thread Terminates! ${e.message}")
            }
        }
    }

     */

    /*
    private fun pauseTimer() {

        timer?.stopTimer()
        isPlaying = false
        binding.songMiniPlayerIv.setImageResource(R.drawable.btn_miniplayer_play)

    }


    private fun startTimer(){

        timer?.stopTimer()
        val playTime = binding.songStartTimeTv.text.toString().toInt()
        timer = Timer(playTime)
        timer?.start()
        isPlaying=true
        binding.songMiniPlayerIv.setImageResource(R.drawable.btn_miniplay_pause)
    }

    inner class Timer(private val playTime: Int):Thread(){

        private var second : Int = 0
        private var mills: Float = 0f
        private val handler = Handler(Looper.getMainLooper())
        private var running: Boolean = true

        override fun run() {
            super.run()
            while(running && second < playTime ){
                sleep(50)
                mills += 50
                    handler.post {
                        binding.songProgressSb.progress = ((mills / playTime) * 100).toInt()
                        binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                    }

                    if (mills % 1000 == 0f) {
                        second++
                    }
            }
        }
        fun stopTimer() {
            running = false
        }
    }
    */

}