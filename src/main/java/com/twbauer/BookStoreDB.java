package com.twbauer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
// just to check if these are found

class BookStoreDB {


  /**
   * The connection to the database.
   */
  private Connection conn = null;

  /**
   * A prepared SQL statement to inject into the database.
   */
  private PreparedStatement ps = null;

  /**
   * An object to hold the results of a SQL query.
   */
  private ResultSet rs = null;

  /**
   * Tracks if the program is connected to a database.
   */
  private boolean isConnectedToDB;

  /**
   * Default constructor.
   */
  BookStoreDB() throws SQLException {
    connectToDB();
  }

  /**
   * Attempts to connect to the database.
   */
  private void connectToDB() throws SQLException {

      // Establish a connection to the database and flag it as connected.
      conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/BookStoreDB.db");
      isConnectedToDB = true;
  }

  /**
   * Insert a new record into the author table of the BookStoreDB database.
   *
   * @param authors A list of authors and associated information. Authors are parsed from a
   *                JSON file via Gson, which creates an Author object representing each
   *                line of the JSON, then stores them in an array of Authors.
   */
  void insertAuthors(AuthorParser[] authors) throws SQLException {
    System.out.println("Inserting record(s) into author table...");

    // Prepare a statement to insert author information into the author table.
    // Note that we do not pass values directly, as this exposes the SQL to
    // injection, and it prevents little Bobby Tables from deleting all your records.
    // https://imgs.xkcd.com/comics/exploits_of_a_mom.png
    ps = conn.prepareStatement("INSERT INTO author" +
        "(author_name, author_email, author_url)" +
        "VALUES (?, ?, ?)");

    // For each author in the list of authors, insert author information into the database.
    for (var author : authors) {

      // Since name is a primary key, it is required, and blank field checks are unnecessary.
      ps.setString(1, author.getName());

      // The database requires unique emails, so we must make sure to pass null if the email field
      // is empty. If we get unique key violations if more than one record has an empty email value.
      if (author.getEmail().isEmpty()) {
        ps.setString(2, null);
      } else {
        ps.setString(2, author.getEmail());
      }

      // The above comment applies to urls as well.
      if (author.getUrl().isEmpty()) {
        ps.setString(3, null);
      } else {
        ps.setString(3, author.getUrl());
      }
      ps.executeUpdate();
    }

    System.out.println("Inserted record(s) into author table.");
  }

  /**
   * Insert a new record into the book table of the BookStoreDB database.
   *
   * @param booksCsv The CsvParser object that contains records parsed from the books CSV. These
   *                 are stored in a List which can be accessed via .getRows(). Within the list,
   *                 each entry is a generic Object containing fields that can be converted to
   *                 their corresponding data types in the database.
   */
  void insertBooks(CsvParser booksCsv) throws SQLException {
    System.out.println("Inserting record(s) into book table...");

    // See prepareStatement in insertAuthors(). In addition, the CSV file has fields in different
    // order and is even missing fields. Luckily, with a prepared statement, we can order the
    // insertion values any way we like. If we order the insertion in the same order as the CSV,
    // we can use a for loop to iterate through the values and store them in the right fields.
    ps = conn.prepareStatement("INSERT INTO book" +
        "(isbn, book_title, author_name, publisher_name)" +
        "VALUES (?, ?, ?, ?)");

    // Get the rows that were parsed from the CSV file.
    List rows = booksCsv.getRows();

    // Remove the header line from the CSV.
    rows.remove(0);

    // For each row that was parsed, i.e. each book being inserted...
    for (Object row : rows) {

      // Since the parser stores the information as a generic Object, we must cast it to its
      // proper type. All of these fields are Strings, so we can convert the entire array.
      String[] book = (String[]) row;

      // For each value in the prepared statement, insert the corresponding field of the row.
      // This is the for loop mentioned in the prepare statement comment.
      for (int i = 0; i < 4; i++) {
        ps.setString(i+1, book[i]);
      }

      /*
      // This is the way it would have to be done if insertion order is not customized in the
      // prepared statement.
      ps.setString(1, book[0]);
      ps.setString(2, book[1]);
      ps.setString(3, book[2]);
      ps.setString(4, book[3]);
      */

      ps.executeUpdate();
    }

    System.out.println("Inserted record(s) into book table.");
  }

}
