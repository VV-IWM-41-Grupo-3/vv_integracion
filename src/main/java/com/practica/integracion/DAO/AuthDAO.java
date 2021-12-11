package com.practica.integracion.DAO;

public interface AuthDAO {

	/**
	 * Obtiene la información de autenticación
	 * 
	 * @param userId id del usuario
	 * @return información de roles de usuario, o null si no se encontrara
	 */
	public User getAuthData(String userId);
}
