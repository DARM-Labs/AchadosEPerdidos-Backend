package com.darm.ifce.achadoseperdidos.service;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.darm.ifce.achadoseperdidos.DTO.AuthenticationResponse;
import com.darm.ifce.achadoseperdidos.DTO.person.PersonRequest;
import com.darm.ifce.achadoseperdidos.DTO.user.UserRecoveryLoginDTO;
import com.darm.ifce.achadoseperdidos.DTO.user.UserRecoveryPasswordDTO;
import com.darm.ifce.achadoseperdidos.DTO.user.UserRequestDTO;
import com.darm.ifce.achadoseperdidos.DTO.user.UserResponse;
import com.darm.ifce.achadoseperdidos.DTO.user.UserUpdateDTO;
import com.darm.ifce.achadoseperdidos.DTO.user.VerificationCodeDTO;
import com.darm.ifce.achadoseperdidos.exceptions.ConflictException;
import com.darm.ifce.achadoseperdidos.exceptions.ResourceNotFoundException;
import com.darm.ifce.achadoseperdidos.model.Person;
import com.darm.ifce.achadoseperdidos.model.User;
import com.darm.ifce.achadoseperdidos.model.enums.PermissionTypeEnum;
import com.darm.ifce.achadoseperdidos.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JavaMailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    public UserService(UserRepository userRepository, JavaMailSender emailSender,PasswordEncoder passwordEncoder,AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.emailSender = emailSender;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
    }

    public User save(UserRequestDTO userRequest) {

        if (existsByLogin(userRequest.getLogin())) {
            throw new ConflictException("The  email provided is already being used.");
        }
        User newUser = userRequest.toUser();
        return this.userRepository.save(newUser);

    }

    public AuthenticationResponse createUser(PersonRequest personModel, Person person){
        if (userRepository.existsByLogin(personModel.email())) {
            throw new ConflictException("This email is already being used.");
        }
        User user = new User();
        user.setLogin(personModel.email());
        user.setPassword(passwordEncoder.encode(personModel.password()));
        user.setPermission(List.of(PermissionTypeEnum.ROLE_USER));
        user.setPerson(person);
        user = this.saveUser(user);

        return authenticationService.registerUser(user);
    }

    public List<User> search() {
        return this.userRepository.findAll();
    }

    public User searchByID(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The user was not found in the database, " +
                        "please check the registered user."));
    }

    public User findByLogin(String login) {
        return this.userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("No user with that email was found in the database, " +
                        "check the registered users."));
    }


    public void userRecoveryPassword(UserRecoveryLoginDTO userRecoveryLoginDTO ) throws MessagingException {
        
        try{
            User user = findByLogin(userRecoveryLoginDTO.getLogin());
        String email = user.getLogin();
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        
        String verificationCode = generateVerificationCode();
        if (user.getVerificationCode() != null) {
            while (user.getVerificationCode().equals(verificationCode)) {
                verificationCode = generateVerificationCode();
            }
        }

        user.setVerificationCode(verificationCode);
        user = this.saveUser(user);

        String htmlContent = "<html><body style='background-color: #8E43ED; color: white; text-align: center; padding-top:20px;'>"
                + "<h2 style='color: white;font-size: 24px;'>Recuperação de Senha</h2>"
                + "<p style='color: white;font-size: 18px;'>Olá,</p>"
                + "<p style='color: white;font-size: 18px;'>Aqui está o código de verificação para recuperar sua senha:</p>"
                + "<p style='font-size: 26px; font-weight: bold; color: white;'>" + verificationCode + "</p>"
                + "<p style='font-size: 18px; color: white;padding-bottom:20px;'>Siga os passos no sistema para alterar sua senha atual.</p>"
                + "</body></html>";

        helper.setTo(email);
        helper.setSubject("Email para recuperação de Senha");
        helper.setText("Seu cliente de e-mail não suporta HTML. Por favor, visualize este e-mail em um cliente de e-mail compatível com HTML.", htmlContent);
        helper.setFrom("seu-email@gmail.com");
        
        emailSender.send(mimeMessage);

            
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email not sent");
        }

    }

    public UserResponse verificationCode(VerificationCodeDTO code){
        return this.findByVerificationCode(code.getCode());
    }

    private UserResponse findByVerificationCode(String code) {
        User user = this.userRepository.findByVerificationCode(code)
        .orElseThrow(() -> new ResourceNotFoundException("No user with that verification code was found in the database, " +
                        "check the registered users."));
        UserResponse response = new UserResponse(user.getId());
        return response;
    }

    public User update(Long id, UserUpdateDTO requestDTO) {
        User oldUser = this.searchByID(id);

        if (!oldUser.getLogin().equals(requestDTO.getLogin())
                && this.existsByLogin(requestDTO.getLogin())) {

            throw new ConflictException("The email provided is already being used.");
        }
        User newUser = requestDTO.toUser(id);
        newUser.setLogin(requestDTO.getLogin());
        return userRepository.save(this.fillUpdateUser(oldUser, newUser));
    }

    public Boolean delete(Long id) {
        User user = this.searchByID(id);
        this.userRepository.delete(user);
        return true;

    }

    public User searchByLogin(String login) {
        return this.findByLogin(login);
    }

    private User fillUpdateUser(User oldUser, User newUser) {
        newUser.setPassword(oldUser.getPassword());
        return newUser;
    }

    public boolean existsByLogin(String login) {
        Optional<User> exist = this.userRepository.findByLogin(login);
        return exist.isPresent();
    }

    public boolean existsByID(Long id) {
        Optional<User> exist = this.userRepository.findById(id);
        return exist.isPresent();
    }

    public String generateVerificationCode() {
        String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$&";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(CHARSET.length());
            sb.append(CHARSET.charAt(index));
        }
        return sb.toString();
    }

    public User updatePassword(UserRecoveryPasswordDTO passwordDTO) {

        if(!passwordDTO.getPassword().equals(passwordDTO.getSecondPassword())){
            throw new ConflictException("Different passwords");
        }
        User user = this.searchByID(passwordDTO.getUserId());
        user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
        this.saveUser(user);
        
        return user;
    }

    public User findByPersonId(Long id) {
        return this.userRepository.findByPersonId(id)
                .orElseThrow(() -> new ResourceNotFoundException("The user was not found in the database, " +
                        "please check the registered user."));
    }

    public User saveUser(User user) {
        return this.userRepository.save(user);
    }
    
    @Transactional
    public void deleteByPersonId(Long id) {
        this.userRepository.deleteByPersonId(id);
    }
}
