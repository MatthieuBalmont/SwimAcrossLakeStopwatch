package com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities;

import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "swimmer")
public class Swimmer {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", length = 36)
    private String id;
    @NotNull(message = "First name must not be 'null'")
    private String firstName;
    @NotNull(message = "Last name must not be 'null'")
    private String lastName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "swimmingClub_id", nullable=false)
    private SwimmingClub swimmingClub;
    @NotNull(message = "Year of birth must not be 'null'")
    private Integer yearOfBirth;
    @OneToMany(mappedBy = "swimmer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Bib> bibs;

    public Swimmer() {

    }

    public Swimmer(String id, String firstName, String lastName, SwimmingClub swimmingClub, int yearOfBirth){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.swimmingClub = swimmingClub;
        this.yearOfBirth = yearOfBirth;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public SwimmingClub getSwimmingClub() {
        return swimmingClub;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

}
