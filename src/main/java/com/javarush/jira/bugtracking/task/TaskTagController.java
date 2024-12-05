package com.javarush.jira.bugtracking.task;

import com.javarush.jira.common.error.IllegalRequestDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskTagController {
    private final TaskTagService taskTagService;

    @GetMapping("/{taskId}/tags")
    public Set<String> getTags(@PathVariable long taskId) {
        return taskTagService.getTagsForTask(taskId);
    }

    @PostMapping("/{taskId}/tags")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addTag(@PathVariable long taskId, @RequestBody String tag) {
        if (tag == null || tag.trim().isEmpty()) {
            throw new IllegalRequestDataException("Tag cannot be null or empty");
        }
        taskTagService.addTagToTask(taskId, tag.trim());
    }

    @DeleteMapping("/{taskId}/tags")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTag(@PathVariable long taskId, @RequestBody String tag) {
        if (tag == null || tag.trim().isEmpty()) {
            throw new IllegalRequestDataException("Tag cannot be null or empty");
        }
        taskTagService.removeTagFromTask(taskId, tag.trim());
    }

    @PutMapping("/{taskId}/tags")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTags(@PathVariable long taskId, @RequestBody Set<String> newTags) {
        if (newTags == null) {
            throw new IllegalRequestDataException("Tags cannot be null");
        }
        taskTagService.updateTagsForTask(taskId, newTags);
    }
}