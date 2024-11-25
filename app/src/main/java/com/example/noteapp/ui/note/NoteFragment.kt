package com.example.noteapp.ui.note

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.noteapp.Utils.NetworkResult
import com.example.noteapp.databinding.FragmentNoteBinding
import com.example.noteapp.models.NoteRequest
import com.example.noteapp.models.NoteResponse
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val noteViewMode by viewModels<NoteViewModel>()
    private var note: NoteResponse? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()
        bindHandlers()
        bindObservers()
    }


    private fun bindHandlers() {
        binding.btnDelete.setOnClickListener {
            note?.let { noteViewMode.deleteNote(it!!._id) }
        }

        binding.btnSubmit.setOnClickListener {
            val title = binding.txtTitle.text.toString()
            val description = binding.txtDescription.text.toString()
            val noteRequest = NoteRequest(description, title)
            if (note == null) {
                noteViewMode.createNote(noteRequest)
            } else {
                noteViewMode.updateNote(note!!._id, noteRequest)
            }
        }
    }

    private fun bindObservers() {
        noteViewMode.statusLiveData.observe(viewLifecycleOwner, Observer {
            when(it) {
                is NetworkResult.Error -> {
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {
                    findNavController().popBackStack()
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setInitialData() {
        val jsonNote = arguments?.getString("note")
        if (jsonNote != null) {
            note = Gson().fromJson(jsonNote, NoteResponse::class.java)
            note?.let {
                binding.txtTitle.setText(it.title)
                binding.txtDescription.setText(it.description)
            }
        } else {
            binding.addEditText.text = "Add Notes"
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}