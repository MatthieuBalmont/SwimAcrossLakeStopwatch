package com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "wave")
public class Wave {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", length = 36)
    private String id;
    @NotNull(message = "Wave number must not be 'null'")
    @Min(value = 0, message = "Order must be higher that {value}")
    private Integer waveNumber;
    @Min(value = 0, message = "Order must be higher that {value}")
    private Long startTime;
    @OneToMany(mappedBy = "wave", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Bib> bibs;
}
