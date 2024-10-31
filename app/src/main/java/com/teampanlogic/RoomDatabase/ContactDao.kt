package com.teampanlogic.RoomDatabase
import androidx.lifecycle.LiveData
import androidx.room.*
import com.teampanlogic.RoomDatabase.ContactEntity

@Dao
interface ContactDao {
     @Insert
     suspend fun insertContact(contact: ContactEntity)

     @Update
     suspend fun updateContact(contact: ContactEntity)

     @Delete
     suspend fun deleteContact(contact: ContactEntity)

     @Query("SELECT * FROM contacts WHERE name LIKE :name LIMIT 1")
     suspend fun findContactByName(name: String): ContactEntity?

     @Query("SELECT * FROM contacts")
     fun getAllContacts(): LiveData<List<ContactEntity>>
}
