package spring.rest.shop.springrestshop.restcontroller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.comment.CommentCreateDTO;
import spring.rest.shop.springrestshop.dto.comment.CommentDTO;
import spring.rest.shop.springrestshop.dto.Status.TaskStatusDTO;
import spring.rest.shop.springrestshop.dto.task.TaskCreateOrEditDTO;
import spring.rest.shop.springrestshop.dto.task.TaskDTO;
import spring.rest.shop.springrestshop.dto.task.TaskDetailsDTO;
import spring.rest.shop.springrestshop.dto.user.UserDTO;
import spring.rest.shop.springrestshop.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "Task", description = "The Task API")
public class TaskRestController {

    @Autowired
    private final TaskService taskService;

    @PostMapping("/task")
    @Operation(summary = "create Task")
    public ResponseEntity<String> createTask(@Validated @RequestBody TaskCreateOrEditDTO taskDTO){
        taskService.saveTask(taskDTO);
        return new ResponseEntity<>("Task created", HttpStatus.OK);
    }

    @PostMapping("/executor/{taskID}")
    @Operation(summary = "Choose executor",
            description = "Set an executor for a task by providing either the user's ID or email.")
    public ResponseEntity<String> chooseExecutor(
            @PathVariable
            @Parameter(description = "ID of the task", required = true) long taskID,
            @RequestBody
            @Parameter(description = "User object with either an ID or an email", required = true) UserDTO executor) {
        taskService.chooseExecutor(taskID, executor);
        return new ResponseEntity<>("Executor has been set", HttpStatus.OK);
    }

    @DeleteMapping("/executor/{taskID}")
    @Operation(summary = "Remove executor")
    public ResponseEntity<String> removeExecutor(
            @PathVariable
            @Parameter(description = "ID of the task", required = true) long taskID,
            @RequestBody
            @Parameter(description = "User object with either an ID or an email", required = true) UserDTO executor) {
        taskService.removeExecutor(taskID, executor);
        return new ResponseEntity<>("Executor has been removed", HttpStatus.OK);
    }

    @PutMapping("/task/{taskID}")
    @Operation(summary = "edit Task")
    public ResponseEntity<String> editTask(@Validated @RequestBody TaskCreateOrEditDTO taskDTO, @PathVariable long taskID){
        taskService.editTask(taskID,taskDTO);
        return new ResponseEntity<>("task edited",HttpStatus.OK);
    }
    @PutMapping("/task/status/{taskId}")
    @Operation(summary = "swap status")
    public ResponseEntity<String> swapStatus(@Validated @RequestBody TaskStatusDTO taskStatusDTO
    , @PathVariable long taskId){
        taskService.swapTaskStatus(taskId,taskStatusDTO);
        return new ResponseEntity<>("Status has been chanded", HttpStatus.OK);
    }

    @DeleteMapping("/task/{taskID}")
    @Operation(summary = "delete task")
    public ResponseEntity<String> deleteTask(@PathVariable long taskID){
        taskService.deleteTask(taskID);
        return new ResponseEntity<>("Task has been deleted",HttpStatus.OK);
    }

    @PostMapping("/task/comment/{taskID}")
    @Operation(summary = "add comment to task")
    public ResponseEntity<String> addCommentToTask(@PathVariable long taskID,@RequestBody CommentCreateDTO comment){
        taskService.addComment(taskID,comment.getMessage());
        return new ResponseEntity<>("Comment has been added",HttpStatus.OK);

    }

    @GetMapping("/task/{taskID}")
    @Operation(summary = "get info about task")
    public ResponseEntity<TaskDetailsDTO> getInfoAboutTask(@PathVariable long taskID){

        return new ResponseEntity<>(new TaskDetailsDTO(taskService.getTaskInfoByID(taskID)),HttpStatus.OK);
    }

    @GetMapping("/task/executor")
    @Operation(summary = "Get tasks for current user where he is executor with pagination")
    public ResponseEntity<Page<TaskDTO>> getTasksForCurrentUserWhereHeIsExecutor(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<TaskDTO> tasksPage = taskService.getTasksForCurrentUserWhereUserIsExecutor(PageRequest.of(page, size));
        return new ResponseEntity<>(tasksPage, HttpStatus.OK);
    }

    @GetMapping("/task/creator")
    @Operation(summary = "Get tasks for current user where he is creator with pagination")
    public ResponseEntity<Page<TaskDTO>> getTasksForCurrentUserWhereHeIsCreator(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<TaskDTO> tasksPage = taskService.getTasksForCurrentUserWhereUserIsCreator(PageRequest.of(page, size));
        return new ResponseEntity<>(tasksPage, HttpStatus.OK);
    }

    @GetMapping("/task/all")
    @Operation(summary = "Get all tasks for all users with pagination")
    public ResponseEntity<Page<TaskDTO>> getAllTasksForAllUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<TaskDTO> tasksPage = taskService.getAllTasks(PageRequest.of(page, size));
        return new ResponseEntity<>(tasksPage, HttpStatus.OK);
    }





}
