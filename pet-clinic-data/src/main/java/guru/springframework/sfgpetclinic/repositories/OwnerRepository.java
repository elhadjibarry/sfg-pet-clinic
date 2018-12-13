package guru.springframework.sfgpetclinic.repositories;

import java.util.Set;
import guru.springframework.sfgpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

    Owner findByLastName(String lastName);

    Set<Owner> findAllByLastNameLike(String lastName);
}
