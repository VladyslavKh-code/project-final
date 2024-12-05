package com.javarush.jira.bugtracking.task.to;

import java.util.List;

public class TaskTagTo {
    private List<String> tasks;

    public TaskTagTo(List<String> tasks) {
        this.tasks = tasks;
    }
}
