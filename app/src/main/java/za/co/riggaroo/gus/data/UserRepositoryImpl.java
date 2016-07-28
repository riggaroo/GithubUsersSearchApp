package za.co.riggaroo.gus.data;


import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import za.co.riggaroo.gus.data.remote.GithubUserRestService;
import za.co.riggaroo.gus.data.remote.model.User;

public class UserRepositoryImpl implements UserRepository {

    private static final int SERVICE_UNAVAILABLE = 503;
    private GithubUserRestService githubUserRestService;

    public UserRepositoryImpl(GithubUserRestService githubUserRestService) {
        this.githubUserRestService = githubUserRestService;
    }

    @Override
    public Observable<List<User>> searchUsers(final String searchTerm) {
        return Observable.defer(() -> githubUserRestService.searchGithubUsers(searchTerm).concatMap(
                usersList -> Observable.from(usersList.getItems())
                        .concatMap(user -> githubUserRestService.getUser(user.getLogin())).toList()))
                .retryWhen(observable -> observable.flatMap(o -> {
                    if (o instanceof HttpException) {
                        HttpException httpException = (HttpException) o;
                        if (httpException.code() == SERVICE_UNAVAILABLE) {
                            return Observable.just(null);
                        }
                    }
                    return Observable.error(o);
                }));
    }

}
