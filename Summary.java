package edu.illinois.cs.cs124.ay2023.mp.models;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Model holding the course summary information shown in the summary list.
 *
 * @noinspection unused
 */
public class Summary implements Comparable<Summary> {
  private String subject;

  public static List<Summary> filter(List<Summary> list, String filter) {
    List<Summary> temp = new ArrayList<>();
    String word = filter.trim().toLowerCase();
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).toString().toLowerCase().contains(word)) {
        temp.add(list.get(i));
      }
    }
    Collections.sort(temp);

    Collections.sort(
        temp, Comparator.comparingInt((Summary one) -> one.toString().toLowerCase().indexOf(word)));
    return temp;
  }

  /**
   * Get the subject for this Summary.
   *
   * @return the subject for this Summary
   */
  @NotNull
  public final String getSubject() {
    return subject;
  }

  private String number;

  /**
   * Get the number for this Summary.
   *
   * @return the number for this Summary
   */
  @NotNull
  public final String getNumber() {
    return number;
  }

  private String label;

  /**
   * Get the label for this Summary.
   *
   * @return the label for this Summary
   */
  @NotNull
  public final String getLabel() {
    return label;
  }

  /** Create an empty Summary. */
  public Summary() {}

  /**
   * Create a Summary with the provided fields.
   *
   * @param setSubject the department for this Summary
   * @param setNumber the number for this Summary
   * @param setLabel the label for this Summary
   */
  public Summary(@NonNull String setSubject, @NonNull String setNumber, @NotNull String setLabel) {
    subject = setSubject;
    number = setNumber;
    label = setLabel;
  }

  /** {@inheritDoc} */
  @NonNull
  @Override
  public String toString() {
    return subject + " " + number + ": " + label;
  }

  @Override
  public int compareTo(Summary o) {
    // sort first by number, then by subject
    if (Summary.this.number.compareTo(o.getNumber()) == 0) {
      return Summary.this.subject.compareTo(o.getSubject());
    }
    return Summary.this.number.compareTo(o.getNumber());
  }
}
