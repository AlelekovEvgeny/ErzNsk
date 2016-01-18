package task;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Task {
	
	public boolean add(String username) throws FileNotFoundException, IOException, Exception;

}
