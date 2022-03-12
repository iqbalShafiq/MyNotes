package space.iqbalsyafiq.todolistapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        // set view
        with(binding) {
            btnAdd.setOnClickListener {
                val action = HomeFragmentDirections.navigateToDetailFragment()
                Navigation.findNavController(binding.root).navigate(action)
            }
        }

        // observe live data
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.empty.observe(viewLifecycleOwner) { isEmpty ->
            isEmpty?.let {
                binding.tvEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
            }
        }

        viewModel.noteList.observe(viewLifecycleOwner) { notes ->
            notes?.let {
                adapter = NoteListAdapter(requireContext(), notes)
                binding.rvNoteList.adapter = adapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}