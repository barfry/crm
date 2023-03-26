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

    public Task editTask(Task task){
        Task tempTask = taskRepository.getById(task.getId());

        tempTask.setName(task.getName());
        tempTask.setDescription(task.getDescription());
        tempTask.setPlannedDate(task.getPlannedDate());
        tempTask.setExecutor(task.getExecutor());
        tempTask.setSupervisor(task.getSupervisor());
        tempTask.setMachine(task.getMachine());

        tempTask.setModifier(userService.getCurrentUser());
        tempTask.setUpdateDate(LocalDate.now());

        return taskRepository.save(tempTask);
    }

    public Task completeTask(Task task){
        task.setUpdateDate(LocalDate.now());
        task.setModifier(userService.getCurrentUser());
        task.setActive(false);

        return taskRepository.save(task);
    }

    public void removeTask(Long taskId){
        taskRepository.deleteById(taskId);
    }

    public List<Task> getActiveTasksByUser(User user){
        return taskRepository.getActiveTasksByUser(user);
    }
}
