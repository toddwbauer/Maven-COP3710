package com.twbauer;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * CsvParser is a tool to load, read, and print CSV files.
 */
public class CsvParser {

  private List fileRows = new ArrayList();

  /**
   * Default constructor.
   *
   * @param infile The CSV file to be read.
   * @throws IOException
   * @throws CsvValidationException
   */
  public CsvParser(String infile) throws IOException, CsvValidationException {

    // If the file exists, read it.
    if (checkFile(infile)) {
      readCsv(infile);
    }
  }

  /**
   *  Returns the rows of the CSV file that the CsvParser parsed.
   *
   * @return fileRows A List containing the rows within a CSV file.
   */
  protected List getRows() {
    return fileRows;
  }

  /**
   * Creates a CSVReader to parse the file, then adds each row in the file to
   * the list of rows in this class, i.e. fileRows.
   *
   * @param readInfile The CSV file to be read.
   * @throws IOException
   * @throws CsvValidationException
   */
  protected void readCsv(String readInfile) throws IOException, CsvValidationException {

    // Make an object to load the file, then an object to read the file, then an
    // object to read CVS files specifically.
    FileInputStream cvsStream = new FileInputStream(readInfile);
    InputStreamReader inputStream = new InputStreamReader(cvsStream,
        StandardCharsets.UTF_8);
    CSVReader reader = new CSVReader(inputStream);


    // Since rows of CSV files are stored as a collection of discrete fields, we must
    // make a String array to hold each field. The entirety of nextLine represents 1 row
    // in the CSV file.
    String[] nextLine;

    // Iterate over the CSV file, adding each row to the ArrayList of rows.
    while ((nextLine = reader.readNext()) != null) {
      fileRows.add(nextLine);
    }

    // Since we're done reading the file, we can close the reader.
    reader.close();
  }

  /*
  protected void printCsv() {

  }
  */

  /**
   * Prints the contents of the CSV file that are stored in the ArrayList fileRows.
   */
  protected void printCsv() {

    // For each row in the CSV file...
    for (Object row : fileRows) {

      // For each field in the current row...
      for (String field : (String[]) row) {

        // Print the contents of that field.
        System.out.print(field + ", ");
      }

      // After a row has been printed, start a new line.
      System.out.println("\b\b\n--------------------------");
    }
  }

  /**
   * Checks to see if the given CSV file path points to an existing file.
   *
   * @param checkInfile
   * @return
   */
  private boolean checkFile(String checkInfile) {
    if (!Files.exists(Paths.get(checkInfile))) {
      System.out.println("This file does not exist.");
      return false;
    }

    return true;
  }
}
