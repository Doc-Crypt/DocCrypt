package com.upt.doccrypt.rest;


import com.upt.doccrypt.dto.AdminNotaryCandidateDto;
import com.upt.doccrypt.dto.AdminUserDto;
import com.upt.doccrypt.model.NotaryCandidate;
import com.upt.doccrypt.model.User;
import com.upt.doccrypt.service.NotaryQueueService;
import com.upt.doccrypt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/admin/")
@CrossOrigin("http://localhost:8080")
public class AdminRestControllerV1 {

    private final UserService userService;
    private final NotaryQueueService notaryQueueService;
    @Autowired
    public AdminRestControllerV1(UserService userService, NotaryQueueService notaryQueueService) {
        this.userService = userService;
        this.notaryQueueService = notaryQueueService;
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        AdminUserDto result = AdminUserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping(value = "candidates")
    public ResponseEntity<List<AdminNotaryCandidateDto>> getAllNotaryCandidates(){
        List<AdminNotaryCandidateDto> adminNotaryCandidateDtoList = new ArrayList<AdminNotaryCandidateDto>();
        notaryQueueService.getAll().forEach(candidate -> {
            adminNotaryCandidateDtoList.add(AdminNotaryCandidateDto.fromNotaryCandidate(candidate));
        });
        return new ResponseEntity<>(adminNotaryCandidateDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "candidates/delete/{id}")
    public ResponseEntity<String> deleteCandidate(@PathVariable long id){
        NotaryCandidate notaryCandidate = notaryQueueService.getNotaryCandidateById(id);
        notaryQueueService.deleteCandidate(notaryCandidate);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }

    @GetMapping(value = "candidates/approve/{id}")
    public ResponseEntity<String> approveCandidate(@PathVariable long id){
        NotaryCandidate notaryCandidate = notaryQueueService.getNotaryCandidateById(id);
        notaryQueueService.addCandidateToUsers(notaryCandidate);
        return new ResponseEntity<>("Approved successfully", HttpStatus.OK);
    }


    @GetMapping("/candidate_document/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getProveDocumentOfCandidate(@PathVariable long id) throws IOException {
        NotaryCandidate notaryCandidate = notaryQueueService.getNotaryCandidateById(id);
        byte[] bytes = notaryCandidate.getProveDocument();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "pdf1.pdf";
        headers.add("content-disposition", "inline;filename=" + filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        return response;
    }
}
