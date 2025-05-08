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
        futureDate = new Date(System.currentTimeMillis() + 86400000); //tomorrow
        pastDate = new Date(System.currentTimeMillis() - 86400000); //yesterday
        currentDate = new Date();

        // Clear the database before each test
        OnTrackService.tasksDatabase.clear();
    }

    @Test
    public void testGetTasksForStudent_NullStudentId() {
        List<Task> result = service.getTasksForStudent(null);
        assertNotNull("Should return empty list for null studentId", result);
        assertTrue("Should return empty list for null studentId", result.isEmpty());
    }

    @Test
    public void testGetTasksForStudent_EmptyStudentId() {
        List<Task> result = service.getTasksForStudent("");
        assertNotNull("Should return empty list for empty studentId", result);
        assertTrue("Should return empty list for empty studentId", result.isEmpty());
    }

    @Test
    public void testGetTasksForStudent_NonExistentStudent() {
        List<Task> result = service.getTasksForStudent("non-existent-id");
        assertNotNull("Should return empty list for non-existent student", result);
        assertTrue("Should return empty list for non-existent student", result.isEmpty());
    }

    @Test
    public void testGetTasksForStudent_StudentWithNoTasks() {
        String studentId = "student123";
        OnTrackService.tasksDatabase.put(studentId, new ArrayList<>());

        List<Task> result = service.getTasksForStudent(studentId);
        assertNotNull("Should return empty list for student with no tasks", result);
        assertTrue("Should return empty list for student with no tasks", result.isEmpty());
    }

    @Test
    public void testGetTasksForStudent_StudentWithOneTask() {
        String studentId = "student123";
        Task task = new Task("Assignment 1", "task1", futureDate, null, Task.Status.NOT_COMPLETED);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        OnTrackService.tasksDatabase.put(studentId, tasks);

        List<Task> result = service.getTasksForStudent(studentId);
        assertEquals("Should return exactly one task", 2, result.size());
        assertEquals("Task should match the one added", task, result.get(0));
    }

    @Test
    public void testGetTasksForStudent_StudentWithMultipleTasks() {
        String studentId = "student123";
        Task task1 = new Task("Assignment 1", "task1", futureDate, null, Task.Status.NOT_COMPLETED);
        Task task2 = new Task("Assignment 2", "task2", pastDate, pastDate, Task.Status.COMPLETED);
        Task task3 = new Task("Assignment 3", "task3", futureDate, currentDate, Task.Status.IN_PROGRESS);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        OnTrackService.tasksDatabase.put(studentId, tasks);

        List<Task> result = service.getTasksForStudent(studentId);
        assertEquals("Should return all tasks", 3, result.size());
        assertTrue("Should contain all added tasks", result.containsAll(tasks));
    }

    @Test
    public void testGetTasksForStudent_TaskWithNullValues() {
        String studentId = "student123";
        Task task = new Task(null, null, null, null, null);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        OnTrackService.tasksDatabase.put(studentId, tasks);

        List<Task> result = service.getTasksForStudent(studentId);
        assertEquals("Should handle task with null values", 1, result.size());
        assertNull("Task name should be null", result.get(0).getTaskName());
        assertNull("Task ID should be null", result.get(0).getTaskId());
        assertNull("Deadline should be null", result.get(0).getDeadline());
        assertNull("Submitted date should be null", result.get(0).getSubmittedDate());
        assertNull("Status should be null", result.get(0).getStatus());
    }

    @Test
    public void testGetTasksForStudent_TaskWithDifferentStatuses() {
        String studentId = "student123";
        Task completed = new Task("Completed", "task1", pastDate, pastDate, Task.Status.COMPLETED);
        Task inProgress = new Task("In Progress", "task2", futureDate, currentDate, Task.Status.IN_PROGRESS);
        Task notCompleted = new Task("Not Completed", "task3", futureDate, null, Task.Status.NOT_COMPLETED);
        Task resubmit = new Task("Resubmit", "task4", futureDate, pastDate, Task.Status.RESUBMIT);

        List<Task> tasks = new ArrayList<>();
        tasks.add(completed);
        tasks.add(inProgress);
        tasks.add(notCompleted);
        tasks.add(resubmit);
        OnTrackService.tasksDatabase.put(studentId, tasks);

        List<Task> result = service.getTasksForStudent(studentId);
        assertEquals("Should handle all status types", 4, result.size());
    }
}