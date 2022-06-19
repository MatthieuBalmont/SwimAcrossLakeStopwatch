package com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
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
    @Size(min = 2, max = 50, message = "First name must be between {min} and {max} characters")
    private String firstName;
    @NotNull(message = "Last name must not be 'null'")
    @Size(min = 2, max = 50, message = "Last name must be between {min} and {max} characters")
    private String lastName;
    @NotNull(message = "Genre must not be 'null'")
    @Size(min = 1, max = 1, message = "Genre must be between {min} and {max} characters")
    private String genre;
    @NotNull(message = "Nationality must not be 'null'")
    @Size(min = 1, max = 6, message = "Nationality must be between {min} and {max} characters")
    private String nationality;
    @NotNull(message = "Year of birth must not be 'null'")
    private Integer yearOfBirth;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "swimmingClub_id", nullable=false)
    @NotNull(message = "Swimming club must not be 'null'")
    private SwimmingClub swimmingClub;
    @OneToMany(mappedBy = "swimmer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Bib> bibs;

    public Swimmer() {

    }

    public Swimmer(String firstName, String lastName, String genre, String nationality, Integer yearOfBirth, SwimmingClub swimmingClub) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.genre = genre;
        this.nationality = nationality;
        this.yearOfBirth = yearOfBirth;
        this.swimmingClub = swimmingClub;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Swimmer swimmer = (Swimmer) o;
        return id.equals(swimmer.id) && firstName.equals(swimmer.firstName) && lastName.equals(swimmer.lastName) && genre.equals(swimmer.genre) && nationality.equals(swimmer.nationality) && yearOfBirth.equals(swimmer.yearOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, genre, nationality, yearOfBirth);
    }
}
