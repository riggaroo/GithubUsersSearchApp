package za.co.riggaroo.gus.data.remote;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import za.co.riggaroo.gus.data.remote.model.User;
import za.co.riggaroo.gus.data.remote.model.UsersList;


public class MockGithubUserRestServiceImpl implements GithubUserRestService {
    private final List<User> usersList = new ArrayList<>();
    private User dummyUser1, dummyUser2;

    public MockGithubUserRestServiceImpl() {
        dummyUser1 = new User("riggaroo", "Rebecca Franks",
                "https://riggaroo.co.za/wp-content/uploads/2016/03/rebeccafranks_circle.png", "Android Dev");
        dummyUser2 = new User("riggaroo2", "Rebecca's Alter Ego",
                "https://s-media-cache-ak0.pinimg.com/564x/e7/cf/f3/e7cff3be614f68782386bfbeecb304b1.jpg", "A unicorn");
        usersList.add(dummyUser1);
        usersList.add(dummyUser2);
    }

    private static Observable dummyGithubSearchResult = null;

    public static void setDummySearchGithubCallResult(Observable result) {
        dummyGithubSearchResult = result;
    }

    @Override
    public Observable<UsersList> searchGithubUsers(final String searchTerm) {
        if (dummyGithubSearchResult != null) {
            return dummyGithubSearchResult;
        }
        return Observable.just(new UsersList(usersList));
    }

    @Override
    public Observable<User> getUser(final String username) {
        if (username.equals("riggaroo")) {
            return Observable.just(dummyUser1);
        } else if (username.equals("riggaroo2")) {
            return Observable.just(dummyUser2);
        }
        return Observable.just(null);
    }


}
