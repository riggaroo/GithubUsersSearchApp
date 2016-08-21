package za.co.riggaroo.gus.presentation.search;

import android.support.test.rule.ActivityTestRule;
import android.view.KeyEvent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import za.co.riggaroo.gus.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class UserSearchActivityTest {

    @Rule
    public ActivityTestRule<UserSearchActivity> testRule = new ActivityTestRule<>(UserSearchActivity.class);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void typeSearchText_DisplaysResult() {

        onView(withId(R.id.menu_search)).perform(click());
        onView(withId(R.id.search_src_text)).perform(typeText("riggaroo"), pressKey(KeyEvent.KEYCODE_ENTER));


    }
}