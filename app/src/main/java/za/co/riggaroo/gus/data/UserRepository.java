package za.co.riggaroo.gus.data;


import java.util.List;

import rx.Observable;
import za.co.riggaroo.gus.data.remote.model.User;

public interface UserRepository {

    Observable<List<User>> searchUsers(String searchTerm);
}
