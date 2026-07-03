package com.dietplanner.controller;

import com.dietplanner.dao.MenuHarianDAO;
import com.dietplanner.dao.UserDAO;
import com.dietplanner.model.Makanan;
import com.dietplanner.model.MenuHarian;
import com.dietplanner.model.User;
import com.dietplanner.service.DietPlannerService;
import com.dietplanner.utils.KaloriUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {

    private final UserDAO userDAO = new UserDAO();
    private final MenuHarianDAO menuHarianDAO = new MenuHarianDAO();
    private final DietPlannerService dietPlannerService = new DietPlannerService();

    @GetMapping("/generate")
    public String generate(HttpSession session, Model model) {

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

        List<Makanan> hasil = dietPlannerService.generateMenuUser(targetKalori, user.getAlergi());

        int totalKalori = 0;
        for (Makanan m : hasil) {
            totalKalori += m.getKalori();
        }

        model.addAttribute("targetKalori", targetKalori);
        model.addAttribute("hasil", hasil);
        model.addAttribute("totalKalori", totalKalori);

        return "menu/generate";
    }

    @PostMapping("/generate/simpan")
    public String simpan(@RequestParam String sarapan,
                          @RequestParam String makanSiang,
                          @RequestParam String makanMalam,
                          @RequestParam int totalKalori,
                          HttpSession session) {

        int userId = (Integer) session.getAttribute("userId");

        MenuHarian menu = new MenuHarian();
        menu.setUserId(userId);
        menu.setTanggal(LocalDate.now().toString());
        menu.setSarapan(sarapan);
        menu.setMakanSiang(makanSiang);
        menu.setMakanMalam(makanMalam);
        menu.setTotalKalori(totalKalori);

        menuHarianDAO.simpan(menu);

        return "redirect:/menu/riwayat";
    }

    @GetMapping("/riwayat")
    public String riwayat(HttpSession session, Model model) {

        int userId = (Integer) session.getAttribute("userId");
        model.addAttribute("daftarRiwayat", menuHarianDAO.getRiwayatByUser(userId));
        return "menu/riwayat";
    }
}
