package negocio;

import enums.EstadoMesa;
import excepciones.CierreMesaConEstadoLibreException;
import excepciones.MesaExistenteException;
import excepciones.MesaNoExistenteException;
import excepciones.MozoNoExistenteException;
import modelo.Empresa;
import modelo.Mesa;
import modelo.Mozo;
import modelo.Pedido;
import persistencia.IPersistencia;
import persistencia.PersistenciaXML;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GestionDeMesas {

    private final Empresa empresa;
    private static GestionDeMesas gestionDeMesas = null;

    private GestionDeMesas() {
        this.empresa = Empresa.getEmpresa();
    }

    public static GestionDeMesas get() {
        if( gestionDeMesas == null )
            gestionDeMesas = new GestionDeMesas();
        return gestionDeMesas;
    }

    public void persistirMesas(){
        IPersistencia<Set<Mesa>> persistencia = new PersistenciaXML();
        try {
            persistencia.abrirOutput("mesas.xml");
            persistencia.escribir(this.empresa.getMesas());
            persistencia.cerrarOutput();
        } catch (IOException ignored) {

        }
    }

    private void altaMesa(Mesa mesa) throws MesaExistenteException {

        Set<Mesa> mesas = this.empresa.getMesas();
        boolean existeMesa = mesas.stream().anyMatch(m -> m.getNroMesa() == mesa.getNroMesa() );

        if(!existeMesa){
            mesas.add(mesa);
            persistirMesas();
        }
        else
            throw new MesaExistenteException();
    }

    private void modificaMesa(Mesa mesa) throws MesaNoExistenteException {
        Set<Mesa> mesas = this.empresa.getMesas();
        Iterator<Mesa> it = mesas.iterator();

        boolean encontreMesa = false;
        Mesa m;

        while(it.hasNext() && !encontreMesa) {
            m = it.next();
            if(m.getNroMesa() == mesa.getNroMesa()){
                encontreMesa = true;
            }
        }
        if(encontreMesa) {
            mesas.remove(it.next());
            mesas.add(mesa);
            this.empresa.setMesas(mesas);
            persistirMesas();
        }
        else
            throw new MesaNoExistenteException();
    }

    private void bajaMesa(int nroMesa) throws MesaNoExistenteException {
        Set<Mesa> mesas = this.empresa.getMesas();
        Optional<Mesa> mesa = mesas.stream().filter(m -> m.getNroMesa() == nroMesa).findFirst();

        if(mesa.isPresent()){
            mesas.remove(mesa.get());
            this.empresa.setMesas(mesas);
            persistirMesas();
        }
        else
            throw new MesaNoExistenteException();
    }

    public void asignarMozoMesa(Mozo mozo, Mesa mesa) throws MozoNoExistenteException, MesaNoExistenteException {

        boolean existeMozo = this.empresa.getMozos().stream().anyMatch(m -> m.getId().equalsIgnoreCase(mozo.getId()) );
        boolean existeMesa;

        if( !existeMozo )
            throw new MozoNoExistenteException();
        else{
            Set<Mesa> mesas = this.empresa.getMesas();
            existeMesa = mesas.stream().anyMatch(m -> m.getNroMesa() == mesa.getNroMesa());
            if( !existeMesa )
                throw new MesaNoExistenteException();
            else{
                mesas.remove(mesa);
                mesa.setMozoAsignado(mozo);
                mesas.add(mesa);
                this.empresa.setMesas(mesas);
                persistirMesas();
            }
        }
    }

    public double cerrarMesa(Mesa mesa) throws MesaNoExistenteException, CierreMesaConEstadoLibreException {

        boolean existeMesa = this.empresa.getMesas().stream().anyMatch(m -> m.getNroMesa() == mesa.getNroMesa());
        if( !existeMesa )
            throw new MesaNoExistenteException();
        else{
            if( mesa.getEstadoMesa() == EstadoMesa.LIBRE ){
                throw new CierreMesaConEstadoLibreException();
            }else{
                List<Pedido> pedidosMesa = mesa.getComanda().getPedidos();
                mesa.setEstadoMesa(EstadoMesa.LIBRE);
                //mesa.setComanda(null); ?
                persistirMesas();

                return pedidosMesa.stream()
                        .mapToDouble(p -> p.getProducto().getPrecio())
                        .sum();
            }
        }
    }

}