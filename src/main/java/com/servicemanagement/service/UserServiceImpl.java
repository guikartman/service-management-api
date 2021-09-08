package com.servicemanagement.service;

import com.servicemanagement.domain.User;
import com.servicemanagement.dto.UserNewDTO;
import com.servicemanagement.repository.UserRepository;
import com.servicemanagement.service.exceptions.EmailNotFoundException;
import com.servicemanagement.service.exceptions.UserAlreadyPresentException;
import com.servicemanagement.service.exceptions.WrongPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository repository;
    private final EmailService emailService;
    private final Random rand;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, EmailService emailService){
        this.repository = repository;
        this.emailService = emailService;
        this.rand = new Random();
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User createNewUser(UserNewDTO userNewDTO) {
        Optional<User> userOptional = repository.findByEmail(userNewDTO.getEmail());
        if (userOptional.isEmpty()){
             var user = new User(userNewDTO.getName(), userNewDTO.getEmail(), new BCryptPasswordEncoder().encode(userNewDTO.getPassword()));
             var finalUser = repository.save(user);
             emailService.sendEmail(finalUser, "Usopper app confirmação de conta.", "Parabéns, você criou sua conta no Usopper app!!!");
             return finalUser;
        }else {
            throw new UserAlreadyPresentException(userNewDTO.getEmail());
        }
    }

    @Override
    public void retrievePassword(String email) {
        User user = repository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException(email));
        String newPass = newPassword();
        user.setPassword(bCryptPasswordEncoder.encode(newPass));
        repository.save(user);
        emailService.sendEmail(user, "Usopper app email de recuperação de senha.",  String.format("Sua nova senha é: %s %nLembre-se de trocar sua senha ao entrar no aplicativo.", newPass));
    }

    @Override
    public void changePassword(String email, String oldPassword,String newPassword) {
        User user = repository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException(email));
        if (bCryptPasswordEncoder.matches(oldPassword,user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            repository.save(user);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            emailService.sendEmail(user, "Senha alterada no Usopper app.", String.format("Sua senha foi alterada, data/hora da modificação: %s", sdf.format(new Date(System.currentTimeMillis()))));
        }else {
            throw new WrongPasswordException();
        }
    }

    @Override
    public void updateUsername(String email, String userName) {
        User user = repository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException(email));
        user.setName(userName);
        repository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException(email));
    }

    private String newPassword() {
        var vet = new char[10];
        for (var i=0; i<10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        var opt = rand.nextInt(3);
        if (opt == 0) {
            return (char) (rand.nextInt(10) + 48);
        }
        else if (opt == 1) {
            return (char) (rand.nextInt(26) + 65);
        }
        else {
            return (char) (rand.nextInt(26) + 97);
        }
    }
}
