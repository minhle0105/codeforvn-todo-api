package com.codeforvn.service.task;

import com.codeforvn.model.Task;
import com.codeforvn.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private ITaskRepository taskRepository;

    @Override
    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        taskRepository.deleteById(id);
    }
}
