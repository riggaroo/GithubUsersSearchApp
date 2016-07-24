package za.co.riggaroo.gus.data.remote;


import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.Single;
import za.co.riggaroo.gus.data.remote.model.User;
import za.co.riggaroo.gus.data.remote.model.UsersList;

public interface GithubUserRestService {

    @GET("/search/users")
    Observable<UsersList> searchGithubUsers(@Query("q") String searchTerm);

    @GET("/users/{username}")
    Observable<User> getUser(@Path("username") String username);
}
