package co.uco.bitacora.services;

import co.uco.bitacora.domains.bitacora.Descripcion;
import co.uco.bitacora.domains.bitacora.Bitacora;
import co.uco.bitacora.domains.equipo.editableEquipo;
import co.uco.bitacora.domains.usuario.editableUsuario;
import co.uco.bitacora.domains.usuario.userDescription;
import co.uco.bitacora.repository.registroRepository;

import java.util.List;

public class registrosService {
    private registroRepository reg = new registroRepository();

    public List<Bitacora> llenarDatos(){
        reg.llenarDatos();
        return reg.getRegistros();
    }//ok
    public List<Bitacora> mostrarAgenda(){
        return reg.getRegistros();
    }//ok

    public List<Bitacora> mostrarCompletados(){
        return reg.getRegistrosCompletados();
    }//ya implementado en observaciones

    public void editarEquipo(long id , editableEquipo dato){
        reg.modificarEquipoPorId(id,dato);
    }//ok


/*    public void crearResgistro(userDescription usde){
        reg.crearRegistro(new editableUsuario(usde.getIdUser(),usde.getNombre(),usde.getIdTipouser()),new Descripcion(usde.getIdDesc(),usde.getDesc()));
    }// ok*/

    public List<Bitacora> mostrarPorUsuario(long id){

        return reg.getRegistrosPorUsuario(id);
    }//ok
    public void  cancelarRegistro(long id){
        reg.eliminarRegistro(id);
    }//
}
