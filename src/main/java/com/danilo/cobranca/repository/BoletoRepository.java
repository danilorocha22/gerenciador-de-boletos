package com.danilo.cobranca.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.danilo.cobranca.model.Boleto;

@Repository
@Transactional
public class BoletoRepository {
	
	@PersistenceContext
	EntityManager em;

	public void save(Boleto boleto) {
		em.persist(boleto);
	}//save

	public void delete(Boleto boleto) {
		em.remove(boleto);
	}//delete

	public Boleto findById(Long id) {
		return em.find(Boleto.class, id);
	}//find
	
	public List<Boleto> findByDescricaoContaining(String descricao) {
		String hql = "from Boleto b where b.descricao like :descricao order by b.id asc";
		TypedQuery<Boleto> query = em.createQuery(hql, Boleto.class);
		query.setParameter("descricao", "%"+descricao	+"%");
		return query.getResultList();
	}

	public Object findAll() {
		Query query = em.createQuery("from Boleto b order by b.id asc");
		return query.getResultList();
	}
	
	public void update(Boleto boleto) {
		em.merge(boleto);
	}

}
