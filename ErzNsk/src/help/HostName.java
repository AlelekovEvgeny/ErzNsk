package help;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
 
public class HostName implements HttpSessionBindingListener {
 
	public static String getHostName() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		byte[] ipAddr = addr.getAddress();
		String hostname = addr.getHostName();
		System.out.println("hostname="+hostname);
		return hostname;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent arg0) {
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
	}
}