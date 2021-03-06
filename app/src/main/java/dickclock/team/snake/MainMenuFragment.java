package dickclock.team.snake;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

/**
 * Fragment with the main menu for the game. The main menu allows the player
 * to choose a gameplay mode (Easy or Hard), and click the buttons to
 * show view achievements/leaderboards.
 *
 * @author Bruno Oliveira (Google)
 */
public class MainMenuFragment extends Fragment implements OnClickListener {
    private String mGreeting = "Hello, anonymous user (not signed in)!";
    private TextView mGreetingTextView;
    private View mSignInBarView;
    private View mSignOutBarView;
    private View mShowAchievementsButton;
    private View mShowLeaderboardsButton;
    private View mShowFriendsButton;

    interface Listener {
        // called when the user presses the `Easy` or `Okay` button; will pass in which via `hardMode`
        void onStartGameRequested(Settings.level level);

        void onShowSettingsRequested();

        // called when the user presses the `Show Achievements` button
        void onShowAchievementsRequested();

        // called when the user presses the `Show Leaderboards` button
        void onShowLeaderboardsRequested();

        // called when the user presses the `Sign In` button
        void onSignInButtonClicked();

        // called when the user presses the `Sign Out` button
        void onSignOutButtonClicked();

        // called when the user presses the `Friends` button
        void onShowFriendsButtonClicked();
    }

    private Listener mListener = null;
    private boolean mShowSignInButton = true;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mainmenu, container, false);

        final int[] clickableIds = new int[]{
                R.id.easy_mode_button,
                R.id.medium_mode_button,
                R.id.hard_mode_button,
                R.id.settings_button,
                R.id.show_achievements_button,
                R.id.show_leaderboards_button,
                R.id.sign_in_button,
                R.id.sign_out_button,
                R.id.show_friends_button
        };

        for (int clickableId : clickableIds) {
            view.findViewById(clickableId).setOnClickListener(this);
        }

        // cache views
        mShowAchievementsButton = view.findViewById(R.id.show_achievements_button);
        mShowLeaderboardsButton = view.findViewById(R.id.show_leaderboards_button);
        mShowFriendsButton = view.findViewById(R.id.show_friends_button);
        mGreetingTextView = view.findViewById(R.id.text_greeting);
        mSignInBarView = view.findViewById(R.id.sign_in_bar);
        mSignOutBarView = view.findViewById(R.id.sign_out_bar);

        updateUI();

        // Set version name
        TextView textVersion = view.findViewById(R.id.version);
        textVersion.setText(BuildConfig.VERSION_NAME);

        return view;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public void setGreeting(String greeting) {
        mGreeting = greeting;
        updateUI();
    }

    private void updateUI() {
        mGreetingTextView.setText(mGreeting);
        mShowAchievementsButton.setEnabled(!mShowSignInButton);
        mShowLeaderboardsButton.setEnabled(!mShowSignInButton);
        mShowFriendsButton.setEnabled(!mShowSignInButton);
        mSignInBarView.setVisibility(mShowSignInButton ? View.VISIBLE : View.GONE);
        mSignOutBarView.setVisibility(mShowSignInButton ? View.GONE : View.VISIBLE);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.easy_mode_button:
                mListener.onStartGameRequested(Settings.level.EASY);
                break;
            case R.id.medium_mode_button:
                mListener.onStartGameRequested(Settings.level.MEDIUM);
                break;
            case R.id.hard_mode_button:
                mListener.onStartGameRequested(Settings.level.HARD);
                break;
            case R.id.settings_button:
                mListener.onShowSettingsRequested();
                break;
            case R.id.show_achievements_button:
                mListener.onShowAchievementsRequested();
                break;
            case R.id.show_leaderboards_button:
                mListener.onShowLeaderboardsRequested();
                break;
            case R.id.show_friends_button:
                mListener.onShowFriendsButtonClicked();
                break;
            case R.id.sign_in_button:
                mListener.onSignInButtonClicked();
                break;
            case R.id.sign_out_button:
                mListener.onSignOutButtonClicked();
                break;
        }
    }

    public void setShowSignInButton(boolean showSignInButton) {
        mShowSignInButton = showSignInButton;
        updateUI();
    }
}
