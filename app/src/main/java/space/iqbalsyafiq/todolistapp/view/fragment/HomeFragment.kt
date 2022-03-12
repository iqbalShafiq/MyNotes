package space.iqbalsyafiq.todolistapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import space.iqbalsyafiq.todolistapp.databinding.FragmentHomeBinding
import space.iqbalsyafiq.todolistapp.view.adapter.NoteListAdapter
import space.iqbalsyafiq.todolistapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NoteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get notes
        viewModel.getNoteList()

        // set view
        with(binding) {
            // navigate to detail fragment to create new note
            btnAdd.setOnClickListener {
                val action = HomeFragmentDirections.navigateToDetailFragment()
                Navigation.findNavController(binding.root).navigate(action)
            }

            // search note
            etSearch.addTextChangedListener {
                viewModel.getNoteList(it.toString())
            }

            // delete note
            btnDelete.setOnClickListener {
                adapter.noteList.forEachIndexed { index, note ->
                    note.isDeleting = !note.isDeleting
                    adapter.notifyItemChanged(index)
                }
            }
        }

        // observe live data
        observeLiveData()
    }

    fun deleteNote(noteId: Int) {
        viewModel.deleteNote(noteId)
    }

    private fun observeLiveData() {
        viewModel.empty.observe(viewLifecycleOwner) { isEmpty ->
            isEmpty?.let {
                binding.tvEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
            }
        }

        viewModel.noteList.observe(viewLifecycleOwner) { notes ->
            notes?.let {
                Log.d(TAG, "observeLiveData: $notes")
                adapter = NoteListAdapter(this, notes)
                binding.rvNoteList.adapter = adapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private val TAG = HomeFragment::class.java.simpleName
    }
}