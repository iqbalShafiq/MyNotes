package space.iqbalsyafiq.todolistapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Note(
    val title: String?,
    @ColumnInfo(name = "date_time")
    val dateTime: String?,
    val content: String?
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var isDeleting: Boolean = false
}
