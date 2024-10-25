package com.teampanlogic

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.teampanlogic.RoomDatabase.ContactDao
import com.teampanlogic.RoomDatabase.ContactDatabase
import com.teampanlogic.RoomDatabase.ContactEntity
import com.teampanlogic.RoomDatabase.ContactRepository
import kotlinx.coroutines.launch

class RoomDatabaseFragment : Fragment(R.layout.fragment_room_database) {
    private lateinit var database: ContactDatabase
    private lateinit var contactDao: ContactDao
    private lateinit var repository: ContactRepository

    private lateinit var roomName: EditText
    private lateinit var roomNumber: EditText
    private lateinit var addDataButton: Button
    private lateinit var saveButton: Button
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button

    private var currentContactId: Long = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = ContactDatabase.getDatabase(requireContext())
        contactDao = database.contactDao()
        repository = ContactRepository(contactDao)

        initUI(view)
        setupListeners()
    }

    private fun initUI(view: View) {
        roomName = view.findViewById(R.id.roomName)
        roomNumber = view.findViewById(R.id.roomNumber)
        addDataButton = view.findViewById(R.id.addDataButton)
        saveButton = view.findViewById(R.id.saveButton)
        updateButton = view.findViewById(R.id.updateButton)
        deleteButton = view.findViewById(R.id.deleteButton)
    }

    private fun setupListeners() {
        // Add Contact
        addDataButton.setOnClickListener {
            val name = roomName.text.toString().trim()
            val number = roomNumber.text.toString().trim()
            if (name.isNotEmpty() && number.isNotEmpty()) {
                lifecycleScope.launch {
                    repository.insert(ContactEntity(name = name, number = number))
                    Toast.makeText(requireContext(), "Contact Added", Toast.LENGTH_SHORT).show()
                    clearInputFields()
                }
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Search for Contact
        saveButton.setOnClickListener {
            val nameToFind = roomName.text.toString().trim()
            lifecycleScope.launch {
                val contact = repository.findByName(nameToFind)
                if (contact != null) {
                    loadContactForEditing(contact)
                } else {
                    Toast.makeText(requireContext(), "Contact not found", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Update Contact
        updateButton.setOnClickListener {
            if (currentContactId != -1L) {
                val name = roomName.text.toString().trim()
                val number = roomNumber.text.toString().trim()
                if (name.isNotEmpty() && number.isNotEmpty()) {
                    lifecycleScope.launch {
                        repository.update(ContactEntity(currentContactId, name, number))
                        Toast.makeText(requireContext(), "Contact Updated", Toast.LENGTH_SHORT).show()
                        clearInputFields()
                        currentContactId = -1 // Reset
                    }
                } else {
                    Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "No contact selected for update", Toast.LENGTH_SHORT).show()
            }
        }

        // Delete Contact
        deleteButton.setOnClickListener {
            if (currentContactId != -1L) {
                lifecycleScope.launch {
                    repository.delete(ContactEntity(currentContactId, "", ""))
                    Toast.makeText(requireContext(), "Contact Deleted", Toast.LENGTH_SHORT).show()
                    clearInputFields()
                    currentContactId = -1 // Reset
                }
            } else {
                Toast.makeText(requireContext(), "No contact selected for deletion", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearInputFields() {
        roomName.text.clear()
        roomNumber.text.clear()
    }

    private fun loadContactForEditing(contact: ContactEntity) {
        roomName.setText(contact.name)
        roomNumber.setText(contact.number)
        currentContactId = contact.id
    }
}
