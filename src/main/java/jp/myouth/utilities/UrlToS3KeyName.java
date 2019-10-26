package jp.myouth.utilities;

public class UrlToS3KeyName {
	
	public String convert(String url) {
		String[] prefix = url.split("/");
		
		int i = 0;
		String keyName = new String();
		for(String string : prefix) {
			if(i >= 4 && i <= 5)
				keyName += string + "/";
			else if(i == 6)
				keyName += string;
			i++;
		}
		return keyName;
	}
}
