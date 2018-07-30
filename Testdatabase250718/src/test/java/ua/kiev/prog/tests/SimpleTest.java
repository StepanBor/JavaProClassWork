package ua.kiev.prog.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.kiev.prog.simple.Client;
import ua.kiev.prog.simple.CustomClient;

import javax.persistence.*;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;

import static org.junit.Assert.*;

public class SimpleTest extends BaseTest {

    private Client saveTestClient(final String name, final Integer age) {
        return performTransaction(new Callable<Client>() {
            public Client call() throws Exception {
                Client client = new Client(name, age);
                em.persist(client);
                return client;
            }
        });
    }

    @Test
    public void testPersistAndFind() {
        Client client = saveTestClient("Nikolay", 20);

        long id = client.getId();
        assertTrue(id > 0);

        // find existing
        Client other = em.find(Client.class, id);
        assertNotNull(other);
        assertEquals(client.getName(), other.getName());
        assertEquals(client.getAge(), other.getAge());

        // clear context
        em.clear();

        // entity was already loaded by find()
        other = em.getReference(Client.class, id);
        assertNotNull(other);
        assertEquals(client.getName(), other.getName());
        assertEquals(client.getAge(), other.getAge());
    }

    @Test(expected = RuntimeException.class)
    public void testNullable() {
        saveTestClient("Nikolay", null);
    }

    @Test
    public void testMerge() {
        final Client client = saveTestClient("Ivan", 10);
        long id = client.getId();

        performTransaction(new Callable<Void>() {
            public Void call() throws Exception {
                //client.setId(50);
                client.setAge(50);
                em.merge(client);
                return null;
            }
        });

        em.clear();

        Client other = em.find(Client.class, id);
        assertEquals(50, other.getAge());
    }

    @Test
    public void testRemove() {
        final Client client = saveTestClient("Ivan", 10);
        final long id = client.getId();

        performTransaction(new Callable<Void>() {
            public Void call() throws Exception {
                Client other = em.getReference(Client.class, id);
                em.remove(other);
                return null;
            }
        });

        Client another = em.find(Client.class, id);
        assertNull(another);
    }

    @Test
    public void testSelect() {
        performTransaction(new Callable<Void>() {
            public Void call() throws Exception {
                for (int i = 0; i < 20; i++) {
                    Client client = new Client("Name" + i, 100 + i);
                    em.persist(client);
                }
                return null;
            }
        });

        List<Client> resultList;

        Query query = em.createQuery("SELECT c FROM Client c WHERE c.age >= :age");
        query.setParameter("age", 100);
        resultList = (List<Client>) query.getResultList(); // type cast!!!
        assertEquals(20, resultList.size());

        TypedQuery<Client> otherQuery = em.createQuery("SELECT c FROM Client c WHERE c.age >= :age", Client.class);
        otherQuery.setParameter("age", 100);
        resultList = otherQuery.getResultList(); // no type cast
        assertEquals(20, resultList.size());

        TypedQuery<Long> countQuery = em.createQuery("SELECT COUNT(c) FROM Client c WHERE c.age >= :age", Long.class);
        countQuery.setParameter("age", 100);
        long count = countQuery.getSingleResult();
        assertEquals(20, count);

        // select properties
        TypedQuery<CustomClient> propQuery = em
                .createQuery("SELECT NEW ua.kiev.prog.simple.CustomClient(c.name, c.age) FROM Client c WHERE c.id = 1",
                        CustomClient.class);
        CustomClient res = propQuery.getSingleResult();
        assertNotNull(res);

        Query propQuery2 = em
                .createQuery("SELECT c.name, c.age FROM Client c WHERE c.id = 1");
        Object[] res2 = (Object[]) propQuery2.getSingleResult();

        assertEquals(2, res2.length);
        assertTrue(res2[0] instanceof String);
        assertTrue(res2[1] instanceof Integer);
    }

    @Test
    public void testSelectWithLimit() {
        performTransaction(new Callable<Void>() {
            public Void call() throws Exception {
                for (int i = 0; i < 100; i++) {
                    Client client = new Client("Anybody" + i, 100 + i);
                    em.persist(client);
                }
                return null;
            }
        });

        TypedQuery<Client> query = em.createNamedQuery("selectByNameLike", Client.class);
        query.setParameter("pattern", "%body%");
        query.setFirstResult(5);
        query.setMaxResults(20);

        List<Client> result = query.getResultList();
        assertEquals(20, result.size());

        Client client = result.get(0);
        assertTrue(client.getName().startsWith("Anybody"));
        assertEquals(105, client.getAge());
    }

    private void performWrongSelect(int age) {
        performTransaction(new Callable<Void>() {
            public Void call() throws Exception {
                for (int i = 0; i < 5; i++) {
                    Client client = new Client("Hello" + i, 200 + i);
                    em.persist(client);
                }
                return null;
            }
        });

        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.age > :age", Client.class);
        query.setParameter("age", age);
        Client client = query.getSingleResult();
    }

    @Test(expected = NonUniqueResultException.class)
    public void testWrongCount1() {
        performWrongSelect(200);
    }

    @Test(expected = NoResultException.class)
    public void testWrongCount2() {
        performWrongSelect(1000);
    }
}
