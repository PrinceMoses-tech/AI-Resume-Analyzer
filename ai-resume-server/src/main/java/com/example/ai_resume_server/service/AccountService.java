package com.example.ai_resume_server.service;

import com.example.ai_resume_server.models.Account;
import com.example.ai_resume_server.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepo repo;

    public Account register(Account account){
        return repo.save(account);
    }
    public boolean login(Account account){
        Account details=repo.findByEmail(account.getEmail());
        return (details.getEmail().equals(account.getEmail()))&&(details.getPassword().equals(account.getPassword()));
    }
    public Account getUserByEmail(String email){
        return repo.findByEmail(email);
    }
    public Account updateUser(String emai,Account updatedAccount){
        Account acc=repo.findByEmail(emai);
        if(updatedAccount.getEmail()!=null){

        acc.setEmail(updatedAccount.getEmail());
        }
        if(updatedAccount.getUsername()!=null){

        acc.setUsername(updatedAccount.getUsername());
        }
        if(updatedAccount.getPassword()!=null){
            acc.setPassword(updatedAccount.getPassword());
        }
        repo.save(acc);
        return acc;
    }
}
