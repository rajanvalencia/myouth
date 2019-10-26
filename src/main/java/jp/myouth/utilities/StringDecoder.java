package jp.myouth.utilities;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class StringDecoder {
	
	public String encodeString(String url) {
		String encodedUrl = null;
		try {
			encodedUrl = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return encodedUrl;
		}
		return encodedUrl;
	}

	public String decodeString(String encodedUrl) {
		String decodedUrl = null;
		try {
			decodedUrl = URLDecoder.decode(encodedUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return decodedUrl;
		}
		return decodedUrl;
	}
	
}
