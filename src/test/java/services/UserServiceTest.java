package services;

import dto.UserDelete;
import dto.UserPost;
import junit.framework.Assert;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repo.BuyBookRepository;
import repo.UserRepository;
import repo.impl.BuyBookRepositoryImpl;
import repo.impl.UserRepositoryImpl;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class UserServiceTest {
    UserService service;
    UserRepository userRepository;
    BuyBookRepository buyBookRepository;

    @BeforeEach
    void prepareData() throws NoSuchFieldException, IllegalAccessException {
        service = UserService.getInstance();
        userRepository = mock(UserRepositoryImpl.class);
        buyBookRepository = mock(BuyBookRepositoryImpl.class);

        Field field = UserService.class.getDeclaredField("userRepository");
        field.setAccessible(true);
        field.set(service, userRepository);

        field = UserService.class.getDeclaredField("buyBookRepository");
        field.setAccessible(true);
        field.set(service, buyBookRepository);
    }
    @Test
    void getInstance() {
        Assertions.assertEquals(UserService.getInstance().getClass(), UserService.class);
    }

    @Test
    void getAllUsers() {

        service.getAllUsers();

        verify(userRepository).getAll();
    }

    @Test
    void haveUserById() {
        when(userRepository.findById(anyInt())).thenReturn(null);

        boolean ans = service.haveUserById(anyInt());

        verify(userRepository).findById(anyInt());
        Assertions.assertFalse(ans);
    }

    @Test
    void deleteUser() {
        UserDelete userDelete = new UserDelete();

        service.deleteUser(userDelete);

        verify(userRepository).delete(any(User.class));
        verify(buyBookRepository).deleteByUserId(anyInt());
    }

    @Test
    void changeUser() {
        User user = new User();

        service.changeUser(user);

        verify(userRepository).update(any(User.class));
    }

    @Test
    void addUser() {
        UserPost userPost = new UserPost();

        service.addUser(userPost);

        verify(userRepository).save(any(User.class));
    }
}