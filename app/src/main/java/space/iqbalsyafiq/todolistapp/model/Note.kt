package space.iqbalsyafiq.todolistapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String?,
    @ColumnInfo(name = "date_time")
    val dateTime: String?,
    val content: String?
)
