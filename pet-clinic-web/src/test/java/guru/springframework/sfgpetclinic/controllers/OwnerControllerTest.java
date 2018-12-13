package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController ownerController;

    Set<Owner> owners;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();

        Owner owner1 = new Owner();
        owner1.setId(1L);
        owners.add(owner1);

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owners.add(owner2);

        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void listOwners() throws Exception {
        when(ownerService.findAll()).thenReturn(owners);
        mockMvc.perform(get("/owners/index"))
               .andExpect(status().isOk())
               .andExpect(view().name("owners/index"))
               .andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    void listOwnersByIndex() throws Exception {
        when(ownerService.findAll()).thenReturn(owners);
        mockMvc.perform(get("/owners/index"))
               .andExpect(status().isOk())
               .andExpect(view().name("owners/index"))
               .andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
               .andExpect(status().isOk())
               .andExpect(view().name("owners/findOwners"))
               .andExpect(model().attributeExists("owner"));
        verifyZeroInteractions(ownerService);

    }

    @Test
    void processFindFormReturnMany() throws Exception {

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(owners);

        mockMvc.perform(get("/owners"))
               .andExpect(status().isOk())
               .andExpect(view().name("owners/ownersList"))
               .andExpect(model().attribute("selections", hasSize(2)));

    }

    @Test
    void processFindFormReturnOne() throws Exception {

        Owner owner1 = new Owner();
        owner1.setId(1L);
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(new HashSet<>(Arrays.asList(owner1)));

        mockMvc.perform(get("/owners"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/owners/1"));

    }

    @Test
    void displayOwner() throws Exception {

        Owner owner = new Owner();
        owner.setId(1L);
        when(ownerService.findById(anyLong())).thenReturn(owner);

        mockMvc.perform(get("/owners/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("owners/ownerDetails"))
               .andExpect(model().attribute("owner", hasProperty("id", is(1L))));

    }
}
