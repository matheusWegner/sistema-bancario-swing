package br.com.tads.sistemaBancario.models.dao.interfaces;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface GenericDAOI<E,Type extends Serializable> {
	public boolean save(E entity) throws SQLException;
	public boolean update(E entity) throws SQLException;
	public E findById(Type id) throws SQLException;
	public List<E> findAll() throws SQLException;
	public boolean delete(Type id) throws SQLException;
}
