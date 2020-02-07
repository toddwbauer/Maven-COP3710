package com.twbauer;

import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.sql.SQLException;
import org.h2.command.dml.Delete;

public class Main {

    public static void main(String[] args)
        throws IOException, CsvValidationException, SQLException {

      // Create a new CsvParser and feed it the SEOExample CSV file.
      CsvParser csvP = new CsvParser("src/main/resources/SEOExample.csv");

      // Now that we can read the file, we can print the file.
      csvP.printCsv();

      System.out.println("\n\n\n");

      // Do a brief demonstration to show the database is accessible.
      ProductManager pm = new ProductManager();
      pm.selectAll();
      pm.closeCon();
    }


}
