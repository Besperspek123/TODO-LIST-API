package spring.rest.shop.springrestshop.dto.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import spring.rest.shop.springrestshop.entity.TaskState;

@Data
public class TaskStatusDTO {

    @Schema(description = "Status of the task",
            example = "Waiting",
            allowableValues = {"Waiting", "In process", "Finished"})
    private String status;
}

