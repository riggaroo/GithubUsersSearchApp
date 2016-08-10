package za.co.riggaroo.gus.presentation.search;


import java.util.List;

import rx.Scheduler;
import rx.Subscriber;
import za.co.riggaroo.gus.data.UserRepository;
import za.co.riggaroo.gus.data.remote.model.User;
import za.co.riggaroo.gus.presentation.base.BasePresenter;

class UserSearchPresenter extends BasePresenter<UserSearchContract.View> implements UserSearchContract.Presenter {
    private final Scheduler mainScheduler, ioScheduler;
    private UserRepository userRepository;

    UserSearchPresenter(UserRepository userRepository, Scheduler ioScheduler, Scheduler mainScheduler) {
        this.userRepository = userRepository;
        this.ioScheduler = ioScheduler;
        this.mainScheduler = mainScheduler;
    }

    @Override
    public void search(String term) {
        checkViewAttached();
        getView().showLoading();
        addSubscription(userRepository.searchUsers(term).subscribeOn(ioScheduler).observeOn(mainScheduler).subscribe(new Subscriber<List<User>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().hideLoading();
                getView().showError(e.getMessage()); //TODO You probably don't want this error to show to users - Might want to show a friendlier message :)
            }

            @Override
            public void onNext(List<User> users) {
                getView().hideLoading();
                getView().showSearchResults(users);
            }
        }));
    }
}
