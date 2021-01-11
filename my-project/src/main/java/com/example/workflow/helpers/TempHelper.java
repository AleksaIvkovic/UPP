package com.example.workflow.helpers;

import com.example.workflow.models.DBs.BookComment;
import com.example.workflow.models.DBs.PublishedBook;
import com.example.workflow.models.DBs.SysUser;
import com.example.workflow.services.systemServices.BookCommentService;
import com.example.workflow.services.systemServices.BookService;
import com.example.workflow.services.systemServices.SystemUserService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.identity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.HashMap;

public class TempHelper {

    public static int getFound(HashMap<String, Boolean> o) {
        int found = 0;
        for(Boolean value : o.values())
        {
            found = value ? found + 1: found;
        }
        return found;
    }

    public static void editCommentsAndHaveCommentedLists(IdentityService identityService,
                                                         RuntimeService runtimeService,
                                                         HashMap<String, Object> map,
                                                         String processInstanceId) {
        editListWithValueInMap(runtimeService, processInstanceId, map, "comments", "comment");
        editList(identityService, runtimeService, processInstanceId, "haveCommented");
    }

    private static User getLoggedInUser(IdentityService identityService) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SysUser sysUser = (SysUser) auth.getPrincipal();
        User user = identityService.createUserQuery().userId(sysUser.getUsername()).singleResult();
        return user;
    }

    public static void editList(IdentityService identityService,
                                RuntimeService runtimeService,
                                String processInstanceId,
                                String variableName) {
        ArrayList<User> list = (ArrayList<User>)runtimeService.getVariable(processInstanceId, variableName);
        list.add(getLoggedInUser(identityService));
        runtimeService.setVariable(processInstanceId, variableName, list);
    }

    public static void editListWithValueInMap(RuntimeService runtimeService,
                                              String processInstanceId,
                                              HashMap<String, Object> map,
                                              String variableName,
                                              String mapKey) {
        ArrayList<String> list = (ArrayList<String>)runtimeService.getVariable(processInstanceId, variableName);
        list.add(map.get(mapKey).toString());
        runtimeService.setVariable(processInstanceId, variableName, list);
    }

    public static void storeComment(String comment,
                                    User user,
                                    BookService bookService,
                                    String title,
                                    SystemUserService systemUserService,
                                    BookCommentService bookCommentService) {
        PublishedBook book = bookService.getBookByTitle(title);
        SysUser sysUser = systemUserService.getSystemUserByUsername(user.getId());
        BookComment bookComment = new BookComment(comment, book, sysUser);
        bookCommentService.StoreComment(bookComment);
        bookService.storeComment(book, bookComment);
    }
}
