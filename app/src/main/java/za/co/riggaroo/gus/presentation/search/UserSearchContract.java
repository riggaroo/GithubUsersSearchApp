package za.co.riggaroo.gus.presentation.search;


import java.util.List;

import za.co.riggaroo.gus.data.remote.model.User;
import za.co.riggaroo.gus.presentation.base.MvpPresenter;
import za.co.riggaroo.gus.presentation.base.MvpView;

interface UserSearchContract {

    interface View extends MvpView {
        void onSearchResultsLoaded(List<User> githubUserList);

        void showError(String message);

        void showLoading(boolean show);
    }

    interface Presenter extends MvpPresenter<View> {
        void search(String term);
    }
}
