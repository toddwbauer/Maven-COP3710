package com.twbauer;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

  public static void main(String[] args)
      throws IOException, CsvValidationException, SQLException {

    // Create a new CsvParser and feed it the SEOExample CSV file, then print the results.
    CsvParser csvP = new CsvParser("src/main/resources/bookstore_report2.csv");
    csvP.printCsv();

    // Create a new Gson object. Use a JSON reader to parse the JSON, then use the Gson object
    // to read each row of the parsed JSON, convert each row into an AuthorParser object, and
    // finally return all of them as an array of AuthorParser objects.
    Gson gson = new Gson();
    JsonReader jread = new JsonReader(new FileReader("src/main/resources/authors.json"));
    AuthorParser[] authors = gson.fromJson(jread, AuthorParser[].class);

    // Print each author's name to test the Gson worked properly.
    for (var author : authors) {
      System.out.println(author.getName());
    }

    // Add the author and book information to the database.
    try {
      BookStoreDB bsdb = new BookStoreDB();
      bsdb.insertBooks(csvP);
      bsdb.insertAuthors(authors);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}
