package spring.rest.shop.springrestshop.restcontroller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.rest.shop.springrestshop.dto.task.TaskDTO;
import spring.rest.shop.springrestshop.service.TaskService;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "Task", description = "The Task API")
public class TaskRestController {

    @Autowired
    private final TaskService taskService;

    @PostMapping("/task")
    public ResponseEntity<String> createTask(@Validated @RequestBody TaskDTO taskDTO){
        taskService.saveTask(taskDTO);
        return new ResponseEntity<>("Task created", HttpStatus.OK);
    }

}
