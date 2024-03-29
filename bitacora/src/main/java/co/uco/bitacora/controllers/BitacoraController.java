package co.uco.bitacora.controllers;

import co.uco.bitacora.domains.bitacora.Bitacora;
import co.uco.bitacora.domains.equipo.Equipo;
import co.uco.bitacora.domains.objetosAuxiliares.DatosEquipo;
import co.uco.bitacora.domains.usuario.userDescription;
import co.uco.bitacora.services.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/procesador/bitacora/v2/bitacora")
public class BitacoraController {

    @Autowired
    private BitacoraService ServiciosDeBitacora = new BitacoraService();



    @PostMapping("/cargarDB")// paso pruebas
    public void llenardata(){
        ServiciosDeBitacora.actualizarDatosBasicos();
    }

    @DeleteMapping("/")//paso pruebas
    public void limpiarDB(){
        ServiciosDeBitacora.limpiarDB();
    }


    @GetMapping("/agenda")//ok paso pruebas
    public ResponseEntity<List<Bitacora>> mostrarAgenda() {
        return ResponseEntity.ok(ServiciosDeBitacora.mostrarAgenda());
    }

    //@PatchMapping( value = "/equipo/{dato}")
    @PatchMapping( value = "/equipo/{idRevision}")//ok paso pruebas
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String actualizarEquipo ( @PathVariable long idRevision , @RequestBody DatosEquipo actualizacion){
        //LA BUSQUEDA SE HACE POR EL ID DE LA REVISION
        System.out.println("entra al link ");
        return ServiciosDeBitacora.editarEquipo(idRevision , actualizacion);
    }

    @PostMapping("/agenda")//ok paso pruebas
    @ResponseStatus(HttpStatus.CREATED)
    public String ingresarRegistro(@RequestBody userDescription usde){
        return ServiciosDeBitacora.AgregarBitacoraAlaAgenda(usde);
    }

    @GetMapping("/usuario/{id}")//ok paso pruebas
    public ResponseEntity<List<Bitacora>> mostarPorUsuario(@PathVariable long id) {
        return ResponseEntity.ok(ServiciosDeBitacora.mostrarPorUsuario(id));
    }


    @DeleteMapping("/usuario/{id}")//ok paso pruebas
    public void calcelarSolicitud(@PathVariable long id){
        ServiciosDeBitacora.cancelarSolicitid(id);
    }

}
