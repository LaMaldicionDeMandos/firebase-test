package firebase.example.pasut.firebasetest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * Created by marcelo on 8/15/14.
 */
public class Tweet {
  private Long id;
  private String text;
  @JsonProperty("image_url")
  private String url;

  public Tweet(){}

  public Tweet(long id, String text, String url) {
    this.id = id;
    this.text = text;
    this.url = url;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Tweet)) {
      return false;
    }
    Tweet t = (Tweet)o;
    return t.id == id;
  }

  @Override
  public int hashCode() {
    return id.intValue();
  }

  @Override
  public String toString() {
    return String.format("{id: %d, text: %s, image_url: %s", id, text, url);
  }
}
