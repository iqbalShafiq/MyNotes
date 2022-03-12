package space.iqbalsyafiq.todolistapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import space.iqbalsyafiq.todolistapp.db.NoteDatabase
import space.iqbalsyafiq.todolistapp.model.Note

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    // init dao
    private val dao = NoteDatabase(getApplication()).noteDao()

    // init live data
    private var _empty = MutableLiveData<Boolean>()
    val empty: LiveData<Boolean> = _empty

    private var _noteList = MutableLiveData<List<Note>>()
    val noteList: LiveData<List<Note>> = _noteList

    // get notes by query
    fun getNoteList(query: String = "") {
        viewModelScope.launch {
            dao.getNotes("%$query%").also {
                Log.d(TAG, "getNoteList: $it")
                if (it.isEmpty()) _empty.value = true
                else {
                    _empty.value = false
                    _noteList.value = it
                }
            }
        }
    }

    // delete note
    fun deleteNote(noteId: Int) {
        viewModelScope.launch {
            dao.deleteNote(noteId)
            getNoteList()
        }
    }

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }
}