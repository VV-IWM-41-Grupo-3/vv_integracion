package TestAddRemoteSystem;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import com.practica.integracion.manager.SystemManagerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TestInvalidUser {
	@Mock
	private static AuthDAO mockAuthDao;
	@Mock
	private static GenericDAO mockGenericDao;

	@Test
	public void testAddRemoteSystemValidSystemInvalidUser() throws Exception{
		String validSystem = "123";
		User invalidUser = new User ("11","David","Martín","Madrid", new ArrayList<Object>(Arrays.asList(1,2)));
		when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);
		when(mockGenericDao.updateSomeData(null, validSystem)).thenThrow(OperationNotSupportedException.class);

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		assertThrows(SystemManagerException.class, () -> {
			manager.addRemoteSystem(invalidUser.getId(),"123");
		});

		ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
		ordered.verify(mockGenericDao, times(1)).updateSomeData(null, "123");
	}

	@Test
	public void testAddRemoteSystemInvalidSystemInvalidUser() throws Exception{
		String invalidSystem = "123";
		User invalidUser = new User ("11","David","Martín","Madrid", new ArrayList<Object>(Arrays.asList(1,2)));
		when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);
		when(mockGenericDao.updateSomeData(null, invalidSystem)).thenThrow(OperationNotSupportedException.class);

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		assertThrows(SystemManagerException.class, () -> {
			manager.addRemoteSystem(invalidUser.getId(),invalidSystem);
		});

		ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
		ordered.verify(mockGenericDao, times(1)).updateSomeData(null, invalidSystem);
	}
}
