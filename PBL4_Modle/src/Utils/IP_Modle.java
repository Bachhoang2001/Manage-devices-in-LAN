package Utils;

public class IP_Modle {
	private String ip;
    private String name;
    private String status ;
    public IP_Modle() {
    }
    public IP_Modle(String ip, String name) {
        this.ip = ip;
        this.name = name;
    }
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the port
     */
    public String getStatus() {
    	return this.status;
    }

    /**
     * @param port the port to set
     */
    public void setStatus(int status) {
    	if(status == 1 )
    	{
    		this.status =  "Online" ;
    	}
    	else this.status =  "Offline";
    }
}
