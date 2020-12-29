package com.example.workflow.services.db;

import com.example.workflow.models.PublishedBook;
import com.example.workflow.models.SysUser;
import com.example.workflow.services.systemServices.BookService;
import com.example.workflow.services.systemServices.SystemUserService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GetBetaReadersService implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    private BookService bookService;

    @Autowired
    private SystemUserService systemUserService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ArrayList<User> allBetaReaders = (ArrayList<User>) identityService.createUserQuery().memberOfGroup("betaReaders").list();
        PublishedBook publishedBook = bookService.GetBookByTitle(execution.getVariable("bookTitle").toString());
        ArrayList<User> chosenBetaReaders = new ArrayList<User>();
        ArrayList<String> betaReadersUsernames = new ArrayList<String>();

        for (User u : allBetaReaders){
            SysUser sysUser = systemUserService.getSystemUserByUsername(u.getId());
            if(sysUser.getBetaGenres().contains(publishedBook.getGenre())){
                chosenBetaReaders.add(u);
                betaReadersUsernames.add(sysUser.getUsername());
            }
        }

        execution.setVariable("chosenBetaReaders", chosenBetaReaders);
    }
}
