package br.com.tads.sistemaBancario.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

import br.com.tads.sistemaBancario.commom.Utility;
import br.com.tads.sistemaBancario.controllers.ClienteController;
import br.com.tads.sistemaBancario.models.Result;
import br.com.tads.sistemaBancario.models.cliente.Cliente;
import br.com.tads.sistemaBancario.models.cliente.ClienteTableModel;

public class ClienteView extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MainView telaPrincipal;
    private ClienteTableModel clienteTableModel;
    private JTable clienteTable;
    private JPanel formPanel;
    private JTextField nomeField;
    private JTextField sobrenomeField;
    private JTextField rgField;
    private JTextField cpfField;
    private JTextField enderecoField;
    private JTextField buscaField;
    private JButton adicionarButton;
    private JButton atualizarButton;
    private JButton excluirButton;
    private JButton cancelarButton;
    private JButton editarButton;
    private Cliente selectedCliente;
    private ClienteController clienteController;
    List<Cliente> clientes = new ArrayList<Cliente>();
    
    public ClienteView(MainView telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
        this.clienteController = new ClienteController();
        
        setLayout(new BorderLayout());

        formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        formPanel.add(nomeField);

        formPanel.add(new JLabel("Sobrenome:"));
        sobrenomeField = new JTextField();
        formPanel.add(sobrenomeField);

        formPanel.add(new JLabel("RG:"));
        rgField = new JTextField();
        formPanel.add(rgField);

        formPanel.add(new JLabel("CPF (apenas numeros):"));
        cpfField = new JTextField();
        formPanel.add(cpfField);
        formPanel.add(new JLabel("Endereço:"));
        enderecoField = new JTextField();
        formPanel.add(enderecoField);
        
        add(formPanel, BorderLayout.NORTH);

        JPanel buscaPanel = new JPanel(new BorderLayout());
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new EmptyBorder(10, 0, 0, 0)); 

        buscaField = new JTextField(20);
        buscaField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarClientes(buscaField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            	filtrarClientes(buscaField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            	filtrarClientes(buscaField.getText());
            }
        });

        buscaPanel.add(new JLabel("Buscar:"), BorderLayout.WEST);
        buscaPanel.add(buscaField, BorderLayout.EAST);
        tablePanel.add(buscaPanel,BorderLayout.NORTH);
        clienteTableModel = new ClienteTableModel(getClientes());
        clienteTable = new JTable(clienteTableModel);
        TableRowSorter<ClienteTableModel> sorter = new TableRowSorter<>(clienteTableModel);
        clienteTable.setRowSorter(sorter);
        JScrollPane scrollPane = new JScrollPane(clienteTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);
        
        adicionarButton = new JButton("Adicionar");
        adicionarButton.addActionListener(e -> {
            adicionarCliente();
            
        });
        JPanel buttonPanel = new JPanel();
        atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(e -> atualizarCliente());

        excluirButton = new JButton("Excluir");
        excluirButton.addActionListener(e -> excluirCliente());

        editarButton = new JButton("Editar");
        editarButton.addActionListener(e -> editarCliente());
        
        cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(e -> {
        	limparCampos();
        	showActionButtons();
        });
        
        
        cancelarButton.setVisible(false);
    	excluirButton.setVisible(false);
    	atualizarButton.setVisible(false);
    	adicionarButton.setVisible(true);
        buttonPanel.add(adicionarButton);
        buttonPanel.add(editarButton);
        buttonPanel.add(atualizarButton);
        buttonPanel.add(excluirButton);
        buttonPanel.add(cancelarButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        preencheTabelaClientes();
    }

    private void adicionarCliente() {
        try {
        	if(validaForm()) {
        		Cliente cliente = new Cliente(
        				nomeField.getText(),
        				sobrenomeField.getText(),
        				rgField.getText(),
        				cpfField.getText(),
        				enderecoField.getText()
        		);
        		Result result = this.clienteController.addCliente(cliente);
                JOptionPane.showMessageDialog(this, result.getMessage());

                if(result.getStatus()) {
                	preencheTabelaClientes();
                }
        		limparCampos();
        	}
       }catch (Exception e) {
       	 e.printStackTrace();
         JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado ao criar perfil , tente novamente mais tarde");
       }
    }

    private boolean validaForm() {
    	if("".equals(nomeField.getText()) || nomeField.getText() == null) {
    		JOptionPane.showMessageDialog(this, "preencha o campo Nome");
    		return false;
    	}
    	if("".equals(sobrenomeField.getText()) || sobrenomeField.getText() == null) {
    		JOptionPane.showMessageDialog(this, "preencha o campo Sobrenome");
    		return false;
    	}
    	if("".equals(cpfField.getText()) || cpfField.getText() == null) {
    		JOptionPane.showMessageDialog(this, "preencha o campo Cpf");
    		return false;
    	}
    	if("".equals(rgField.getText()) || rgField.getText() == null) {
    		JOptionPane.showMessageDialog(this, "preencha o campo Rg");
    		return false;
    	}
    	
    	if("".equals(enderecoField.getText()) || enderecoField.getText() == null) {
    		JOptionPane.showMessageDialog(this, "preencha o campo Endereço");
    		return false;
    	}
    	if(!Utility.validarCpf(cpfField.getText())) {
            JOptionPane.showMessageDialog(this, "Cpf inválido");
    		return false;
    	}
    	return true;
    }
    private void atualizarCliente() {
    	try {
    		if(validaForm()) {
                if (this.selectedCliente != null && this.selectedCliente.getCpf() != null) {
                	selectedCliente.setNome(nomeField.getText());
                	selectedCliente.setSobrenome(sobrenomeField.getText());
                	selectedCliente.setRg(rgField.getText());
                    //cliente.setCpf(cpfField.getText());
                	selectedCliente.setEndereco(enderecoField.getText());
                    Result result = this.clienteController.updateCliente(selectedCliente);
                    JOptionPane.showMessageDialog(this, result.getMessage());
                    if(result.getStatus()) {
                    	preencheTabelaClientes();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Selecione um cliente para atualizar.");
                }
    		}
         }catch (Exception e) {
        	 e.printStackTrace();
             JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado ao editar  perfil , tente novamente mais tarde");
         }
    }

    private void excluirCliente() {
        try {
             int dialogResult = JOptionPane.showConfirmDialog(this, "Deseja excluir o cliente ?", "Confirmação de exclusão", JOptionPane.YES_NO_OPTION);

             if (dialogResult == JOptionPane.YES_OPTION) {
                 if (this.selectedCliente != null && this.selectedCliente.getCpf() != null) {
                     Result result = this.clienteController.deleteCliente(selectedCliente.getCpf());
                     JOptionPane.showMessageDialog(this, result.getMessage());

                     if(result.getStatus()) {
                    	 preencheTabelaClientes();
                     }
                     limparCampos();
                 } else {
                     JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.");
                 }
                 limparCampos();
                 showActionButtons();
             } 
        	
        }catch (Exception e) {
        	 e.printStackTrace();
             JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado ao remover  perfil , tente novamente mais tarde");
        }
    }

    private void editarCliente() {
    	try {
    		int selectedRow = clienteTable.getSelectedRow();
	        if (selectedRow >= 0) {
	            int modelRow = clienteTable.convertRowIndexToModel(selectedRow);
	            Cliente cliente = clienteTableModel.getClienteAt(modelRow);
	            cliente = this.clienteController.getCliente(cliente.getCpf());
	            this.selectedCliente = cliente;
	        	preencherCamposClienteSelecionado(cliente);
	        	showEditActionButtons();
	        }else {
	            JOptionPane.showMessageDialog(this, "Selecione um cliente primeiro");
	        }
    		
    	}catch (Exception e) {
    		e.printStackTrace();
    		JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado ao remover  perfil , tente novamente mais tarde");
    	}
    }

    private void limparCampos() {
        nomeField.setText("");
        sobrenomeField.setText("");
        rgField.setText("");
        cpfField.setText("");
        enderecoField.setText("");
        this.selectedCliente = null;
        this.getClientes();
    }
    
    private void preencherCamposClienteSelecionado(Cliente cliente) {
    	try {
            nomeField.setText(cliente.getNome());
            sobrenomeField.setText(cliente.getSobrenome());
            rgField.setText(cliente.getRg());
            cpfField.setText(cliente.getCpf());
            enderecoField.setText(cliente.getEndereco());
       }catch (Exception e) {
       	 e.printStackTrace();
         JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado ao remover  perfil , tente novamente mais tarde");
       }
    }
    
    public void showEditActionButtons() {
    	adicionarButton.setVisible(false);
		editarButton.setVisible(false);
    	cancelarButton.setVisible(true);
    	atualizarButton.setVisible(true);
    	excluirButton.setVisible(true);
        cpfField.setEnabled(false);
    }
    
    public void showActionButtons() {
    	cancelarButton.setVisible(false);
    	excluirButton.setVisible(false);
    	atualizarButton.setVisible(false);
    	adicionarButton.setVisible(true);
    	editarButton.setVisible(true);
        cpfField.setEnabled(true);
    }
    
    public void preencheTabelaClientes() {
        this.clientes = clienteController.getAllClientes();
        clienteTableModel.setClientes(this.clientes);
    }
    
    public void filtrarClientes(String termo) {
        List<Cliente> resultados = new ArrayList<>();
        for (Cliente cliente : this.clientes ) {
            if (cliente.getNome().contains(termo) ||
                cliente.getSobrenome().contains(termo) ||
                cliente.getRg().contains(termo) ||
                cliente.getCpf().contains(termo) ||
                cliente.getEndereco().contains(termo)) {
                resultados.add(cliente);
            }
        }
        clienteTableModel.setClientes(resultados);
    }
    
    public List<Cliente> getClientes() {
    	List<Cliente> clientes = this.clienteController.getAllClientes();
    	return clientes;
    }
    
}