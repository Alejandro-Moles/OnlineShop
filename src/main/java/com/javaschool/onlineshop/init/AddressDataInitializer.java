package com.javaschool.onlineshop.init;

import com.javaschool.onlineshop.models.CityModel;
import com.javaschool.onlineshop.models.CountryModel;
import com.javaschool.onlineshop.models.PostalCodeModel;
import com.javaschool.onlineshop.repositories.CityRepository;
import com.javaschool.onlineshop.repositories.CountryRepository;
import com.javaschool.onlineshop.repositories.PostalCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AddressDataInitializer implements CommandLineRunner {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final PostalCodeRepository postalCodeRepository;

    @Override
    public void run(String... args) throws Exception {
        insertCountriesAndCities();
    }

    private void insertCountriesAndCities() {
        if (countryRepository.count() == 0) {

            CountryModel countrySpain = new CountryModel();
            countrySpain.setCountryUuid(UUID.randomUUID());
            countrySpain.setName("Spain");
            countrySpain.setDeleted(false);
            countryRepository.save(countrySpain);
            insertCitiesForCountry(countrySpain, "Barcelona", "Valencia", "Seville");

            CountryModel countryGermany = new CountryModel();
            countryGermany.setCountryUuid(UUID.randomUUID());
            countryGermany.setName("Germany");
            countryGermany.setDeleted(false);
            countryRepository.save(countryGermany);
            insertCitiesForCountry(countryGermany, "Berlin", "Munich", "Hamburg");

            CountryModel countryFrance = new CountryModel();
            countryFrance.setCountryUuid(UUID.randomUUID());
            countryFrance.setName("France");
            countryFrance.setDeleted(false);
            countryRepository.save(countryFrance);
            insertCitiesForCountry(countryFrance, "Paris", "Marseille", "Lyon");

            CountryModel countryUK = new CountryModel();
            countryUK.setCountryUuid(UUID.randomUUID());
            countryUK.setName("United Kingdom");
            countryUK.setDeleted(false);
            countryRepository.save(countryUK);
            insertCitiesForCountry(countryUK, "London", "Manchester", "Birmingham");
        }
    }

    private void insertCitiesForCountry(CountryModel country, String city1, String city2, String city3) {
        CityModel city1Model = new CityModel();
        city1Model.setCityUuid(UUID.randomUUID());
        city1Model.setCountry(country);
        city1Model.setName(city1);
        city1Model.setDeleted(false);
        cityRepository.save(city1Model);
        insertPostalCodesForCity(city1Model, "08001", "08002", "08003");

        CityModel city2Model = new CityModel();
        city2Model.setCityUuid(UUID.randomUUID());
        city2Model.setCountry(country);
        city2Model.setName(city2);
        city2Model.setDeleted(false);
        cityRepository.save(city2Model);
        insertPostalCodesForCity(city2Model, "46001", "46002", "46003");

        CityModel city3Model = new CityModel();
        city3Model.setCityUuid(UUID.randomUUID());
        city3Model.setCountry(country);
        city3Model.setName(city3);
        city3Model.setDeleted(false);
        cityRepository.save(city3Model);
        insertPostalCodesForCity(city3Model, "41001", "41002", "41003");
    }

    private void insertPostalCodesForCity(CityModel city, String postalCode1, String postalCode2, String postalCode3) {
        insertPostalCode(city, postalCode1);
        insertPostalCode(city, postalCode2);
        insertPostalCode(city, postalCode3);
    }

    private void insertPostalCode(CityModel city, String content) {
        if(!postalCodeRepository.existsByContent(content)) {
            PostalCodeModel postalCodeModel = new PostalCodeModel();
            postalCodeModel.setPostalCodeUuid(UUID.randomUUID());
            postalCodeModel.setCity(city);
            postalCodeModel.setContent(content);
            postalCodeModel.setDeleted(false);
            postalCodeRepository.save(postalCodeModel);
        }
    }
}
