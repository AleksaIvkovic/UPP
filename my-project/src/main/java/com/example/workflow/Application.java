package com.example.workflow;

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
import java.util.List;

@SpringBootApplication (exclude = { SecurityAutoConfiguration.class })
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
      }
    } catch (Exception e) {
      System.out.println();
      e.printStackTrace();
    }
  }

  @PostConstruct
  private void InitGroups(){
    List<Group> groups = identityService.createGroupQuery()
            .groupIdIn("readers", "betaReaders", "writers", "editors", "committee", "lectors", "headCommittee", "sysAdmins", "headEditor").list();
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

      Group headEditorGroup = identityService.newGroup("headEditor");
      identityService.saveGroup(headEditorGroup);
    }
  }
}