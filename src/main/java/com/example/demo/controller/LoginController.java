


///////////////////////////////////////////////////////
package com.example.demo.controller;

import com.example.demo.entity.BackupCode;
import com.example.demo.entity.BackupCode;
import com.example.demo.entity.HotelRoom;
import com.example.demo.entity.User;
import com.example.demo.repository.BackupCodeRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BackupCodeRepository backupCodeRepo;

    @Autowired
    private HotelRepository hotelRepository;
    
//    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/")
    public String login(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/userLogin")
    public String loginUser(@ModelAttribute("user") User user, Model model) {
        User userFromDb = userRepo.findByUserNameOrEmail(user.getUserName(), user.getUserName());
        if (userFromDb != null && userFromDb.getPassword().equals(user.getPassword())) {
            List<BackupCode> backupCodes = backupCodeRepo.findByUserId(userFromDb.getUserId());
            if (backupCodes.isEmpty()) {
                backupCodes = generateBackupCodes(userFromDb.getUserId());
                backupCodeRepo.saveAll(backupCodes);
                model.addAttribute("backupCodes", backupCodes);
                model.addAttribute("user", userFromDb); 
                return "showBackupCodes";
            } else {
                model.addAttribute("user", userFromDb); 
                model.addAttribute("backupCodes", backupCodes);
                model.addAttribute("userId", userFromDb.getUserId());
                return "verifyBackupCode";
            }
        }
        return "error";
    }

    
//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new User(null, null, null));
//        return "registration";
//    }
//
//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute("user") User user, Model model) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepo.save(user);  
//        return "redirect:/";
//    }

    
    

    @PostMapping("/verifyBackupCode/{userId}")
    public String verifyBackupCode(@RequestParam String backupCode, @PathVariable("userId") String userId, Model model) {
        System.out.println(userId);
        Long userid = Long.parseLong(userId);
        User userFromDb = userRepo.findById(userid).orElse(null);
        if (userFromDb != null) {
            BackupCode code = backupCodeRepo.findByUserIdAndCode(userid, backupCode);
            if (code != null) {
                backupCodeRepo.delete(code);
                HotelRoom room = new HotelRoom();
                model.addAttribute("room", room);

                List<HotelRoom> rooms = hotelRepository.findAll();
                model.addAttribute("rooms", rooms);

                if ("admin".equals(userFromDb.getUserRole())) {
                    return "adminPage";
                } else {
                    return "home";
                }
            }
        }
        return "error";
    }



    private List<BackupCode> generateBackupCodes(Long userId) {
        List<BackupCode> backupCodes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            BackupCode backupCode = new BackupCode();
            backupCode.setUserId(userId);
            backupCode.setCode(UUID.randomUUID().toString());
            backupCodes.add(backupCode);
        }
        return backupCodes;
    }
    


    

    @GetMapping("/adminPage")
    public String getAdminPage(Model model) {
        List<HotelRoom> rooms = hotelRepository.findAll();
        model.addAttribute("rooms", rooms);
        model.addAttribute("room", new HotelRoom());
        return "adminPage";
    }
}
