package edu.cnm.deepdive.fizzbuzz.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import edu.cnm.deepdive.fizzbuzz.R;
import edu.cnm.deepdive.fizzbuzz.model.Round;
import java.util.List;

public class RoundAdapter extends ArrayAdapter<Round> {

  private Drawable correct;
  private Drawable incorrect;

  public RoundAdapter(@NonNull Context context, @NonNull List<Round> objects) {
    super(context, R.layout.round_item, objects);
    correct = context.getDrawable(R.drawable.check);
    incorrect = context.getDrawable(R.drawable.error);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    View layout = (convertView != null) ?
        convertView :
        LayoutInflater.from(getContext()).inflate(R.layout.round_item, parent, false);

    TextView valueDisplay = layout.findViewById(R.id.value_display);
    TextView categoryDisplay = layout.findViewById(R.id.category_display);
    ImageView resultDisplay = layout.findViewById(R.id.result_display);
    Round round = getItem(position);

    valueDisplay.setText(Integer.toString(round.getValue()));
    categoryDisplay.setText(round.getCategory().toString());
    resultDisplay.setImageDrawable(round.isCorrect() ? correct : incorrect);

    return layout;
  }
}
