package vistas;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class VistaAltaProducto extends JFrame implements IGenerica, KeyListener {

	private JFrame frmAltaDeProducto;
	private JTextField txtNombre;
	private JButton btnAceptar;
	private JButton btnVolver;
	private JTextField txtPrecioCosto;
	private JTextField txtPrecioVenta;
	private JTextField txtStockInicial;
	
	private String nombre;
	private float precioCosto;
	private float precioVenta;
	private int stockInicial;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaAltaProducto window = new VistaAltaProducto();
					window.frmAltaDeProducto.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VistaAltaProducto() {
		initialize();
	}

	private void initialize() {
		frmAltaDeProducto = new JFrame();
		frmAltaDeProducto.setTitle("Nuevo Producto");
		frmAltaDeProducto.setBounds(100, 100, 450, 300);
		frmAltaDeProducto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAltaDeProducto.getContentPane().setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre producto");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombre.setBounds(39, 24, 110, 21);
		frmAltaDeProducto.getContentPane().add(lblNombre);
		
		JLabel lblPrecioCosto = new JLabel("Precio Costo");
		lblPrecioCosto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecioCosto.setBounds(39, 66, 126, 20);
		frmAltaDeProducto.getContentPane().add(lblPrecioCosto);
		
		JLabel lblPrecioVenta = new JLabel("Precio Venta");
		lblPrecioVenta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecioVenta.setBounds(39, 106, 126, 32);
		frmAltaDeProducto.getContentPane().add(lblPrecioVenta);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(200, 20, 184, 32);
		frmAltaDeProducto.getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		txtNombre.addKeyListener(this);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAceptar.setBounds(274, 218, 110, 32);
		frmAltaDeProducto.getContentPane().add(btnAceptar);
		
		btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnVolver.setBounds(39, 218, 110, 32);
		frmAltaDeProducto.getContentPane().add(btnVolver);
		
		txtPrecioCosto = new JTextField();
		txtPrecioCosto.setColumns(10);
		txtPrecioCosto.setBounds(200, 63, 184, 32);
		txtPrecioCosto.addKeyListener(this);
		frmAltaDeProducto.getContentPane().add(txtPrecioCosto);
		
		txtPrecioVenta = new JTextField();
		txtPrecioVenta.setColumns(10);
		txtPrecioVenta.setBounds(200, 108, 184, 32);
		txtPrecioVenta.addKeyListener(this);
		frmAltaDeProducto.getContentPane().add(txtPrecioVenta);
		
		JLabel lblStockInicial = new JLabel("Stock Inicial");
		lblStockInicial.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStockInicial.setBounds(39, 153, 126, 32);
		frmAltaDeProducto.getContentPane().add(lblStockInicial);
		
		txtStockInicial = new JTextField();
		txtStockInicial.setColumns(10);
		txtStockInicial.setBounds(200, 155, 184, 32);
		txtStockInicial.addKeyListener(this);
		frmAltaDeProducto.getContentPane().add(txtStockInicial);
		
		limpia();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.nombre = this.txtNombre.getText();
		try {
			this.precioCosto = Float.parseFloat(this.txtPrecioCosto.getText());
			this.precioVenta = Float.parseFloat(this.txtPrecioVenta.getText());
			this.stockInicial = Integer.parseInt(this.txtStockInicial.getText());
		}
		catch (Exception ex){
			JOptionPane.showMessageDialog(null, "Ingreso mal algun valor numerico", "Error", JOptionPane.ERROR_MESSAGE);
			this.txtPrecioCosto.setText("");
			this.txtPrecioVenta.setText("");
			this.txtStockInicial.setText("");
		}
		if( nombre.length() > 0 &&
			precioCosto > 0 &&
			precioVenta > 0 &&
			stockInicial > 0) {
			
			this.btnAceptar.setEnabled(true);
			
		}
		
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		this.btnAceptar.addActionListener(actionListener);
		this.btnVolver.addActionListener(actionListener);
	}

	@Override
	public void mostrar() {
		this.frmAltaDeProducto.setVisible(true);
	}

	@Override
	public void esconder() {
		this.frmAltaDeProducto.setVisible(false);
		
	}

	@Override
	public void limpia() {
		this.btnAceptar.setEnabled(false);
		this.txtNombre.setText("");
		this.txtPrecioCosto.setText("");
		this.txtPrecioVenta.setText("");
		this.txtStockInicial.setText("");
	}

	@Override
	public void success(String msg) {

	}

	@Override
	public void failure(String msg) {

	}

	public String getNombre() {
		return nombre;
	}

	public float getPrecioCosto() {
		return precioCosto;
	}

	public float getPrecioVenta() {
		return precioVenta;
	}

	public int getStockInicial() {
		return stockInicial;
	}
}
