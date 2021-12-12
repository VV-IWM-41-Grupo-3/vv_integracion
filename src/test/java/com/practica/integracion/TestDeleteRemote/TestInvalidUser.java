package com.practica.integracion.TestDeleteRemote;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import com.practica.integracion.manager.SystemManagerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.OperationNotSupportedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestInvalidUser {
	@Mock
	GenericDAO mockGenericDao;
	@Mock
	AuthDAO mockAuthDao;

	@Test
	public void testDeleteUserInvalidGood() throws OperationNotSupportedException {
		User invalidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		//lenient().when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);
		//Por la implementación del método, esto no se utiliza ya que no hace la llamada
		//Al metodo de verificacion
		String validId = "12345";
		lenient().when(mockGenericDao.deleteSomeData(null, validId)).thenThrow(OperationNotSupportedException.class);
		InOrder ordered = inOrder(mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		Assertions.assertThrows(SystemManagerException.class, () ->{
			manager.deleteRemoteSystem(invalidUser.getId(), validId);
		});
		//ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
		//Lo mismo pasa con esta linea
		ordered.verify(mockGenericDao).deleteSomeData(null, validId);
	}
	@Test
	public void testDeleteUserInvalidBad() throws OperationNotSupportedException {
		User invalidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		//lenient().when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);
		//Por la implementación del método, esto no se utiliza ya que no hace la llamada
		//Al metodo de verificacion
		String validId = "12345";
		lenient().when(mockGenericDao.deleteSomeData(null, validId)).thenThrow(OperationNotSupportedException.class);
		InOrder ordered = inOrder(mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		Assertions.assertThrows(SystemManagerException.class, () ->{
			manager.deleteRemoteSystem(invalidUser.getId(), validId);
		});
		//ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
		//Lo mismo pasa con esta linea
		ordered.verify(mockGenericDao).deleteSomeData(null, validId);
	}

}
