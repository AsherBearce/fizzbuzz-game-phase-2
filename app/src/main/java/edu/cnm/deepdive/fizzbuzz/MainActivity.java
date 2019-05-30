package edu.cnm.deepdive.fizzbuzz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.view.GestureDetectorCompat;
import androidx.preference.PreferenceManager;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Main game play screen for FizzBuzz number classification game.
 * <p>When the game is running, this class displays a randomly selected number; the user then
 * flings the number in one of 4 directions, to indicate whether the number is a Fizz (divisible by
 * 3), Buzz (divisible by 5), FizzBuzz (divisible by both 3 & 5), or neither Fizz nor Buzz. A record
 * of numbers displayed, user actions, as well as an overall correct/incorrect tally are kept.</p>
 */
public class MainActivity extends AppCompatActivity
    implements View.OnTouchListener, SharedPreferences.OnSharedPreferenceChangeListener {

  private Random rng = new Random();
  private int value;
  private boolean running;
  private TextView valueDisplay;
  private ViewGroup valueContainer;
  private Rect displayRect = new Rect();
  private GestureDetectorCompat detector;
  private Timer timer;
  private SharedPreferences preferences;

  /**
   * Initializes this activity when created, and when restored after {@link #onDestroy()} (for
   * example, after a change of orientation). In the latter case, the game state is retrieved from
   * <code>savedInstanceState</code>.
   *
   * @param savedInstanceState saved game state {@link Bundle}.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    valueDisplay = findViewById(R.id.value_display);
    valueContainer = (ViewGroup) valueDisplay.getParent();
    detector = new GestureDetectorCompat(this, new FlingListener());
    valueContainer.setOnTouchListener(this);
    preferences = PreferenceManager.getDefaultSharedPreferences(this);
    preferences.registerOnSharedPreferenceChangeListener(this);
    // TODO Restore any necessary fields, set game state, etc.
  }

  /**
   * Updates timer(s) and UI to return display &amp; game to the pre-{@link #onPause()} state.
   */
  @Override
  protected void onResume() {
    super.onResume();
    // TODO Resume game if running.
  }

  /**
   * Captures current state of timer(s) to fields, for possible saving by subsequent {@link
   * #onSaveInstanceState(Bundle)} invocation and/or restoration by {@link #onResume()} invocation.
   */
  @Override
  protected void onPause() {
    super.onPause();
    // TODO Record any in-progress data from timers, etc. to fields.
  }

  /**
   * Writes critical game state information to <code>outState</code>.
   *
   * @param outState game state write target.
   */
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    // TODO Save any necessary fields.
  }

  /**
   * Inflates menu options for control of game, access to settings, and display of current game
   * results.
   *
   * @param menu {@link Menu} to which inflated options are added.
   * @return flag indicating that a menu was inflated &amp; added (always <code>true</code> in this
   * case).
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.options, menu);
    return true;
  }

  /**
   * Updates visible and enabled state of menu options, depending on game state.
   *
   * @param menu options menu.
   * @return flag indicating that the options menu should be re-rendered (always <code>true</code>
   * in this case).
   */
  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    MenuItem play = menu.findItem(R.id.play);
    MenuItem pause = menu.findItem(R.id.pause);
    play.setEnabled(!running);
    play.setVisible(!running);
    pause.setEnabled(running);
    pause.setVisible(running);
    return true;
  }

  /**
   * Handles user selection from the options menu.
   *
   * @param item option selected.
   * @return flag indicating that the selection was handled (or not).
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    boolean handled = true;
    switch (item.getItemId()) {
      case R.id.play:
        resumeGame();
        break;
      case R.id.pause:
        pauseGame();
        break;
      case R.id.settings:
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        break;
      default:
        handled = super.onOptionsItemSelected(item);
        break;
    }
    return handled;
  }

  /**
   * Handles user touch events on the screen. In general, these are handled simply by delegating to
   * an instance of a {@link android.view.GestureDetector.SimpleOnGestureListener} subclass.
   *
   * @param view target of touch event.
   * @param event details of event (location, type, time, etc.).
   * @return flag indicating that the user touch event was handled (or not).
   */
  @Override
  public boolean onTouch(View view, MotionEvent event) {
    boolean handled = false;
    if (running) {
      handled = detector.onTouchEvent(event);
      if (!handled && event.getActionMasked() == MotionEvent.ACTION_UP) {
        valueDisplay.setTranslationX(0);
        valueDisplay.setTranslationY(0);
        handled = true;
      }
    }
    return handled;
  }

  /**
   * Detects and handles changes in any {@link SharedPreferences} values in which this app's
   * configuration settings are stored. Most such changes are handled by re-starting the game in
   * progress (if any).
   *
   * @param sharedPreferences configuration settings.
   * @param key specific setting changed.
   */
  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    // TODO Set any necessary flags, etc. to indicate game should be restarted.
  }

  private void pauseGame() {
    running = false;
    if (timer != null) {
      timer.cancel();
      timer = null;
    }
    // TODO Update any additional necessary fields.
    invalidateOptionsMenu();
  }

  private void resumeGame() {
    running = true;
    updateValue();
    // TODO Update any additional necessary fields.
    invalidateOptionsMenu();
  }

  private void updateValue() {
    int numDigits = PreferenceManager.getDefaultSharedPreferences(this)
        .getInt(getString(R.string.num_digits_key),
            getResources().getInteger(R.integer.num_digits_default));
    int valueLimit = (int) Math.pow(10, numDigits) - 1;
    int timeLimit = PreferenceManager.getDefaultSharedPreferences(this)
        .getInt(getString(R.string.time_limit_key),
            getResources().getInteger(R.integer.time_limit_default));
    if (timer != null) {
      timer.cancel();
    }
    value = 1 + rng.nextInt(valueLimit);
    String valueString = Integer.toString(value);
    valueDisplay.setTranslationX(0);
    valueDisplay.setTranslationY(0);
    valueDisplay.setText(valueString);
    valueDisplay.getPaint().getTextBounds(valueString, 0, valueString.length(), displayRect);
    displayRect.top += valueDisplay.getBaseline();
    displayRect.bottom += valueDisplay.getBaseline();
    if (timeLimit != 0) {
      timer = new Timer();
      timer.schedule(new TimeoutTask(), timeLimit * 1000);
    }
  }

  private class TimeoutTask extends TimerTask {

    @Override
    public void run() {
      runOnUiThread(() -> updateValue());
    }

  }

  private class FlingListener extends GestureDetector.SimpleOnGestureListener {

    private static final int RADIUS_FACTOR = 5;
    private static final double SPEED_THRESHOLD = 300;

    private float originX;
    private float originY;

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
      valueDisplay.setTranslationX(e2.getX() - originX);
      valueDisplay.setTranslationY(e2.getY() - originY);
      return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
      int containerHeight = valueContainer.getHeight();
      int containerWidth = valueContainer.getWidth();
      int radiusX = containerWidth / RADIUS_FACTOR;
      int radiusY = containerHeight / RADIUS_FACTOR;
      double deltaX = e2.getX() - e1.getX();
      double deltaY = e2.getY() - e1.getY();
      double ellipticalDistance =
          deltaX * deltaX / radiusX / radiusX + deltaY * deltaY / radiusY / radiusY;
      double speed = Math.hypot(velocityX, velocityY);
      if (speed >= SPEED_THRESHOLD && ellipticalDistance >= 1) {
        if (Math.abs(deltaY) * containerWidth <= Math.abs(deltaX) * containerHeight) {
          if (deltaX > 0) {
            Log.d("Trace", "Right fling");
          } else {
            Log.d("Trace", "Left fling");
          }
        } else {
          if (deltaY > 0) {
            Log.d("Trace", "Down fling");
          } else {
            Log.d("Trace", "Up fling");
          }
        }
        updateValue();
        return true;
      } else {
        return false;
      }
    }

    @Override
    public boolean onDown(MotionEvent evt) {
      if (displayRect.contains(Math.round(evt.getX()), Math.round(evt.getY()))) {
        originX = evt.getX() - valueDisplay.getTranslationX();
        originY = evt.getY() - valueDisplay.getTranslationY();
        return true;
      }
      return false;
    }

  }

}
