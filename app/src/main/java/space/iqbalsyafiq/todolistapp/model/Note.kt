package space.iqbalsyafiq.todolistapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String?,
    @ColumnInfo(name = "date_time")
    val dateTime: String?,
    val content: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var isDeleting: Boolean = false
}
