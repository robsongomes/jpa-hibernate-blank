package com.robsonaraujo.jhb;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import com.robsonaraujo.jhb.entity.Test;
import com.robsonaraujo.jhb.entity.TestFilha;

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
		
		// NOVA TRANSAÇÃO
		
		entityManager.getTransaction().begin();
		
		Test resultAttached = entityManager.find(Test.class, t.getId());
		
		TestFilha filha = new TestFilha();
		filha.setName("Filha 01");
		filha.setParent(resultAttached);
		
		resultAttached.setFilhas(new ArrayList<TestFilha>());
		resultAttached.getFilhas().add(filha);
		
		Test newTest = entityManager.merge(resultAttached);
		
		entityManager.getTransaction().commit();		

		entityManager.getTransaction().begin();
		
		newTest.getFilhas().iterator().next().setName("Novo nome");
		
		entityManager.merge(newTest);		
		
		entityManager.getTransaction().commit();

		// FECHA TUDO
		entityManager.close();
		JPAUtil.shutdown();
	}
}