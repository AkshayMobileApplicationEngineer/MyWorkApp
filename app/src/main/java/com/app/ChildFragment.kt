package com.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.teampanlogic.R


class ChildFragment : Fragment() {
    private lateinit var childFragmentTextView: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    childFragmentTextView = view.findViewById(R.id.childFragmentTextView)
        val title = arguments?.getString(ARG_TITLE) ?: "Default Title"
        childFragmentTextView.text = title
    }

    companion object {
        private const val ARG_TITLE = "title"

        fun newInstance(title: String): ChildFragment {
            return ChildFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                }
            }
        }
    }
}
