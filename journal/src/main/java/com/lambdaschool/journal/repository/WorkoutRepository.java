package com.lambdaschool.journal.repository;

import com.lambdaschool.journal.models.Workout;
import com.lambdaschool.journal.views.JustTheCount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface WorkoutRepository extends PagingAndSortingRepository<Workout, Long>
{
    @Query(value = "SELECT COUNT(*) as count FROM exercise WHERE workoutid = :workoutid AND userid = :userid",
           nativeQuery = true)
    JustTheCount checkExerciseCombo(long workoutid,
                                 long userid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Exercise (workoutid, userid) VALUES (:workoutid, :userid)",
           nativeQuery = true)
    void insertExercise(long workoutid,
                     long userid);
}