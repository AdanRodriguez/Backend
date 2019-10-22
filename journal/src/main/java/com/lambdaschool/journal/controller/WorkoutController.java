package com.lambdaschool.journal.controller;

import com.lambdaschool.journal.models.Workout;
import com.lambdaschool.journal.service.WorkoutService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class WorkoutController
{
    @Autowired
    private WorkoutService workoutService;

    @ApiOperation(value = "lists all workouts",
                  response = Workout.class,
                  responseContainer = "List")
    @ApiImplicitParams({
                               @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                                                 value = "Results page you want to retrieve (0..N)"),
                               @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                                                 value = "Number of records per page."),
                               @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                                                 value = "Sorting criteria in the format: property(,asc|desc). " +
                                                         "Default sort order is ascending. " +
                                                         "Multiple sort criteria are supported.")})
    //GET http://localhost:2019/workout/all
    @GetMapping(value = "/workout/all",
                produces = {"application/json"})
    public ResponseEntity<?> listAllWorkouts(@PageableDefault(page = 0, size = 10) Pageable pageable)
    {
        return new ResponseEntity<>(workoutService.findAll(pageable), HttpStatus.OK);
    }


//    @ApiOperation(value = "add an existing book to an existing author using ids")
//    @ApiResponses(value = {@ApiResponse(code = 200,
//                                        message = "Book Added to Author"), @ApiResponse(code = 404,
//                                                                                        message = "Book/Author Not Found",
//                                                                                        response = ErrorDetail.class)})
//    // POST /data/books/{bookid}/authors/{authorid} - assigns a book already in the system (bookid) to an author already in the system (authorid)
//    @PostMapping(value = "/data/books/{bookid}/authors/{authorid}")
//    public ResponseEntity<?> addBookToAuthor(@PathVariable long bookid,
//                                             @PathVariable long authorid)
//    {
//        bookService.addBookToAuthor(bookid, authorid);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    //POST http://localhost:2019/workout/new
    @ApiOperation(value = "adds a new workout")
    @PostMapping(value = "/workout/new", consumes = {"application/json"})
    public ResponseEntity<?> addWorkout(@Valid
                                           @RequestBody Workout workout)
    {
        workoutService.save(workout);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //PUT http://localhost:2019/workout/update/16
    @PutMapping(value = "workout/update/{id}",
                        consumes = {"application/json"})
    public ResponseEntity<?> updateWorkout(@Valid
                                           @RequestBody Workout workout,
                                           @PathVariable long id)
    {
        workoutService.update(workout,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //DELETE http://localhost:2019/workout/delete/15
    @DeleteMapping(value = "/workout/delete/{id}")
    public ResponseEntity<?> deleteWorkout(@PathVariable long id)
    {
        workoutService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
