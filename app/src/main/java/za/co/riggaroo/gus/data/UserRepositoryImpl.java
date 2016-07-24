package za.co.riggaroo.gus.data;


import java.util.List;

import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;
import za.co.riggaroo.gus.data.remote.GithubUserRestService;
import za.co.riggaroo.gus.data.remote.model.User;
import za.co.riggaroo.gus.data.remote.model.UsersList;

public class UserRepositoryImpl implements UserRepository {

    private GithubUserRestService githubUserRestService;

    public UserRepositoryImpl(GithubUserRestService githubUserRestService) {
        this.githubUserRestService = githubUserRestService;
    }

    @Override
    public Observable<List<User>> searchUsers(final String searchTerm) {
        return Observable.defer(new Func0<Observable<List<User>>>() {
            @Override
            public Observable<List<User>> call() {
                return githubUserRestService.searchGithubUsers(searchTerm).concatMap(new Func1<UsersList, Observable<List<User>>>() {
                    @Override
                    public Observable<List<User>> call(UsersList usersList) {
                        return Observable.from(usersList.getItems()).concatMap(new Func1<User, Observable<User>>() {
                            @Override
                            public Observable<User> call(User user) {
                                return githubUserRestService.getUser(user.getName());
                            }
                        }).toList();
                    }
                });
            }
        });
    }

}
