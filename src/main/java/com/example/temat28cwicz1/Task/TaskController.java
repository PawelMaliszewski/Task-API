package com.example.temat28cwicz1.Task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final ObjectMapper objectMapper;

    public TaskController(TaskService taskService, ObjectMapper objectMapper) {
        this.taskService = taskService;
        this.objectMapper = objectMapper;
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

    @PatchMapping("/{id}")
    ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody JsonMergePatch jsonMergePatch) {
        try {
            TaskDto taskDto = taskService.getTaskDtoById(id).orElseThrow();
            TaskDto taskDtoPatcher = applyPatch(taskDto, jsonMergePatch);
            taskService.updateTask(taskDtoPatcher);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@PathVariable Long id) {
        return (taskService.deleteTask(id)) ? ResponseEntity.accepted().build() : ResponseEntity.notFound().build();
    }

    private TaskDto applyPatch(TaskDto taskDto, JsonMergePatch jsonMergePatch) throws JsonPatchException, JsonProcessingException {
        JsonNode taskDtoNode = objectMapper.valueToTree(taskDto);
        JsonNode taskDtoPatchedNode = jsonMergePatch.apply(taskDtoNode);
        return objectMapper.treeToValue(taskDtoPatchedNode, TaskDto.class);
    }
}
