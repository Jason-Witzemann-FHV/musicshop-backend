package at.fhv.ae;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Test");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNativeQuery("CREATE TABLE Persons (\n" +
                    "    PersonID int,\n" +
                    "    LastName varchar(255),\n" +
                    "    FirstName varchar(255)\n" +
                    ");").executeUpdate();
            em.getTransaction().commit();
            System.out.println("commited");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            em.getTransaction().begin();
            em.createNativeQuery("INSERT INTO Persons values ('1', 'Tobias', 'Kurz'  )").executeUpdate();
            em.getTransaction().commit();
            System.out.println("commited");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
