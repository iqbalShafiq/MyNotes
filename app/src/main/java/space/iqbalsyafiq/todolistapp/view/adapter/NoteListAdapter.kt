package space.iqbalsyafiq.todolistapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*
import space.iqbalsyafiq.todolistapp.databinding.ItemNoteBinding
import space.iqbalsyafiq.todolistapp.model.Note
import space.iqbalsyafiq.todolistapp.view.fragment.HomeFragment

class NoteListAdapter(
    private val fragment: HomeFragment,
    val noteList: List<Note>
) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var _binding: ItemNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]
        with(holder.itemView) {
            tvTitle.text = note.title
            tvContent.text = note.content

            // check llConfirmationDelete visibility by isDeleting value
            llConfirmationDelete.visibility = if (note.isDeleting) View.VISIBLE else View.GONE

            // on canceling delete
            btnCancelDelete.setOnClickListener {
                holder.itemView.btnDelete.visibility = View.VISIBLE
                holder.itemView.llConfirmationDelete.visibility = View.GONE
            }

            // on confirming delete
            btnConfirmDelete.setOnClickListener {
                fragment.deleteNote(note.id)
                notifyItemChanged(position)
            }

            // apply to btnDelete
            btnDelete.apply {
                // set visibility by isDeleting value
                if (note.isDeleting) {
                    visibility = View.VISIBLE
                    holder.itemView.llConfirmationDelete.visibility = View.GONE
                } else visibility = View.GONE

                // on clicked
                setOnClickListener {
                    btnDelete.visibility = View.GONE
                    holder.itemView.llConfirmationDelete.visibility = View.VISIBLE
                }
            }

            // on card clicked
            cvMain.setOnClickListener {
                fragment.navigateToDetail(note)
            }
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