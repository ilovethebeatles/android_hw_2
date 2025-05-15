package com.example.todoapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.TaskDatabase
import com.example.todoapp.model.Task
import com.example.todoapp.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
//    val allTasks: LiveData<List<Task>>


    init {
        val taskDao = TaskDatabase.getInstance(application).getTaskDao()
        repository = TaskRepository(taskDao)

    }

    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

    fun getAllTasks(onResult: (List<Task>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val tasks = repository.getAllTasks()
            withContext(Dispatchers.Main) {
                onResult(tasks)
            }
        }
    }

    fun getTaskById(taskId: Int, onResult: (Task?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val task = repository.getTaskById(taskId)
            withContext(Dispatchers.Main) {
                onResult(task)
            }
        }
    }
}
