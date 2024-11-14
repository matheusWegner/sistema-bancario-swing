package br.com.tads.sistemaBancario.models.cliente;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public  class ClienteTableModel extends AbstractTableModel {
    private List<Cliente> clientes;
    private String[] colunas = {"Nome", "Sobrenome", "RG", "CPF", "Endereço"};

    public ClienteTableModel(List<Cliente> clientes) {
        this.clientes = new ArrayList<>(clientes);
    }

    @Override
    public int getRowCount() {
        return clientes.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente cliente = clientes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return cliente.getNome();
            case 1:
                return cliente.getSobrenome();
            case 2:
                return cliente.getRg();
            case 3:
                return cliente.getCpf();
            case 4:
                return cliente.getEndereco();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    public void addCliente(Cliente cliente) {
        clientes.add(cliente);
        fireTableRowsInserted(clientes.size() - 1, clientes.size() - 1);
    }
    
    public void setClientes(List<Cliente> clientes) {
    	this.clientes = clientes;
        this.fireTableDataChanged();
    }

    public void updateCliente(int rowIndex, Cliente cliente) {
        clientes.set(rowIndex, cliente);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void removeCliente(int rowIndex) {
        clientes.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public Cliente getClienteAt(int rowIndex) {
        return clientes.get(rowIndex);
    }
}