package negocio;

import dto.MozoDTO;
import enums.EstadoMozo;
import excepciones.MozoExistenteException;
import excepciones.MozoNoExistenteException;
import excepciones.PermisoDenegadoException;
import modelo.Empresa;
import modelo.Mozo;
import persistencia.IPersistencia;
import persistencia.PersistenciaXML;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public class GestionDeMozos {

    private final Empresa empresa;
    private static GestionDeMozos gestionDeMozos = null;

    private GestionDeMozos() {
        this.empresa = Empresa.getEmpresa();
    }

    public static GestionDeMozos get() {
        if( gestionDeMozos == null )
            gestionDeMozos = new GestionDeMozos();
        return gestionDeMozos;
    }

    public void persistirMozos(){
        IPersistencia<Set<Mozo>> persistencia = new PersistenciaXML();
        try {
            persistencia.abrirOutput("mozos.xml");
            persistencia.escribir(this.empresa.getMozos());
            persistencia.cerrarOutput();
        } catch (IOException e) {

        }
    }

    public void altaMozo(MozoDTO mozo) throws MozoExistenteException, PermisoDenegadoException {

        if (this.empresa.getUsuarioLogueado().getUsername().equals("admin")){
            Set<Mozo> mozos = this.empresa.getMozos();

            Mozo nuevoMozo = new Mozo(mozo.getNombreCompleto(),mozo.getFechaNacimiento(),mozo.getCantidadHijos());
            boolean existeMozo = mozos.stream().anyMatch(m -> m.getNombreCompleto().equalsIgnoreCase(nuevoMozo.getNombreCompleto()) );
            if( existeMozo )
                throw new MozoExistenteException();
            else{
                mozos.add(nuevoMozo);
                this.empresa.setMozos(mozos);
                persistirMozos();
            }
        }else
            throw new PermisoDenegadoException();
    }

    public void modificarMozo(MozoDTO mozo) throws MozoNoExistenteException, PermisoDenegadoException {

        if (this.empresa.getUsuarioLogueado().getUsername().equals("admin")){
            Set<Mozo> mozos = this.empresa.getMozos();

            Mozo mozoMod = new Mozo(mozo.getNombreCompleto(),mozo.getFechaNacimiento(),mozo.getCantidadHijos());

            boolean existeMozo = mozos.stream().anyMatch(m -> m.getNombreCompleto().equalsIgnoreCase(mozo.getNombreCompleto()) );
            if( !existeMozo )
                throw new MozoNoExistenteException();
            else{
                mozos.remove(mozoMod);
                mozos.add(mozoMod);
                this.empresa.setMozos(mozos);
                persistirMozos();
            }
        }else
            throw new PermisoDenegadoException();
    }

    public void bajaMozo(Mozo mozo) throws MozoNoExistenteException, PermisoDenegadoException {

        if (this.empresa.getUsuarioLogueado().getUsername().equals("admin")){
            Set<Mozo> mozos = this.empresa.getMozos();

            boolean existeMozo = mozos.stream().anyMatch(m -> m.getId().equalsIgnoreCase(mozo.getId()) );
            if( !existeMozo )
                throw new MozoNoExistenteException();
            else{
                mozos.remove(mozo);
                this.empresa.setMozos(mozos);
                persistirMozos();
            }
        }else
            throw new PermisoDenegadoException();

    }

    public void modEstadoMozo(Mozo mozo, EstadoMozo nuevoEstado) throws MozoNoExistenteException {

        Set<Mozo> mozos = this.empresa.getMozos();
        Optional<Mozo> mozoMod = mozos.stream()
                .filter(m -> m.getId().equals(mozo.getId()))
                .findFirst();

        if( mozoMod.isPresent() ){
            mozoMod.get().setEstadoMozo(nuevoEstado);
            mozos.remove(mozoMod.get());
            mozos.add(mozoMod.get());
            persistirMozos();
        } else
            throw new MozoNoExistenteException();

    }

    public Set<Mozo> getMozos(){
        return empresa.getMozos();
    }
}
