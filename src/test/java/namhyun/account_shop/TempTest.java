package namhyun.account_shop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TempTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void test1() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query nativeQuery = em.createNativeQuery("select now() from dual", String.class);
        Object singleResult = nativeQuery.getSingleResult();
        System.out.println("singleResult.toString() = " + singleResult.toString());

        tx.commit();
    }
}
