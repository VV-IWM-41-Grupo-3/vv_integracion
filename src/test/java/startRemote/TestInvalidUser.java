package startRemote;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestInvalidUser {

    @Mock
    private static AuthDAO mockAuthDao;
    @Mock
    private static GenericDAO mockGenericDao;

    @Test
    public void testStartRemoteSystemWithInvalidUserValidSystem() throws Exception {

        User invalidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
        Mockito.lenient().when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);

        String validId = "12345"; // id valido de sistema
        ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
        Mockito.lenient().when(mockGenericDao.getSomeData(invalidUser, "where id=" + validId)).thenReturn(lista);
        // primero debe ejecutarse la llamada al dao de autenticación
        // despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
        // instanciamos el manager con los mock creados
        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
        // llamada al api a probar
        Collection<Object> retorno = manager.startRemoteSystem(invalidUser.getId(), validId);
        assertEquals(retorno.toString(), "[]");
        // vemos si se ejecutan las llamadas a los dao, y en el orden correcto
        ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
        ordered.verify(mockGenericDao).getSomeData(invalidUser, "where id=" + validId);
    }

    @Test
    public void testStartRemoteSystemWithInvalidUserAndSystem() throws Exception {
        User inValidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
        when(mockAuthDao.getAuthData(inValidUser.getId())).thenReturn(null);

        String invalidId = "12345";
        ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
        Mockito.lenient().when(mockGenericDao.getSomeData(inValidUser, "where id=" + invalidId)).thenReturn(null);

        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

        Collection<Object> retorno = manager.startRemoteSystem(inValidUser.getId(), invalidId);
        assertNull(retorno);
        //Poner en la memoria que se asume que se devuelve null en el caso de que el usuario sea válido y el sistema inválido ya que en la documentación no se especifica qué ocurre en este caso
        ordered.verify(mockAuthDao).getAuthData(inValidUser.getId());
        ordered.verify(mockGenericDao).getSomeData(inValidUser, "where id=" + invalidId);
    }
}
