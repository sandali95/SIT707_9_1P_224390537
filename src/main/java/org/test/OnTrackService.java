package org.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnTrackService {
    public static final Map<String, List<Task>> tasksDatabase = new HashMap<>();

    public List<Task> getTasksForStudent(String studentId) {
        // Check for null student ID
        if (studentId == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }

        // Check for empty student ID
        if (studentId.isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be empty");
        }

        // Check if student exists in database
        if (!tasksDatabase.containsKey(studentId)) {
            throw new IllegalArgumentException("Student ID '" + studentId + "' not found");
        }

        // Return tasks for valid student ID
        List<Task> tasks = tasksDatabase.get(studentId);
        return tasks != null ? tasks : new ArrayList<>();
    }

}
