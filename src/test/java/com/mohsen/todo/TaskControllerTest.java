package com.mohsen.todo;

import com.mohsen.todo.controller.TaskController;
import com.mohsen.todo.dto.TaskDto;
import com.mohsen.todo.entity.Task;
import com.mohsen.todo.entity.TaskStatus;
import com.mohsen.todo.service.TaskService;
import com.mohsen.todo.service.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {
    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    public void testGetTaskById() {
        Task mockTask = new Task("Mock Task", "Description", new Date().toString(), TaskStatus.TODO);
        mockTask.setId("5");
        when(taskService.getTaskById(mockTask.getId())).thenReturn(Mono.just(mockTask));

        WebTestClient
                .bindToController(taskController)
                .build()
                .get()
                .uri("/tasks/5")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Task.class)
                .value(task -> {
                    assertEquals("Mock Task", task.getTitle());
                    assertEquals("Description", task.getDescription());
                    assertEquals(TaskStatus.TODO, task.getStatus());
                    assertEquals("5", task.getId());
                });
        StepVerifier.create(taskService.getTaskById("5"))
                .expectNextMatches(task -> "Mock Task".equals(task.getTitle()) &&
                        "Description".equals(task.getDescription()) &&
                        TaskStatus.TODO.equals(task.getStatus()) &&
                        "5".equals(task.getId()))
                .verifyComplete();
    }

    @Test
    public void testGetAllTasks() {
        Task task1 = new Task("Task 1", "Description 1", new Date().toString(), TaskStatus.TODO);
        task1.setId("45");
        Task task2 = new Task("Task 2", "Description 2", new Date().toString(), TaskStatus.IN_PROGRESS);
        task2.setId("36");
        List<Task> mockTasks = Arrays.asList(task1, task2);

        when(taskService.getAllTasks()).thenReturn(Flux.fromIterable(mockTasks));

        WebTestClient
                .bindToController(taskController)
                .build()
                .get()
                .uri("/tasks")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Task.class)
                .value(tasks -> {
                    assertEquals(2, tasks.size());
                    assertEquals("Task 1", tasks.get(0).getTitle());
                    assertEquals("Description 1", tasks.get(0).getDescription());
                    assertEquals(TaskStatus.TODO, tasks.get(0).getStatus());
                    assertEquals("45", task1.getId());
                    assertEquals("Task 2", tasks.get(1).getTitle());
                    assertEquals("Description 2", tasks.get(1).getDescription());
                    assertEquals(TaskStatus.IN_PROGRESS, tasks.get(1).getStatus());
                    assertEquals("36", task2.getId());
                });

        StepVerifier.create(taskService.getAllTasks())
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void testCreateNewTask() {
        TaskDto mockTaskDto = new TaskDto("New Task", "Description", new Date().toString(), TaskStatus.TODO);
        mockTaskDto.setId("28");
        Task mockTask = TaskServiceImpl.taskDtoToEntity(mockTaskDto);

        when(taskService.createTask(any(TaskDto.class))).thenReturn(Mono.just(mockTask));

        WebTestClient
                .bindToController(taskController)
                .build()
                .post()
                .uri("/tasks")
                .body(Mono.just(mockTask), Task.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Task.class)
                .value(task -> {
                    assertEquals("New Task", task.getTitle());
                    assertEquals("Description", task.getDescription());
                    assertEquals(TaskStatus.TODO, task.getStatus());
                    assertEquals("28", task.getId());
                });

        StepVerifier.create(taskService.createTask(mockTaskDto))
                .expectNextMatches(task -> "New Task".equals(task.getTitle()) &&
                        "Description".equals(task.getDescription()) &&
                        TaskStatus.TODO.equals(task.getStatus()) &&
                        "28".equals(task.getId()))
                .verifyComplete();
    }

    @Test
    public void testUpdateTask() {
        String id = "72";
        TaskDto updatedTaskDto = new TaskDto("Updated Task", "Updated Description", new Date().toString(), TaskStatus.IN_PROGRESS);
        updatedTaskDto.setId(id);
        Task updatedTask = TaskServiceImpl.taskDtoToEntity(updatedTaskDto);
        when(taskService.updateTask(any(TaskDto.class), any(String.class))).thenReturn(Mono.just(updatedTask));

        WebTestClient
                .bindToController(taskController)
                .build()
                .put()
                .uri("/tasks/72")
                .body(Mono.just(updatedTask), TaskDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Task.class)
                .value(task -> {
                    assertEquals("Updated Task", task.getTitle());
                    assertEquals("Updated Description", task.getDescription());
                    assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
                    assertEquals(id, task.getId());
                });

        StepVerifier.create(taskService.updateTask(updatedTaskDto, id))
                .expectNextMatches(task -> "Updated Task".equals(task.getTitle()) &&
                        "Updated Description".equals(task.getDescription()) &&
                        TaskStatus.IN_PROGRESS.equals(task.getStatus()) &&
                        id.equals(task.getId()))
                .verifyComplete();
    }

    @Test
    public void testDeleteTask() {
        Task deletedTask = new Task("Deleted Task", "Description", new Date().toString(), TaskStatus.COMPLETED);
        deletedTask.setId("76");
        when(taskService.deleteTask(any(String.class))).thenReturn(Mono.just(deletedTask));
        WebTestClient
                .bindToController(taskController)
                .build()
                .delete()
                .uri("/tasks/76")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Task.class)
                .value(task -> {
                    assertEquals("Deleted Task", task.getTitle());
                    assertEquals("Description", task.getDescription());
                    assertEquals(TaskStatus.COMPLETED, task.getStatus());
                    assertEquals("76", task.getId());
                });

        StepVerifier.create(taskService.deleteTask("76"))
                .expectNextMatches(task -> "Deleted Task".equals(task.getTitle()) &&
                        "Description".equals(task.getDescription()) &&
                        TaskStatus.COMPLETED.equals(task.getStatus()) &&
                        "76".equals(task.getId()))
                .verifyComplete();
    }

}
