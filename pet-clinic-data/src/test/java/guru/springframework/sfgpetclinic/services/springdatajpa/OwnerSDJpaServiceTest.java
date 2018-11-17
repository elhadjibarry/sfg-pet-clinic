package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    final Long OWNER_ID = 1L;
    final String LAST_NAME = "Smith";
    Owner owner;

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    OwnerSDJpaService ownerService;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        owner.setId(OWNER_ID);
        owner.setLastName(LAST_NAME);
    }

    @Test
    void findByLastName() {

        when(ownerRepository.findByLastName(any())).thenReturn(owner);
        Owner smith = ownerService.findByLastName(LAST_NAME);

        assertEquals(LAST_NAME, smith.getLastName());
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));
        Owner ownerFound = ownerService.findById(OWNER_ID);
        assertNotNull(ownerFound);
        assertEquals(OWNER_ID, ownerFound.getId());
    }

    @Test
    void findByUnknownId() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner ownerFound = ownerService.findById(OWNER_ID);
        assertNull(ownerFound);
    }

    @Test
    void save() {

        when(ownerRepository.save(any())).thenReturn(owner);
        Owner savedOwner = ownerService.save(owner);

        assertNotNull(savedOwner);
        verify(ownerRepository).save(any());
    }

    @Test
    void findAll() {
        Set<Owner> owners = new HashSet<>();
        owners.add(owner);
        owners.add(Owner.builder().build());

        when(ownerRepository.findAll()).thenReturn(owners);
        Set<Owner> ownersFound = ownerService.findAll();

        assertNotNull(ownersFound);
        assertEquals(2, owners.size());
    }

    @Test
    void delete() {
        ownerService.delete(owner);
        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerService.deleteById(OWNER_ID);
        verify(ownerRepository).deleteById(anyLong());

    }
}