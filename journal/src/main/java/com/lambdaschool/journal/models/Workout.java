package com.lambdaschool.journal.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "workouts")
public class Workout extends Auditable
{
    //fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long workoutid;

    private String title;

    private String day;

    private String exerciseName;

    private int weight;

    private int sets;

    private int reps;

    private String muscleGroup;

    //ManyToOne
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties("workouts")
    private User user;

    //default constructor
    public Workout()
    {
    }

    //constructor
    public Workout(String title, String day, String exerciseName, int weight, int sets, int reps, String muscleGroup)
    {
        this.title = title;
        this.day = day;
        this.exerciseName = exerciseName;
        this.weight = weight;
        this.sets = sets;
        this.reps = reps;
        this.muscleGroup = muscleGroup;
    }

    //getters and setters
    public long getWorkoutid() {
        return workoutid;
    }

    public void setWorkoutid(long workoutid) {
        this.workoutid = workoutid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    @Override
    public String toString() {
        return "Workout{" + "workoutid=" + workoutid + ", title='" + title + '\'' + ", day='" + day + '\'' + ", exerciseName='" + exerciseName + '\'' + ", weight=" + weight + ", sets=" + sets + ", reps=" + reps + ", muscleGroup='" + muscleGroup + '\'' + '}';
    }

}
