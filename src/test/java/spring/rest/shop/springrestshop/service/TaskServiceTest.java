package spring.rest.shop.springrestshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import spring.rest.shop.springrestshop.aspect.SecurityContext;
import spring.rest.shop.springrestshop.dto.task.TaskCreateOrEditDTO;
import spring.rest.shop.springrestshop.dto.user.UserDTO;
import spring.rest.shop.springrestshop.entity.Task;
import spring.rest.shop.springrestshop.entity.TaskState;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.exception.EmptyFieldException;
import spring.rest.shop.springrestshop.exception.EntityNotFoundException;
import spring.rest.shop.springrestshop.repository.TaskRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void UserTryToChooseExecutor_shouldThrowEntityNotFoundException(){
        when(taskRepository.findById(1L)).thenReturn(null);
        assertThrows(EntityNotFoundException.class,() -> taskService.chooseExecutor(1L,new UserDTO()));
    }
    @Test
    void UserTryToChooseExecutor_shouldThrowAccessDeniedException(){
        User currentUser = new User(1L,"password","email");
        try (MockedStatic<SecurityContext> mocked = mockStatic(SecurityContext.class)) {
            mocked.when(SecurityContext::getCurrentUser).thenReturn(currentUser);
            when(taskRepository.findById(1L)).thenReturn(new Task());
            assertThrows(AccessDeniedException.class, () -> taskService.chooseExecutor(1L, new UserDTO()));
        }
        }

    @Test
    void UserTryToChooseExecutor_shouldThrowEmptyFieldException(){
        User currentUser = new User(1L,"password","email");
        Task task = new Task();
        task.setCreator(currentUser);
        try (MockedStatic<SecurityContext> mocked = mockStatic(SecurityContext.class)) {
            mocked.when(SecurityContext::getCurrentUser).thenReturn(currentUser);
            when(taskRepository.findById(1L)).thenReturn(task);
            assertThrows(EmptyFieldException.class, () -> taskService.chooseExecutor(1L, null));
        }
    }
    @Test
    void UserTryToChooseExecutor_shouldChooseExecutorByEmail(){
        User currentUser = new User(1L,"password","email");
        Task task = new Task();
        task.setCreator(currentUser);
        task.setTitle("title");
        task.setDescription("description");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("emailDTO");
        task.setCreator(currentUser);
        try (MockedStatic<SecurityContext> mocked = mockStatic(SecurityContext.class)) {
            mocked.when(SecurityContext::getCurrentUser).thenReturn(currentUser);
            when(taskRepository.findById(1L)).thenReturn(task);
            when(userService.getUserByEmail("emailDTO")).thenReturn(currentUser);
            taskService.chooseExecutor(1L,userDTO);
            assertEquals(task.getExecutors().get(0),currentUser);
            verify(taskRepository).save(task);
        }
    }

    @Test
    void UserTryToChooseExecutor_shouldChooseExecutorById(){
        User currentUser = new User(1L,"password","mail");
        Task task = new Task();
        task.setCreator(currentUser);
        task.setTitle("title");
        task.setDescription("description");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail(null);
        task.setCreator(currentUser);
        try (MockedStatic<SecurityContext> mocked = mockStatic(SecurityContext.class)) {
            mocked.when(SecurityContext::getCurrentUser).thenReturn(currentUser);
            when(taskRepository.findById(1L)).thenReturn(task);
            when(userService.getUserById(userDTO.getId())).thenReturn(currentUser);
            taskService.chooseExecutor(1L,userDTO);
            assertEquals(task.getExecutors().get(0),currentUser);
            verify(taskRepository).save(task);
        }
    }
    }