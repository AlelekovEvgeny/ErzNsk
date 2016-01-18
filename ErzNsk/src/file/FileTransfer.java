package file;

public interface FileTransfer {
	
	public boolean copy(String fromName, String toName);
	
	public boolean delete(String fileName);

}
