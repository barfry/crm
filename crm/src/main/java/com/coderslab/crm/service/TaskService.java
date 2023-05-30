package com.coderslab.crm.service;

import com.coderslab.crm.model.Task;
import com.coderslab.crm.model.User;
import com.coderslab.crm.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
    UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public Task addNewTask(Task task){
        return taskRepository.save(task);
    }

    public Task getTaskById(Long taskId){
        return taskRepository.getById(taskId);
    }

    public void editTask(Task task){

        taskRepository.updateTask(task.getName(),task.getDescription(),task.getPlannedDate(),task.getExecutor(),task.getSupervisor(), userService.getCurrentUser(), LocalDate.now(), task.getId());
    }

    public void completeTask(Task task){

        taskRepository.completeTask(LocalDate.now(), userService.getCurrentUser(), false, task.getId());
    }

    public void removeTask(Long taskId){
        taskRepository.deleteById(taskId);
    }

    public List<Task> getActiveTasksByUser(User user){
        return taskRepository.getActiveTasksByUser(user);
    }
}
