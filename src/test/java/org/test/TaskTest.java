package org.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;

public class TaskTest {
    private Date futureDate;
    private Date pastDate;
    private Date currentDate;

    @Before
    public void setUp() {
        futureDate = new Date(System.currentTimeMillis() + 86400000);
        pastDate = new Date(System.currentTimeMillis() - 86400000);
        currentDate = new Date();
    }

    @Test
    public void testTaskCreationWithAllValues() {
        Task task = new Task("Assignment 1", "task1", futureDate, currentDate, Task.Status.IN_PROGRESS);

        assertEquals("Assignment 1", task.getTaskName());
        assertEquals("task1", task.getTaskId());
        assertEquals(futureDate, task.getDeadline());
        assertEquals(currentDate, task.getSubmittedDate());
        assertEquals(Task.Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    public void testTaskCreationWithNullValues() {
        Task task = new Task(null, null, null, null, null);

        assertNull(task.getTaskName());
        assertNull(task.getTaskId());
        assertNull(task.getDeadline());
        assertNull(task.getSubmittedDate());
        assertNull(task.getStatus());
    }

    @Test
    public void testTaskWithEmptyStrings() {
        Task task = new Task("", "", futureDate, currentDate, Task.Status.NOT_COMPLETED);

        assertEquals("", task.getTaskName());
        assertEquals("", task.getTaskId());
    }

    @Test
    public void testTaskWithDifferentStatuses() {
        Task completed = new Task("Task1", "t1", futureDate, pastDate, Task.Status.COMPLETED);
        Task inProgress = new Task("Task2", "t2", futureDate, currentDate, Task.Status.IN_PROGRESS);
        Task notCompleted = new Task("Task3", "t3", futureDate, null, Task.Status.NOT_COMPLETED);
        Task resubmit = new Task("Task4", "t4", futureDate, pastDate, Task.Status.RESUBMIT);

        assertEquals(Task.Status.COMPLETED, completed.getStatus());
        assertEquals(Task.Status.IN_PROGRESS, inProgress.getStatus());
        assertEquals(Task.Status.NOT_COMPLETED, notCompleted.getStatus());
        assertEquals(Task.Status.RESUBMIT, resubmit.getStatus());
    }

    @Test
    public void testTaskDateCombinations() {
        // Submitted before deadline
        Task earlySubmission = new Task("Early", "e1", futureDate, pastDate, Task.Status.COMPLETED);
        // Submitted after deadline
        Task lateSubmission = new Task("Late", "l1", pastDate, currentDate, Task.Status.RESUBMIT);
        // No submission
        Task noSubmission = new Task("NoSub", "n1", futureDate, null, Task.Status.NOT_COMPLETED);
        // Submitted on deadline day
        Task onTimeSubmission = new Task("OnTime", "o1", currentDate, currentDate, Task.Status.IN_PROGRESS);

        assertNotNull(earlySubmission);
        assertNotNull(lateSubmission);
        assertNotNull(noSubmission);
        assertNotNull(onTimeSubmission);
    }

}