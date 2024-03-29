package co.uco.bitacora.controllers.jwt;

import co.uco.bitacora.domains.jwt.JwtResponse;
import co.uco.bitacora.domains.objetosAuxiliares.EditableUsuario;
import co.uco.bitacora.domains.objetosAuxiliares.Login;
import co.uco.bitacora.services.jwt.JwtAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/procesador/bitacora/v2/Authentication")
@RequiredArgsConstructor
public class JwtAuthenticationController {

    @Autowired
    private JwtAuthenticationService jwtAuthenticationService = new JwtAuthenticationService();
    @PostMapping(value ="/log")
    public ResponseEntity<JwtResponse> login (@RequestBody  Login login){
        return ResponseEntity.ok(jwtAuthenticationService.login(login) );
    }

    @PostMapping(value = "/reg")
    public  ResponseEntity<JwtResponse> registrar (@RequestBody EditableUsuario editableUsuario){
        return ResponseEntity.ok(jwtAuthenticationService.registrar(editableUsuario));
    }
}