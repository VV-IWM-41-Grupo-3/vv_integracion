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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestValidUser {
	@Mock
	private GenericDAO mockGenericDao;
	@Mock
	AuthDAO mockAuthDao;

	@Test
	public void testDeleteUserValidGood() throws OperationNotSupportedException, SystemManagerException {
		final User validUser = new User("1", "Antonio", "Perez", "Madrid", new ArrayList<Object>());
		lenient().when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
		lenient().when(mockGenericDao.deleteSomeData(validUser, "1")).thenReturn(true);
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		String validId = "1";
		manager.deleteRemoteSystem(validUser.getId(), validId);
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).deleteSomeData(validUser, "1");
	}

	@Test
	public void testDeleteUserValidBad() throws OperationNotSupportedException {
		final User validUser = new User("1", "Antonio", "Perez", "Madrid", new ArrayList<Object>());
		lenient().when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
		lenient().when(mockGenericDao.deleteSomeData(validUser, "2")).thenReturn(true);
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		String validId = "1";
		Assertions.assertThrows(SystemManagerException.class, () -> {
			manager.deleteRemoteSystem(validUser.getId(), validId);
		});
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).deleteSomeData(validUser, "1");
	}
}
