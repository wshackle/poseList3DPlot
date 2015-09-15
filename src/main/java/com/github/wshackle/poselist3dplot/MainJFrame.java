/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.wshackle.poselist3dplot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import rcs.posemath.PmPose;

/**
 *
 * @author Will Shackleford {@literal <william.shackleford@nist.gov>}
 */
public class MainJFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainJFrame
     */
    public MainJFrame() {
        initComponents();
    }
    
   
    private static MainJFrame mjfForShowList = null;
    public static MainJFrame showPoseList(final List<? extends PmPose> l) {

        if(null == mjfForShowList) {
            mjfForShowList = new MainJFrame();
        }
        final MainJFrame mjf = mjfForShowList;
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (null != l) {
                    mjf.view3DPlotJPanel1.addTrack(TrackUtils.toTrack(l));
                }
                mjf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                mjf.setVisible(true);
            }
        });
        return mjf;
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        view3DPlotJPanel1 = new com.github.wshackle.poselist3dplot.View3DPlotJPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItemClearAll = new javax.swing.JMenuItem();
        jMenuItemSave = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItemTest1 = new javax.swing.JMenuItem();

        FormListener formListener = new FormListener();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pose List 3D Plot");

        jMenu1.setText("File");

        jMenuItem1.setText("Open ...");
        jMenuItem1.addActionListener(formListener);
        jMenu1.add(jMenuItem1);

        jMenuItemClearAll.setText("Clear All");
        jMenuItemClearAll.addActionListener(formListener);
        jMenu1.add(jMenuItemClearAll);

        jMenuItemSave.setText("Save As ...");
        jMenuItemSave.addActionListener(formListener);
        jMenu1.add(jMenuItemSave);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Test/Demo");

        jMenuItemTest1.setText("Test 1");
        jMenuItemTest1.addActionListener(formListener);
        jMenu3.add(jMenuItemTest1);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(view3DPlotJPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(view3DPlotJPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == jMenuItem1) {
                MainJFrame.this.jMenuItem1ActionPerformed(evt);
            }
            else if (evt.getSource() == jMenuItemClearAll) {
                MainJFrame.this.jMenuItemClearAllActionPerformed(evt);
            }
            else if (evt.getSource() == jMenuItemSave) {
                MainJFrame.this.jMenuItemSaveActionPerformed(evt);
            }
            else if (evt.getSource() == jMenuItemTest1) {
                MainJFrame.this.jMenuItemTest1ActionPerformed(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    public Track readTrack(CsvParseOptions options, File f) {
        return readTrack(options, f);
    }

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Comma-Separated-Values", "csv");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                final File f = chooser.getSelectedFile();
                openCsvFile(f);
            } catch (Exception ex) {
                Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    public void openCsvFile(final File f) {
        CsvParseOptions csvParseOptions = CsvParseOptionsJPanel.showDialog(this, f);
//                List<PM_POSE> l = Posemath.csvToPoseListF(chooser.getSelectedFile(),
//                        csvParseOptions.X_INDEX,
//                        csvParseOptions.Y_INDEX,
//                        csvParseOptions.Z_INDEX,
//                        -1, -1, -1);
        Track track = this.readTrack(csvParseOptions, f);
        track.cur_time_index = track.getData().size();
        this.view3DPlotJPanel1.setSingleTrack(track);
    }


    public void saveCsvFile(final File f) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            pw.println(TrackPoint.HEADER);
            this.view3DPlotJPanel1
                    .getTracksList()
                    .stream()
                    .flatMap(x -> x.stream())
                    .map(x -> x.getData())
                    .flatMap(x -> x.stream())
                    .map(x -> x.toString())
                    .forEach(x -> pw.println(x));
        }
    }


    private void jMenuItemClearAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemClearAllActionPerformed
        List<List<Track>> tracksList = this.view3DPlotJPanel1.getTracksList();
        if (null != tracksList) {
            tracksList.clear();
        }
        this.view3DPlotJPanel1.setTracksList(null);
    }//GEN-LAST:event_jMenuItemClearAllActionPerformed

    private void jMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Comma-Separated-Values", "csv");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                final File f = chooser.getSelectedFile();
                saveCsvFile(f);
            } catch (Exception ex) {
                Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItemSaveActionPerformed

    private void jMenuItemTest1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTest1ActionPerformed
        Track t = getTest1Track();
        this.view3DPlotJPanel1.setSingleTrack(t);
        this.view3DPlotJPanel1.autoSetScale();
    }//GEN-LAST:event_jMenuItemTest1ActionPerformed

    public static Track getTest1Track() {
        return TrackUtils.getTest1Track();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

//        JOptionPane.showMessageDialog(null, "Current Directory = "+ System.getProperty("user.dir"));
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainJFrame mjf = new MainJFrame();
                mjf.setVisible(true);
                for (String arg : args) {
                    try {
                        File f = new File(arg);
                        mjf.openCsvFile(f);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItemClearAll;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JMenuItem jMenuItemTest1;
    private com.github.wshackle.poselist3dplot.View3DPlotJPanel view3DPlotJPanel1;
    // End of variables declaration//GEN-END:variables
}
