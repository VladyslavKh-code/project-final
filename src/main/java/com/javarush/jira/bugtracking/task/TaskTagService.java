package com.javarush.jira.bugtracking.task;

import com.javarush.jira.common.error.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskTagService {
    private final TaskRepository taskRepository;

    @Transactional
    public Set<String> getTagsForTask(long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task with id " + taskId + " not found"));
        task.getTags().size();
        return task.getTags();
    }

    @Transactional
    public void addTagToTask(long taskId, String tag) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task with id " + taskId + " not found"));
        task.getTags().size();
        task.getTags().add(tag);
        taskRepository.save(task);
    }

    @Transactional
    public void removeTagFromTask(long taskId, String tag) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task with id " + taskId + " not found"));
        task.getTags().size();
        task.getTags().remove(tag);
        taskRepository.save(task);
    }

    @Transactional
    public void updateTagsForTask(long taskId, Set<String> newTags) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task with id " + taskId + " not found"));
        task.getTags().size();
        task.setTags(newTags);
        taskRepository.save(task);
    }
}