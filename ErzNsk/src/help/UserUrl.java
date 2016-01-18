package help;

public class UserUrl {
	
	public static String trimUrl(StringBuffer s) {
		String ret = null;
		ret = s.substring(7, s.length());
		ret = ret.substring(0, ret.indexOf('/'));	
		if (ret.indexOf(':') != -1) {
			ret = ret.substring(0, ret.indexOf(':'));
		}
		return ret;
	}

}
