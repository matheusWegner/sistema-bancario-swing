package br.com.tads.sistemaBancario.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.tads.sistemaBancario.controllers.ClienteController;
import br.com.tads.sistemaBancario.models.cliente.Cliente;

public class MainView extends JFrame {
    private CardLayout cardLayout;
    private JPanel panelContainer;
    private EditarContaView editarContaView;
    private CriarContaView criarContaView;
    public MainView() {
        setTitle("Sistema Bancário");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        panelContainer = new JPanel(cardLayout);

        panelContainer.add(new ClienteView(this), "Clientes");
        criarContaView = new CriarContaView(this);
        panelContainer.add(criarContaView, "VincularConta");
        editarContaView = new EditarContaView(this);
        panelContainer.add(editarContaView, "EditarConta");
        
        add(panelContainer);
        
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnTelaCliente = new JButton("Gerenciar Clientes");
        JButton btnTelaCriarConta = new JButton("Vincular Conta");
        JButton btnTelaEditarConta = new JButton("Editar Conta Conta");

        btnTelaCliente.addActionListener(e -> mostrarTela("Clientes"));
        btnTelaCriarConta.addActionListener(e -> mostrarTela("VincularConta"));
        btnTelaEditarConta.addActionListener(e -> mostrarTela("EditarConta"));

        headerPanel.add(btnTelaCliente);
        headerPanel.add(btnTelaCriarConta);
        headerPanel.add(btnTelaEditarConta);

        add(headerPanel, BorderLayout.NORTH);
        add(panelContainer, BorderLayout.CENTER);

        setVisible(true);
    }

    public void mostrarTela(String nomeTela) {
    	if ("EditarConta".equals(nomeTela)) {
            editarContaView.getClientes(); 
        }
    	if ("VincularConta".equals(nomeTela)) {
    		criarContaView.getClientes(); 
    	}
        cardLayout.show(panelContainer, nomeTela);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView());
    }
    
}