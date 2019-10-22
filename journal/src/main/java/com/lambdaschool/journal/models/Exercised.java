package com.lambdaschool.journal.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "exercised",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"workoutid", "userid"})})
public class Exercised extends Auditable implements Serializable
{
    private static final long serialVersionUID = 3L;

    @Id
    @ManyToOne
    @JoinColumn(name = "workoutid")
    @JsonIgnoreProperties("exercised")
    private Workout workout;

    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties("exercised")
    private User user;

    public Exercised()
    {
    }

    public Exercised(Workout workout, User user)
    {
        this.workout = workout;
        this.user = user;
    }

    public Workout getWorkout()
    {
        return workout;
    }

    public void setWorkout(Workout workout)
    {
        this.workout = workout;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Exercised exercised = (Exercised) o;
        return Objects.equals(workout, exercised.workout) && Objects.equals(user, exercised.user);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(workout, user);
    }
}