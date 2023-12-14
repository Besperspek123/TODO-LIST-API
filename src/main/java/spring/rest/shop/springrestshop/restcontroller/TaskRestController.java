package spring.rest.shop.springrestshop.restcontroller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    @Operation(summary = "choose executor")
    public ResponseEntity<String> chooseExecutor(@PathVariable long taskID, @RequestBody UserDTO executor){
        taskService.chooseExecutor(taskID,executor);
        return new ResponseEntity<>("Executor has been set",HttpStatus.OK);
    }

    @PutMapping("/task/{taskID}")
    @Operation(summary = "edit Task")
    public ResponseEntity<String> editTask(@Validated @RequestBody TaskDTO taskDTO, @PathVariable long taskID){
        taskService.editTask(taskID,taskDTO);
        return new ResponseEntity<>("task edited",HttpStatus.OK);
    }



}
