package io.robusta.jpa.demo;

import java.util.List;

import javax.persistence.EntityManager;

import io.robusta.fora.EmFactory;
import io.robusta.jpa.demo.entities.FunkoPop;
import io.robusta.jpa.demo.entities.Universe;

public class JpaApplication {

	public static void main(String[] args) {

		// We start
		EntityManager em = EmFactory.createEntityManager();
		em.getTransaction().begin();
		System.out.println("  ========== STARTING WORK ======= ");

		Universe lotr = new Universe("LOTR");
		Universe starTrek = new Universe("Star Trek");
		Universe starWars = new Universe("Star Wars");
		
		
		FunkoPop gandalf = new FunkoPop("Gandalf", lotr);
		gandalf.setWaterproof(true);

		FunkoPop aragorn = new FunkoPop("Aragorn", lotr);
			aragorn.setWaterproof(true);
			
		FunkoPop yoda = new FunkoPop("Yoda", starWars);
		yoda.setWaterproof(true);

		em.persist(gandalf);
		em.persist(aragorn);
		em.persist(lotr);
		em.persist(starWars);
		em.persist(starTrek);
		em.persist(yoda);

		/*int idGandalf = gandalf.getId();
		System.out.println("id of Gandalf :" + idGandalf);
		FunkoPop gandalfFetched = em.find(FunkoPop.class, idGandalf);
		System.out.println("Gandalf fetched : " + gandalfFetched.getName());*/

		System.out.println("  ========== COMMIT ======= ");
		em.getTransaction().commit();
		em.close(); // on doit le fermer comme on ne travaille aps avec un
					// serveur qui le fait automatiquement

		System.out.println("  ========== NEW QUERY ======= ");

		EmFactory.transaction(e -> {

			String query = "SELECT f FROM FunkoPop f WHERE f.universe = :universe ";

			List<FunkoPop> list = e.
					createQuery(query, FunkoPop.class).
					setParameter("universe", lotr).
					getResultList();

			System.out.println(list);

		});

		System.out.println(">>>>>>");

		/*
		 * EmFactory.transaction(e ->{
		 * 
		 * String query =" SELECT c.products FROM Caddie c " +
		 * "JOIN c.products prods " + "WHERE c.id = 1 AND prods.price > :price";
		 * 
		 * 
		 * List<Collection> result = e.createQuery(query, Collection.class )
		 * .setParameter("price", 2f) .getResultList();
		 * System.out.println(">>>>>result"); System.out.println(result);
		 * 
		 * });
		 */
		EmFactory.getInstance().close();

	}
}
