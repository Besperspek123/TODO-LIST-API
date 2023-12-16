package spring.rest.shop.springrestshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import spring.rest.shop.springrestshop.aspect.SecurityContext;
import spring.rest.shop.springrestshop.dto.task.TaskCreateOrEditDTO;
import spring.rest.shop.springrestshop.dto.user.UserDTO;
import spring.rest.shop.springrestshop.entity.Task;
import spring.rest.shop.springrestshop.entity.TaskState;
import spring.rest.shop.springrestshop.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import spring.rest.shop.springrestshop.aspect.SecurityContext;
import spring.rest.shop.springrestshop.entity.Role;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.exception.*;
import spring.rest.shop.springrestshop.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @Test
    void userTryToSaveTask_ShouldThrowNullPointerException(){
        User currentUser = new User(1L,"password","email");
        TaskCreateOrEditDTO taskForSave = new TaskCreateOrEditDTO();
        taskForSave.setTitle(null);
        try (MockedStatic<SecurityContext> mocked = mockStatic(SecurityContext.class)) {
            mocked.when(SecurityContext::getCurrentUser).thenReturn(currentUser);
            assertThrows(NullPointerException.class, () -> taskService.saveTask(taskForSave));

        }
        }

    @Test
    void userTryToSaveTask_EmptyFieldException(){
        User currentUser = new User(1L,"password","email");
        TaskCreateOrEditDTO taskForSave = new TaskCreateOrEditDTO();
        taskForSave.setTitle("");
        try (MockedStatic<SecurityContext> mocked = mockStatic(SecurityContext.class)) {
            mocked.when(SecurityContext::getCurrentUser).thenReturn(currentUser);
            assertThrows(EmptyFieldException.class, () -> taskService.saveTask(taskForSave));

        }
    }

    @Test
    void userTryToSaveTask_ShouldSaveTask(){
        User currentUser = new User(1L,"password","email");
        TaskCreateOrEditDTO taskDtoForSave = new TaskCreateOrEditDTO();
        taskDtoForSave.setTitle("title");
        Task taskForSave = new Task(currentUser,taskDtoForSave);
        taskForSave.setStatus(TaskState.WAITING);
        try (MockedStatic<SecurityContext> mocked = mockStatic(SecurityContext.class)) {
            mocked.when(SecurityContext::getCurrentUser).thenReturn(currentUser);
            taskService.saveTask(taskDtoForSave);
            verify(taskRepository).save(taskForSave);
        }
    }

    @Test
    void userTryToChooseExecutor_ShouldThrowEntityNotFoundException(){
       UserDTO userDTO = new UserDTO();
        when(taskRepository.findById(1L)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> taskService.chooseExecutor(1L,userDTO));
    }

    @Test
    void userTryToChooseExecutor_AccessDeniedException(){
        User currentUser = new User(1L,"password","email");
        UserDTO userDTO = new UserDTO();
        try (MockedStatic<SecurityContext> mocked = mockStatic(SecurityContext.class)) {
            mocked.when(SecurityContext::getCurrentUser).thenReturn(currentUser);
            when(taskRepository.findById(1L)).thenReturn(new Task());
            assertThrows(AccessDeniedException.class, () -> taskService.chooseExecutor(1L, userDTO));
        }


    }

    @Test
    void userTryToChooseExecutor_EmptyFieldException(){
        User currentUser = new User(1L,"password","email");
        Task task = new Task();
        task.setCreator(currentUser);
        UserDTO userDTO = null;
        try (MockedStatic<SecurityContext> mocked = mockStatic(SecurityContext.class)) {
            mocked.when(SecurityContext::getCurrentUser).thenReturn(currentUser);
            when(taskRepository.findById(1L)).thenReturn(task);
            assertThrows(EmptyFieldException.class, () -> taskService.chooseExecutor(1L, userDTO));
        }


    }

    @Test
    void userTryToDeleteTask_ShouldDeleteTask(){
        User currentUser = new User(1L,"password","email");
        Task task = new Task();
        task.setTitle("title");
        task.setId(1L);
        task.setStatus(TaskState.WAITING);
        task.setCreator(currentUser);
        try (MockedStatic<SecurityContext> mocked = mockStatic(SecurityContext.class)) {
            mocked.when(SecurityContext::getCurrentUser).thenReturn(currentUser);
            when(taskRepository.findById(1L)).thenReturn(task);
            taskService.deleteTask(task.getId());
            verify(taskRepository).delete(task);
        }

        }

    }