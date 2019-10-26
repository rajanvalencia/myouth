package jp.myouth.servlets;

import java.util.ArrayList;

import jp.myouth.storage.DeleteObject;

public class DeleteTrashedInbox {
	
	final static String BUCKET_NAME = "jp.myouth.messages";
	
	public Boolean attachments(String messageId) {
		try {
			
			DeleteObject del = new DeleteObject();
			
			Boolean res = del.folder(BUCKET_NAME, messageId+"/");

			if(res)
				return true;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean attachments(ArrayList<String> messageIds) {
		try {
			
			DeleteObject del = new DeleteObject();
			
			Boolean res = false;
			for(String messageId : messageIds)
				res = del.folder(BUCKET_NAME, messageId);
			
			if(res)
				return true;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
