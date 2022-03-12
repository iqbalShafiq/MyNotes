package space.iqbalsyafiq.todolistapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import space.iqbalsyafiq.todolistapp.R
import space.iqbalsyafiq.todolistapp.databinding.FragmentDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailFragment : Fragment() {

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
            tvDateTime.text = getString(
                R.string.date_time, SimpleDateFormat(
                    "dd MMM yyyy HH:mm",
                    Locale.getDefault()
                ).format(Date())
            )

            btnBack.setOnClickListener { activity?.onBackPressed() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}