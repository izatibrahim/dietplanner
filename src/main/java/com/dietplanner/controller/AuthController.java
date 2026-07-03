package com.dietplanner.controller;

import com.dietplanner.dao.UserDAO;
import com.dietplanner.model.User;
import com.dietplanner.utils.PasswordUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserDAO userDAO = new UserDAO();

    @GetMapping("/login")
    public String formLogin(@RequestParam(required = false) String error,
                             @RequestParam(required = false) String sukses,
                             Model model) {
        if (error != null) {
            model.addAttribute("pesanError", "Username atau password salah.");
        }
        if (sukses != null) {
            model.addAttribute("pesanSukses", "Akun berhasil dibuat, silakan login.");
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                         @RequestParam String password,
                         HttpServletRequest request) {

        User user = userDAO.cariByUsername(username);

        if (user == null || user.getPassword() == null || !PasswordUtil.cocok(password, user.getPassword())) {
            return "redirect:/login?error=1";
        }

        request.getSession(true).setAttribute("userId", user.getId());
        request.getSession().setAttribute("userNama", user.getNama());

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        var session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String formRegister(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user,
                            @RequestParam String konfirmasiPassword,
                            Model model) {

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            model.addAttribute("pesanError", "Username wajib diisi.");
            return "auth/register";
        }

        if (userDAO.cariByUsername(user.getUsername()) != null) {
            model.addAttribute("pesanError", "Username sudah dipakai, pilih username lain.");
            return "auth/register";
        }

        if (user.getPassword() == null || !user.getPassword().equals(konfirmasiPassword)) {
            model.addAttribute("pesanError", "Konfirmasi password tidak cocok.");
            return "auth/register";
        }

        user.setPassword(PasswordUtil.hash(user.getPassword()));
        userDAO.tambahUser(user);

        return "redirect:/login?sukses=1";
    }
}
