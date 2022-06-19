package com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "swimming_club")
public class SwimmingClub {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", length = 36)
    private String id;

    @NotNull(message = "Swimming club name must not be 'null'")
    @Size(min = 3, max = 50, message = "Name must be between {min} and {max} characters")
    private String swimmingClubName;
    @OneToMany(mappedBy = "swimmingClub", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Swimmer> swimmers;

    public SwimmingClub() {
        swimmers = new HashSet<>();
    }

    public SwimmingClub(String swimmingClubName) {
        this.swimmingClubName = swimmingClubName;
    }

    public String getId() {
        return id;
    }

    public String getSwimmingClubName() {
        return swimmingClubName;
    }

    public Set<Swimmer> getSwimmers() {
        return swimmers;
    }

    public void addSwimmer(Swimmer swimmer){
        this.swimmers.add(swimmer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SwimmingClub that = (SwimmingClub) o;
        return id.equals(that.id) && swimmingClubName.equals(that.swimmingClubName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, swimmingClubName);
    }
}
