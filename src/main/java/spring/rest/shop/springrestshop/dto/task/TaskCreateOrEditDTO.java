package spring.rest.shop.springrestshop.dto.task;

import lombok.Data;

@Data
public class TaskCreateOrEditDTO {
    private String title;
    public String description;

    public TaskCreateOrEditDTO(){

    }
}
