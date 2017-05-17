package com.robsonaraujo.jhb;
import javax.persistence.EntityManager;

import com.robsonaraujo.jhb.entity.Test;

public class MainApp {
	
	public static void main(String[] args) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();
		
		Test t = new Test();
		t.setName("Robson");
		
		entityManager.persist(t);

		
		Test result = entityManager.find(Test.class, t.getId());
		
		System.out.println(result.getName());

		entityManager.getTransaction().commit();
		entityManager.close();

		JPAUtil.shutdown();
	}
}