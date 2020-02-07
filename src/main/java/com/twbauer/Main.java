package com.twbauer;

import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, CsvValidationException {

      // Create a new CsvParser and feed it the SEOExample CSV file.
      CsvParser csvP = new CsvParser("src/main/resources/SEOExample.csv");

      // Now that we can read the file, we can print the file.
      csvP.printCsv();
    }
}
