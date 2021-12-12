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


import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestValidUser {
    @Mock
    private static AuthDAO mockAuthDao;
    @Mock
    private static GenericDAO mockGenericDao;

    @Test
    public void testAddRemoteSystemValidSystemandUser() throws Exception{
        User validUser = new User ("11","David","Martín","Madrid", new ArrayList<Object>(Arrays.asList(1,2)));
        when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
        when(mockGenericDao.updateSomeData(validUser, "123")).thenReturn(true);

        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

        manager.addRemoteSystem(validUser.getId(),"123");

        ordered.verify(mockAuthDao).getAuthData(validUser.getId());
        ordered.verify(mockGenericDao, times(1)).updateSomeData(validUser, "123");
    }

    @Test
    public void testAddRemoteSystemInvalidSystemValidUser() throws Exception{
        User validUser = new User ("11","David","Martín","Madrid", new ArrayList<Object>(Arrays.asList(1,2)));
        when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
        String invalidSystem = "1234";
        when(mockGenericDao.updateSomeData(validUser, invalidSystem)).thenReturn(false);

        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

        SystemManagerException exception = assertThrows(SystemManagerException.class, () -> {
            manager.addRemoteSystem(validUser.getId(),invalidSystem);
        });

        assertEquals("cannot add remote",exception.getMessage());

        ordered.verify(mockAuthDao).getAuthData(validUser.getId());
        ordered.verify(mockGenericDao, times(1)).updateSomeData(validUser, invalidSystem);
    }
}

