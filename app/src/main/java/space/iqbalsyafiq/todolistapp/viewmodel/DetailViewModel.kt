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

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    // init dao
    private val dao = NoteDatabase(getApplication()).noteDao()

    // live data
    private var _note = MutableLiveData<Note>()
    val note: LiveData<Note> = _note

    // methods
    fun storeNote(note: Note) {
        viewModelScope.launch {
            dao.insertNote(note).also {
                Log.d(TAG, "storeNote: $it")
            }
        }
    }

    companion object {
        private val TAG = DetailViewModel::class.java.simpleName
    }
}