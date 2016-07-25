package za.co.riggaroo.gus.presentation.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import za.co.riggaroo.gus.R;
import za.co.riggaroo.gus.data.remote.model.User;
import za.co.riggaroo.gus.injection.Injection;


public class UserSearchActivity extends AppCompatActivity implements UserSearchContract.View {

    private static final String TAG = "UserSearchActi";
    private UserSearchContract.Presenter userSearchPresenter;
    private UsersAdapter usersAdapter;
    private SearchView searchView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userSearchPresenter = new UserSearchPresenter(Injection.provideUserRepo(), Schedulers.io(), AndroidSchedulers.mainThread());

        userSearchPresenter.attachView(this);
        RecyclerView recyclerViewUsers = (RecyclerView) findViewById(R.id.recycler_view_users);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewUsers.setLayoutManager(manager);
        usersAdapter = new UsersAdapter(null, this);
        recyclerViewUsers.setAdapter(usersAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userSearchPresenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                userSearchPresenter.search(query);
                toolbar.setTitle(query);
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchResultsLoaded(List<User> githubUserList) {
        Log.d(TAG, "onSearchResultsLoaded() called with: githubUserList = [" + githubUserList + "]");
        usersAdapter.setItems(githubUserList);
    }

    @Override
    public void showError(String message) {
        Log.d(TAG, "showError() called with: message = [" + message + "]");
    }

    @Override
    public void showLoading(boolean show) {
        Log.d(TAG, "showLoading() called with: show = [" + show + "]");
    }
}
