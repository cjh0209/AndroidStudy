package com.example.viewandviewgroup

//import androidx.room.Entity
//import androidx.room.PrimaryKey

//@Entity(tableName = "SongTable")
data class Song(
        val text: String = "",
        val text2: String ="",
        var minute: String = "",
        var second: String = "",
        var total: Int = 0,
        var isPlaying: Boolean = false,
        var coverImg: Int? = null,
        var isLike: Boolean = false
)
/*{
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}


 */