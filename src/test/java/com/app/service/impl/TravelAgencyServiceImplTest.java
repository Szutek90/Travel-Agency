package com.app.service.impl;

import com.app.dto.travel_agency.CreateTravelAgencyDto;
import com.app.model.agency.TravelAgency;
import com.app.repository.TravelAgencyRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TravelAgencyServiceImplTest {
    static TravelAgency travelAgency;

    @BeforeAll
    static void beforeAll(){
        travelAgency = new TravelAgency(1,"Best Agency", "Warsaw", "123987456");
    }

    @Mock
    private TravelAgencyRepository repository;

    @InjectMocks
    private TravelAgencyServiceImpl service;

    @Test
    @DisplayName("When getting by id")
    void test1(){
        when(repository.findById(anyInt())).thenReturn(Optional.of(travelAgency));
        assertThat(service.getTravelAgencyById(1)).isEqualTo(travelAgency.toGetTravelAgencyDto());
    }

    @Test
    @DisplayName("When getting by id is false")
    void test2(){
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getTravelAgencyById(1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("There is no Travel Agency with given id");
    }

    @Test
    @DisplayName("When getting by name")
    void test3(){
        when(repository.findByName("Best Agency")).thenReturn(Optional.of(travelAgency));
        assertThat(service.getTravelAgencyByName("Best Agency"))
                .isEqualTo(travelAgency.toGetTravelAgencyDto());
    }

    @Test
    @DisplayName("When getting by name is false")
    void test4(){
        when(repository.findByName("just agency")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getTravelAgencyByName("Best Agency"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("There is no Travel Agency with given id");
    }

    @Test
    @DisplayName("When getting agencies by city")
    void test5(){
        when(repository.findByCity("Warsaw")).thenReturn(List.of(travelAgency));
        assertThat(service.getAllTravelAgenciesByCity("Warsaw"))
                .isEqualTo(List.of(travelAgency.toGetTravelAgencyDto()));
    }

    @Test
    @DisplayName("When adding new travel agency")
    void test6(){
        var expected = new TravelAgency(2,"Agency2", "London", "987654852");
        var travelAgencyDto = new CreateTravelAgencyDto("Agency2", "London", "987654852");
        when(repository.findByName(anyString())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(2);
        when(repository.getAll()).thenReturn(List.of(travelAgency));
        assertThat(service.addTravelAgency(travelAgencyDto)).isEqualTo(expected.toGetTravelAgencyDto());
    }

    @Test
    @DisplayName("When agency is present during adding new travel agency")
    void test7(){
        var travelAgencyDto = new CreateTravelAgencyDto("Agency2", "London", "987654852");
        when(repository.findByName(anyString())).thenReturn(Optional.of(travelAgency));
        assertThatThrownBy(() -> service.addTravelAgency(travelAgencyDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("There is already a Travel Agency with given name");
    }

    @Test
    @DisplayName("When getting all travel agencies")
    void test8(){
        when(repository.getAll()).thenReturn(List.of(travelAgency));
        assertThat(service.getAllTravelAgency()).isEqualTo(List.of(travelAgency.toGetTravelAgencyDto()));
    }
}
