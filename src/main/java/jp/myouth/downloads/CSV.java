package jp.myouth.downloads;

import java.io.IOException;

import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

public class CSV {
	
	public void create(HttpServletResponse response, String event, ArrayList<String> data, int total) throws IOException {
	
	response.setContentType("text/csv");
	response.setHeader("Content-Disposition", "attachment; filename=\"" + event + ".csv\"");
	
	OutputStream outputStream = response.getOutputStream();
	
	//BOM‚ð•t—^
	outputStream.write(0xef);
	outputStream.write(0xbb);
	outputStream.write(0xbf);

	String outputResult;
	int i = 1;
	int len = data.size() / total;
	for (String string : data) {
		if (i == len) {
			if (string == null)
				outputResult = "\n";
			else
				outputResult = string + "\n";
			i = 0;
		} else {
			if (string == null)
				outputResult = ",";
			else
				outputResult = string + ",";
		}
		outputStream.write(outputResult.getBytes("UTF-8"));
		i++;
	}
	outputStream.flush();
	outputStream.close();
	}
}
