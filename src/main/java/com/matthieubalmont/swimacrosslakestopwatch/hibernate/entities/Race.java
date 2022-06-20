package com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "race")
public class Race {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", length = 36)
    private String id;
    @NotNull(message = "Distance must not be 'null'")
    @Min(value = 0, message = "Distance must be higher that {value}")
    private int distance;
    @NotNull(message = "Start order must not be 'null'")
    @Min(value = 0, message = "Start order must be higher that {value}")
    private int startOrder;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id", nullable=false)
    @NotNull(message = "Competition must not be 'null'")
    private Competition competition;

    public Race() {

    }

    public Race(int dist, int startOrder, Competition competition){
        this.distance = dist;
        this.startOrder = startOrder;
        this.competition = competition;
    }

    public String getId() {
        return id;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getStartOrder() {
        return startOrder;
    }

    public void setStartOrder(int startOrder) {
        this.startOrder = startOrder;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Race race = (Race) o;
        return distance == race.distance && startOrder == race.startOrder && id.equals(race.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, distance, startOrder);
    }
}
