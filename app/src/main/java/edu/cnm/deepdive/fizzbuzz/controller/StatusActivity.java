package edu.cnm.deepdive.fizzbuzz.controller;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.cnm.deepdive.fizzbuzz.R;
import edu.cnm.deepdive.fizzbuzz.model.Game;
import edu.cnm.deepdive.fizzbuzz.model.Round;
import edu.cnm.deepdive.fizzbuzz.view.RoundAdapter;

public class StatusActivity extends AppCompatActivity {

  private ListView roundList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_status);
    roundList = findViewById(R.id.round_list);
    Game game = (Game)getIntent().getSerializableExtra(getString(R.string.game_data_key));
    RoundAdapter adapter = new RoundAdapter(this, game.getRounds());
    roundList.setAdapter(adapter);
  }
}
