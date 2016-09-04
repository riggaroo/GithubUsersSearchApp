package za.co.riggaroo.gus.presentation.search;

import android.support.test.espresso.matcher.ViewMatchers;
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
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;


public class UserSearchActivityTest {

    @Rule
    public ActivityTestRule<UserSearchActivity> testRule = new ActivityTestRule<>(UserSearchActivity.class);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void searchActivity_onLaunch_HintTextDisplayed(){
        //Given activity automatically launched

        //Then
        onView(withText("Start typing to search")).check(matches(isDisplayed()));
    }

    @Test
    public void searchText_ReturnsCorrectlyFromWebService_DisplaysResult() {
        //Given activity is automatically launched

        //When
        onView(allOf(withId(R.id.menu_search), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).perform(
                click());  // When using a SearchView, there are two views that match the id menu_search - one that represents the icon, and the other the edit text view. We want to click on the visible one.
        onView(withId(R.id.search_src_text)).perform(typeText("riggaroo"), pressKey(KeyEvent.KEYCODE_ENTER));

        //Then
        onView(withText("Start typing to search")).check(matches(not(isDisplayed())));
        onView(withText("riggaroo - Rebecca Franks")).check(matches(isDisplayed()));
        onView(withText("Android Dev")).check(matches(isDisplayed()));
        onView(withText("A unicorn")).check(matches(isDisplayed()));
        onView(withText("riggaroo2 - Rebecca's Alter Ego")).check(matches(isDisplayed()));
    }
}