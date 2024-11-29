package br.com.tads.sistemaBancario.models.dao.interfaces;

import java.io.Serializable;
import java.util.List;

public interface GenericDAOI<E,Type extends Serializable> {
	public boolean save(E entity) throws Exception;
	public boolean update(E entity) throws Exception;
	public E findById(Type id) throws Exception;
	public List<E> findAll() throws Exception;
	public boolean delete(Type id) throws Exception;
}
