package com.twbauer;

public class AuthorParser {
  private String author_name;
  private String author_email;
  private String author_url;

  protected void setName(String newName) {
    this.author_name = newName;
  }
  protected String getName() {
    return author_name;
  }

  protected void setEmail(String newEmail) {
    this.author_email = newEmail;
  }
  protected String getEmail() {
    return author_email;
  }

  protected void setUrl(String newUrl) {
    this.author_url = newUrl;
  }
  protected String getUrl() {
    return author_url;
  }


}
