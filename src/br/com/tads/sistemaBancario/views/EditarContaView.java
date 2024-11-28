package br.com.tads.sistemaBancario.views;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.com.tads.sistemaBancario.controllers.ClienteController;
import br.com.tads.sistemaBancario.controllers.ContaController;
import br.com.tads.sistemaBancario.models.Result;
import br.com.tads.sistemaBancario.models.cliente.Cliente;
import br.com.tads.sistemaBancario.models.conta.Conta;
import br.com.tads.sistemaBancario.models.conta.ContaCorrente;
import br.com.tads.sistemaBancario.models.conta.ContaInvestimento;

public class EditarContaView extends JPanel{
    private MainView telaPrincipal;
    private ContaController contaController;
    private JLabel clienteLabel;
    private JComboBox<Cliente> clienteComboBox;
    private JLabel valorLabel;
    private JTextField valorText;
    private JLabel limiteLabel;
    private JTextField limiteText;
    private JLabel depositoMinimoLabel;
    private JTextField depositoMinimoText;
    private JLabel montanteMinimoLabel;
    private JTextField montanteMinimoText;
    private JButton saqueContaButton;
    private JButton saldoContaButton;
    private JButton depositoContaButton;
    private JButton remuneraContaButton;
    DefaultComboBoxModel<Cliente> comboClienteModel = new DefaultComboBoxModel<>();
    private ClienteController clienteController;

    public EditarContaView(MainView telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
        this.contaController = new ContaController();
        this.clienteController = new ClienteController();

        JPanel editarContaPanel = new JPanel(new GridLayout(3,1 ,10 ,1));

        JPanel contaPanel = new JPanel(new GridLayout(3,1 ,10 ,1));
        clienteLabel = new JLabel("Cliente:");
        clienteComboBox = new JComboBox<Cliente>();
        getClientes();
        clienteComboBox.setModel(comboClienteModel);
        clienteComboBox.addActionListener(e -> atualizarInfoConta());
        contaPanel.add(clienteLabel);
        contaPanel.add(clienteComboBox);

        valorLabel = new JLabel("Valor :");
        valorText = new JTextField();
        contaPanel.add(valorLabel);
        contaPanel.add(valorText);

        
        
        JPanel acoesPanel = new JPanel(new GridLayout(1,4 ,5 ,5));
        saqueContaButton = new JButton("Sacar");
        saqueContaButton.addActionListener(e -> sacarConta());
        acoesPanel.add(saqueContaButton);
        saldoContaButton = new JButton("Ver saldo");
        saldoContaButton.addActionListener(e -> verSaldoConta());
        acoesPanel.add(saldoContaButton);
        depositoContaButton = new JButton("Depositar");
        depositoContaButton.addActionListener(e -> depositarConta());
        acoesPanel.add(depositoContaButton);
        remuneraContaButton = new JButton("Remunerar");
        remuneraContaButton.addActionListener(e -> remunerarConta());
        acoesPanel.add(remuneraContaButton);
        
        
        JPanel infoPanel = new JPanel(new GridLayout(3,1 ,10 ,1));

        limiteLabel = new JLabel("Limite:");
        limiteText = new JTextField();
        limiteText.setEditable(false);
        infoPanel.add(limiteLabel);
        infoPanel.add(limiteText);

        depositoMinimoLabel = new JLabel("Depósito Mínimo:");
        depositoMinimoText = new JTextField();
        depositoMinimoText.setEditable(false);
        infoPanel.add(depositoMinimoLabel);
        infoPanel.add(depositoMinimoText);

        montanteMinimoLabel= new JLabel("Montante Mínimo:");
        montanteMinimoText = new JTextField();
        montanteMinimoText.setEditable(false);
        infoPanel.add(montanteMinimoLabel);
        infoPanel.add(montanteMinimoText);
        infoPanel.setBorder(new EmptyBorder(10, 0, 0, 0)); 
        
        editarContaPanel.add(contaPanel);
        editarContaPanel.add(acoesPanel);
        editarContaPanel.add(infoPanel);
        add(editarContaPanel);
    }
    
    public void avisoConta () {
        JOptionPane.showMessageDialog(this, "O cliente não possui conta vinculada");
    }
    
    private void remunerarConta() {
        try {
            if (validaForm()) {
                Cliente selectedCliente = (Cliente) clienteComboBox.getSelectedItem();
                Result result = contaController.remunerarConta(selectedCliente);
                JOptionPane.showMessageDialog(this, result.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao sacar da conta");
        }
    }

    private void depositarConta() {
        if (validaForm()) {
            try {
                Cliente selectedCliente = (Cliente) clienteComboBox.getSelectedItem();
                double valor = Double.parseDouble(valorText.getText());
                Result result = contaController.depositarConta(selectedCliente, valor);
                JOptionPane.showMessageDialog(this, result.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao sacar da conta");
            }
        }
    }

    private void sacarConta() {
        try {
            if (validaForm()) {
                Cliente selectedCliente = (Cliente) clienteComboBox.getSelectedItem();
                double valor = Double.parseDouble(valorText.getText());
                Result result = contaController.sacarConta(selectedCliente, valor);
                JOptionPane.showMessageDialog(this, result.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao sacar da conta");
        }
    }

    private void verSaldoConta() {
        try {
            if (validaConta()) {
                Cliente selectedCliente = (Cliente) clienteComboBox.getSelectedItem();
                Result result = contaController.verSaldoConta(selectedCliente);
                JOptionPane.showMessageDialog(this, result.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao buscar saldo");
        }
    }
    
    public boolean validaForm() {
        if(valorText.getText() != null && !"".equals(valorText.getText())) {
            try {
               double valor = Double.parseDouble(valorText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Preencha o campo com um número válido.");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Preencha o campo com um número válido.");
            return false;
        }
        return validaConta();
    }
    
    public boolean validaConta() {
        Cliente selectedCliente = (Cliente) clienteComboBox.getSelectedItem();
        if (selectedCliente == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente");
            return false;
        }
        return true;
    }
    
    public List<Cliente> getClientes() {
        List<Cliente> clientes = this.clienteController.getAllClientes();
        this.comboClienteModel.removeAllElements();
        for(Cliente cliente : clientes) {
            this.comboClienteModel.addElement(cliente);
        }
        return clientes;
    }

    private void atualizarInfoConta() {
        Cliente selectedCliente = (Cliente) clienteComboBox.getSelectedItem();
        if (selectedCliente != null) {
        	Conta conta = contaController.findByCliente(selectedCliente);
        	if (conta instanceof ContaCorrente) {
        		showInfoContaCorrente();
        		ContaCorrente contaInvestimento = (ContaCorrente) conta;
        		double limite = contaInvestimento.getLimite();
        		limiteText.setText(String.valueOf(limite));
    		} else if (conta instanceof ContaInvestimento) {
    			showInfoContaInvestimento();
    			ContaInvestimento contaInvestimento = (ContaInvestimento) conta;
    			double depositoMinimo = contaInvestimento.getDepositoMinimo();
    			double montanteMinimo = contaInvestimento.getMontanteMinimo();
    			depositoMinimoText.setText(String.valueOf(depositoMinimo));
    			montanteMinimoText.setText(String.valueOf(montanteMinimo));
    			
    		}
        } else {
            limiteText.setText("");
            depositoMinimoText.setText("");
        }
    }
    
    public void showInfoContaCorrente() {
		limiteText.setVisible(true);
    	limiteLabel.setVisible(true);
    	montanteMinimoText.setVisible(false);
    	montanteMinimoLabel.setVisible(false);
    	depositoMinimoText.setVisible(false);
    	depositoMinimoLabel.setVisible(false);
    }
    

    public void showInfoContaInvestimento() {
    	limiteText.setVisible(false);
    	limiteLabel.setVisible(false);
    	montanteMinimoText.setVisible(true);
    	montanteMinimoLabel.setVisible(true);
    	depositoMinimoText.setVisible(true);
    	depositoMinimoLabel.setVisible(true);
    }

}
