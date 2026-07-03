package com.dietplanner.controller;

import com.dietplanner.dao.ProgressDAO;
import com.dietplanner.dao.UserDAO;
import com.dietplanner.model.Progress;
import com.dietplanner.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/progress")
public class ProgressController {

    private final ProgressDAO progressDAO = new ProgressDAO();
    private final UserDAO userDAO = new UserDAO();

    @GetMapping
    public String index(HttpSession session, Model model) {

        int userId = (Integer) session.getAttribute("userId");
        User user = userDAO.cariById(userId);

        List<Progress> riwayat = progressDAO.getByUser(userId);

        model.addAttribute("user", user);
        model.addAttribute("riwayat", riwayat);

        if (!riwayat.isEmpty()) {
            double terakhir = riwayat.get(riwayat.size() - 1).getBeratBadan();
            model.addAttribute("beratTerakhir", terakhir);

            if (riwayat.size() > 1) {
                double sebelumnya = riwayat.get(riwayat.size() - 2).getBeratBadan();
                model.addAttribute("selisih", Math.round((terakhir - sebelumnya) * 10.0) / 10.0);
            }
        }

        return "progress/index";
    }

    @PostMapping("/tambah")
    public String tambah(@RequestParam double beratBadan, HttpSession session) {

        int userId = (Integer) session.getAttribute("userId");

        Progress p = new Progress();
        p.setUserId(userId);
        p.setTanggal(LocalDate.now().toString());
        p.setBeratBadan(beratBadan);
        progressDAO.tambah(p);

        // sinkronkan juga ke profil, supaya BMI/BMR di dashboard ikut update
        User user = userDAO.cariById(userId);
        if (user != null) {
            user.setBeratBadan(beratBadan);
            userDAO.updateUser(user);
        }

        return "redirect:/progress";
    }
}
