package ca.josue.roomdemo

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.josue.roomdemo.db.Subscriber
import ca.josue.roomdemo.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {
    val subscribers = repository.subscribers

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    // Event
    private val statusMessage = MutableLiveData<Event<String>>()

    val messages : LiveData<Event<String>> get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        val name = inputName.value!!
        val email = inputEmail.value!!

        if (name.isEmpty()){
            statusMessage.value = Event("Please enter subscriber's name")
            return
        } else if (email.isEmpty()){
            statusMessage.value = Event("Please enter subscriber's email")
            return
        } else if(Patterns.EMAIL_ADDRESS.matcher(email).matches().not()){
            statusMessage.value = Event("Please enter a valid email")
            return
        }

        if (isUpdateOrDelete){
            subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!
            update(subscriberToUpdateOrDelete)
            return
        }

        insert(Subscriber(0, name, email))
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete)
            delete(subscriberToUpdateOrDelete)
        else
            clearAll()
    }

    private fun insert(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        val newRowId = repository.insert(subscriber)
        withContext(Dispatchers.Main) {
            if(newRowId <= -1){
                statusMessage.value = Event("Error Occurred while inserting data")
                return@withContext
            }

            statusMessage.value = Event("Subscriber with ID($newRowId) Inserted Successfully")
            inputName.value = ""
            inputEmail.value = ""
        }
    }

    private fun update(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        val numberOfRows = repository.update(subscriber)
        withContext(Dispatchers.Main) {
            if(numberOfRows <= 0){
                statusMessage.value = Event("Error Occurred while updating data")
                return@withContext
            }

            statusMessage.value = Event("$numberOfRows Row Updated Successfully")
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
        }
    }

    private fun delete(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        val numberOfRows = repository.delete(subscriber)
        withContext(Dispatchers.Main) {
            if(numberOfRows <= 0){
                statusMessage.value = Event("Error Occurred while deleting data")
                return@withContext
            }

            statusMessage.value = Event("$numberOfRows Row Deleted Successfully")
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
        }
    }

    private fun clearAll() = viewModelScope.launch(Dispatchers.IO) {
        val numberOfRows = repository.deleteAll()
        withContext(Dispatchers.Main) {
            if(numberOfRows <= 0){
                statusMessage.value = Event("Error Occurred while deleting all data")
                return@withContext
            }

            statusMessage.value = Event("$numberOfRows Rows Deleted Successfully")
        }
    }

    fun initUpdateAndDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

}