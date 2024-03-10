package co.uco.bitacora.controllers;

import co.uco.bitacora.domains.equipo.editableEquipo;
import co.uco.bitacora.domains.usuario.Usuario;
import co.uco.bitacora.domains.usuario.editableUsuario;
import co.uco.bitacora.domains.usuario.userDescription;
import co.uco.bitacora.services.BitacoraService;
import co.uco.bitacora.services.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/procesador/bitacora/v2/login")
public class LoginController {
    private LoginService loginService=new LoginService();

    @PostMapping ("/cargarDB")
    public String  llenardata(){
        loginService.actualizarDatosBasicos();
        return  null;
    }

    @GetMapping("/usuario")
    public ResponseEntity<?> mostrarUsuario() {
        return ResponseEntity.ok(loginService.mostrarUsuario());
    }

    @PatchMapping( value = "/usuario")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void actualizarUsuario (@RequestBody editableUsuario dato){
        loginService.editarUsuario(dato);
    }
    @PostMapping("/nuevoUsuario")
    @ResponseStatus(HttpStatus.CREATED)
    public void ingresarUsuario(@RequestBody editableUsuario usde){
        loginService.AgregarUsuario(usde);
    }
    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> mostarPorUsuario(@PathVariable long id) {
        return ResponseEntity.ok(loginService.mostrarPorUsuario(id));
    }

    @DeleteMapping("/usuario/{id}")
    public void calcelarSolicitud(@PathVariable long id){
        loginService.cancelarSolicitid(id);
    }
}