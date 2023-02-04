package Utils;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class IPTable_Modle extends AbstractTableModel{
	private List<String> IPs ;
	private String[] columns ;
	private List<IP_Modle> listIP ;
	
	
	private IP_Modle createIPModel(String ip) {
		IP_Modle com = new IP_Modle();
        com.setName(ip);
        com.setIp(ip);
        com.setStatus(1);
        return com;
    }
	
	public IPTable_Modle(List<String> IPs)
	{
		this.IPs = IPs;
		this.listIP = new ArrayList<IP_Modle>();
		columns = new String[] {"STT", "Name ", "IP Computer", "Status"};
		for (String ip : IPs) {
			listIP.add(createIPModel(ip));
		}
	}
	
	public int getSize() {
        return this.IPs.size();
    }
	
	public List<String> getList() {
        return this.IPs;
    }
	
	public void removeAllElement() {
        this.IPs.clear();
        this.listIP.clear();
        fireTableDataChanged();
    }
	public void addElement(String e) {
        // Adds the element in the last position in the list
		IPs.add(e);
		listIP.add(createIPModel(e));
        fireTableRowsInserted(IPs.size() - 1, IPs.size() - 1);
    }
	@Override
	public int getRowCount() {
		return IPs.size();
	}
	public String getColumnName(int col) {
	    return columns[col] ;
	}
	public int getColumnCount() {
		return columns.length;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
            switch (columnIndex) {
                case 0:
                    return rowIndex + 1;
                case 1:
                    return listIP.get(rowIndex).getName();
                case 2:
                    return listIP.get(rowIndex).getIp();
                case 3:
                    return listIP.get(rowIndex).getStatus();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
	}

}
