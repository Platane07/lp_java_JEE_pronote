package Projet.Model;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class GroupeDAO {

    public static Groupe create(String nom, List<Module> modules, List<Etudiant> etudiants) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        // create
        em.getTransaction().begin();

        // create new groupe
        Groupe groupe = new Groupe();
        groupe.setNom(nom);

        em.persist(groupe);

        for (Module module : modules) {
            groupe.addModule(module);
        }
        for (Etudiant etudiant : etudiants) {
            groupe.addEtudiant(etudiant);
        }

        em.merge(groupe);


        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return groupe;
    }

    public static Groupe delete(int id) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();
        //
        em.getTransaction().begin();

        Groupe groupe = em.find(Groupe.class, id);
        em.merge(groupe);
        em.remove(groupe);

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return groupe;
    }

    public static void deleteModule(int idModule, int idGroupe) {

        EntityManager em = GestionFactory.factory.createEntityManager();
        //
        em.getTransaction().begin();

        Module module = em.find(Module.class, idModule);
        Groupe groupe = em.find(Groupe.class, idGroupe);
        groupe.removeModule(module);

        em.merge(groupe);

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

    }

    public static void deleteEtudiant(int idEtudiant, int idGroupe) {

        EntityManager em = GestionFactory.factory.createEntityManager();
        //
        em.getTransaction().begin();


        //tentative de suppression dun etudiant dans un groupe, l'étudiant existe toujours hors du groupe, il pourra être assigné à un autre groupe
        try {
            Etudiant etudiant = em.find(Etudiant.class, idEtudiant);
            Groupe groupe = em.find(Groupe.class, idGroupe);
            em.merge(groupe);
            em.merge(etudiant);
            groupe.removeEtudiant(etudiant);

        } catch (Exception e) {
            System.out.println("Suppression de l'étudiant du groupe n'a pas abouti");
        }
        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

    }

    public static void addEtudiant(int idEtudiant, int idGroupe) {

        EntityManager em = GestionFactory.factory.createEntityManager();
        //
        em.getTransaction().begin();

        //ajoute un étudiant au groupe
        Etudiant etudiant = em.find(Etudiant.class, idEtudiant);
        Groupe groupe = em.find(Groupe.class, idGroupe);
        groupe.addEtudiant(etudiant);

        em.merge(groupe);
        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

    }

    public static void addModule(int idModule, int idGroupe) {

        EntityManager em = GestionFactory.factory.createEntityManager();
        //
        em.getTransaction().begin();

        //ajoute un module au groupe
        Module module = em.find(Module.class, idModule);
        Groupe groupe = em.find(Groupe.class, idGroupe);
        groupe.addModule(module);

        em.merge(groupe);
        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

    }


    public static List<Groupe> getAll() {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        // Récupération de tous les groupes à l'aide d'une requête sql
        Query q = em.createQuery("SELECT g FROM Groupe g ORDER BY g.id DESC");

        @SuppressWarnings("unchecked")
        List<Groupe> listGroupe = q.getResultList();

        return listGroupe;
    }

    public static Groupe getById(int id) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        // Recherche
        Query q = em.createQuery("SELECT g FROM Groupe g WHERE g.id = :id", Groupe.class);

        Groupe groupe = (Groupe) q.setParameter("id", id).getSingleResult();

        return groupe;
    }
}
