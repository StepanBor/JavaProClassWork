package ua.kiev.prog.tests;

import org.junit.Test;
import ua.kiev.prog.one2many.Address;
import ua.kiev.prog.one2many.Client;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class OneToManyTest extends BaseTest {
    private Long saveTestClientWithAddresses(
            String name, int age, String country, String city, String street) {
        final Client client = new Client(name, age);
        final Address address1 = new Address(country, city, street);
        final Address address2 = new Address(country + "1", city + "1", street + "1");

        client.addAddress(address1);
        client.addAddress(address2);

        return performTransaction(new Callable<Long>() {
            public Long call() throws Exception {
                em.persist(client);
                return client.getId();
            }
        });
    }

    @Test
    public void testPersist() {
        long id = saveTestClientWithAddresses("Vsevolod", 31,
                "Ukraine", "Kyiv", "Some street");

        em.clear();

        Client otherClient = em.find(Client.class, id);
        assertNotNull(otherClient);

        List<Address> addrs = otherClient.getAddresses();
        assertEquals(2, addrs.size());
    }

    @Test
    public void testRemoveClient() {
        long id = saveTestClientWithAddresses("Aleksandr", 35,
                "Ukraine", "Lviv", "Any street");

        em.clear();

        final Client client = em.getReference(Client.class, id);
        assertNotNull(client);

        performTransaction(new Callable<Void>() {
            public Void call() throws Exception {
                em.remove(client);
                return null;
            }
        });
    }

    @Test
    public void testRemoveAddress() {
        long id = saveTestClientWithAddresses("Anybody", 50,
                "Ukraine", "Kyiv", "Some street 45");

        em.clear();

        final Client otherClient = em.find(Client.class, id);
        assertNotNull(otherClient);

        final List<Address> addrs = otherClient.getAddresses();
        assertEquals(2, addrs.size());

        performTransaction(new Callable<Void>() {
            public Void call() throws Exception {
                Address a1 = otherClient.getAddress(0);
                Address a2 = otherClient.getAddress(1);

                otherClient.clearAddresses();
                em.merge(otherClient);

                em.remove(a1);
                em.remove(a2);
                return null;
            }
        });

        Client otherClient2 = em.find(Client.class, id);
        assertNotNull(otherClient2);

        List<Address> addrs2 = otherClient2.getAddresses();
        assertEquals(0, addrs2.size());
    }
}
