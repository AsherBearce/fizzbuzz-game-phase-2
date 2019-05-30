package edu.cnm.deepdive.fizzbuzz.model;

import androidx.annotation.NonNull;

public class Round {

  public static final String FORMAT_STRING = "Value: %1$d; category: %2$s; selection: %3$s";

  private final int value;
  private final Category category;
  private final Category selection;
  //TODO Record time for selection.

  public Round(int value, Category category, Category selection) {
    this.value = value;
    this.category = category;
    this.selection = selection;
  }

  @NonNull
  @Override
  public String toString() {
    return String.format(FORMAT_STRING, value, category, selection);
  }

  public enum Category {
    FIZZ, BUZZ, FIZZ_BUZZ, NEITHER;

    public static Category fromValue(int value) {
      Category category = null;

      if (FizzBuzz.isFizz(value)) {
        if (FizzBuzz.isBuzz((value))) {
          category = FIZZ_BUZZ;
        } else {
          category = FIZZ;
        }
      } else if (FizzBuzz.isBuzz(value)) {
        category = BUZZ;
      } else {
        category = NEITHER;
      }

      return category;
    }
  }
}
