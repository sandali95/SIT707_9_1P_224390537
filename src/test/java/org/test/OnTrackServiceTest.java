package org.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OnTrackServiceTest {
    private OnTrackService service;
    private Date futureDate;
    private Date pastDate;
    private Date currentDate;

    @Before
    public void setUp() {
        service = new OnTrackService();
        futureDate = new Date(System.currentTimeMillis() + 86400000); // tomorrow
        pastDate = new Date(System.currentTimeMillis() - 86400000); // yesterday
        currentDate = new Date();

        // Clear and initialize the database before each test
        OnTrackService.tasksDatabase.clear();
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Valid Task", "task1", futureDate, null, Task.Status.NOT_COMPLETED));
        OnTrackService.tasksDatabase.put("validStudent", tasks);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TC_01() {
        service.getTasksForStudent(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TC_02() {
        service.getTasksForStudent("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TC_03() {
        service.getTasksForStudent("non-existent-id");
    }

    @Test
    public void TC_04() {
        String studentId = "studentWithNoTasks";
        OnTrackService.tasksDatabase.put(studentId, new ArrayList<>());

        List<Task> result = service.getTasksForStudent(studentId);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void TC_05() {
        String studentId = "validStudent";
        List<Task> result = service.getTasksForStudent(studentId);

        assertEquals(1, result.size());
        assertEquals("Valid Task", result.get(0).getTaskName());
    }

    @Test
    public void TC_06() {
        String studentId = "student123";
        Task task1 = new Task("Assignment 1", "task1", futureDate, null, Task.Status.NOT_COMPLETED);
        Task task2 = new Task("Assignment 2", "task2", pastDate, pastDate, Task.Status.COMPLETED);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        OnTrackService.tasksDatabase.put(studentId, tasks);

        List<Task> result = service.getTasksForStudent(studentId);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(tasks));
    }

    @Test
    public void TC_07() {
        String studentId = "studentWithNullTask";
        Task task = new Task(null, null, null, null, null);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        OnTrackService.tasksDatabase.put(studentId, tasks);

        List<Task> result = service.getTasksForStudent(studentId);
        assertEquals(1, result.size());
        assertNull(result.get(0).getTaskName());
    }

    @Test
    public void TC_08() {
        String studentId = "studentWithMixedStatuses";
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Completed", "task1", pastDate, pastDate, Task.Status.COMPLETED));
        tasks.add(new Task("In Progress", "task2", futureDate, currentDate, Task.Status.IN_PROGRESS));

        OnTrackService.tasksDatabase.put(studentId, tasks);

        List<Task> result = service.getTasksForStudent(studentId);
        assertEquals(2, result.size());
    }

    @Test
    public void TC_09() {
        String studentId = "studentWithNullTaskList";
        OnTrackService.tasksDatabase.put(studentId, null);

        List<Task> result = service.getTasksForStudent(studentId);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}