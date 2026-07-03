package com.dietplanner.controller;

import com.dietplanner.dao.MakananDAO;
import com.dietplanner.model.Makanan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/makanan")
public class MakananController {

    private final MakananDAO makananDAO = new MakananDAO();
    private static final List<String> KATEGORI = List.of("SARAPAN", "MAKAN_SIANG", "MAKAN_MALAM", "SNACK");

    @GetMapping
    public String list(Model model) {
        model.addAttribute("daftarMakanan", makananDAO.getSemuaMakanan());
        return "makanan/list";
    }

    @GetMapping("/tambah")
    public String formTambah(Model model) {
        model.addAttribute("makanan", new Makanan());
        model.addAttribute("daftarKategori", KATEGORI);
        return "makanan/form";
    }

    @PostMapping("/tambah")
    public String tambah(@ModelAttribute Makanan makanan) {
        makananDAO.tambahMakanan(makanan);
        return "redirect:/makanan";
    }

    @GetMapping("/edit/{id}")
    public String formEdit(@PathVariable int id, Model model) {
        Makanan makanan = null;
        for (Makanan m : makananDAO.getSemuaMakanan()) {
            if (m.getId() == id) {
                makanan = m;
                break;
            }
        }
        if (makanan == null) {
            return "redirect:/makanan";
        }
        model.addAttribute("makanan", makanan);
        model.addAttribute("daftarKategori", KATEGORI);
        return "makanan/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable int id, @ModelAttribute Makanan makanan) {
        makanan.setId(id);
        makananDAO.updateMakanan(makanan);
        return "redirect:/makanan";
    }

    @GetMapping("/hapus/{id}")
    public String hapus(@PathVariable int id) {
        makananDAO.hapusMakanan(id);
        return "redirect:/makanan";
    }
}
