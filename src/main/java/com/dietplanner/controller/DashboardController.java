package com.dietplanner.controller;

import com.dietplanner.dao.MenuHarianDAO;
import com.dietplanner.dao.ProgressDAO;
import com.dietplanner.dao.UserDAO;
import com.dietplanner.model.MenuHarian;
import com.dietplanner.model.Progress;
import com.dietplanner.model.User;
import com.dietplanner.utils.BMIUtil;
import com.dietplanner.utils.KaloriUtil;
import com.dietplanner.utils.StreakUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    private final UserDAO userDAO = new UserDAO();
    private final MenuHarianDAO menuHarianDAO = new MenuHarianDAO();
    private final ProgressDAO progressDAO = new ProgressDAO();

    @GetMapping("/")
    public String dashboard(HttpSession session, Model model) {

        int userId = (Integer) session.getAttribute("userId");
        User user = userDAO.cariById(userId);

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        int bmr = KaloriUtil.hitungBMR(user.getJenisKelamin(), user.getBeratBadan(),
                user.getTinggiBadan(), user.getUmur());
        int tdee = KaloriUtil.hitungTDEE(bmr);
        int targetKalori = KaloriUtil.hitungTargetKalori(tdee, user.getTarget());
        double bmi = BMIUtil.hitungBMI(user.getBeratBadan(), user.getTinggiBadan());

        model.addAttribute("bmr", bmr);
        model.addAttribute("tdee", tdee);
        model.addAttribute("targetKalori", targetKalori);
        model.addAttribute("bmi", bmi);
        model.addAttribute("kategoriBMI", BMIUtil.kategori(bmi));

        // Streak: hari berturut-turut generate & simpan menu
        List<MenuHarian> riwayatMenu = menuHarianDAO.getRiwayatByUser(userId);
        @SuppressWarnings("null")
        List<String> tanggalMenu = riwayatMenu.stream().map(MenuHarian::getTanggal).collect(Collectors.toList());
        int streak = StreakUtil.hitungStreak(tanggalMenu);
        model.addAttribute("streak", streak);

        // Progress berat badan terbaru (buat ringkasan tren singkat di dashboard)
        List<Progress> riwayatBerat = progressDAO.getByUser(userId);
        if (riwayatBerat.size() >= 2) {
            double terakhir = riwayatBerat.get(riwayatBerat.size() - 1).getBeratBadan();
            double sebelumnya = riwayatBerat.get(riwayatBerat.size() - 2).getBeratBadan();
            model.addAttribute("selisihBerat", Math.round((terakhir - sebelumnya) * 10.0) / 10.0);
        }

        return "dashboard";
    }
}
