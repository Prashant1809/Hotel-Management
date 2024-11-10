package com.example.demo.controller;

import com.example.demo.entity.BackupCode;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("user")
public class ForgotPasswordController {

    @Autowired
    private UserRepository userRepository;

 // Show forgot password form
    @GetMapping("/forgotPassword")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("user", new User());
        return "forgotPassword";
    }
    @GetMapping("/resetPassword")
    public String showResetPasswordForm(Model model, @ModelAttribute("user") User user) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "resetPassword";
    }

    // Process forgot password form submission
    @PostMapping("/forgotPassword")
    public String processForgotPassword(@ModelAttribute("user") User user, Model model) {
        System.out.println(user.getEmail()); // Optional: Print email for debugging
        User userFromDb = userRepository.findByEmail(user.getEmail());
        System.out.println(userFromDb.getBackupCodes()); // Optional: Print email for debugging
        System.out.println(user.getBackupCodes()); // Optional: Print email for debugging

        if (userFromDb != null && validateBackupCode(userFromDb.getBackupCodes(), user.getBackupCodes())) {
            model.addAttribute("user", userFromDb);
            return "redirect:/resetPassword"; 
        } else {
            model.addAttribute("error", "Invalid email or backup code");
            return "forgotPassword";
        }
    }

    private boolean validateBackupCode(List<BackupCode> backupCodesFromDb, List<BackupCode> backupCodesFromForm) {
        // Extract codes from the backup codes lists
        List<String> codesFromDb = backupCodesFromDb.stream()
                .map(BackupCode::getCode)
                .collect(Collectors.toList());

        List<String> codesFromForm = backupCodesFromForm.stream()
                .map(BackupCode::getCode)
                .collect(Collectors.toList());

        // Compare the lists of codes
        return codesFromDb.containsAll(codesFromForm);
    }


	
    @PostMapping("/resetPassword")
    public String resetPassword(@ModelAttribute("user") User user, @RequestParam ("password") String newPassword) {
        User userToUpdate = userRepository.findByEmail(user.getEmail());
        if (userToUpdate != null) {
            userToUpdate.setPassword(newPassword); 
            userRepository.save(userToUpdate); 
            return "login"; 
        }
        return "error"; 
    }



}
