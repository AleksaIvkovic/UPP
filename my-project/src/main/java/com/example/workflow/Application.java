package com.example.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {

    SpringApplication.run(Application.class);


    String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=uppDB;integratedSecurity=true;";

    try {
      System.out.print("Connecting to SQL Server ... ");
      try (Connection connection = DriverManager.getConnection(connectionUrl)) {
        System.out.println("Done.");

        /*
        System.out.print("Dropping and creating database 'SampleDB' ... ");
        String sql = "DROP DATABASE IF EXISTS [SampleDB]; CREATE DATABASE [SampleDB]";
        try (Statement statement = connection.createStatement()) {
          statement.executeUpdate(sql);
          System.out.println("Done.");
        }

        // Create a Table and insert some sample data
        System.out.print("Creating sample table with data, press ENTER to continue...");
        System.in.read();
        sql = new StringBuilder().append("USE SampleDB; ").append("CREATE TABLE Employees ( ")
                .append(" Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY, ").append(" Name NVARCHAR(50), ")
                .append(" Location NVARCHAR(50) ").append("); ")
                .append("INSERT INTO Employees (Name, Location) VALUES ").append("(N'Jared', N'Australia'), ")
                .append("(N'Nikita', N'India'), ").append("(N'Tom', N'Germany'); ").toString();
        try (Statement statement = connection.createStatement()) {
          statement.executeUpdate(sql);
          System.out.println("Done.");
        }
        */

      }
    } catch (Exception e) {
      System.out.println();
      e.printStackTrace();
    }



  }

}