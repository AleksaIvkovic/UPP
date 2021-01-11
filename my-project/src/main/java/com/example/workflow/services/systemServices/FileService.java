package com.example.workflow.services.systemServices;

import com.example.workflow.intefaces.IFile;
import com.example.workflow.models.DTOs.FileDTO;
import com.example.workflow.models.DBs.SubmittedFile;
import com.example.workflow.repositories.FileRepository;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
public class FileService implements IFile {
    @Autowired
    private FileRepository fileRepository;

    @Override
    public void storeSubmittedFile(SubmittedFile newSubmittedFile) {
        fileRepository.save(newSubmittedFile);
    }

    @Override
    public String getContentType(HttpServletRequest request, Resource resource) {
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get("src\\main\\resources\\PDFFiles", fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new NotFoundException();
            }
        } catch (MalformedURLException ex) {
            throw new NotFoundException();
        }
    }

    @Override
    public ResponseEntity<?> download(String name) throws MalformedURLException {
        Path filepath = Paths.get("src\\main\\resources\\PDFFiles", name);

        File file = new File(filepath.toAbsolutePath().toString());

        UrlResource urlResource = new UrlResource("file:///" + filepath.toAbsolutePath().toString());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        return new ResponseEntity<>(urlResource, httpHeaders, HttpStatus.OK);
    }

    @Override
    public void savePDF(FileDTO fileDto, String taskId){
        MultipartFile multipartFile = fileDto.getFile();
        Path filepath = Paths.get(".\\src\\main\\resources\\PDFFiles", multipartFile.getOriginalFilename());

        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<SubmittedFile> getAllByProcessId(String processId) {
        return (ArrayList<SubmittedFile>)fileRepository.getAllByProcessId(processId);
    }


}
