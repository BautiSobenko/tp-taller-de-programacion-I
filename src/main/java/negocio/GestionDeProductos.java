package negocio;

import dto.ProductoDTO;
import excepciones.ProductoExistenteException;
import excepciones.ProductoNoExistenteException;
import modelo.Empresa;
import modelo.Operario;
import modelo.Producto;
import persistencia.IPersistencia;
import persistencia.PersistenciaXML;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class GestionDeProductos {

    private final Empresa empresa;
    private static GestionDeProductos gestionDeProductos = null;

    private GestionDeProductos() {
        this.empresa = Empresa.getEmpresa();
    }

    public static GestionDeProductos get() {
        if( gestionDeProductos == null )
            gestionDeProductos = new GestionDeProductos();
        return gestionDeProductos;
    }

    public void persistirProductos(){
        IPersistencia<Set<Producto>> persistencia = new PersistenciaXML();
        try {
            persistencia.abrirOutput("productos.xml");
            persistencia.escribir(this.empresa.getProductos());
            persistencia.cerrarOutput();
        } catch (IOException e) {

        }
    }

    public void altaProducto(ProductoDTO producto) throws ProductoExistenteException {
        Set<Producto> productos = this.empresa.getProductos();
        Producto productoNuevo = new Producto(producto.getNombre(), producto.getPrecio(), producto.getCosto(), producto.getStock());
        boolean existeProducto = productos.stream().anyMatch(p -> p.getId().equals(productoNuevo.getId()));

        if(!existeProducto){
            productos.add(productoNuevo);
            this.empresa.setProductos(productos);
            persistirProductos();
        }
        else
            throw new ProductoExistenteException();
    }

    public void modificaProducto(ProductoDTO producto) {
        Set<Producto> productos = this.empresa.getProductos();
        Iterator<Producto> it = productos.iterator();
        Producto productoMod= new Producto(producto.getNombre(), producto.getPrecio(), producto.getCosto(), producto.getStock());

        boolean encontreProducto = false;
        Producto prod = null;

        while(it.hasNext() && !encontreProducto) {
            prod = it.next();
            if(prod.getId().equals(productoMod.getId())){
                encontreProducto = true;
            }
        }
        if(encontreProducto) {
            productos.remove(prod);
            productos.add(productoMod);
            this.empresa.setProductos(productos);
            persistirProductos();
        }

    }

    public void bajaProducto(String id) {
        Set<Producto> productos = this.empresa.getProductos();
        Iterator<Producto> it = productos.iterator();
        boolean encontreProducto = false;
        Producto prod = null;

        while(it.hasNext() && !encontreProducto) {
            prod = it.next();
            if(prod.getId().equals(id)){
                encontreProducto = true;
            }
        }
        if(encontreProducto){
            productos.remove(prod);
            this.empresa.setProductos(productos);
            persistirProductos();
        }
    }

    public Set<Producto> getProductos(){
        return this.empresa.getProductos();
    }



}
