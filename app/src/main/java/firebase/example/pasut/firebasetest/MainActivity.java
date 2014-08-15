package firebase.example.pasut.firebasetest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
  private final static String TAG = MainActivity.class.getSimpleName();

  private List<Tweet> timeline = new ArrayList<Tweet>();
  private Tweet liveTweet;
  private ArrayAdapter<Tweet> adapter;
  private Firebase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      db = ((FirebaseApplication)getApplication()).getDatabase();
      ListView listView = (ListView)findViewById(R.id.list);
      adapter = new ArrayAdapter<Tweet>(this, android.R.layout.simple_list_item_1, timeline);
      listView.setAdapter(adapter);
    }

  @Override
  protected void onResume() {
    super.onResume();
    db.child("widget1").addChildEventListener(liveTweetListener);
    db.child("widget1").child("timeline").addChildEventListener(timelineListener);
  }

  @Override
  protected void onPause() {
    super.onPause();
    timeline.clear();
    liveTweet = null;
    db.child("widget1").removeEventListener(liveTweetListener);
    db.child("widget1").child("timeline").removeEventListener(timelineListener);
  }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

  private ChildEventListener timelineListener = new ChildEventListener() {
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
      Tweet tweet = dataSnapshot.getValue(Tweet.class);
      timeline.add(tweet);
      adapter.notifyDataSetChanged();
      Log.d(TAG, "add new Tweet to timeline" + tweet);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
      Tweet tweet = dataSnapshot.getValue(Tweet.class);
      timeline.add(tweet);
      adapter.notifyDataSetChanged();
      Log.d(TAG, "replace Tweet on timeline" + tweet);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
      Tweet tweet = dataSnapshot.getValue(Tweet.class);
      timeline.remove(tweet);
      adapter.notifyDataSetChanged();
      Log.d(TAG, "remove Tweet from timeline" + tweet);
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
      Tweet tweet = dataSnapshot.getValue(Tweet.class);
      Log.d(TAG, "move Tweet from timeline" + tweet);
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
      Log.e(TAG, "Error" + firebaseError);
    }
  };

  private ChildEventListener liveTweetListener = new ChildEventListener() {
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
      if (dataSnapshot.getName() == "live") {
        liveTweet = dataSnapshot.getValue(Tweet.class);
        Log.d(TAG, "Must show live Tweet" + liveTweet);
      }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
      if (dataSnapshot.getName() == "live") {
        liveTweet = dataSnapshot.getValue(Tweet.class);
        Log.d(TAG, "Must show live Tweet change" + liveTweet);
      }
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
      if (dataSnapshot.getName() == "live") {
        liveTweet = dataSnapshot.getValue(Tweet.class);
        Log.d(TAG, "Must remove live Tweet" + liveTweet);
      }
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
      if (dataSnapshot.getName() == "live") {
        liveTweet = dataSnapshot.getValue(Tweet.class);
        Log.d(TAG, "Must move? live Tweet" + liveTweet);
      }
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
      Log.e(TAG, "Error" + firebaseError);
    }
  };
}
