package za.co.riggaroo.gus.data;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;
import za.co.riggaroo.gus.data.remote.GithubUserRestService;
import za.co.riggaroo.gus.data.remote.model.User;
import za.co.riggaroo.gus.data.remote.model.UsersList;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class UserRepositoryImplTest {

    @Mock
    GithubUserRestService githubUserRestService;

    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userRepository = new UserRepositoryImpl(githubUserRestService);
    }

    @Test
    public void searchUsers() throws Exception {
        when(githubUserRestService.searchGithubUsers(anyString())).thenReturn(Observable.just(githubUserList()));
        when(githubUserRestService.getUser(anyString())).thenReturn(Observable.just(user1FullDetails()), Observable.just(user2FullDetails()));
        TestSubscriber<List<User>> subscriber = new TestSubscriber<>();
        userRepository.searchUsers("riggaroo").subscribe(subscriber);

        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<List<User>> onNextEvents = subscriber.getOnNextEvents();
        List<User> users = onNextEvents.get(0);
        Assert.assertEquals("riggaroo", users.get(0).getName());
        Assert.assertEquals("rebecca", users.get(1).getName());
    }

    private UsersList githubUserList() {
        User user = new User();
        user.setName("riggaroo");

        User user2 = new User();
        user2.setName("rebecca");

        List<User> githubUsers = new ArrayList<>();
        githubUsers.add(user);
        githubUsers.add(user2);
        UsersList usersList = new UsersList();
        usersList.setItems(githubUsers);
        return usersList;
    }

    private User user1FullDetails() {
        User user = new User();
        user.setName("riggaroo");
        user.setAvatarUrl("avatar_url");
        user.setBio("Bio1");
        return user;
    }

    private User user2FullDetails() {
        User user = new User();
        user.setName("rebecca");
        user.setAvatarUrl("avatar_url2");
        user.setBio("Bio2");
        return user;
    }
}