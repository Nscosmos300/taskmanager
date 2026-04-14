package com.example.taskmanager;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "ALL") String filter,
                        @AuthenticationPrincipal UserDetails userDetails,
                        Model model) {

        String username = userDetails.getUsername();
        List<Task> tasks;

        if (filter.equals("ACTIVE")) {
            tasks = taskRepository.findByUsername(username).stream()
                    .filter(t -> !t.isCompleted()).toList();
        } else if (filter.equals("COMPLETED")) {
            tasks = taskRepository.findByUsername(username).stream()
                    .filter(Task::isCompleted).toList();
        } else {
            tasks = taskRepository.findByUsername(username);
        }

        long totalAll = taskRepository.countByUsername(username);
        long completedCount = taskRepository.countByUsernameAndCompleted(username, true);

        model.addAttribute("tasks", tasks);
        model.addAttribute("newTask", new Task());
        model.addAttribute("filter", filter);
        model.addAttribute("totalAll", totalAll);
        model.addAttribute("completedCount", completedCount);
        model.addAttribute("username", username);
        return "index";
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute Task newTask,
                          @AuthenticationPrincipal UserDetails userDetails) {
        newTask.setUsername(userDetails.getUsername());
        taskRepository.save(newTask);
        return "redirect:/";
    }

    @GetMapping("/toggle/{id}")
    public String toggleTask(@PathVariable String id,
                             @AuthenticationPrincipal UserDetails userDetails) {
        Task task = taskRepository.findById(id).orElseThrow();
        if (task.getUsername().equals(userDetails.getUsername())) {
            task.setCompleted(!task.isCompleted());
            taskRepository.save(task);
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable String id,
                             @AuthenticationPrincipal UserDetails userDetails) {
        Task task = taskRepository.findById(id).orElseThrow();
        if (task.getUsername().equals(userDetails.getUsername())) {
            taskRepository.deleteById(id);
        }
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {
        Task task = taskRepository.findById(id).orElseThrow();
        String username = userDetails.getUsername();

        if (!task.getUsername().equals(username))
            return "redirect:/";

        model.addAttribute("editTask", task);
        model.addAttribute("tasks", taskRepository.findByUsername(username));
        model.addAttribute("newTask", new Task());
        model.addAttribute("filter", "ALL");
        model.addAttribute("username", username);
        model.addAttribute("totalAll", taskRepository.countByUsername(username));
        model.addAttribute("completedCount",
                taskRepository.countByUsernameAndCompleted(username, true));
        return "index";
    }

    @PostMapping("/edit/{id}")
    public String saveEdit(@PathVariable String id,
                           @ModelAttribute Task updated,
                           @AuthenticationPrincipal UserDetails userDetails) {
        Task task = taskRepository.findById(id).orElseThrow();
        if (task.getUsername().equals(userDetails.getUsername())) {
            task.setTitle(updated.getTitle());
            task.setPriority(updated.getPriority());
            task.setDueDate(updated.getDueDate());
            taskRepository.save(task);
        }
        return "redirect:/";
    }
}