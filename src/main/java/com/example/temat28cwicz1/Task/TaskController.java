package com.example.temat28cwicz1.Task;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    ResponseEntity<TaskDto> getTasksDto(@PathVariable Long id) {
        return taskService.getTaskDtoById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping()
    ResponseEntity<List<TaskDto>> getAllDtoTasks() {
        if (taskService.getListOfTaskDyo().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taskService.getListOfTaskDyo());
    }

    @PostMapping
    ResponseEntity<TaskDto> saveTask(@RequestBody TaskDto taskDto) {
        TaskDto responseDto = taskService.save(taskDto);
        URI savedEntityLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.getId())
                .toUri();
        return ResponseEntity.created(savedEntityLocation).body(responseDto);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> replaceTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        return taskService.replaceTask(id, taskDto).map(c -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

}
