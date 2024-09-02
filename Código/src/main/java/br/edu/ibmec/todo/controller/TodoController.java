package br.edu.ibmec.todo.controller;


import br.edu.ibmec.todo.model.Todo;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {
    private static List<Todo> Todos = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Todo>> getTodo() {
        return new ResponseEntity(Todos, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable("id") int id) {
        Todo response = null;

        for (Todo todo : Todos) {
            if (todo.getId() == id) {
                response = todo;
                break;
            }
        }

        if (response == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @PostMapping
    public ResponseEntity<Todo> saveTodo (@Valid @RequestBody Todo todo) {
        Todos.add(todo);
        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable("id") int id, @Valid @RequestBody Todo novosDados) {
        Todo todoASerAtualizado = null;

        for (Todo todo : Todos) {
            if (todo.getId() == id) {
                todoASerAtualizado = todo;
                break;
            }
        }

        if (todoASerAtualizado == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Todos.remove(todoASerAtualizado);
        
        todoASerAtualizado.setName(novosDados.getName());
        todoASerAtualizado.setOwner(novosDados.getOwner());
        todoASerAtualizado.setStatus(novosDados.getStatus());
        todoASerAtualizado.setDescription(novosDados.getDescription());
        
        Todos.add(todoASerAtualizado);

        return new ResponseEntity<>(todoASerAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Todo> deleteTodo(@PathVariable("id") int id) {
        Todo todoASerExcluido = null;

        for (Todo todo : Todos) {
            if (todo.getId() == id) {
                todoASerExcluido = todo;
                break;
            }
        }

        if (todoASerExcluido == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        //Remove da lista
        Todos.remove(todoASerExcluido);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
