/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servidor.wsrest;

import com.servidor.dao.SesionOtpDAO;
import com.servidor.dao.UsuarioDAO;
import com.servidor.dto.SesionOtp;
import com.servidor.dto.Usuario;
import com.servidor.general.Utilidad;
import com.servidor.response.ResponseGeneric;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jonathan
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("login")
public class WSRestLogin {

    private Utilidad util = new Utilidad();

    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private SesionOtpDAO sesionOtpDAO;

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseGeneric iniciarSesion(@RequestParam("nomUsuario") String nomUsuario, @RequestParam("clave") String clave) {
        ResponseGeneric response = new ResponseGeneric();
        try {
            List<Usuario> lista = usuarioDAO.findByNomUsuario(nomUsuario);
            if (lista == null && lista.size() == 0) {
                response.setbRta(false);
                response.setsMsg("No se ha encontrado ningún usuario");
                return response;
            }

            Usuario usuarioActivo = lista.get(0);
            if (!usuarioActivo.getClaveUsuario().equals(clave)) {
                response.setbRta(false);
                response.setsMsg("La clave no coincide para el usuario " + nomUsuario);
                return response;
            }
            
            return this.enviarOTP(usuarioActivo.getCodUsuario(), usuarioActivo);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setbRta(false);
            response.setsMsg("Ha ocurrido un error");
        }
        return response;
    }

    @PostMapping("/enviarOTP")
    @ResponseStatus(HttpStatus.OK)
    public ResponseGeneric enviarOTP(@RequestParam("codUsuario") int codUsuario, Usuario usuarioActual) {
        ResponseGeneric response = new ResponseGeneric();
        try {
            Usuario usuario = null;

            if (usuarioActual != null && usuarioActual.getNomUsuario()!=null) {
                usuario = usuarioActual;
            }
            else {
                Optional<Usuario> lista = usuarioDAO.findById(codUsuario);
                if (!lista.isPresent()) {
                    response.setbRta(false);
                    response.setsMsg("No se ha encontrado ningún usuario");
                    return response;
                }
                usuario = lista.get();
            }

            List<SesionOtp> listaSesion = sesionOtpDAO.findByCodUsuario(usuario);
            if (listaSesion != null && listaSesion.size() > 0) {
                for (int i = 0; i < listaSesion.size(); i++) {
                    sesionOtpDAO.delete(listaSesion.get(i));
                }
            }

            String codigoOTP = util.enviarCodigoOTP(usuario.getEmailUsuario());
            if (codigoOTP == null) {
                response.setbRta(false);
                response.setsMsg("Ha ocurrido un error al enviar código de autenticación");
                return response;
            }

            SesionOtp nuevaSesion = new SesionOtp();
            nuevaSesion.setCodigoOtp(codigoOTP);
            nuevaSesion.setFechaExpuesta(new Date());
            nuevaSesion.setCodUsuario(usuario);
            sesionOtpDAO.save(nuevaSesion);
            
            List<Usuario> listaUsuario = new ArrayList<>();
            listaUsuario.add(usuario);
            response.setbRta(true);
            response.setsMsg("Se ha enviado un código de autenticación al correo electrónico registrado");
            response.setList(listaUsuario);
            return response;
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setbRta(false);
            response.setsMsg("Ha ocurrido un error ");
        }
        return response;
    }

    @PostMapping("/validarOtp")
    @ResponseStatus(HttpStatus.OK)
    public ResponseGeneric validarOtp(@RequestParam("codUsuario") int xCodUsuario, @RequestParam("xOtp") String xOtp) {
        ResponseGeneric response = new ResponseGeneric();
        try {
            Optional<Usuario> lista = usuarioDAO.findById(xCodUsuario);
            if (!lista.isPresent()) {
                response.setbRta(false);
                response.setsMsg("No se ha encontrado ningún usuario");
                return response;
            }

            List<SesionOtp> listaSesion = sesionOtpDAO.findByCodUsuario(lista.get());
            if (listaSesion == null || listaSesion.size() == 0) {
                response.setbRta(false);
                response.setsMsg("No se ha podido válidar el código, intentelo de nuevo");
            }
            
            

            SesionOtp sesion = listaSesion.get(0);
            long minutosExpiracion = 3;
            long fechasComparadas = util.compararFechasMinutos(new Date(), sesion.getFechaExpuesta());
            if (fechasComparadas > minutosExpiracion) {
                response.setbRta(false);
                response.setsMsg("El tiempo para válidar el código ha expirado, por favor vuelva a iniciar sesión");
                return response;
            }
            
            if (!sesion.getCodigoOtp().equals(xOtp)) {
                response.setbRta(false);
                response.setsMsg("El código no concuerda, intentelo de nuevo");
                return response;
            }
            sesionOtpDAO.delete(sesion);
            response.setbRta(true);
            response.setsMsg("El código es correcto");
            return response;
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setbRta(false);
            response.setsMsg("Ha ocurrido un error ");
        }
        return response;
    }

}
