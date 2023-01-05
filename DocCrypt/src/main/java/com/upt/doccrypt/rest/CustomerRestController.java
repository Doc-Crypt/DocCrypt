package com.upt.doccrypt.rest;


import com.upt.doccrypt.dto.AuthenticationRequestDto;
import com.upt.doccrypt.dto.FolderDto;
import com.upt.doccrypt.dto.UserDto;
import com.upt.doccrypt.model.file.Document;
import com.upt.doccrypt.model.file.FileStatus;
import com.upt.doccrypt.model.file.Folder;
import com.upt.doccrypt.model.file.StackFolder;
import com.upt.doccrypt.model.user.Customer;
import com.upt.doccrypt.model.user.User;
import com.upt.doccrypt.repository.file_repository.FolderRepository;
import com.upt.doccrypt.repository.user_repository.CustomerRepository;
import com.upt.doccrypt.security.jwt.JwtTokenProvider;
import com.upt.doccrypt.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/api/v1/admin/")
@CrossOrigin("http://localhost:8080")
public class CustomerRestController {

    private final CustomerService customerService;
    private final FolderService folderService;
    private final DocumentService documentService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final CustomerRepository customerRepository;
    private final FolderRepository folderRepository;
    private final PublicStackFolderService stackFolderService;


    public CustomerRestController(CustomerService customerService, FolderService folderService, DocumentService documentService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, CustomerRepository customerRepository, FolderRepository folderRepository, PublicStackFolderService stackFolderService) {
        this.customerService = customerService;
        this.folderService = folderService;
        this.documentService = documentService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.customerRepository = customerRepository;
        this.folderRepository = folderRepository;
        this.stackFolderService = stackFolderService;
    }

    @PostMapping("customer_login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        String emailRegex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        try {
            Customer customer;
            String username = requestDto.getUsername();
            if(pattern.matcher(username).matches()){
                customer = customerService.getCustomerByEmail(username);
                username = customer.getUsername();
            }else customer = customerService.findByUsername(username);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            if (customer == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, customer.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("FullName", customer.getFirstName() + " " + customer.getLastName());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("customer_registration")
    public ResponseEntity registrationCustomer(@RequestBody UserDto requestDto) {
        if(userService.containUserByEmail(requestDto.getEmail())) throw new BadCredentialsException("Email already exist in DB");
        if(userService.findByUsername(requestDto.getUsername()) != null){
            requestDto.setUsername(requestDto.getUsername() + UUID.randomUUID());
        }
        if(requestDto.getPassword().isEmpty()) throw new BadCredentialsException("Password is empty");
        Customer customer = requestDto.toCustomer();
        customerService.register(customer);
        Map<String, String> response = new HashMap<>();
        response.put("Response", "Ok");
        response.put("username", requestDto.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("customer_creation_folder")
    public ResponseEntity createFolder(@RequestBody FolderDto folderDto) {
        Folder folder = folderDto.toFolder();
        folder = folderService.save(folder);
        Customer customer = customerService.findByUsername(folderDto.getOwnerUsername());
        customerService.addFolder(customer, folder);
        customerRepository.save(customer);
        Map<String, String> response = new HashMap<>();
        response.put("Response", "Ok");
        response.put("Owner", folderDto.getOwnerUsername());
        response.put("File name", folderDto.getFileName());
        return ResponseEntity.ok(response);
    }

    @PostMapping("customer_add_Document_to_Folder")
    public ResponseEntity<Folder> addDocumentInFolder(@RequestParam("file") MultipartFile file,
                                              @RequestParam("username") String username,
                                              @RequestParam("folderId") long folderId) throws Exception {

        Folder folder = getFolderFromCustomerById(username, folderId);
        Document document = new Document();
        document.setFile(file.getBytes());
        document.setStatus(FileStatus.PENDING);
        document.setFileName(file.getName());
        document.setUpdated(new Date());
        document.setCreated(new Date());

        document = documentService.save(document);
        folder.addDocumentInList(document);
        folder = folderRepository.save(folder);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    private Folder getFolderFromCustomerById(@RequestParam("username") String username, @RequestParam("folderId") long folderId) throws Exception {
        Customer customer = customerService.findByUsername(username);
        Folder folder = null;
        for (Folder personalListOfFolder : customer.getPersonalListOfFolders()) {
            if(personalListOfFolder.getId() == folderId){
                folder = personalListOfFolder;
                break;
            }
        }
        if(folder == null) throw new Exception("Folder not found");
        return folder;
    }

    @PostMapping("customer_post_Folder")
    public ResponseEntity<StackFolder> postFolderPublic(@RequestParam("username") String username,
                                                        @RequestParam("folderId") long folderId) throws Exception {

        Folder folder = getFolderFromCustomerById(username, folderId);
        StackFolder stackFolder = stackFolderService.post(StackFolder.createStackFolder(folder));
        return new ResponseEntity<>(stackFolder, HttpStatus.OK);
    }

}
