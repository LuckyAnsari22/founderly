package com.foundrly.app.data.repository

import com.foundrly.app.data.local.TaskDao
import com.foundrly.app.data.local.TaskEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    fun getTasks(): Flow<List<TaskEntity>> = taskDao.getAllTasks()

    suspend fun addTask(task: TaskEntity) = taskDao.insertTask(task)

    suspend fun updateTask(task: TaskEntity) = taskDao.updateTask(task)

    suspend fun deleteTask(task: TaskEntity) = taskDao.deleteTask(task)

    suspend fun seedInitialTasks() {
        val initialTasks = listOf(
            TaskEntity(title = "Finish Pitch Deck", isCompleted = false, orderIndex = 0),
            TaskEntity(title = "Schedule Mentor Meeting", isCompleted = true, orderIndex = 1),
            TaskEntity(title = "Update Landing Page", isCompleted = false, orderIndex = 2)
        )
        taskDao.insertTasks(initialTasks)
    }
}
