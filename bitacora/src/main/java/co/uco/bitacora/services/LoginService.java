package co.uco.bitacora.services;

import co.uco.bitacora.domains.bitacora.*;

import co.uco.bitacora.domains.usuario.TipoUsuario;
import co.uco.bitacora.domains.usuario.Usuario;

import co.uco.bitacora.domains.usuario.editableUsuario;
import co.uco.bitacora.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService {
    @Autowired
    private IUsuarioRepository iUsuarioRepository;
    @Autowired
    private ITipoUsuarioRepository iTipoUsuarioRepository;

    private Usuario usuarioAux = new Usuario();
    private TipoUsuario tipoUsuarioAux = new TipoUsuario();


    public void actualizarDatosBasicos() {
        /* aqui se crea los datos estaticos de
         * tipo usuario ( 1 ó 2 y 3) ok
         * tipo equipo (1 ó 2 ó 3) ok
         * recomendaciones (arranca en 1 con el default) ok
         * estado (1 ó 2 ó 3 arranca en default con 1)
         * */
        //llenamos los tipo usuario
        tipoUsuarioAux = new TipoUsuario(1);

        System.out.println("id: " + tipoUsuarioAux.getId() + " descripcion: " + tipoUsuarioAux.getDescripcion());

        iTipoUsuarioRepository.save(tipoUsuarioAux);

        System.out.println("intento guardar");

        tipoUsuarioAux = new TipoUsuario(2);
        iTipoUsuarioRepository.save(tipoUsuarioAux);

        tipoUsuarioAux = new TipoUsuario(3);
        iTipoUsuarioRepository.save(tipoUsuarioAux);
    }

    public String AgregarUsuario(editableUsuario usde) {
        try {
            //Aqui se Crea el usuario
            usuarioAux = new Usuario(usde.getNombre(), usde.getApellido(), usde.getUsuario(), usde.getContrasena(), usde.getIdTipoUsuario());
            iUsuarioRepository.save(usuarioAux);
            return "Registro Exitoso";
        }catch (Exception e ){
            return "Usuario No Registrado \n" +  e.getMessage();
        }
    }

    public List<Usuario> mostrarUsuarios() {
        return iUsuarioRepository.findAll();
    }

    public void editarUsuario(editableUsuario dato) {

        Usuario equ = new Usuario();
        equ.setNombre(dato.getNombre());
        equ.setApellido(dato.getApellido());
        equ.setContrasena(dato.getContrasena());
        tipoUsuarioAux = new TipoUsuario(dato.getIdTipoUsuario());
        equ.setTipoUsuario(tipoUsuarioAux);
        iUsuarioRepository.save(equ);
    }

    public List<Usuario> mostrarPorUsuario(long id) {
        List<Usuario> bitacorasPorUsuario = new ArrayList<>();
        for (int i = 0; i < iUsuarioRepository.findAll().size(); i++) {
            iUsuarioRepository.findById((long) i).ifPresent(dato -> {
                if (dato.getId() == id) {
                    bitacorasPorUsuario.add(dato);
                }
            });
        }
        return bitacorasPorUsuario;
    }

    // revisar con mas detalle
    public Usuario traerUsuarioPorID(long id) {
        iUsuarioRepository.findById(id).ifPresent(dato -> {
            usuarioAux = dato;
        });
        return usuarioAux;
    }

    public List<Usuario> mostrarUsuario (){
        return iUsuarioRepository.findAll();
    }

    public void cancelarSolicitid(long id) {
        iUsuarioRepository.deleteById(id);
    }
}