/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import panels.ItemMVPanel;

/**
 *
 * @author tranv
 */
public class MyCustomDialog extends JDialog {

    public MyCustomDialog(JFrame parent,String name, JPanel pn) {
        super(parent, name, true);
        setLayout(new BorderLayout());

        getContentPane().add(pn);

        pack();
        setLocationRelativeTo(parent);
    }
    
//     public MyCustomDialog(Component parent,String name, JPanel pn) {
//        setLayout(new BorderLayout());
//
//        getContentPane().add(pn);
//
//        pack();
//        setLocationRelativeTo(parent);
//    }

}
