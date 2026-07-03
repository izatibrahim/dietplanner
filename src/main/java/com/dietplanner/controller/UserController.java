package com.dietplanner.controller;

import com.dietplanner.dao.UserDAO;
import com.dietplanner.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserDAO userDAO = new UserDAO();

    // Halaman "Profil" - cuma nampilin & edit akun yang sedang login sendiri.
    @GetMapping
    public String profil(HttpSession session, Model model) {

        int userId = (Integer) session.getAttribute("userId");
        User user = userDAO.cariById(userId);

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "user/list";
    }

    @GetMapping("/edit/{id}")
    public String formEdit(@PathVariable int id, HttpSession session, Model model) {

        int userId = (Integer) session.getAttribute("userId");

        // cegah orang edit profil user lain lewat ubah-ubah URL
        if (id != userId) {
            return "redirect:/user";
        }

        User user = userDAO.cariById(id);
        if (user == null) {
            return "redirect:/user";
        }

        model.addAttribute("user", user);
        return "user/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable int id, @ModelAttribute User user, HttpSession session) {

        int userId = (Integer) session.getAttribute("userId");

        if (id != userId) {
            return "redirect:/user";
        }

        User existing = userDAO.cariById(id);
        if (existing != null) {
            user.setUsername(existing.getUsername());
            user.setPassword(existing.getPassword());
        }

        user.setId(id);
        userDAO.updateUser(user);

        return "redirect:/user";
    }
}
