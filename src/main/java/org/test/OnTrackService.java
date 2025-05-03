package org.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnTrackService {
    public static final Map<String, List<Task>> tasksDatabase = new HashMap<>();

    public List<Task> getTasksForStudent(String studentId) {
        if (studentId == null) {
            return new ArrayList();
        }
        return tasksDatabase.getOrDefault(studentId, new ArrayList<>());
    }

}
