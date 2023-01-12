package com.upt.doccrypt.rest;


import com.upt.doccrypt.dto.AdminNotaryCandidateDto;
import com.upt.doccrypt.model.NotaryCandidate;
import com.upt.doccrypt.model.Status;
import com.upt.doccrypt.model.file.Document;
import com.upt.doccrypt.model.file.FileStatus;
import com.upt.doccrypt.model.file.Folder;
import com.upt.doccrypt.model.file.StackFolder;
import com.upt.doccrypt.model.user.Customer;
import com.upt.doccrypt.model.user.Notary;
import com.upt.doccrypt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/admin/")
@CrossOrigin("http://localhost:8080")
public class AdminRestControllerV1 {

    private final UserService userService;
    private final NotaryQueueService notaryQueueService;
    private final NotaryService notaryService;
    private final CustomerService customerService;
    private final DocumentService documentService;
    private final FolderService folderService;
    private final PublicStackFolderService stackFolderService;
    @Autowired
    public AdminRestControllerV1(UserService userService, NotaryQueueService notaryQueueService, NotaryService notaryService, CustomerService customerService, DocumentService documentService, FolderService folderService, PublicStackFolderService stackFolderService) {
        this.userService = userService;
        this.notaryQueueService = notaryQueueService;
        this.notaryService = notaryService;
        this.customerService = customerService;
        this.documentService = documentService;
        this.folderService = folderService;
        this.stackFolderService = stackFolderService;
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
        notaryQueueService.addCandidateToNotary(notaryCandidate);
        return new ResponseEntity<>("Approved successfully", HttpStatus.OK);
    }


    @GetMapping("/candidate_document/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getProveDocumentOfCandidate(@PathVariable long id) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/file.pdf");
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

    @GetMapping("/add_notary")
    @ResponseBody
    public ResponseEntity<Notary> addNotary(){
        Notary notary = new Notary();
        notary.setUsername("notary");
        notary.setFirstName("notary");
        notary.setLastName("notary");
        notary.setEmail("email");
        notary.setPassword("password");
        notary.setCreated(new Date());
        notary.setUpdated(new Date());
        notary.setStatus(Status.ACTIVE);
        notaryService.register(notary);
        return new ResponseEntity<>(notary, HttpStatus.OK);
    }

    @GetMapping("/add_customer")
    @ResponseBody
    public ResponseEntity<Customer> addCustomer(){
        Customer customer = new Customer();
        customer.setUsername("customer");
        customer.setFirstName("customer");
        customer.setLastName("customer");
        customer.setEmail("email@");
        customer.setPassword("password");
        customer.setCreated(new Date());
        customer.setUpdated(new Date());
        customer.setStatus(Status.ACTIVE);
        customerService.register(customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/add_Folder")
    @ResponseBody
    public ResponseEntity<Folder> addFolder(){
        Folder folder = new Folder();
        folder.setCreated(new Date());
        folder.setUpdated(new Date());
        folder.setStatus(FileStatus.PENDING);
        folder.setFileName("new folder 1");
        folderService.save(folder);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    @GetMapping("/add_Document")
    @ResponseBody
    public ResponseEntity<Document> addDocument(){
        Document document = new Document();
        document.setCreated(new Date());
        document.setUpdated(new Date());
        document.setStatus(FileStatus.PENDING);
        document.setFileName("new document 1");
        document.setFile(new byte[10]);
        documentService.save(document);
        return new ResponseEntity<>(document, HttpStatus.OK);
    }

    @GetMapping("/add_Document_to_folder")
    @ResponseBody
    public ResponseEntity<Folder> addDocToFolder(){
        Folder folder = folderService.getAll().get(0);
        Document document = documentService.getAll().get(0);
        folderService.addDocumentInFolder(folder, document);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    @GetMapping("/add_Folder_to_folder_stack")
    @ResponseBody
    public ResponseEntity<List<StackFolder>> addFolderToStackFolder() throws Exception {
        Folder folder = folderService.getAll().get(0);
        StackFolder stackFolder = new StackFolder();
        stackFolder.setCreated(new Date());
        stackFolder.setUpdated(new Date());
        stackFolder.setFolder(folder);

        stackFolderService.post(stackFolder);
        return new ResponseEntity<>(stackFolderService.getAll(), HttpStatus.OK);
    }
}
