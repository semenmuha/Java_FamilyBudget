package org.semen.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.semen.entities.User;
import org.semen.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Transactional
    public @NonNull User save(@NonNull User user){
        return userRepo.save(user);
    }

    public @NonNull User create(@NonNull User user){
        if (userRepo.existsByLogin(user.getLogin())){
            throw new EntityExistsException("Пользователь с логином " + user.getLogin() + " уже существует");
        }
        if (userRepo.existsByEmail(user.getEmail())){
            throw new EntityExistsException("Пользователь с email " + user.getEmail() + " уже существует");
        }

        user.setDateOfRegistration(LocalDate.now());
        return save(user);
    }

    public void deleteById (@NonNull Long userId){
        userRepo.deleteById(userId);
    }

    public @NonNull User getById(@NonNull Long userId){
        return userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с ID " + userId + " не найден"));
    }

    public @NonNull User update(@NonNull Long userId, @NonNull User newData){
        User user = getById(userId);
        userUpdate(user, newData);
        return save(user);
    }



    private void userUpdate(@NonNull User user, @NonNull User newData){
        Optional.ofNullable(newData.getLogin()).map(user::setLogin);
        Optional.ofNullable(newData.getEmail()).map(user::setEmail);
        Optional.ofNullable(newData.getName()).map(user::setName);
    }


}
