package com.teampanlogic.RoomDatabase

import androidx.lifecycle.LiveData

class ContactRepository(private val contactDao: ContactDao) {
    val allContacts: LiveData<List<ContactEntity>> = contactDao.getAllContacts()

    suspend fun insert(contact: ContactEntity) {
        contactDao.insertContact(contact)
    }

    suspend fun update(contact: ContactEntity) {
        contactDao.updateContact(contact)
    }

    suspend fun delete(contact: ContactEntity) {
        contactDao.deleteContact(contact)
    }

    suspend fun findByName(name: String): ContactEntity? {
        return contactDao.findContactByName(name)
    }
}
