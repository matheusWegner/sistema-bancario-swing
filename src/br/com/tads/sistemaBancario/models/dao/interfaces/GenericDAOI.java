package br.com.tads.sistemaBancario.models.dao.interfaces;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface GenericDAOI<E,Type extends Serializable> {
	public boolean save(E entity) throws SQLException ,IOException;
	public boolean update(E entity) throws SQLException ,IOException;
	public E findById(Type id) throws SQLException ,IOException;
	public List<E> findAll() throws SQLException ,IOException;
	public boolean delete(Type id) throws SQLException ,IOException;
}
