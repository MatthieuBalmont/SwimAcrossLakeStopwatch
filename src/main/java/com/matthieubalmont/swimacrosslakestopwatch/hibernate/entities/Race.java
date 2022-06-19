package com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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
    @Min(value = 0, message = "Order must be higher that {value}")
    private int startOrder;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id", nullable=false)
    private Competition competition;

    public Race() {

    }

    public Race(int dist){
        this.distance = dist;
    }

    public String getId() {
        return id;
    }

    public int getDistance() {
        return distance;
    }

    public int getStartOrder() {
        return startOrder;
    }
}
