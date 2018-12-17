package guru.springframework.sfgpetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "owners")
public class Owner extends Person {

    @Column(name = "adress")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "telephone")
    private String telephone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();

    public Pet getPet(String name, boolean ignoreNew) {
        return pets.stream()
                .filter(pet -> (!ignoreNew || !pet.isNew()) && pet.getName().toLowerCase().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Pet getPet(String name) {
        return getPet(name, false);
    }

    public void addPet(Pet pet) {
        this.pets.add(pet);
        pet.setOwner(this);
    }
}
