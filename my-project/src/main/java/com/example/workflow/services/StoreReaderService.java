package com.example.workflow.services;

import com.example.workflow.intefaces.IGenre;
import com.example.workflow.intefaces.IReader;
import com.example.workflow.models.Genre;
import com.example.workflow.models.Reader;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoreReaderService implements JavaDelegate {
    @Autowired
    private IdentityService identityService;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Autowired
    private IGenre genreService;

    @Autowired
    private IReader readerService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        HashMap<String, Object> readerForm = (HashMap<String, Object>)execution.getVariable("newReader");
        boolean isBeta = readerForm.get("isBeta").equals("") ? false:(boolean)readerForm.get("isBeta");
        //boolean isBeta = (boolean)readerForm.get("isBeta");

        /*User user = identityService.newUser(readerForm.get("username").toString());
        user.setPassword(readerForm.get("password").toString());
        user.setFirstName(readerForm.get("firstname").toString());
        user.setLastName(readerForm.get("lastname").toString());
        user.setEmail(readerForm.get("email").toString());
        identityService.saveUser(user);*/

        Reader newReader = new Reader(
                readerForm.get("firstname").toString(),
                readerForm.get("lastname").toString(),
                readerForm.get("city").toString(),
                readerForm.get("country").toString(),
                readerForm.get("username").toString(),
                //passwordEncoder.encode(readerForm.get("password").toString()),
                readerForm.get("password").toString(),
                readerForm.get("email").toString(),
                isBeta
        );

        List<Genre> genres = new ArrayList<>();
        HashMap<String, Boolean> genresHM = (HashMap<String, Boolean>)(readerForm.get("genres"));
        for (Map.Entry mapElement: genresHM.entrySet()) {
            if ((boolean)mapElement.getValue()) {
                genres.add(genreService.getGenre(Long.parseLong(mapElement.getKey().toString())));
            }
        }

        newReader.setGenres(genres);

        HashMap<String, Object> betaGenresForm = null;
        List<Genre> betaGenres = new ArrayList<>();
        if (isBeta) {
            betaGenresForm = (HashMap<String, Object>)execution.getVariable("betaGenresForm");

            HashMap<String, Boolean> betaGenresHM = (HashMap<String, Boolean>)(betaGenresForm.get("betaGenres"));
            for (Map.Entry mapElement: betaGenresHM.entrySet()) {
                if ((boolean)mapElement.getValue()) {
                    betaGenres.add(genreService.getGenre(Long.parseLong(mapElement.getKey().toString())));
                }
            }
        }

        newReader.setBetaGenres(betaGenres);

        //Repository za Reader-a i Genre-ove
        readerService.storeReader(newReader);
        execution.setVariable("email", newReader.getEmail());
    }
}
