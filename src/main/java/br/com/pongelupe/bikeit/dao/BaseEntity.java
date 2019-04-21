package br.com.pongelupe.bikeit.dao;

/**
 * This interface defines base operations for entities
 * 
 * @author pongelupe
 *
 */
public interface BaseEntity<T> {

	T getId();

	void setId(T id);
}
