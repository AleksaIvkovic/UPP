package com.example.workflow;

import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

@SpringBootApplication//(exclude = { SecurityAutoConfiguration.class })
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
            .groupIdIn("readers", "betaReaders", "writers", "editors", "committee", "lectors", "headCommittee", "sysAdmins").list();
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

      Group lectorGroup = identityService.newGroup("lectors");
      identityService.saveGroup(lectorGroup);

      Group headCommitteeGroup = identityService.newGroup("headCommittee");
      identityService.saveGroup(headCommitteeGroup);

      Group sysAdminGroup = identityService.newGroup("sysAdmins");
      identityService.saveGroup(sysAdminGroup);
    }

    List<User> users = identityService.createUserQuery().userIdIn("editor1","editor2","committee1","committee2","committee3","lector","headCommittee","sysAdmin").list();
    if (users.isEmpty()) {

      registerInCamunda("editor1","pass","Aleksa","Ivkovic","upppomocninalog@gmail.com");
      registerInCamunda("editor2","pass","Aleksa","Ivkovic","upppomocninalog@gmail.com");
      registerInCamunda("committee1","pass","Aleksa","Ivkovic","upppomocninalog@gmail.com");
      registerInCamunda("committee2","pass","Aleksa","Ivkovic","upppomocninalog@gmail.com");
      registerInCamunda("committee3","pass","Aleksa","Ivkovic","upppomocninalog@gmail.com");
      registerInCamunda("lector","pass","Aleksa","Ivkovic","upppomocninalog@gmail.com");
      registerInCamunda("headCommittee","pass","Aleksa","Ivkovic","upppomocninalog@gmail.com");
      registerInCamunda("sysAdmin","pass","Aleksa","Ivkovic","upppomocninalog@gmail.com");

      identityService.createMembership("editor1", "editors");
      identityService.createMembership("editor2", "editors");
      identityService.createMembership("committee1", "committee");
      identityService.createMembership("committee2", "committee");
      identityService.createMembership("committee3", "committee");
      identityService.createMembership("lector", "lectors");
      identityService.createMembership("headCommittee", "headCommittee");
      identityService.createMembership("sysAdmin", "sysAdmins");
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