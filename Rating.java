package edu.illinois.cs.cs124.ay2023.mp.models;

public class Rating extends Course {
  public static final float NOT_RATED = -1.0f;

  /**
   * @noinspection checkstyle:HiddenField
   */
  private float rating = NOT_RATED;

  public void setRating(float r) {
    this.rating = r;
  }

  public Summary getSummary() {
    return summary;
  }

  private Summary summary;

  public Rating(Summary setSummary, float setRating) {
    summary = setSummary;
    rating = setRating;
  }

  public Rating() {}

  public float getRating() {
    return rating;
  }
}
