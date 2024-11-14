package br.com.tads.sistemaBancario.views;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.tads.sistemaBancario.controllers.ContaController;
import br.com.tads.sistemaBancario.models.Result;
import br.com.tads.sistemaBancario.models.cliente.Cliente;

public class EditarContaView extends JPanel{
	private MainView telaPrincipal;
    private ContaController contaController;
	private JLabel clienteLabel;
    private JComboBox<Cliente> clienteComboBox;
    private JLabel valorLabel;
    private JTextField valorText;
    private JButton saqueContaButton;
    private JButton saldoContaButton;
    private JButton depositoContaButton;
    private JButton remuneraContaButton;
    
    public EditarContaView(MainView telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
        this.contaController = new ContaController();

        JPanel editarContaPanel = new JPanel(new GridLayout(2,1 ,10 ,1));

        JPanel contaPanel = new JPanel(new GridLayout(2	,1 ,10 ,1));
        clienteLabel = new JLabel("Cliente:");
        clienteComboBox = new JComboBox<Cliente>();
        clienteComboBox.setModel(telaPrincipal.getClientesListModel());
        contaPanel.add(clienteLabel);
        contaPanel.add(clienteComboBox);

        valorLabel = new JLabel("Valor :");
        valorText = new JTextField();
        contaPanel.add(valorLabel);
        contaPanel.add(valorText);
        JPanel acoesPanel = new JPanel(new GridLayout(1	,4 ,5 ,5));
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
        editarContaPanel.add(contaPanel);
        editarContaPanel.add(acoesPanel);
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
		}else {
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
}
