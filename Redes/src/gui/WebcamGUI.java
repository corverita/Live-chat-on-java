/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import cliente.udp.ClienteEnviaAudioUDP;
import cliente.udp.ClienteEnviaVideoUDP;
import servidor.udp.ServidorEscuchaAudioUDP;
import servidor.udp.ServidorEscuchaVideoUDP;

import javax.swing.*;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author oscar
 */
public class WebcamGUI extends javax.swing.JFrame {
    private javax.swing.JButton jBtnActivarCamara;
    private javax.swing.JButton jBtnActivarMicrofono;
    private javax.swing.JButton jBtnDesactivarCamara;
    private javax.swing.JButton jBtnDesactivarMicrofono;
    private javax.swing.JLabel jLabelUsuario;
    private javax.swing.JLabel jLabelWServidor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private ClienteEnviaVideoUDP clienteEnviaVideoUDP;
    private ClienteEnviaAudioUDP clienteEnviaAudioUDP;
    private ServidorEscuchaVideoUDP servidorEscuchaVideoUDP;
    private ServidorEscuchaAudioUDP servidorEscuchaAudioUDP;
    private String ipServer;
    private int puertoServerVideo;
    private int puertoServerAudio;

    /**
     *
     */
    public WebcamGUI(String ipServer, int puertoServerVideo, int puertoClienteVideo, int puertoServerAudio, int puertoClienteAudio) throws SocketException, UnknownHostException {
        initComponents();
        this.ipServer=ipServer;
        this.puertoServerVideo=puertoServerVideo;
        this.puertoServerAudio=puertoServerAudio;

        servidorEscuchaAudioUDP=new ServidorEscuchaAudioUDP(puertoClienteAudio);
        servidorEscuchaAudioUDP.start();
        servidorEscuchaVideoUDP=new ServidorEscuchaVideoUDP(puertoClienteVideo, jLabelWServidor);
        servidorEscuchaVideoUDP.start();
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabelUsuario = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabelWServidor = new javax.swing.JLabel();
        jBtnActivarCamara = new javax.swing.JButton();
        jBtnDesactivarCamara = new javax.swing.JButton();
        jBtnActivarMicrofono = new javax.swing.JButton();
        jBtnDesactivarMicrofono = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelWServidor, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelWServidor, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 365, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(345, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addContainerGap()))
        );

        jBtnActivarCamara.setLabel("Activar Camara");
        jBtnActivarCamara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    jBtnActivarCamaraActionPerformed(evt);
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        });

        jBtnDesactivarCamara.setText("Desactivar Camara");
        jBtnDesactivarCamara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    jBtnDesactivarCamaraActionPerformed(evt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        jBtnActivarMicrofono.setText("Activar Microfono");
        jBtnActivarMicrofono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    jBtnActivarMicrofonoActionPerformed(evt);
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        });

        jBtnDesactivarMicrofono.setText("Desactivar Microfono");
        jBtnDesactivarMicrofono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnDesactivarMicrofonoActionPerformed(evt);
            }
        });

        jLabel1.setText("Cámara ajena");

        jLabel2.setText("Mi Cámara");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(54, 54, 54)
                                                .addComponent(jBtnActivarMicrofono)
                                                .addGap(18, 18, 18)
                                                .addComponent(jBtnDesactivarMicrofono)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jBtnActivarCamara)
                                                .addGap(18, 18, 18)
                                                .addComponent(jBtnDesactivarCamara)
                                                .addGap(15, 15, 15)))
                                .addContainerGap(23, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(163, 163, 163)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(150, 150, 150))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jBtnActivarCamara)
                                        .addComponent(jBtnDesactivarCamara)
                                        .addComponent(jBtnActivarMicrofono)
                                        .addComponent(jBtnDesactivarMicrofono))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2)))
        );

        pack();
    }

    private void jBtnActivarCamaraActionPerformed(java.awt.event.ActionEvent evt) throws SocketException, UnknownHostException {
        clienteEnviaVideoUDP= new ClienteEnviaVideoUDP(ipServer,puertoServerVideo,jLabelUsuario);
        clienteEnviaVideoUDP.start();

    }

    private void jBtnDesactivarCamaraActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        if(clienteEnviaVideoUDP!=null) {
            clienteEnviaVideoUDP.detener();
        }
    }

    private void jBtnActivarMicrofonoActionPerformed(java.awt.event.ActionEvent evt) throws SocketException, UnknownHostException {
        clienteEnviaAudioUDP=new ClienteEnviaAudioUDP(ipServer,puertoServerAudio);
        clienteEnviaAudioUDP.start();
    }

    private void jBtnDesactivarMicrofonoActionPerformed(java.awt.event.ActionEvent evt) {
        if(clienteEnviaAudioUDP!=null){
            clienteEnviaAudioUDP.detener();
        }
    }
}
