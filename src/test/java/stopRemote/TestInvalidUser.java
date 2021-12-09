package stopRemote;

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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.inOrder;

@ExtendWith(MockitoExtension.class)
public class TestInvalidUser {

	@Mock
	private static AuthDAO mockAuthDao;
	@Mock
	private static GenericDAO mockGenericDao;

	@Test
	public void testStopRemoteSystemWithInvalidUserValidSystem() throws Exception {

		User invalidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		Mockito.lenient().when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);
		String validId = "12345";
		Mockito.lenient().when(mockGenericDao.getSomeData(null, "where id=" + validId)).thenThrow(OperationNotSupportedException.class);
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		Assertions.assertThrows(SystemManagerException.class, () ->{
			Collection<Object> retorno = manager.stopRemoteSystem(invalidUser.getId(), validId);
		});
		ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
		ordered.verify(mockGenericDao).getSomeData(null, "where id=" + validId);
	}

	@Test
	public void testStopRemoteSystemWithInvalidUserAndSystem() throws Exception {

		User invalidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		Mockito.lenient().when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);
		String validId = "12345";
		Mockito.lenient().when(mockGenericDao.getSomeData(null, "where id=" + validId)).thenThrow(OperationNotSupportedException.class);
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		Assertions.assertThrows(SystemManagerException.class, () ->{
			Collection<Object> retorno = manager.stopRemoteSystem(invalidUser.getId(), validId);
		});
		ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
		ordered.verify(mockGenericDao).getSomeData(null, "where id=" + validId);
	}
}
