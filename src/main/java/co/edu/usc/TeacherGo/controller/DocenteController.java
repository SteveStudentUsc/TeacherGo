package co.edu.usc.TeacherGo.controller;

import co.edu.usc.TeacherGo.model.Docentes;
import co.edu.usc.TeacherGo.model.Valoraciones;
import co.edu.usc.TeacherGo.repository.DocenteRepository;
import co.edu.usc.TeacherGo.repository.ValoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/docentes")
public class DocenteController {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private ValoracionRepository valoracionRepository;

    @GetMapping("/listar")
    public String listarDocentes(Model model) {
        List<Docentes> docentes = docenteRepository.findAll();
        Map<Long, List<Valoraciones>> valoracionesMap = new HashMap<>();

        for (Docentes docente : docentes) {
            List<Valoraciones> valoraciones = valoracionRepository.findByDocenteId(docente.getId());
            valoracionesMap.put(docente.getId(), valoraciones);
        }

        model.addAttribute("docentes", docentes);
        model.addAttribute("valoracionesMap", valoracionesMap);
        return "listar-docentes";
    }

    @GetMapping("/{id}")
    public String verDetalleDocente(@PathVariable Long id, Model model) {
        Docentes docente = docenteRepository.findById(id).orElse(null);
        if (docente == null) {
            model.addAttribute("mensaje", "Docente no encontrado");
            return "error";
        }

        List<Valoraciones> valoraciones = valoracionRepository.findByDocenteId(id);

        model.addAttribute("docente", docente);
        model.addAttribute("valoraciones", valoraciones);
        return "detalle-docente";
    }
}
