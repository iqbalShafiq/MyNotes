package space.iqbalsyafiq.todolistapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import space.iqbalsyafiq.todolistapp.R
import space.iqbalsyafiq.todolistapp.databinding.FragmentDetailBinding
import space.iqbalsyafiq.todolistapp.model.Note
import space.iqbalsyafiq.todolistapp.viewmodel.DetailViewModel
import java.text.SimpleDateFormat
import java.util.*

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            // set date and time
            tvDateTime.text = getString(
                R.string.date_time, SimpleDateFormat(
                    "dd MMM yyyy HH:mm",
                    Locale.getDefault()
                ).format(Date())
            )

            // set button back on click
            btnBack.setOnClickListener { activity?.onBackPressed() }

            // save to database
            btnSaveEditAndConfirm.setOnClickListener {
                val title = etTitle.text.toString()
                val content = etContent.text.toString()
                viewModel.storeNote(
                    Note(
                        title,
                        tvDateTime.text.toString(),
                        content
                    )
                )

                // show success toast
                Toast.makeText(
                    requireContext(),
                    "note successfully added to list.",
                    Toast.LENGTH_SHORT
                ).show()

                // set disabled edit text
                etTitle.isEnabled = false
                etContent.isEnabled = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}