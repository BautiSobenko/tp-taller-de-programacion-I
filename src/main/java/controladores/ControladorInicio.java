package controladores;

import modelo.Empresa;
import vistas.IGenerica;
import vistas.VistaInicio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorInicio implements ActionListener {

    private VistaInicio vistaInicio;
    private Empresa empresa;

    private static ControladorInicio controladorInicio = null;

    private ControladorInicio() {
        this.vistaInicio = new VistaInicio();
        this.vistaInicio.setActionListener(this);
        this.empresa = Empresa.getEmpresa();
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public static ControladorInicio getControladorInicio(boolean mostrar) {
        if (controladorInicio == null) {
            controladorInicio = new ControladorInicio();
        }
        if( mostrar )
            controladorInicio.vistaInicio.mostrar();

        return controladorInicio;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if(comando.equals("Gestionar Mozos")){
            this.vistaInicio.esconder();
            ControladorGestionMozos controladorGestionMozos = ControladorGestionMozos.getControladorGestionMozos(true);
        }
        else if(comando.equals("Gestionar Operarios")){
            this.vistaInicio.esconder();
            ControladorGestionOperario controladorGestionOperario = ControladorGestionOperario.getControladorGestionOperario(true);
        }
        else if(comando.equals("Gestionar Mesas")){
            this.vistaInicio.esconder();
            ControladorGestionMesas controladorGestionMesas = ControladorGestionMesas.getControladorGestionMesas(true);
        }
        else if(comando.equals("Gestionar Productos")){
            this.vistaInicio.esconder();
            ControladorGestionProductos controladorGestionProductos = ControladorGestionProductos.getControladorGestionProductos(true);
        }
        else if(comando.equals("Gestionar Promociones")){
            this.vistaInicio.esconder();
            ControladorGestionPromociones controladorGestionPromociones = ControladorGestionPromociones.getControladorGestionPromociones(true);
        }
        else if(comando.equals("Abrir Local")){
            this.vistaInicio.esconder();
            ControladorLocalAbierto controladorLocalAbierto = ControladorLocalAbierto.getControladorLocalAbierto();
        }
        else if(comando.equals("Cerrar Sesion")){
            this.vistaInicio.esconder();
            ControladorLogin controladorLogin = ControladorLogin.getControladorLogin(true);
        }


    }
}
