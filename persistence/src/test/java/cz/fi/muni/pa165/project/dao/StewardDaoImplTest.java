package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.PersistenceTestsConfiguration;
import cz.fi.muni.pa165.project.dto.StewardFilterDTO;
import cz.fi.muni.pa165.project.entity.Flight;
import cz.fi.muni.pa165.project.entity.Steward;
import cz.fi.muni.pa165.project.exceptions.AirportManagerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Simon Kobyda
 * @created 10.04.2021
 * @project airport-manager
 **/
@SpringBootTest
@Transactional
@ContextConfiguration(classes = PersistenceTestsConfiguration.class)
class StewardDaoImplTest {

    @Autowired
    private StewardDao stewardDao;

    @PersistenceContext
    EntityManager em;

    private Steward stewardAnna;
    private Steward stewardEva;
    private Steward stewardEva2;
    private Steward stewardAbdur;
    private Steward stewardPocahontas;

    private Flight toDubai;
    private Flight toVienna;
    private Flight toPrague;

    @BeforeEach
    void setUp() {
        toDubai = createFlight("OS823");
        toVienna = createFlight("W6231");
        toPrague = createFlight("WZZ321");

        em.persist(toDubai);
        em.persist(toVienna);
        em.persist(toPrague);

        createStewards();
        em.persist(stewardAnna);
        em.persist(stewardEva);
        em.persist(stewardEva2);
        em.persist(stewardAbdur);
        em.persist(stewardPocahontas);

    }

    /**
     * Tests findAll method by checking if the number of stewards in list is correct
     * and if all stewards are present in the list.
     */
    @Test
    void findAll() {
        List<Steward> allStewards = stewardDao.findAll(null);
        assertEquals(5, allStewards.size());

        assertContainsSteward(allStewards, "Anna");
        assertContainsSteward(allStewards, "Eva");
        assertContainsSteward(allStewards, "Pocahontas");
        assertContainsSteward(allStewards, "Abdul");
    }

    @Test
    void findAllEva() {
        List<Steward> allEvaStewards = stewardDao.findAll(filter("Eva", null, null));

        assertEquals(2, allEvaStewards.size());
        assertThat(allEvaStewards).containsExactly(stewardEva, stewardEva2);
    }

    @Test
    void findSpecificEva() {
        List<Steward> specificEva = stewardDao.findAll(filter("Eva", "Mitchel", null));

        assertEquals(1, specificEva.size());
        assertThat(specificEva).containsExactly(stewardEva2);
    }

    /**
     * Tests delete method by checking if the removed steward is no longer in list of stewards and the list decreases by one
     */
    @Test
    void delete() {
        int originalSize = stewardDao.findAll(null).size();
        stewardDao.findById(stewardAnna.getId());
        stewardDao.delete(stewardAnna);
        assertEquals(originalSize - 1, stewardDao.findAll(null).size());
    }

    /**
     * Tests update method by checking if it's possible to change the last name of steward
     */
    @Test
    void update() {
        Long id = stewardAnna.getId();
        Steward tmpAnna = stewardDao.findById(id);
        String annaLastName = tmpAnna.getLastName();
        // she got married :)
        tmpAnna.setLastName("Petrova");
        stewardDao.update(tmpAnna);
        Steward newTmpAnna = stewardDao.findById(id);
        String newAnnaLastName = newTmpAnna.getLastName();
        assertNotEquals(annaLastName, newAnnaLastName);
    }

    /**
     * Tests findByFirstName by checking if it's possible to find the steward by name
     */
    @Test
    void findByFirstName() {
        Long id = stewardAnna.getId();
        List<Steward> list = stewardDao.findByFirstName("Anna");
        assertEquals(list.get(0).getId(), id);
    }

    /**
     * Tests findByLastName by checking if it's possible to find the steward by last name
     */
    @Test
    void findByLastName() {
        Long id = stewardEva.getId();
        List<Steward> list = stewardDao.findByLastName("Malakova");
        assertEquals(list.get(0).getId(), id);
    }

    /**
     * Tests findByCountryCode by checking if it's possible to find the steward by country code
     */
    @Test
    void findByCountryCode() {
        Long id = stewardEva.getId();
        List<Steward> list = stewardDao.findByCountryCode("CZ");
        assertEquals(list.get(0).getId(), id);
    }

    /**
     * Tests create by checking number of stewards created
     */
    @Test
    void create() {
        Steward testSteward = new Steward();
        testSteward.setPassportNumber("42");
        stewardDao.create(testSteward);
        assertEquals(6, stewardDao.findAll(null).size());
    }

    /**
     * Tests findByPassportNumber by checking if it's possible to find the steward by passport number
     */
    @Test
    void findByPassportNumber() {
        Long id = stewardAnna.getId();
        List<Steward> list = stewardDao.findByPassportNumber("EAP33855");
        assertEquals(list.get(0).getId(), id);
    }

    private void assertContainsSteward(List<Steward> stewards, String expectedFirstName) {
        for (Steward steward : stewards) {
            if (steward.getFirstName().equals(expectedFirstName))
                return;
        }

        Assertions.fail("Couldn't find first name" + expectedFirstName + " in list of stewards " + stewards);
    }

    private void createStewards() {
        stewardEva = createSteward(
                "CZ",
                "KA219932",
                "Eva",
                "Malakova"
        );

        stewardEva2 = createSteward(
                "CZ",
                "123456789",
                "Eva",
                "Mitchel"
        );

        stewardAnna = createSteward(
                "PL",
                "EAP33855",
                "Anna",
                "Czeczesova"
        );

        stewardAbdur = createSteward(
                "AE",
                "A43GFSE",
                "Abdul",
                "Mehmed"

        );

        stewardPocahontas = createSteward(
                "US",
                "EAP33855",
                "Pocahontas",
                "QuietEagle"
        );
    }

    private Steward createSteward(
            String countryCode,
            String passportNumber,
            String firstName,
            String lastName) {
        Steward steward = new Steward();
        steward.setCountryCode(countryCode);
        steward.setPassportNumber(passportNumber);
        steward.setFirstName(firstName);
        steward.setLastName(lastName);

        return steward;
    }

    private Flight createFlight(String flightCode) {
        Flight flight = new Flight();
        flight.setFlightCode(flightCode);

        return flight;
    }

    private StewardFilterDTO filter(String firstName, String lastName, String countryCode) {
        StewardFilterDTO filter = new StewardFilterDTO();
        filter.setFirstName(firstName);
        filter.setLastName(lastName);
        filter.setCountryCode(countryCode);
        return filter;
    }
}