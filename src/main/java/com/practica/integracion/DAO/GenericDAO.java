package com.practica.integracion.DAO;

import java.util.Collection;

import javax.naming.OperationNotSupportedException;

public interface GenericDAO {

	/**
	 * Obtiene datos filtrados
	 * 
	 * @param auth informacion de autenticación para roles
	 * @param filter filtro para la información. Si filter es null se devuelven todos los datos 
	 * @return Una colección de objetos obtenidos
	 * @throws OperationNotSupportedException si no se puede realizar la operacion por 
	 * permisos
	 */
	public Collection<Object> getSomeData(User auth, Object filter) throws OperationNotSupportedException;
	
	/**
	 * Actualiza un dato o lo añade si no existiera
	 * 
	 * @param auth informacion de autenticación para roles
	 * @param data dato a actualizar o añadir
	 * @return true si todo fue bien, false si no se pudo actualizar
	 * @throws OperationNotSupportedException si no se puede realizar la operacion por 
	 *
	 */
	public boolean updateSomeData(User auth, Object data) throws OperationNotSupportedException;
	
	/**
	 * Elimina un dato
	 * 
	 * @param auth informacion de autenticación para roles
	 * @param data dato a borrar
	 * @return true si todo fue bien, false si no se pudo eliminar
	 * @throws OperationNotSupportedException si no se puede realizar la operacion por 
	 *
	 */
	public boolean deleteSomeData(User auth, String id) throws OperationNotSupportedException;
}
