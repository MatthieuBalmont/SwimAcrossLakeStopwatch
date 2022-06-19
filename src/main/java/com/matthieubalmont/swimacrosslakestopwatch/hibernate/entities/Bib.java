package com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "bib")
public class Bib {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", length = 36)
    private String id;
    @NotNull(message = "Bib must not be 'null'")
    @Min(value = 0, message = "Bib must be higher that {value}")
    private Integer bib;
    @Min(value = 0, message = "End time must be higher that {value}")
    private Long endTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wave_id", nullable=false)
    private Wave wave;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "swimmer_iuf", nullable=false)
    private Swimmer swimmer;

    public Bib() {
    }

    public String getId() {
        return id;
    }

    public Integer getBib() {
        return bib;
    }

    public Long getEndTime() {
        return endTime;
    }

    public Wave getWaveId() {
        return wave;
    }

    public Swimmer getSwimmerIuf() {
        return swimmer;
    }
}
