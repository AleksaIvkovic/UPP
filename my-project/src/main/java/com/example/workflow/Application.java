package com.example.workflow;

import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

@SpringBootApplication
public class Application {

  @Autowired
  private IdentityService identityService;

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

  @PostConstruct
  private void InitGroups(){
    List<Group> groups = identityService.createGroupQuery()
            .groupIdIn("readers", "betaReaders", "writers", "editors", "committee", "headCommittee", "sysAdmin").list();
    if (groups.isEmpty()) {

      Group readerGroup = identityService.newGroup("readers");
      identityService.saveGroup(readerGroup);

      Group betaReaderGroup = identityService.newGroup("betaReaders");
      identityService.saveGroup(betaReaderGroup);

      Group writerGroup = identityService.newGroup("writers");
      identityService.saveGroup(writerGroup);

      Group editorGroup = identityService.newGroup("editors");
      identityService.saveGroup(editorGroup);

      Group committeeGroup = identityService.newGroup("committee");
      identityService.saveGroup(committeeGroup);

      Group headCommitteeGroup = identityService.newGroup("headCommittee");
      identityService.saveGroup(headCommitteeGroup);

      Group sysAdminGroup = identityService.newGroup("sysAdmin");
      identityService.saveGroup(sysAdminGroup);
    }

    List<User> users = identityService.createUserQuery().userIdIn("reader1").list();
    if (users.isEmpty()) {

      registerInCamunda("reader1","pass","reader","reader","97ivkovic@gmail.com");

      identityService.createMembership("reader1", "readers");
    }
  }

  private void registerInCamunda(String username, String password, String firstname, String lastname, String email) {
    try {
      User camundaUser = identityService.newUser(username);
      camundaUser.setPassword(password);
      camundaUser.setFirstName(firstname);
      camundaUser.setLastName(lastname);
      camundaUser.setEmail(email);
      identityService.saveUser(camundaUser);
    } catch (Exception e) {
      System.out.println("User all ready exists");
    }
  }

}