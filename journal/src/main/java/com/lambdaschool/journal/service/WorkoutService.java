package com.lambdaschool.journal.service;

import com.lambdaschool.journal.models.Workout;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface WorkoutService
{
    ArrayList<Workout> findAll(Pageable pageable);

    Workout save(Workout workout);

    Workout update(Workout workout, long id);

    void addWorkoutToUser(long workoutid, long userid);

    void delete(long id);
}
