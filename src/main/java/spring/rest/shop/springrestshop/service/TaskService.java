package spring.rest.shop.springrestshop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.aspect.SecurityContext;
import spring.rest.shop.springrestshop.dto.Status.TaskStatusDTO;
import spring.rest.shop.springrestshop.dto.task.TaskCreateOrEditDTO;
import spring.rest.shop.springrestshop.dto.task.TaskDTO;
import spring.rest.shop.springrestshop.dto.user.UserDTO;
import spring.rest.shop.springrestshop.entity.Comment;
import spring.rest.shop.springrestshop.entity.Task;
import spring.rest.shop.springrestshop.entity.TaskState;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.exception.*;
import spring.rest.shop.springrestshop.repository.TaskRepository;

import java.time.LocalDateTime;

@Service
@Slf4j
public class TaskService {


    private final TaskRepository taskRepository;
    private final UserService userService;
    private final CommentService commentService;
    @Autowired
    public TaskService(TaskRepository taskRepository,UserService userService, CommentService commentService){
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.commentService = commentService;
    }
    public void saveTask(TaskCreateOrEditDTO taskDTO){
        if(taskDTO.getTitle() == null){
            throw new NullPointerException("title cant be null");
        }
        if (taskDTO.getTitle().isEmpty()){
            throw new EmptyFieldException("Title cant be empty");
        }

        User currentUser = SecurityContext.getCurrentUser();
        Task taskForSave = new Task(currentUser,taskDTO);
        taskForSave.setStatus(TaskState.WAITING);
        taskRepository.save(taskForSave);
    }

    public void chooseExecutor(long taskId, UserDTO executorDTO){
        User currentUser = SecurityContext.getCurrentUser();
        Task task = taskRepository.findById(taskId);

        if(task == null){
            throw new EntityNotFoundException("Task with this id not found");
        }
        if(task.getCreator() != currentUser){
            throw new AccessDeniedException("You cant choose executor into not your task");
        }
        User executor = new User();
        if(executorDTO == null){
            throw new EmptyFieldException("Executor cant be empty");
        }
        if(executorDTO.getEmail() == null ) {
                executor = userService.getUserById(executorDTO.getId());
            }
        else executor = userService.getUserByEmail(executorDTO.getEmail());
        if(task.getExecutors().contains(executor)){
            throw new UserAlreadyHasThisTaskInHisTaskListException("This executor already exist in this task");
        }
        if(executor == null){
            throw new UserNotFoundException("User with these email or id not found");
        }
        task.getExecutors().add(executor);
        taskRepository.save(task);
    }

    public void removeExecutor(long taskId, UserDTO executorDTO){
        User currentUser = SecurityContext.getCurrentUser();
        Task task = taskRepository.findById(taskId);

        if(task == null){
            throw new EntityNotFoundException("Task with this id not found");
        }
        if(task.getCreator() != currentUser){
            throw new AccessDeniedException("You can choose executor into not your task");
        }
        User executor = new User();
        if(executorDTO == null){
            throw new EmptyFieldException("Executor cant be empty");
        }
        if(executorDTO.getEmail() == null){
            executor = userService.getUserById(executorDTO.getId());
        }
        else executor = userService.getUserByEmail(executorDTO.getEmail());
        if(executor == null){
            throw new UserNotFoundException("User with these email or id not found");
        }
        if(!task.getExecutors().contains(executor)){
            throw new UserAlreadyHasThisTaskInHisTaskListException("This user is not executor of this task");
        }

        task.getExecutors().remove(executor);
        taskRepository.save(task);
    }

    public void editTask(long taskId,TaskCreateOrEditDTO taskDTO){
        Task task = taskRepository.findById(taskId);
        User currentUser = SecurityContext.getCurrentUser();
        if (task == null){
            throw new EntityNotFoundException("task not found");
        }
        if(task.getCreator() !=currentUser){
            throw new AccessDeniedException("You cant edit not your task");
        }
        if(taskDTO == null){
            throw new EmptyFieldException("task cant be empty");

        }

        if(taskDTO.getTitle() != null && !taskDTO.getTitle().isEmpty()){
            task.setTitle(taskDTO.getTitle());
        }
        if(taskDTO.getDescription() != null && !taskDTO.getDescription().isEmpty()){
            task.setDescription(taskDTO.getDescription());
        }
        taskRepository.save(task);

    }

    public void swapTaskStatus(long taskId, TaskStatusDTO taskStatusDTO){
        User currentUser = SecurityContext.getCurrentUser();
        Task task = taskRepository.findById(taskId);
        if (task == null){
            throw new EntityNotFoundException("task not found");
        }
        if(taskStatusDTO == null || taskStatusDTO.getStatus() == null || taskStatusDTO.getStatus().isEmpty()){
            throw new EmptyFieldException("Status cant be empty or null");
        }
        if(!currentUser.equals(task.getCreator()) && !task.getExecutors().contains(currentUser)){
            System.out.println(task.getExecutors());
            throw new AccessDeniedException("You cant swap status into task where you are not a creator or an executor");
        }
        if(taskStatusDTO.getStatus().equalsIgnoreCase("WAITING")){
            task.setStatus(TaskState.WAITING);
        }
        else if(taskStatusDTO.getStatus().equalsIgnoreCase("IN PROCESS")){
            task.setStatus(TaskState.IN_PROGRESS);
        }
        else if(taskStatusDTO.getStatus().equalsIgnoreCase("FINISHED")){
            task.setStatus(TaskState.COMPLETED);
        }
        else throw new InvalidFieldDataException("You write invalid status. Status can be only WAITING, IN PROCESS or FINISHED");
        taskRepository.save(task);
    }
    public Page<TaskDTO> getTasksForCurrentUserWhereUserIsExecutor(Pageable pageable) {
        User currentUser = SecurityContext.getCurrentUser();
        return taskRepository.findByExecutors(currentUser, pageable).map(TaskDTO::new);
    }

    public Page<TaskDTO> getTasksForCurrentUserWhereUserIsCreator(Pageable pageable) {
        User currentUser = SecurityContext.getCurrentUser();
        return taskRepository.findByCreator(currentUser, pageable).map(TaskDTO::new);
    }


    public void deleteTask(long taskId){
        Task task = taskRepository.findById(taskId);
        User currentUser= SecurityContext.getCurrentUser();

        if(task == null){
            throw new EntityNotFoundException("Task with this id not found");
        }
        if(!task.getCreator().equals(currentUser)){
            throw new AccessDeniedException("You cant delete not your task");
        }
        taskRepository.delete(task);
    }

    public void addComment(long taskId , String message){
        User currentUser = SecurityContext.getCurrentUser();
        Task task = taskRepository.findById(taskId);
        if(task == null){
            throw new EntityNotFoundException("Task with this id not found");
        }
        if(message == null || message.isEmpty() ){
            throw new EmptyFieldException("Message cant be empty or null");
        }
        if(!currentUser.equals(task.getCreator()) &&     !task.getExecutors().contains(currentUser)){
            System.out.println(task.getExecutors());
            throw new AccessDeniedException("You cant write comment on task where you are not a creator or an executor");
        }

        Comment comment = new Comment();
        comment.setMessage(message);
        comment.setCreator(currentUser);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setTask(task);
        commentService.saveComment(comment);
    }

    public Task getTaskInfoByID(long taskId){
        Task task = taskRepository.findById(taskId);
        if(task == null){
            throw new EntityNotFoundException("Task with this ID is not found");
        }
        return task;
    }

    public Page<TaskDTO> getAllTasks(PageRequest pageRequest) {
        return taskRepository.findAll(pageRequest).map(TaskDTO::new);
    }
}
