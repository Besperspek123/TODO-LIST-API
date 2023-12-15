package spring.rest.shop.springrestshop.restcontroller;


import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.Status.TaskStatusDTO;
import spring.rest.shop.springrestshop.dto.comment.CommentCreateDTO;
import spring.rest.shop.springrestshop.dto.task.TaskCreateOrEditDTO;
import spring.rest.shop.springrestshop.dto.task.TaskDTO;
import spring.rest.shop.springrestshop.dto.task.TaskDetailsDTO;
import spring.rest.shop.springrestshop.dto.user.UserDTO;
import spring.rest.shop.springrestshop.service.TaskService;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Hidden
public class TaskRestController {

    @Autowired
    private final TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity<String> createTask(@Validated @RequestBody TaskCreateOrEditDTO taskDTO){
        taskService.saveTask(taskDTO);
        return new ResponseEntity<>("Task created", HttpStatus.OK);
    }

    @PostMapping("/tasks/executors/{taskID}")
    public ResponseEntity<String> chooseExecutor(
            @PathVariable long taskID,
            @RequestBody UserDTO executor) {
        taskService.chooseExecutor(taskID, executor);
        return new ResponseEntity<>("Executor has been set", HttpStatus.OK);
    }

    @DeleteMapping("/tasks/executors/{taskID}")
    public ResponseEntity<String> removeExecutor(
            @PathVariable long taskID,
            @RequestBody UserDTO executor) {
        taskService.removeExecutor(taskID, executor);
        return new ResponseEntity<>("Executor has been removed", HttpStatus.OK);
    }

    @PutMapping("/tasks/{taskID}")
    public ResponseEntity<String> editTask(@Validated @RequestBody TaskCreateOrEditDTO taskDTO, @PathVariable long taskID){
        taskService.editTask(taskID,taskDTO);
        return new ResponseEntity<>("task edited",HttpStatus.OK);
    }
    @PutMapping("/tasks/status/{taskId}")
    public ResponseEntity<String> swapStatus(@Validated @RequestBody TaskStatusDTO taskStatusDTO
    , @PathVariable long taskId){
        taskService.swapTaskStatus(taskId,taskStatusDTO);
        return new ResponseEntity<>("Status has been chanded", HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{taskID}")
    public ResponseEntity<String> deleteTask(@PathVariable long taskID){
        taskService.deleteTask(taskID);
        return new ResponseEntity<>("Task has been deleted",HttpStatus.OK);
    }

    @PostMapping("/tasks/comment/{taskID}")
    public ResponseEntity<String> addCommentToTask(@PathVariable long taskID,@RequestBody CommentCreateDTO comment){
        taskService.addComment(taskID,comment.getMessage());
        return new ResponseEntity<>("Comment has been added",HttpStatus.OK);

    }

    @GetMapping("/tasks/{taskID}")
    public ResponseEntity<TaskDetailsDTO> getInfoAboutTask(@PathVariable long taskID){

        return new ResponseEntity<>(new TaskDetailsDTO(taskService.getTaskInfoByID(taskID)),HttpStatus.OK);
    }

    @GetMapping("/tasks/executor")
    public ResponseEntity<Page<TaskDTO>> getTasksForCurrentUserWhereHeIsExecutor(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<TaskDTO> tasksPage = taskService.getTasksForCurrentUserWhereUserIsExecutor(PageRequest.of(page, size));
        return new ResponseEntity<>(tasksPage, HttpStatus.OK);
    }

    @GetMapping("/tasks/creator")
    public ResponseEntity<Page<TaskDTO>> getTasksForCurrentUserWhereHeIsCreator(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<TaskDTO> tasksPage = taskService.getTasksForCurrentUserWhereUserIsCreator(PageRequest.of(page, size));
        return new ResponseEntity<>(tasksPage, HttpStatus.OK);
    }

    @GetMapping("/tasks/all")
    public ResponseEntity<Page<TaskDTO>> getAllTasksForAllUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<TaskDTO> tasksPage = taskService.getAllTasks(PageRequest.of(page, size));
        return new ResponseEntity<>(tasksPage, HttpStatus.OK);
    }





}
