package Utils;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


public class ComputerTable_Modle extends AbstractTableModel{
	
	String columnNames[] = {"STT", "IP Computer", "Name Computer", "Port", "Status"};
	
	private List<Socket> list;
    private List<Computer_Modle> listComputer;
    
    
    private Computer_Modle createComputerModel(Socket s) {
    	Computer_Modle com = new Computer_Modle();
        com.setName(s.getInetAddress().getHostName());
        com.setIp(s.getInetAddress().getHostAddress());
        com.setPort(s.getPort());
        com.setStatus(1);
        return com;
    }
    
    public ComputerTable_Modle(List<Socket> list) {
        this.list = list;
        this.listComputer = new ArrayList<Computer_Modle>();
        for (Socket s : list) {
            listComputer.add(createComputerModel(s));
        }
    }
    
    public int getSize() {
        return this.list.size();
    }

    public List<Socket> getList() {
        return this.list;
    }
    
    public Socket getItem(int rowIndex) {
        return this.list.get(rowIndex);
    }
    
    public void removeAllElement() {
        this.list.clear();
        this.listComputer.clear();
        fireTableDataChanged();
    }
    
	public void updateAllElement() { 
		int i = 0; 
		try { 
			for (Socket s : list) { 
					if (Handle.checkConnectClosed(s)) { 
						list.remove(i); listComputer.remove(i);
						fireTableRowsDeleted(i, i); 
					} else  i++; 
				} 
		} catch (Exception e) { } 
		}
	 
    public void addElement(Socket e) {
        // Adds the element in the last position in the list
        list.add(e);
        listComputer.add(createComputerModel(e));
        fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void removeElement(int rowIndex) {
        list.remove(rowIndex);
        listComputer.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
    
	public int getRowCount() {
		return list.size();
	}

	public int getColumnCount() {
		return columnNames.length;
	}
	
	public String getColumnName(int index) {
        return columnNames[index];
    }
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
            switch (columnIndex) {
                case 0:
                    return rowIndex + 1;
                case 2:
                    return listComputer.get(rowIndex).getIp();
                case 1:
                    return listComputer.get(rowIndex).getName();  
                case 3:
                    return listComputer.get(rowIndex).getPort(); 
                case 4:
                    return listComputer.get(rowIndex).getStatus();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
	}
}
