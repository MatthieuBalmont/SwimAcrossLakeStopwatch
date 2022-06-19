package com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "competition")
public class Competition {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", length = 36)
    private String id;
    @OneToMany(mappedBy="competition", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Race> races;
    @NotNull(message = "Date must not be 'null'")
    @Temporal(TemporalType.DATE)
    private Calendar date;
    @NotNull(message = "Name must not be 'null'")
    @Size(min = 3, max = 50, message = "Name must be between {min} and {max} characters")
    private String name;

    public Competition(String name, Calendar date){
        this.name = name;
        this.date = date;
        this.races = new HashSet<>();
    }

    public Competition() {
        this.races = new HashSet<>();
    }


    public String getId() {
        return id;
    }

    public Set<Race> getRaces() {
        return races;
    }

    public void addRace(Race race){
        this.races.add(race);
    }

    public Calendar getDate() {
        return date;
    }

    public String getDateToString() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(this.date.getTime());

    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competition that = (Competition) o;
        return id.equals(that.id) && date.equals(that.date) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, name);
    }

    @Override
    public String toString() {
        return "id:" + this.id +
                " - name:" + this.name +
                " - date:" + this.getDateToString();
    }
}
