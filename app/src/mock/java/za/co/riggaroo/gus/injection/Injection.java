package za.co.riggaroo.gus.injection;


import za.co.riggaroo.gus.data.UserRepository;
import za.co.riggaroo.gus.data.UserRepositoryImpl;
import za.co.riggaroo.gus.data.remote.GithubUserRestService;
import za.co.riggaroo.gus.data.remote.MockGithubUserRestServiceImpl;

public class Injection {

    private static GithubUserRestService userRestService;


    public static UserRepository provideUserRepo() {
        return new UserRepositoryImpl(provideGithubUserRestService());
    }

    static GithubUserRestService provideGithubUserRestService() {
        if (userRestService == null) {
            userRestService = new MockGithubUserRestServiceImpl();
        }
        return userRestService;
    }

}