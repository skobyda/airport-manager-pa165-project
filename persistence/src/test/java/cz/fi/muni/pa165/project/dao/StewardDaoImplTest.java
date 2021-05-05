package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.AirportManagerApplication;
import cz.fi.muni.pa165.project.entity.Flight;
import cz.fi.muni.pa165.project.entity.Steward;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Simon Kobyda
 * @created 10.04.2021
 * @project airport-manager
 **/
@SpringBootTest(classes = AirportManagerApplication.class)
@Transactional
class StewardDaoImplTest {

    @Autowired
    private StewardDaoImpl StewardDao;

    @Autowired
    private FlightDaoImpl flightDao;

    private Steward stewardAnna;
    private Steward stewardEva;
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

        flightDao.create(toDubai);
        flightDao.create(toVienna);
        flightDao.create(toPrague);

        createStewards();
        StewardDao.create(stewardAnna);
        StewardDao.create(stewardEva);
        StewardDao.create(stewardAbdur);
        StewardDao.create(stewardPocahontas);

    }

    /**
     * Tests findAll method by checking if the number of stewards in list is correct
     * and if all stewards are present in the list.
     */
    @Test
    void findAll() {
        List<Steward> allStewards = StewardDao.findAll();
        assertEquals(4, allStewards.size());

        assertContainsSteward(allStewards, "Anna");
        assertContainsSteward(allStewards, "Eva");
        assertContainsSteward(allStewards, "Pocahontas");
        assertContainsSteward(allStewards, "Abdul");
    }

    /**
     * Tests delete method by checking if the removed steward is no longer in list of stewards and the list decreases by one
     */
    @Test
    void delete() {
        int originalSize = StewardDao.findAll().size();
        StewardDao.findById(stewardAnna.getId());
        StewardDao.delete(stewardAnna);
        assertNull(StewardDao.findById(stewardAnna.getId()));
        assertEquals( originalSize - 1, StewardDao.findAll().size());
    }

    /**
     * Tests update method by checking if it's possible to change the last name of steward
     */
    @Test
    void update() {
        Long id = stewardAnna.getId();
        Steward tmpAnna = StewardDao.findById(id);
        String annaLastName = tmpAnna.getLastName();
        // she got married :)
        tmpAnna.setLastName("Petrova");
        StewardDao.update(tmpAnna);
        Steward newTmpAnna = StewardDao.findById(id);
        String newAnnaLastName = newTmpAnna.getLastName();
        assertNotEquals(annaLastName, newAnnaLastName);
    }

    /**
     * Tests findByFirstName by checking if it's possible to find the steward by name
     */
    @Test
    void findByFirstName() {
        Long id = stewardAnna.getId();
        List<Steward> list = StewardDao.findByFirstName("Anna");
        assertEquals(list.get(0).getId(), id);
    }

    /**
     * Tests findByLastName by checking if it's possible to find the steward by last name
     */
    @Test
    void findByLastName() {
        Long id = stewardEva.getId();
        List<Steward> list = StewardDao.findByLastName("Malakova");
        assertEquals(list.get(0).getId(), id);
    }

    /**
     * Tests findByCountryCode by checking if it's possible to find the steward by country code
     */
    @Test
    void findByCountryCode() {
        Long id = stewardEva.getId();
        List<Steward> list = StewardDao.findByCountryCode("CZ");
        assertEquals(list.get(0).getId(), id);
    }

    /**
     * Tests findByPassportNumber by checking if it's possible to find the steward by passport number
     */
    @Test
    void findByPassportNumber() {
        Long id = stewardAnna.getId();
        List<Steward> list = StewardDao.findByPassportNumber("EAP33855");
        assertEquals(list.get(0).getId(), id);
    }

    private void assertContainsSteward(List<Steward> stewards, String expectedFirstName) {
        for(Steward steward: stewards){
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
}