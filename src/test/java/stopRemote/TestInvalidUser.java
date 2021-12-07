package stopRemote;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

		User invalidUser = null;
		//User invalidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		String userId = "1";
		lenient().when(mockAuthDao.getAuthData(userId)).thenReturn(null);

		String validId = "12345"; // id valido de sistema
		lenient().when(mockGenericDao.getSomeData(invalidUser, "where id=" + validId)).thenReturn(null);
		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		// llamada al api a probar
		Collection<Object> retorno = manager.stopRemoteSystem(userId, validId);
		assertNull(retorno);
		//Poner en la memoria que se asume que se devuelve null en el caso de que el usuario sea inválido y el sistema válido ya que en la documentación no se especifica qué ocurre en este caso, y se probó y no lanza la excepción
		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao).getAuthData(userId);
		ordered.verify(mockGenericDao).getSomeData(invalidUser, "where id=" + validId);
	}

	@Test
	public void testStopRemoteSystemWithInvalidUserAndSystem() throws Exception {
		User invalidUser = null;
		String userId = "1";
		lenient().when(mockAuthDao.getAuthData(userId)).thenReturn(null);

		String invalidId = "12345";
		when(mockGenericDao.getSomeData(invalidUser, "where id=" + invalidId)).thenReturn(null);

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		Collection<Object> retorno = manager.stopRemoteSystem(userId, invalidId);
		assertNull(retorno);
		//Poner en la memoria que se asume que se devuelve null en el caso de que el usuario sea válido y el sistema inválido ya que en la documentación no se especifica qué ocurre en este caso
		ordered.verify(mockAuthDao).getAuthData(userId);
		ordered.verify(mockGenericDao).getSomeData(invalidUser, "where id=" + invalidId);
	}
}
