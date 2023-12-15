package spring.rest.shop.springrestshop.restcontroller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.CommentDTO;
import spring.rest.shop.springrestshop.dto.Status.TaskStatusDTO;
import spring.rest.shop.springrestshop.dto.task.TaskDTO;
import spring.rest.shop.springrestshop.dto.user.UserDTO;
import spring.rest.shop.springrestshop.service.TaskService;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "Task", description = "The Task API")
public class TaskRestController {

    @Autowired
    private final TaskService taskService;

    @PostMapping("/task")
    @Operation(summary = "create Task")
    public ResponseEntity<String> createTask(@Validated @RequestBody TaskDTO taskDTO){
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

    @PutMapping("/task/{taskID}")
    @Operation(summary = "edit Task")
    public ResponseEntity<String> editTask(@Validated @RequestBody TaskDTO taskDTO, @PathVariable long taskID){
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

    @DeleteMapping("task/{taskID}")
    @Operation(summary = "delete task")
    public ResponseEntity<String> deleteTask(@PathVariable long taskID){
        taskService.deleteTask(taskID);
        return new ResponseEntity<>("Task has been deleted",HttpStatus.OK);
    }

    @PostMapping("task/comment/{taskID}")
    @Operation(summary = "add comment to task")
    public ResponseEntity<String> addCommentToTask(@PathVariable long taskID,@RequestBody CommentDTO comment){
        taskService.addComment(taskID,comment.getMessage());
        return new ResponseEntity<>("Comment has been added",HttpStatus.OK);

    }


}
