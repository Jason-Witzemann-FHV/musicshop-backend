package at.fhv.ae;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory em = Persistence.createEntityManagerFactory("test");
        EntityManager eManager = em.createEntityManager();

        try {
            eManager.getTransaction().begin();
            eManager.createNativeQuery("CREATE TABLE Persons (\n" +
                    "    PersonID int,\n" +
                    "    LastName varchar(255),\n" +
                    "    FirstName varchar(255),\n" +
                    ");");
            eManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
