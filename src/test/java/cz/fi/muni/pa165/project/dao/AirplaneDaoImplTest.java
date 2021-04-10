package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.AirportManagerApplication;
import cz.fi.muni.pa165.project.entity.Airplane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jozef Vanický
 * @created 10.04.2021
 * @project airport-manager
 **/

@SpringBootTest(classes = AirportManagerApplication.class)
@Transactional
class AirplaneDaoImplTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    private AirplaneDaoImpl airplaneDao;

    private Airplane airplane1;
    private Airplane airplane2;
    private Airplane airplane3;
    private Airplane airplane4;

    private Airplane createAirplane(String airplaneName, Integer capacity, String airplaneType) {
        Airplane airplane = new Airplane();
        airplane.setName(airplaneName);
        airplane.setCapacity(capacity);
        airplane.setType(airplaneType);
        return airplane;
    }


    @BeforeEach
    void setUp() {
        airplane1 = createAirplane("RandomAirplaneName1", 100, "AirplaneType1");
        airplane2 = createAirplane("RandomAirplaneName2", 892, "AirplaneType2");
        airplane3 = createAirplane("RandomAirplaneName10", 2, "AirplaneType111");
        airplane4 = createAirplane("RandomAirplaneName10", 892, "AirplaneType2");

        em.persist(airplane1);
        em.persist(airplane2);
        em.persist(airplane3);
        em.persist(airplane4);

    }

    @Test
    void findAll() {
        List<Airplane> found = airplaneDao.findAll();
        assertEquals(found.size(), 4);
    }

    @Test
    void findById() {
        Airplane found = airplaneDao.findById(airplane1.getId());
        assertEquals(found.getName(), "RandomAirplaneName1");
        assertEquals(found.getCapacity(), 100);
        assertEquals(found.getType(), "AirplaneType1");
    }

    @Test
    void remove() {
        assertNotNull(airplaneDao.findById(airplane3.getId()));
        airplaneDao.remove(airplane3);
        assertNull(airplaneDao.findById(airplane3.getId()));
    }

    @Test
    void updateName() {
        Airplane found = airplaneDao.findById(airplane1.getId());
        assertEquals(found.getName(), "RandomAirplaneName1");
        found.setName("NewAirplaneName");
        assertNotEquals(found.getName(), "RandomAirplaneName1");
        assertEquals(found.getName(), "NewAirplaneName");
    }

    @Test
    void updateCapacity() {
        Airplane found = airplaneDao.findById(airplane1.getId());

        assertEquals(found.getCapacity(), 100);
        found.setCapacity(120);
        assertNotEquals(found.getCapacity(), 100);
        assertEquals(found.getCapacity(), 120);
    }

    @Test
    void updateType() {
        Airplane found = airplaneDao.findById(airplane1.getId());

        assertEquals(found.getType(), "AirplaneType1");
        found.setType("AirplaneNewType");
        assertNotEquals(found.getType(), "AirplaneType1");
        assertEquals(found.getType(), "AirplaneNewType");
    }

    @Test
    void findByName() {
        assertEquals(airplaneDao.findByName("RandomAirplaneName1").size(), 1);
        assertEquals(airplaneDao.findByName("UnknownAirplane").size(), 0);
    }

    @Test
    void findByType() {
        assertEquals(airplaneDao.findByType("AirplaneType1").size(), 1);
        assertEquals(airplaneDao.findByType("UnknownType").size(), 0);
    }

    @Test
    void findByCapacity() {
        assertEquals(airplaneDao.findByCapacity(2).size(),1);
        assertEquals(airplaneDao.findByCapacity(0).size(),0);
        assertEquals(airplaneDao.findByCapacity(892).size(),2);
    }
}