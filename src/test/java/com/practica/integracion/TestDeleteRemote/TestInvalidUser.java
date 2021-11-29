package com.practica.integracion.TestDeleteRemote;

import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import com.practica.integracion.manager.SystemManagerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.OperationNotSupportedException;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestInvalidUser {
	@Mock
	GenericDAO dao;

	@Test
	public void testDeleteUserInvalidGood() throws OperationNotSupportedException {
		final User auth = null;
		lenient().when(dao.deleteSomeData(auth, "123")).thenReturn(false);

		SystemManager sysm = new SystemManager(null, dao);
		Assertions.assertThrows(SystemManagerException.class, () ->{
			sysm.deleteRemoteSystem("1", "123");
		});
		verify(dao, times(1)).deleteSomeData(auth, "123");
	}

	@Test
	public void testDeleteUserInvalidBad() throws OperationNotSupportedException {
		final User auth = null;
		lenient().when(dao.deleteSomeData(auth, "123")).thenReturn(false);

		SystemManager sysm = null;
		Assertions.assertThrows(SystemManagerException.class, () ->{
			sysm.deleteRemoteSystem("1", "123");
		});
		verify(dao, times(1)).deleteSomeData(auth, "123");
	}
}
