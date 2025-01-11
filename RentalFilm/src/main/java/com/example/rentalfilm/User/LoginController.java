package com.example.rentalfilm.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        User user = userRepo.findByEmail(email);

        if (user == null) {
            model.addAttribute("email", user.getEmail());
            model.addAttribute("password", user.getPass());
            return "login";
        } else {
            session.setAttribute("email", user.getEmail());
            if (user.getRole().equals("admin")) {
                return "redirect:/homeAdmin";
            } else {
                return "redirect:/homeUser";
            }
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(
            @RequestParam("nama") String nama,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirmpassword") String confirmpassword,
            Model model) {

        if (nama.isEmpty() || email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()) {
            model.addAttribute("error", "Semua field harus diisi.");
            return "register";
        }

        if (userService.findByEmail(email) != null) {
            model.addAttribute("error", "Email sudah terdaftar.");
            return "register";
        }

        if (!password.equals(confirmpassword)) {
            model.addAttribute("error", "Password dan konfirmasi password tidak cocok.");
            return "register";
        }

        User user = new User();
        user.setNama(nama);
        user.setEmail(email);
        user.setPass(password);
        userService.registerUser(user);

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}