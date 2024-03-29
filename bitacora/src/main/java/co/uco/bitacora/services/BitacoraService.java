package co.uco.bitacora.services;

import co.uco.bitacora.domains.bitacora.*;
import co.uco.bitacora.domains.equipo.Equipo;
import co.uco.bitacora.domains.equipo.TipoEquipo;
import co.uco.bitacora.domains.objetosAuxiliares.DatosEquipo;
import co.uco.bitacora.domains.recomendacion.Recomendacion;
import co.uco.bitacora.domains.usuario.TipoUsuario;
import co.uco.bitacora.domains.usuario.Usuario;
import co.uco.bitacora.domains.usuario.userDescription;
import co.uco.bitacora.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class BitacoraService {


    @Autowired
    private ITipoUsuarioRepository iTipoUsuarioRepository;
    @Autowired
    private IBitacoraRepository iBitacoraRepository;
    @Autowired
    private IDescripcionRepository iDescripcionRepository;
    @Autowired
    private IUsuarioRepository iUsuarioRepository;
    @Autowired
    private IEquipoRepository iEquipoRepository;
    @Autowired
    private ITipoEquipoRepository iTipoEquipoRepository;
    @Autowired
    private IRecomendacionRepository iRecomendacionRepository;
    @Autowired
    private IRevisionRepository iRevisionRepository;
    @Autowired
    private IEstadoRepository iEstadoRepository;
    @Autowired
    private RevisionService revisionService = new RevisionService() ;

    private Bitacora bitacoraAUX = new Bitacora();
    private Usuario usuarioAux = new Usuario();
    private Descripcion descripcionAux =new Descripcion();
    private TipoUsuario tipoUsuarioAux = new TipoUsuario();
    private Estado estadoAux = new Estado();
    private TipoEquipo tipoEquipoAux = new TipoEquipo();
    private Recomendacion recomendacionAux = new Recomendacion();




    //posiblemente no lo use pero es la base de todo

    public String AgregarBitacoraAlaAgenda(userDescription usde){
        if (iUsuarioRepository.findById(usde.getIdUser()).isEmpty()){
            return "No existe el usuario";
        }else {

            try {
                //Aqui se Crea el usuario
                iUsuarioRepository.findById(usde.getIdUser()).ifPresent(dato -> {
                    usuarioAux = dato;
                });

                descripcionAux = new Descripcion(usde.getDesc());
                iDescripcionRepository.save(descripcionAux);
                estadoAux = new Estado();
                bitacoraAUX = new Bitacora(usuarioAux,descripcionAux,revisionService.revisionNuevaNula() ,estadoAux);
                iBitacoraRepository.save(bitacoraAUX);


                return  "La agenda Se A Guardado Con Exito ";

            }catch (Exception e ){
                return "salio un error" + e.getMessage();
            }
        }
    }

    public void actualizarDatosBasicos(){
        /* aqui se crea los datos estaticos de
        * tipo usuario ( 1 ó 2 y 3) ok
        * tipo equipo (1 ó 2 ó 3) ok
        * recomendaciones (arranca en 1 con el default) ok
        * estado (1 ó 2 ó 3 arranca en default con 1)
        * */


        //llenamos los tipo usuario
        tipoUsuarioAux = new TipoUsuario(1);
        iTipoUsuarioRepository.save(tipoUsuarioAux);

        tipoUsuarioAux = new TipoUsuario(2);
        iTipoUsuarioRepository.save(tipoUsuarioAux);

        tipoUsuarioAux = new TipoUsuario(3);
        iTipoUsuarioRepository.save(tipoUsuarioAux);

        // llenamos los tipo equipo
        tipoEquipoAux = new TipoEquipo(1);
        iTipoEquipoRepository.save(tipoEquipoAux);

        tipoEquipoAux = new TipoEquipo(2);
        iTipoEquipoRepository.save(tipoEquipoAux);

        tipoEquipoAux = new TipoEquipo(3);
        iTipoEquipoRepository.save(tipoEquipoAux);

        tipoEquipoAux = new TipoEquipo(4);
        iTipoEquipoRepository.save(tipoEquipoAux);

        //aqui creamos la recomendacion por defecto
        recomendacionAux = new Recomendacion();
        iRecomendacionRepository.save(recomendacionAux);

        //aqui creamos los estados posibles
        estadoAux=new Estado(1);
        iEstadoRepository.save(estadoAux);

        estadoAux=new Estado(2);
        iEstadoRepository.save(estadoAux);

        estadoAux=new Estado(3);
        iEstadoRepository.save(estadoAux);

        estadoAux=new Estado(4);
        iEstadoRepository.save(estadoAux);

    }

    public List<Bitacora> mostrarAgenda(){
        return iBitacoraRepository.findAll();
    }

    @Transactional
    public String editarEquipo(long idRevision, DatosEquipo actualizacion){
        try {
            Equipo equipo = new Equipo();
            iRevisionRepository.findById(idRevision).ifPresent(dato->{
                equipo.setId(dato.getEquipo().getId());
            });

            // Validación de parámetros
            if (equipo.getId() <= 0) {
                throw new IllegalArgumentException("El ID del equipo debe ser mayor a 0");
            }

            if (actualizacion == null) {
                throw new IllegalArgumentException("La información de actualización no puede ser nula");
            }
            System.out.println("trata de hacer el cambio");
            iEquipoRepository.modificarMarcaYTipoPorId(actualizacion.getMarca(),new TipoEquipo(actualizacion.getTipoEquipo()),equipo.getId());
            return  "Se Guardaron los cambios";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "no se actualizo";
        }
    }


    public List<Bitacora> mostrarPorUsuario(long id){
        List<Bitacora> bitacorasPorUsuario = new ArrayList<>();
        for (int i = 0 ; i <= iBitacoraRepository.findAll().size(); i++ ){
            iBitacoraRepository.findById((long)i).ifPresent(dato -> {
                if (dato.getUsuario().getId() == id){
                    bitacorasPorUsuario.add(dato);
                }
            });
        }
        return bitacorasPorUsuario;
    }


    public Bitacora traerBitacoraPorID(long id){
        iBitacoraRepository.findById(id).ifPresent(dato -> {
            bitacoraAUX= dato;
        });
        return bitacoraAUX;
    }

    public void cancelarSolicitid(long id){
        iBitacoraRepository.deleteById(id);
    }

    public void limpiarDB(){
        iTipoUsuarioRepository.deleteAll();
        iTipoEquipoRepository.deleteAll();
        iRecomendacionRepository.deleteAll();
        iEstadoRepository.deleteAll();
    }


}
