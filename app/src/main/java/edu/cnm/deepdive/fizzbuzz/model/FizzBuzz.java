package edu.cnm.deepdive.fizzbuzz.model;

/**
 * Includes several public methods and constants for computing FizzBuzz values.
 * <p>This class includes <code>static</code> methods that take <code>int</code>
 * parameters and return <code>boolean</code> or
 * {@link String} results, indicating whether the specified values are divisible by 3 (fizz) or
 * 5 (buzz).</p>
 *
 * @author Asher Bearce &amp; Deep Dive Coding Java+Android Cohort 7.
 * @version 1.0.0
 */
public class FizzBuzz {
  /** {@link String} value indicating a number divisible by 3. */
  public static final String FIZZ = "Fizz";
  /** {@link String} value indicating a number divisible by 5. */
  public static final String BUZZ = "Buzz";

  private FizzBuzz(){}

  /**
   * Computes and returns "Fizz", "Buzz", or a {@link String} representation of <code>value</code>, depending on whether
   * <code>value</code> is divisible by 3 or 5.
   *
   * @param value Integer value to be tested for divisibility by 3 and 5.
   * @return "Fizz", "Buzz", or value as a String.
   */
  public static String fizzBuzzValue(int value){
    if (value <= 0){
      throw new IllegalArgumentException();
    }
    String result = "";
    if (isFizz(value)){
      result += FIZZ;
    }
    if (isBuzz(value)){
      result += BUZZ;
    }
    if (result.isEmpty()){
      result += value;
    }

    return result;
  }

  /**
   * Computes and returns whether or not <code>value</code> is divisible by 3.
   *
   * @param value Integer value to be tested for divisibility by 3.
   * @return True if <code>value</code> is divisible by 3, false otherwise.
   */
  public static boolean isFizz(int value){
    return value % 3 == 0;
  }

  /**
   * Computes and returns whether or not <code>value</code> is divisible by 5.
   *
   * @param value Integer value to be tested for divisibility by 5.
   * @return True if <code>value</code> is divisible by 5, false otherwise.
   */
  public static boolean isBuzz(int value){
    return value % 5 == 0;
  }
}