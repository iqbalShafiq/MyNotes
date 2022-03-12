package space.iqbalsyafiq.todolistapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import space.iqbalsyafiq.todolistapp.databinding.ItemNoteBinding
import space.iqbalsyafiq.todolistapp.model.Note

class NoteListAdapter(
    private val context: Context,
    private val noteList: List<Note>
) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var _binding: ItemNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = ItemNoteBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )

        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]
        with(binding) {
            tvTitle.text = note.title
            tvContent.text = note.content
        }
    }

    override fun getItemCount(): Int = noteList.size

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        _binding = null
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        _binding = null
    }
}