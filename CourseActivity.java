package edu.illinois.cs.cs124.ay2023.mp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.illinois.cs.cs124.ay2023.mp.R;
import edu.illinois.cs.cs124.ay2023.mp.application.CourseableApplication;
import edu.illinois.cs.cs124.ay2023.mp.models.Course;
import edu.illinois.cs.cs124.ay2023.mp.models.Rating;
import edu.illinois.cs.cs124.ay2023.mp.models.Summary;
import edu.illinois.cs.cs124.ay2023.mp.network.Client;

public class CourseActivity extends AppCompatActivity {
  private Course course;

  private RatingBar ratingBar;
  private Button submitRatingButton;

  /**
   * @noinspection checkstyle:Indentation
   */
  @SuppressLint("MissingInflatedId")
  @Override
  protected void onCreate(@Nullable Bundle unused) {
    super.onCreate(unused);
    Client client = Client.start();
    // Load this activity's layout and set the title
    setContentView(R.layout.activity_course);
    TextView descriptionTextView = findViewById(R.id.description);
    Intent intent = getIntent();
    String str = intent.getStringExtra("summary");
    ObjectMapper mapper = new ObjectMapper();
    RatingBar rb = findViewById(R.id.rating);
    Summary summary;
    try {
      summary = mapper.readValue(str, Summary.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    Summary sum2 = summary;
    Summary sum;

    try {
      sum = mapper.readValue(str, Summary.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    Rating rates = new Rating();
    CourseableApplication application = (CourseableApplication) getApplication();
    application
        .getClient()
        .getCourse(
            sum,
            (result) -> {
              try {
                course = result.getValue();
                runOnUiThread(
                    () -> {
                      descriptionTextView.setText(
                          course.toString() + " " + course.getDescription());
                    });
              } catch (Exception e) {
                throw new RuntimeException(e);
              }
            });

    submitRatingButton = findViewById(R.id.submitRatingButton);
    rb.setOnRatingBarChangeListener(
        new RatingBar.OnRatingBarChangeListener() {
          /**
           * @noinspection checkstyle:ParameterAssignment, checkstyle:HiddenField ,
           *     checkstyle:HiddenField
           */
          @Override
          public void onRatingChanged(RatingBar ratingBar5, float rating, boolean fromUser) {
            Rating r = new Rating(sum2, rating);
            client.postRating(
                r,
                callBack -> {
                  if (callBack.getException() != null) {
                    System.out.println("no exception!!!!");
                  }
                });
            // do post here
          }
        });

    client.getRating(
        sum2,
        callBack -> {
          Rating r2 = callBack.getValue();
          runOnUiThread(
              () -> {
                rb.setRating(r2.getRating());
              });
        });

    // once we have summary, make the request using the client for course details
    // once the request completes, update the UI with details about the course
  }
}

// class SubmitRatingTask extends AsyncTask<Float, Void, String> {
//
//  @Override
//  protected String doInBackground(Float... params) {
//    float rating = params[0];
//    return postRatingToServer(rating);
//  }
//
//  @Override
//  protected void onPostExecute(String result) {
//    // Handle the server response (if needed)
//    // This runs on the UI thread
//    // Example: display a toast message or update UI
//  }
//
//  private String postRatingToServer(Rating rating) {
//    try {
//      // Construct the URL for the postRating endpoint
//      String postRatingUrl = CourseableApplication.SERVER_URL
//          + "/rating/"
//          + rating.getSummary().getSubject() + "/" + rating.getSummary().getNumber();
//          //URLEncoder.encode(String.valueOf(rating), "UTF-8");
//
//      // Open a connection to the URL
//      URL url = new URL(postRatingUrl);
//      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//      // Set the request method to POST
//      connection.setRequestMethod("POST");
//
//      // Get the response from the server
//      int responseCode = connection.getResponseCode();
//      if (responseCode == HttpURLConnection.HTTP_OK) {
//        BufferedReader in = new BufferedReader(new
// InputStreamReader(connection.getInputStream()));
//        StringBuilder response = new StringBuilder();
//        String line;
//
//        while ((line = in.readLine()) != null) {
//          response.append(line);
//        }
//        in.close();
//
//        return response.toString();
//      } else {
//        return "Error: " + responseCode;
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//      return "Error: " + e.getMessage();
//    }
//  }
// }
