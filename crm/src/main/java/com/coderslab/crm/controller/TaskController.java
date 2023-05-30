package com.coderslab.crm.controller;

import com.coderslab.crm.model.Task;
import com.coderslab.crm.service.TaskService;
import com.coderslab.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;
    UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/task-details")
    public String showTaskDetailsPage(@RequestParam(name = "taskId") Long taskId, Model model){
        model.addAttribute("task", taskService.getTaskById(taskId));

        return "user-zone/task-details";
    }

    @GetMapping("/edit-task")
    public String initEditTaskForm(@RequestParam(name = "taskId") Long taskId, Model model){
        model.addAttribute("task", taskService.getTaskById(taskId));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("url", "/tasks/edit-task");

        return "user-zone/edit-task";
    }

    @PostMapping("/edit-task")
    public String editTask(@Valid Task task, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("task", task);
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("url", "/tasks/edit-task");
        }

        taskService.editTask(task);

        return "redirect:/tasks/task-details?taskId=" + task.getId();
    }

    @GetMapping("/complete-task")
    public String initCompleteTaskPage(@RequestParam(name = "taskId") Long taskId, Model model){
        model.addAttribute("task", taskService.getTaskById(taskId));
        model.addAttribute("actionUrl", "/tasks/complete-task");

        return "user-zone/complete-task";
    }
    @PostMapping("/complete-task")
    public String completeTask(@Valid Task task, BindingResult result, Model model){
        if(result.hasErrors() || task.getExecutionDate() == null){
            model.addAttribute("task", task);
            model.addAttribute("executionDateError", "Please select execution date");
            model.addAttribute("actionUrl", "/tasks/complete-task");
            return "user-zone/complete-task";
        }

        taskService.completeTask(task);
        return "redirect:/tasks/task-details?taskId=" + task.getId();
    }
}
