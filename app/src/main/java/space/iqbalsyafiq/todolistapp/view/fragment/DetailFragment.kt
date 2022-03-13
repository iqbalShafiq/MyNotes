package space.iqbalsyafiq.todolistapp.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import space.iqbalsyafiq.todolistapp.R
import space.iqbalsyafiq.todolistapp.databinding.FragmentDetailBinding
import space.iqbalsyafiq.todolistapp.model.Note
import space.iqbalsyafiq.todolistapp.viewmodel.DetailViewModel
import java.text.SimpleDateFormat
import java.util.*

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var note: Note? = null
    private var id: Int? = null
    private var mode = MODE_SAVE
    private var isUpdating = false

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

        // init note
        note = args.note
        id = note?.id
        mode = if (note == null) MODE_SAVE else MODE_EDIT

        with(binding) {
            // set date and time
            tvDateTime.text = getDateTime()

            // set button back on click
            btnBack.setOnClickListener { activity?.onBackPressed() }
        }

        // check note
        checkNote()

        // observe live data
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.id.observe(viewLifecycleOwner) { noteId ->
            noteId?.let {
                id = noteId.toInt()
            }
        }
    }

    private fun onDeletingEvent() {
        with(binding) {
            btnDeleteAndCancel.setImageResource(R.drawable.ic_close)
            btnSaveEditAndConfirm.setImageResource(R.drawable.ic_check)

            // on confirming delete
            btnSaveEditAndConfirm.setOnClickListener {
                viewModel.deleteNote(id!!)
                activity?.onBackPressed()
            }

            // on canceling delete
            btnDeleteAndCancel.setOnClickListener {
                // back checking note with mode edit
                checkNote()
            }
        }
    }

    private fun getDateTime(): String {
        return getString(
            R.string.date_time, SimpleDateFormat(
                "dd MMM yyyy HH:mm",
                Locale.getDefault()
            ).format(Date())
        )
    }

    @SuppressLint("NewApi")
    private fun checkNote() {
        with(binding) {
            if (mode == MODE_SAVE) {
                // set edit text
                etTitle.isEnabled = true
                etContent.apply {
                    isEnabled = true

                    // set focus to this edit text and show keyboard
                    requestFocus(textDirection)
                    val imm =
                        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
                }

                // set save and delete button
                btnSaveEditAndConfirm.setImageResource(R.drawable.ic_save)
                btnDeleteAndCancel.visibility = View.GONE

                // set save button and store note to database when clicked
                btnSaveEditAndConfirm.setOnClickListener {
                    val title = etTitle.text.toString()
                    val content = etContent.text.toString()
                    val dateTime = getDateTime()

                    // assign to note
                    note = Note(title, dateTime, content)

                    // check is updating or not then store to database
                    if (isUpdating) {
                        note!!.id = id!!
                        viewModel.updateNote(note!!)
                    } else viewModel.storeNote(note!!)

                    // switch mode to edit
                    mode = MODE_EDIT
                    checkNote()
                }
            } else {
                // set title, content, and date time view
                etTitle.setText(note!!.title)
                tvDateTime.text = note!!.dateTime
                etContent.setText(note!!.content)

                // set disabled edit text
                etTitle.isEnabled = false
                etContent.isEnabled = false

                // set edit and delete button
                btnSaveEditAndConfirm.setImageResource(R.drawable.ic_edit)
                btnDeleteAndCancel.setImageResource(R.drawable.ic_trash)
                btnDeleteAndCancel.visibility = View.VISIBLE

                // set edit click event
                btnSaveEditAndConfirm.setOnClickListener {
                    // switch mode to edit
                    mode = MODE_SAVE
                    isUpdating = true
                    checkNote()
                }

                // set delete click event
                btnDeleteAndCancel.setOnClickListener { onDeletingEvent() }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val MODE_SAVE = "save"
        private const val MODE_EDIT = "edit"
    }
}