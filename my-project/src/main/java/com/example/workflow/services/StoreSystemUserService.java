package com.example.workflow.services;

import com.example.workflow.intefaces.IGenre;
import com.example.workflow.intefaces.ISystemUser;
import com.example.workflow.models.Genre;
import com.example.workflow.models.SysUser;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StoreSystemUserService implements JavaDelegate {
    @Autowired
    private IdentityService identityService;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Autowired
    private IGenre genreService;

    @Autowired
    private ISystemUser systemUserService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        HashMap<String, Object> systemUserForm = (HashMap<String, Object>)execution.getVariable("newSysUser");
        boolean isBeta = false;
        String group = "";
        //boolean isBeta = (boolean)systemUserForm.get("isBeta");
        String role = execution.getVariable("systemUserRole").toString();

        if (role.equals("READER")) {
            isBeta = systemUserForm.get("isBeta").equals("") ? false:(boolean)systemUserForm.get("isBeta");
            if(isBeta){
                group = "betaReaders";
            }
            else{
                group = "readers";
            }
        }
        else{
            group = "writers";
        }

        User user = identityService.newUser(systemUserForm.get("username").toString());
        user.setPassword(systemUserForm.get("password").toString());
        user.setFirstName(systemUserForm.get("firstname").toString());
        user.setLastName(systemUserForm.get("lastname").toString());
        user.setEmail(systemUserForm.get("email").toString());
        identityService.saveUser(user);
        identityService.createMembership(user.getId(), group);

        SysUser newSysUser = new SysUser(
                systemUserForm.get("firstname").toString(),
                systemUserForm.get("lastname").toString(),
                systemUserForm.get("city").toString(),
                systemUserForm.get("country").toString(),
                systemUserForm.get("username").toString(),
                //passwordEncoder.encode(systemUserForm.get("password").toString()),
                systemUserForm.get("password").toString(),
                systemUserForm.get("email").toString(),
                role //Dodati isBeta vrednost
        );


        List<Genre> genres = new ArrayList<>();
        HashMap<String, Boolean> genresHM = (HashMap<String, Boolean>)(systemUserForm.get("genres"));
        for (Map.Entry mapElement: genresHM.entrySet()) {
            if ((boolean)mapElement.getValue()) {
                genres.add(genreService.getGenre(Long.parseLong(mapElement.getKey().toString())));
            }
        }

        newSysUser.setGenres(genres);

        if (isBeta) {
            newSysUser.setBeta(true);
            HashMap<String, Object> betaGenresForm = null;
            List<Genre> betaGenres = new ArrayList<>();

            betaGenresForm = (HashMap<String, Object>)execution.getVariable("betaGenresForm");

            HashMap<String, Boolean> betaGenresHM = (HashMap<String, Boolean>)(betaGenresForm.get("betaGenres"));
            for (Map.Entry mapElement: betaGenresHM.entrySet()) {
                if ((boolean)mapElement.getValue()) {
                    betaGenres.add(genreService.getGenre(Long.parseLong(mapElement.getKey().toString())));
                }
            }

            newSysUser.setBetaGenres(betaGenres);
        }

        //Repository za SysUser-a i Genre-ove
        try {
            systemUserService.storeSystemUser(newSysUser);
            execution.setVariable("invalidSave", false);
        } catch (Exception e) {
            execution.setVariable("invalidSave", true);
            throw new Exception("Something went wrong");
            //Poslati na front da je nesto poslo naopako
        }

        String token = UUID.randomUUID().toString();
        systemUserService.createVerificationToken(newSysUser, token);

        execution.setVariable("email", newSysUser.getEmail());
        execution.setVariable("verificationToken", token);
    }
}
