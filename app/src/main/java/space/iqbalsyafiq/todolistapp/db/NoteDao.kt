package space.iqbalsyafiq.todolistapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import space.iqbalsyafiq.todolistapp.model.Note

@Dao
interface NoteDao {
    // Insert watch list
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    // Get notes
    @Query("SELECT * FROM note")
    suspend fun getNotes(): List<Note>

    // Get note's detail
    @Query("SELECT * FROM note WHERE id == :noteId")
    suspend fun getNoteDetail(noteId: Int): Note

    // Update note
    @Query("UPDATE note SET content = :content AND date_time = :dateTime AND title = :title WHERE id = :noteId")
    suspend fun updateNote(noteId: Int, content: String, dateTime: String, title: String)

    // Delete note
    @Query("DELETE FROM note WHERE id = :noteId")
    suspend fun deleteNote(noteId: Int)
}