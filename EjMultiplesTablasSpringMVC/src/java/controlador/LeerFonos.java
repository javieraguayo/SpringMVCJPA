/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Alumno;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import modelo.AlumnoDAO2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Javier
 */
@Controller
@RequestMapping("/mostrar.htm")
public class LeerFonos {
    
    @Autowired //Me simplifica a usar 1 sola vez AlumnoDAO2 inyeccion de depencia dale memoria cuando ocupe ese objeto
    private AlumnoDAO2 dao; //AlumnoDAO2 hace las querys a mi BD
    
    @RequestMapping(method = RequestMethod.GET)
    public String mostrarAlumno(){     
        return "buscar";   
    }
    
    @RequestMapping(method = RequestMethod.POST)    
    public String mostrarAlumno(
            @RequestParam("txtRut") String rut, //resivo el rut
            Model model, HttpServletRequest request){ //trabajar con secciones http...
            //modelo para pasar del controlador a la vista
            if (rut.trim().equals("")) {//si rut esta vacio
                return "buscar";
            }
            
            Alumno a = dao.readByRutJPQL(rut);//leo el alumno y lo alamceno en a
            
            if (a == null) {
                return "error";
            }
            //request.getSession().setAttribute("alumno", a);
            
            model.addAttribute("listaFonos", a.getFonoCollection());
            
            return "fonos";  
    }
    
}