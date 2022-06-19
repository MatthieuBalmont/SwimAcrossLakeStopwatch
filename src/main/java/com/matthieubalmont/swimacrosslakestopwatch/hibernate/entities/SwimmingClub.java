package com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities;

import jakarta.validation.constraints.Size;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "swimming_club")
public class SwimmingClub {
    @Id
    @Size(min = 3, max = 50, message = "Name must be between {min} and {max} characters")
    private String swimmingClubName;
    @OneToMany(mappedBy = "swimmingClub", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Swimmer> swimmers;

    public SwimmingClub() {
        swimmers = new HashSet<>();
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

}
