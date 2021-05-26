/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.File;
import java.io.FileReader;
import java.net.DatagramSocket;
import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

import cliente.tcp.ClienteEnviaTCP;
import cliente.udp.*;
import servidor.tcp.ServidorEscuchaTCP;
import servidor.udp.ServidorEscuchaUDP;
import servidor.udp.ServidorEscuchaVideoUDP;

/**
 *
 * @author oscar
 */
public class Gui1 extends javax.swing.JFrame {
    File file;
    FileReader lecturaArchivo;
	ClienteEnviaUDP clienteEnviaUDP;
	ClienteEnviaTCP clienteEnviaTCP;
	ClienteEnviaVideoUDP clienteEnviaVideoUDP;
	ServidorEscuchaUDP servidorEscuchaUDP;
	ServidorEscuchaTCP servidorEscuchaTCP;
	ServidorEscuchaVideoUDP servidorEscuchaVideoUDP;
	JTextArea textAreaRecibidos;
	JTextArea textAreaEnviados;
	String ipServer;
	int puertoServidorArchivos;
    
    /**
     * Creates new form Gui
     */
    public Gui1(DatagramSocket socketEscucha, String ipServer, int puertoServerMensajes, int puertoClienteMensajes, int puertoServerVideo, int puertoClienteVideo, int puertoServerArchivos) throws Exception {
        initComponents();
        this.ipServer=ipServer;
        labelRuta.setText("No hay un archivo seleccionados");
		clienteEnviaUDP=new ClienteEnviaUDP(socketEscucha,ipServer,puertoServerMensajes);
		clienteEnviaVideoUDP= new ClienteEnviaVideoUDP(ipServer,puertoServerVideo);
		servidorEscuchaVideoUDP=new ServidorEscuchaVideoUDP(puertoClienteVideo);
		servidorEscuchaUDP =new ServidorEscuchaUDP(puertoClienteMensajes,textAreaRecibidos,textAreaEnviados);
		puertoServidorArchivos=puertoServerArchivos;
		servidorEscuchaUDP.start();
    }

    public void iniciaServidorTCP(int puertoClienteArchivos){
        try {
            servidorEscuchaTCP=new ServidorEscuchaTCP(puertoClienteArchivos);
            servidorEscuchaTCP.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        textPaneMensaje = new javax.swing.JTextPane();
        btnSeleccionarArchivo = new javax.swing.JButton();
        btnActivarCamara = new javax.swing.JButton();
        btnEnviarMensaje = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaRecibidos = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        textAreaEnviados = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        labelRuta = new javax.swing.JLabel();
        btnEnviarArchivo = new javax.swing.JButton();
        labelVelocidadEnvio = new javax.swing.JLabel();
        labelTiempoEnvio = new javax.swing.JLabel();
        labelVelActual = new javax.swing.JLabel();
        labelTiempoRestante = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textPaneMensaje.setToolTipText("Escribe tu mensaje");
        jScrollPane1.setViewportView(textPaneMensaje);

        btnSeleccionarArchivo.setText("Seleccionar Archivo");
        btnSeleccionarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarArchivoActionPerformed(evt);
            }
        });

        btnActivarCamara.setIcon(new javax.swing.ImageIcon(getClass().getResource("camara-web.png"))); // NOI18N

        btnEnviarMensaje.setText("Enviar");
        btnEnviarMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarMensajeActionPerformed(evt);
            }
        });

        btnActivarCamara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActivarCamaraActionPerformed(evt);
            }
        });

        textAreaRecibidos.setEditable(false);
        textAreaRecibidos.setColumns(20);
        textAreaRecibidos.setRows(5);
        textAreaRecibidos.setEnabled(false);
        textAreaRecibidos.setName("jTextServer"); // NOI18N
		textAreaRecibidos.setText("");
        jScrollPane2.setViewportView(textAreaRecibidos);

        textAreaEnviados.setEditable(false);
        textAreaEnviados.setColumns(20);
        textAreaEnviados.setRows(5);
        textAreaEnviados.setEnabled(false);
        textAreaEnviados.setName("jTextCliente"); // NOI18N
		textAreaEnviados.setText("");
        jScrollPane3.setViewportView(textAreaEnviados);

        jTextField1.setEditable(false);
        jTextField1.setText("Mensajes del cliente");
        jTextField1.setEnabled(false);

        jTextField2.setEditable(false);
        jTextField2.setText("Mensajes del servidor");
        jTextField2.setEnabled(false);

        labelRuta.setText("Archivo seleccionado:");

        btnEnviarArchivo.setText("Enviar Archivo");
        btnEnviarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnEnviarArchivoActionPerformed(evt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        labelVelocidadEnvio.setText("Velocidad de envío");

        labelTiempoEnvio.setText("Tiempo de envio");

        labelVelActual.setText("NA");

        labelTiempoRestante.setText("NA");

        jLabel1.setText("Activar Camara");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnEnviarMensaje)
                                            .addComponent(btnEnviarArchivo)
                                            .addComponent(btnSeleccionarArchivo))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                                        .addComponent(btnActivarCamara, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(labelVelocidadEnvio)
                                            .addComponent(labelVelActual))
                                        .addGap(26, 26, 26)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(labelTiempoRestante)
                                            .addComponent(labelTiempoEnvio)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(labelRuta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(216, 216, 216)
                                .addComponent(jLabel1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(39, 39, 39))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(labelRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnSeleccionarArchivo)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnEnviarArchivo)
                            .addGap(11, 11, 11)
                            .addComponent(btnEnviarMensaje)
                            .addGap(15, 15, 15))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnActivarCamara)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(labelTiempoEnvio)
                                .addComponent(labelVelocidadEnvio))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(labelTiempoRestante)
                                .addComponent(labelVelActual)))))
                .addGap(0, 0, 0))
        );

        pack();
    }

    private void btnActivarCamaraActionPerformed(java.awt.event.ActionEvent evt) {
        servidorEscuchaVideoUDP.start();
        clienteEnviaVideoUDP.start();
    }

    private void btnSeleccionarArchivoActionPerformed(java.awt.event.ActionEvent evt) {
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            if(file.exists()){
                labelRuta.setText("Archivo seleccionado: "+file.getAbsolutePath());
            }else{
                labelRuta.setText("Error al leer el archivo. No se encuentra o no se puede leer.");
            }
            try {
                lecturaArchivo = new FileReader( file.getAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No pude leer el archivo: "+file.getAbsolutePath());
            }
        } else {
            System.out.println("File access cancelled by user.");
        }
    }

    private void btnEnviarArchivoActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
        if(clienteEnviaTCP==null){
            clienteEnviaTCP=new ClienteEnviaTCP(ipServer,puertoServidorArchivos);
        }
        if(file!=null){
            clienteEnviaTCP.enviarArchivo(file);
            //Proceder a enviarlo
            file=null;
            labelRuta.setText("No hay un archivo seleccionados");
        }else{
            //No enviarlo, cancelarlo y dar un warning
            JOptionPane.showMessageDialog(null, "No hay archivo para enviar");
        }
    }

    private void btnEnviarMensajeActionPerformed(java.awt.event.ActionEvent evt) {
        String mensaje=textPaneMensaje.getText();
        if(mensaje.isEmpty()){
            JOptionPane.showMessageDialog(null, "Tu mensaje está vacío");
            return;
        }String mensajesRecibidos=textAreaRecibidos.getText();
        mensajesRecibidos+="\n";
        textAreaRecibidos.setText(mensajesRecibidos);
        String mensajesEnviados=textAreaEnviados.getText();
        mensajesEnviados+=mensaje+"\n";
        textAreaEnviados.setText(mensajesEnviados);
        textPaneMensaje.setText("");

		clienteEnviaUDP.enviarMensaje(mensaje);
    }

    private javax.swing.JButton btnActivarCamara;
    private javax.swing.JButton btnEnviarArchivo;
    private javax.swing.JButton btnEnviarMensaje;
    private javax.swing.JButton btnSeleccionarArchivo;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel labelRuta;
    private javax.swing.JLabel labelTiempoEnvio;
    private javax.swing.JLabel labelTiempoRestante;
    private javax.swing.JLabel labelVelActual;
    private javax.swing.JLabel labelVelocidadEnvio;
    private javax.swing.JTextPane textPaneMensaje;
}
