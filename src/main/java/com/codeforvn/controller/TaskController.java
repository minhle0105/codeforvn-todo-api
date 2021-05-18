package com.codeforvn.controller;

import com.codeforvn.model.Task;
import com.codeforvn.service.task.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping
public class TaskController {
    @Autowired
    private ITaskService taskService;

    @GetMapping
    public ResponseEntity<Iterable<Task>> getAll() {
        return new ResponseEntity<>(taskService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) {
        Optional<Task> productOptional = taskService.findById(id);
        return productOptional.map(product -> new ResponseEntity<>(product, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Task> save(@RequestBody Task product) {
        return new ResponseEntity<>(taskService.save(product), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateProduct(@PathVariable Long id, @RequestBody Task product) {
        Optional<Task> productOptional = taskService.findById(id);
        if (productOptional.isPresent()) {
            product.setId(productOptional.get().getId());
            return new ResponseEntity<>(taskService.save(product), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteProduct(@PathVariable Long id) {
        Optional<Task> productOptional = taskService.findById(id);
        if (productOptional.isPresent()) {
            taskService.deleteById(id);
            return new ResponseEntity<>(productOptional.get(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
