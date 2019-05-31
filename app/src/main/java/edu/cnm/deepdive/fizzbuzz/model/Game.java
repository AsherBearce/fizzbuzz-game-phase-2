package edu.cnm.deepdive.fizzbuzz.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Game implements Serializable {
  private static final long serialVersionUID = 755828491505417423L;
  private final int timeLimit;
  private final int maxDigits;
  private final int gameDuration;
  private final List<Round> rounds;

  public Game(int timeLimit, int maxDigits, int gameDuration) {
    this.timeLimit = timeLimit;
    this.maxDigits = maxDigits;
    this.gameDuration = gameDuration;
    this.rounds = new LinkedList<>();
  }

  public void add(Round round){
    rounds.add(round);
  }

  public int getTimeLimit() {
    return timeLimit;
  }

  public int getMaxDigits() {
    return maxDigits;
  }

  public int getGameDuration() {
    return gameDuration;
  }

  public List<Round> getRounds() {
    return Collections.unmodifiableList(rounds);
  }
}
