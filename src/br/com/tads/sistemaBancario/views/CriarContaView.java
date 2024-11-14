package br.com.tads.sistemaBancario.views;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.tads.sistemaBancario.controllers.ContaController;
import br.com.tads.sistemaBancario.models.Result;
import br.com.tads.sistemaBancario.models.cliente.Cliente;

public class CriarContaView extends JPanel {
		private MainView telaPrincipal;
	    private JLabel clienteLabel;
	    private JLabel tipoContaLabel;
	    private JComboBox<String> tipoContaComboBox;
	    private JComboBox<Cliente> clienteComboBox;
	    private JLabel depositoInicialLabel;
	    private JTextField depositoInicialField;
	    private JLabel limiteLabel;
	    private JTextField limiteField;
	    private JLabel montanteMinimoLabel;
	    private JTextField montanteMinimoField;
	    private JLabel depositoMinimoLabel;
	    private JTextField depositoMinimoFieldInicial;
	    private JButton criarContaButton;
	    private ContaController contaController;

	    public CriarContaView(MainView telaPrincipal) {
	    	
	        this.telaPrincipal = telaPrincipal;
	        this.contaController = new ContaController();

	        setLayout(new FlowLayout());

	        JPanel contaPanel = new JPanel(new GridLayout(7	,1 ,10 ,1));
	        clienteLabel = new JLabel("Cliente:");
	        clienteComboBox = new JComboBox<Cliente>();
	        clienteComboBox.setModel(telaPrincipal.getClientesListModel());
	        contaPanel.add(clienteLabel);
	        contaPanel.add(clienteComboBox);

	        tipoContaLabel = new JLabel("Tipo de Conta:");
	        tipoContaComboBox = new JComboBox<>();
	        DefaultComboBoxModel<String> defaultComboBoxModel = new DefaultComboBoxModel<String>();
	        defaultComboBoxModel.addElement("selecione");
	        defaultComboBoxModel.addElement("corrente");
	        defaultComboBoxModel.addElement("investimento");
	        tipoContaComboBox.setModel(defaultComboBoxModel);
	        tipoContaComboBox.addActionListener(e -> updateContaFields());
	        contaPanel.add(tipoContaLabel);
	        contaPanel.add(tipoContaComboBox);

	        depositoInicialLabel = new JLabel("Depósito Inicial:");
	        depositoInicialField = new JTextField();
	        contaPanel.add(depositoInicialLabel);
	        contaPanel.add(depositoInicialField);
	        limiteLabel = new JLabel("Limite:");
	        limiteField = new JTextField();
	        contaPanel.add(limiteLabel);
	        contaPanel.add(limiteField);

	        montanteMinimoLabel = new JLabel("Montante Mínimo:");
	        montanteMinimoField = new JTextField(0);
	        contaPanel.add(montanteMinimoLabel);
	        contaPanel.add(montanteMinimoField);

	        depositoMinimoLabel = new JLabel("Depósito Mínimo Inicial:");
	        depositoMinimoFieldInicial = new JTextField();
	        contaPanel.add(depositoMinimoLabel);
	        contaPanel.add(depositoMinimoFieldInicial);

	        criarContaButton = new JButton("Criar Conta");
	        criarContaButton.addActionListener(e -> criarConta());
	        contaPanel.add(criarContaButton);
	        
	        depositoInicialLabel.setVisible(false);
            depositoInicialField.setVisible(false);
            limiteLabel.setVisible(false);
            limiteField.setVisible(false);
            montanteMinimoLabel.setVisible(false);
            montanteMinimoField.setVisible(false);
            depositoMinimoLabel.setVisible(false);
            depositoMinimoFieldInicial.setVisible(false); 
	        add(contaPanel);

	    }
	    
	    private void updateContaFields() {
	        String selectedTipoConta = (String) tipoContaComboBox.getSelectedItem();
	        if (selectedTipoConta != null) {
	            switch (selectedTipoConta) {
	                case "corrente":
	                    depositoInicialLabel.setVisible(true);
	                    depositoInicialField.setVisible(true);
	                    limiteLabel.setVisible(true);
	                    limiteField.setVisible(true);
	                    montanteMinimoLabel.setVisible(false);
	                    montanteMinimoField.setVisible(false);
	                    depositoMinimoLabel.setVisible(false);
	                    depositoMinimoFieldInicial.setVisible(false);
	                    break;
	                case "investimento":
	                    depositoInicialLabel.setVisible(true);
	                    depositoInicialField.setVisible(true);
	                    limiteLabel.setVisible(false);
	                    limiteField.setVisible(false);
	                    montanteMinimoLabel.setVisible(true);
	                    montanteMinimoField.setVisible(true);
	                    depositoMinimoLabel.setVisible(true);  
	                    depositoMinimoFieldInicial.setVisible(true);  
	                    break;
	                default: 
	                	depositoInicialLabel.setVisible(false);
	                    depositoInicialField.setVisible(false);
	                    limiteLabel.setVisible(false);
	                    limiteField.setVisible(false);
	                    montanteMinimoLabel.setVisible(false);
	                    montanteMinimoField.setVisible(false);
	                    depositoMinimoLabel.setVisible(false);  
	                    depositoMinimoFieldInicial.setVisible(false);  
	                    break;
	            }
	        } 
	    }
	    
	    private void criarConta() {
	    	try {
	    		Cliente cliente = (Cliente) clienteComboBox.getSelectedItem();
		        String tipoConta = (String) tipoContaComboBox.getSelectedItem();

		        if (cliente == null || tipoConta == null || tipoConta.equals("selecione")) {
		            JOptionPane.showMessageDialog(this, "Por favor, selecione um cliente e um tipo de conta.");
		            return;
		        }

		        double depositoInicial;
		        try {
		            depositoInicial = Double.parseDouble(depositoInicialField.getText());
		        } catch (NumberFormatException e) {
		            JOptionPane.showMessageDialog(this, "Depósito inicial inválido.");
		            return;
		        }

		        Result result;
		        switch (tipoConta) {
		            case "corrente":
		                double limite;
		                try {
		                    limite = Double.parseDouble(limiteField.getText());
		                } catch (NumberFormatException e) {
		                    JOptionPane.showMessageDialog(this, "Limite inválido.");
		                    return;
		                }
		                result = contaController.criarContaCorrente(cliente, depositoInicial, limite);
		                break;
		            case "investimento":
		                double montanteMinimo;
		                double depositoMinimoInicial;
		                try {
		                    montanteMinimo = Double.parseDouble(montanteMinimoField.getText());
		                    depositoMinimoInicial = Double.parseDouble(depositoMinimoFieldInicial.getText());
		                } catch (NumberFormatException e) {
		                    JOptionPane.showMessageDialog(this, "Montante mínimo ou depósito mínimo inicial inválido.");
		                    return;
		                }
		                result = contaController.criarContaInvestimento(cliente, depositoInicial, montanteMinimo, depositoMinimoInicial);
		                break;
		            default:
		                JOptionPane.showMessageDialog(this, "Tipo de conta inválido.");
		                return;
		        }

		        if (result.getStatus()) {
		            JOptionPane.showMessageDialog(this, result.getMessage());
		            //telaPrincipal.showMainView();
		        } else {
		            JOptionPane.showMessageDialog(this, result.getMessage());
		        }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Erro ao criar conta");
			}
	        
	    }
}
