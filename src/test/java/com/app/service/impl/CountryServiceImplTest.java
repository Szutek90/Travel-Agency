package com.app.service.impl;


import com.app.dto.CountryDto;
import com.app.repository.CountryRepository;
import com.app.repository.impl.CountryRepositoryImpl;
import com.app.service.impl.extension.DatabaseExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DatabaseExtension.class)
 class CountryServiceImplTest {
    static CountryRepository repository;

    @BeforeAll
    static void beforeAll(){
        repository = new CountryRepositoryImpl(DatabaseExtension.jdbi);
    }

    @Test
    @DisplayName("When adding country")
    void test1(){
        var countryDto = new CountryDto("Czechy");
    }
}
