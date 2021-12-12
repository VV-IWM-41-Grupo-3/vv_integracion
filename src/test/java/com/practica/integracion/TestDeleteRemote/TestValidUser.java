package com.practica.integracion.TestDeleteRemote;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import com.practica.integracion.manager.SystemManagerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.OperationNotSupportedException;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestValidUser {
	@Mock
	private GenericDAO dao;
	@Test
	public void testDeleteUserValidGood() throws OperationNotSupportedException, SystemManagerException {
		final User auth = new User("1", "Antonio", "Perez", "Madrid", new ArrayList<Object>());
		lenient().when(dao.deleteSomeData(auth, "1")).thenReturn(true);
		SystemManager sysm = new SystemManager(null, dao);
		sysm.deleteRemoteSystem("1", "1");
		verify(dao, times(1)).deleteSomeData(auth, "1");
	}
	@Test
	public void testDeleteUserValidBad() throws OperationNotSupportedException, SystemManagerException {
		final User auth = new User("1", "Antonio", "Perez", "Madrid", new ArrayList<Object>());
		lenient().when(dao.deleteSomeData(auth, "2")).thenReturn(true);
		SystemManager sysm = null;
		sysm.deleteRemoteSystem("1", "1");
		verify(dao, times(1)).deleteSomeData(auth, "1");
	}
}
