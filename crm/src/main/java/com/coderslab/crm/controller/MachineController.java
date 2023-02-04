package com.coderslab.crm.controller;

import com.coderslab.crm.filter.EquipmentFilter;
import com.coderslab.crm.filter.MachineFilter;
import com.coderslab.crm.filter.ManufacturerFilter;
import com.coderslab.crm.model.*;
import com.coderslab.crm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/machines")
@SessionAttributes({"machineFilter", "equipmentFilter"})
public class MachineController {

    @Autowired
    MachineService machineService;
    UserService userService;
    TaskService taskService;
    EquipmentService equipmentService;
    TypeService typeService;
    ManufacturerService manufacturerService;

    public MachineController(MachineService machineService, UserService userService, TaskService taskService, EquipmentService equipmentService, TypeService typeService, ManufacturerService manufacturerService) {
        this.machineService = machineService;
        this.userService = userService;
        this.taskService = taskService;
        this.equipmentService = equipmentService;
        this.typeService = typeService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("")
    public String showAllMachines(Model model){
        MachineFilter machineFilter = new MachineFilter();
        model.addAttribute("machines", machineService.getAllMachines());

        return  showAllMachinesPage(machineFilter,1,"id","asc", model);
    }

    @GetMapping("/page/{pageNo}")
    public String showAllMachinesPage(@ModelAttribute MachineFilter machineFilter, @PathVariable(value = "pageNo") int pageNo,
                                           @RequestParam(value = "sortField", defaultValue = "id") String sortField, @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                                           Model model){
        int pageSize = 5;

        Page<Machine> page = machineService.findMachinesBySearchWithPaginationAndSorting(pageNo, pageSize, sortField, sortDir, machineFilter);
        List<Machine> listMachines = page.getContent();

        model.addAttribute("machineFilter", machineFilter);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("machines", listMachines);

        return "user-zone/all-machines";
    }

    @GetMapping("/machine-details")
    public String showMachineDetailsPage(@RequestParam(value = "machineId") Long machineId, Model model){
        model.addAttribute("machine", machineService.getMachineById(machineId));
        model.addAttribute("currentUserId", userService.getCurrentUser().getId());

        return "user-zone/machine-details";
    }

    @GetMapping("/machine-details/add-new-task")
    public String initAddNewTaskPage(@RequestParam(value = "machineId") Long machineId, Model model){
        model.addAttribute("task", new Task());
        model.addAttribute("machineId", machineId);
        model.addAttribute("users", userService.getAllUsers());

        return "user-zone/add-new-task";
    }

    @PostMapping("/machine-details/add-new-task")
    public String addNewTask(@RequestParam(value = "machineId") Long machineId, @Valid Task task, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("task", task);
            model.addAttribute("machineId", machineId);
            model.addAttribute("users", userService.getAllUsers());

            return "user-zone/add-new-task";
        }

        machineService.addNewTaskToMachine(machineId, task);

        return "redirect:/machines/machine-details?machineId=" + machineId;
    }

    @GetMapping("/machine-details/edit-task")
    public String initEditTaskPage(@RequestParam(name = "taskId") Long taskId, @RequestParam(name = "machineId") Long machineId, Model model){
        model.addAttribute("task", taskService.getTaskById(taskId));
        model.addAttribute("machineId", machineId);
        model.addAttribute("users", userService.getAllUsers());

        return "user-zone/edit-task";
    }

    @PostMapping("/machine-details/edit-task")
    public String editTask(@RequestParam(name = "machineId") Long machineId, @Valid Task task, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("task", task);
            model.addAttribute("machineId", machineId);
            model.addAttribute("users", userService.getAllUsers());

            return "user-zone/edit-task";
        }

        taskService.editTask(task);

        return "redirect:/machines/machine-details?machineId=" + machineId;
    }

    @GetMapping("/machine-details/complete-task")
    public String initCompleteTaskPage(@RequestParam(name = "taskId") Long taskId, @RequestParam(name = "machineId") Long machineId, Model model){
        model.addAttribute("task", taskService.getTaskById(taskId));
        model.addAttribute("machineId", machineId);

        return "user-zone/complete-task";
    }

    @PostMapping("/machine-details/complete-task")
    public String completeTask(@RequestParam(name = "machineId") Long machineId, @Valid Task task, BindingResult result, Model model){
        if(result.hasErrors() || task.getExecutionDate() == null){
            model.addAttribute("task", task);
            model.addAttribute("machineId", machineId);
            model.addAttribute("executionDateError", "Please select execution date");

            return "user-zone/complete-task";
        }

        taskService.completeTask(task);

        return "redirect:/machines/machine-details?machineId=" + machineId;
    }

    @PostMapping("/machine-details/remove-task")
    public String removeTaskFromMachine(@RequestParam(name = "machineId") Long machineId, @RequestParam(name = "taskId") Long taskId, Model model){
        machineService.removeTaskFromMachine(taskId, machineId);

        return "redirect:/machines/machine-details?machineId=" + machineId;
    }

    @GetMapping("/machine-details/add-equipment")
    public String showAllEquipment(@RequestParam(name = "machineId") Long machineId, Model model){
        EquipmentFilter equipmentFilter = new EquipmentFilter();
        model.addAttribute("equipmentList", equipmentService.getAllEquipment());

        return  initAddEquipmentPage(machineId, equipmentFilter,1,"id","asc", model);
    }

    @GetMapping("/machine-details/add-equipment/page/{pageNo}")
    public String initAddEquipmentPage(@RequestParam(name = "machineId") Long machineId, @ModelAttribute EquipmentFilter equipmentFilter, @PathVariable(value = "pageNo") int pageNo,
                                       @RequestParam(value = "sortField", defaultValue = "id") String sortField, @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir, Model model){

        int pageSize = 5;

        Page<Equipment> page = equipmentService.findEquipmentBySearchWithPaginationAndSorting(pageNo, pageSize, sortField, sortDir, equipmentFilter);
        List<Equipment> listEquipment = page.getContent();

        model.addAttribute("equipmentFilter", equipmentFilter);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("equipmentList", listEquipment);

        model.addAttribute("machine", machineService.getMachineById(machineId));

        return "user-zone/add-equipment";
    }

    @PostMapping("/machine-details/add-equipment")
    public String addEquipment(@RequestParam(name = "machineId") Long machineId, @RequestParam(name = "equipmentId") Long equipmentId, Model model){
        machineService.addEquipmentToMachine(equipmentId, machineId);

        return "redirect:/machines/machine-details/add-equipment?machineId=" + machineId;
    }

    @PostMapping("/machine-details/remove-equipment")
    public String removeEquipmentFromMachine(@RequestParam(name = "machineId") Long machineId, @RequestParam(name = "equipmentId") Long equipmentId, Model model){
        machineService.removeEquipmentFromMachine(equipmentId, machineId);

        return "redirect:/machines/machine-details?machineId=" + machineId;
    }

    @GetMapping("/machine-details/edit-machine")
    public String initEditMachine(@RequestParam(value = "machineId") Long machineId, Model model){
        model.addAttribute("machine", machineService.getMachineById(machineId));
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());

        return "/user-zone/edit-machine";
    }

    @PostMapping("/machine-details/edit-machine")
    public String editMachine(@Valid Machine machine, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("machine", machine);
            model.addAttribute("types", typeService.getAllTypes());
            model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());

            return "/user-zone/edit-machine";
        }
        machineService.editMachine(machine);

        return "redirect:/machines/machine-details?machineId=" + machine.getId();
    }

    @PostMapping("/machine-details/disable-machine")
    public String disableMachine(@RequestParam(value = "machineId") Long machineId, Model model){

        machineService.disableMachine(machineId);

        return "redirect:/machines/machine-details?machineId=" + machineId;
    }

    @PostMapping("/machine-details/activate-machine")
    public String activateMachine(@RequestParam(value = "machineId") Long machineId, Model model){

        machineService.activateMachine(machineId);

        return "redirect:/machines/machine-details?machineId=" + machineId;
    }
    

}
