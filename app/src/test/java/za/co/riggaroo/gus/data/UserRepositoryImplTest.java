package za.co.riggaroo.gus.data;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;
import za.co.riggaroo.gus.data.remote.GithubUserRestService;
import za.co.riggaroo.gus.data.remote.model.User;
import za.co.riggaroo.gus.data.remote.model.UsersList;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserRepositoryImplTest {

    @Mock
    GithubUserRestService githubUserRestService;

    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userRepository = new UserRepositoryImpl(githubUserRestService);
    }

    @Test
    public void searchUsers_invokesCorrectApiCalls() {
        //Given
        when(githubUserRestService.searchGithubUsers(anyString())).thenReturn(Observable.just(githubUserList()));
        when(githubUserRestService.getUser(anyString())).thenReturn(Observable.just(user1FullDetails()), Observable.just(user2FullDetails()));

        //When
        TestSubscriber<List<User>> subscriber = new TestSubscriber<>();
        userRepository.searchUsers("riggaroo").subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<List<User>> onNextEvents = subscriber.getOnNextEvents();
        List<User> users = onNextEvents.get(0);
        Assert.assertEquals("riggaroo", users.get(0).getLogin());
        Assert.assertEquals("rebecca", users.get(1).getLogin());
        verify(githubUserRestService).searchGithubUsers("riggaroo");
        verify(githubUserRestService).getUser("riggaroo");
        verify(githubUserRestService).getUser("rebecca");
    }

    private UsersList githubUserList() {
        User user = new User();
        user.setLogin("riggaroo");

        User user2 = new User();
        user2.setLogin("rebecca");

        List<User> githubUsers = new ArrayList<>();
        githubUsers.add(user);
        githubUsers.add(user2);
        UsersList usersList = new UsersList();
        usersList.setItems(githubUsers);
        return usersList;
    }

    private User user1FullDetails() {
        User user = new User();
        user.setLogin("riggaroo");
        user.setName("Rigs Franks");
        user.setAvatarUrl("avatar_url");
        user.setBio("Bio1");
        return user;
    }

    private User user2FullDetails() {
        User user = new User();
        user.setLogin("rebecca");
        user.setName("Rebecca Franks");
        user.setAvatarUrl("avatar_url2");
        user.setBio("Bio2");
        return user;
    }

    @Test
    public void searchUsers_InvalidResponse_OnErrorCalled(){
        //Given
        when(githubUserRestService.searchGithubUsers(anyString())).thenReturn(Observable.<UsersList>error(new IOException("Failed to make the request")));

        //When
        TestSubscriber<List<User>> subscriber = new TestSubscriber<>();
        userRepository.searchUsers("riggaroo").subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertError(IOException.class);

        verify(githubUserRestService).searchGithubUsers("riggaroo");
        verify(githubUserRestService, never()).getUser("riggaroo");
        verify(githubUserRestService, never()).getUser("rebecca");
    }
}