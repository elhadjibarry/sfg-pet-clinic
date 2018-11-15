package guru.springframework.sfgpetclinic.services.map;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import guru.springframework.sfgpetclinic.model.Owner;

/**
 *
 */
class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    final Long ownerId = 1L;
    final String lastName = "Smith";

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setLastName(lastName);
        ownerMapService.save(owner);
    }

    @Test
    void findByLastName() {
        Owner smith = ownerMapService.findByLastName(lastName);
        assertNotNull(smith);
        assertEquals(ownerId, smith.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Owner owner = ownerMapService.findByLastName("foo");
        assertNull(owner);
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(ownerId);
        assertEquals(ownerId, owner.getId());
    }

    @Test
    void saveExistingId() {
        Long id = 2L;
        Owner owner = new Owner();
        owner.setId(id);
        Owner savedOwner = ownerMapService.save(owner);
        assertEquals(id, owner.getId());
    }

    @Test
    void saveNonExistingId() {
        Owner owner = new Owner();
        Owner savedOwner = ownerMapService.save(owner);
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerMapService.findAll();
        assertEquals(1, owners.size() );
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId);
        Set<Owner> owners = ownerMapService.findAll();
        assertEquals(0, owners.size() );
    }

    @Test
    void delete() {
        Owner owner = ownerMapService.findById(ownerId);
        ownerMapService.delete(owner);

        Set<Owner> owners = ownerMapService.findAll();
        assertEquals(0, owners.size() );
    }
}
