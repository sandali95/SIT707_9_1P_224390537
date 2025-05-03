package org.test;

import java.util.Date;

public class Task {

    enum Status {
        COMPLETED,
        IN_PROGRESS,
        NOT_COMPLETED,
        RESUBMIT
    }

    private String taskName;
    private String taskId;
    private Date deadline;
    private Date submittedDate;

    private Status status;

    public Task(String taskName, String taskId, Date deadline, Date submittedDate, Status status) {
        this.taskName = taskName;
        this.taskId = taskId;
        this.deadline = deadline;
        this.submittedDate = submittedDate;
        this.status = status;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskId() {
        return taskId;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public Status getStatus() {
        return status;
    }
}
