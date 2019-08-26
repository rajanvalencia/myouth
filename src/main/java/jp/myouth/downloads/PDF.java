package jp.myouth.downloads; 
 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream; 
import java.util.ArrayList; 
 
import javax.servlet.http.HttpServletResponse; 
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font; 
import com.itextpdf.text.PageSize; 
import com.itextpdf.text.Phrase; 
import com.itextpdf.text.pdf.BaseFont; 
import com.itextpdf.text.pdf.PdfPCell; 
import com.itextpdf.text.pdf.PdfPTable; 
import com.itextpdf.text.pdf.PdfWriter; 
 
public class PDF { 
 
    public void createPDFFile(HttpServletResponse response, String event, ArrayList<String> data, int total, String fontSize) throws Exception { 
 
        Document document = new Document(PageSize.A4.rotate()); 

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
 
        BaseFont japaneseFont = BaseFont.createFont("HeiseiMin-W3", "UniJIS-UCS2-HW-H", BaseFont.NOT_EMBEDDED); 
        BaseFont alphaNumericFont = BaseFont.createFont(BaseFont.HELVETICA,BaseFont.WINANSI,BaseFont.NOT_EMBEDDED);
        
        PdfWriter.getInstance(document, byteOut); 
        document.open(); 
 
        int len = data.size() / total; 
         
        PdfPTable table = new PdfPTable(len); 
        PdfPCell cell = new PdfPCell(); 
 
        for (String string : data) {
        	if(isHalfWidthAlphanumeric(string)) /*if the string is an alphanumeric string*/
        		cell = new PdfPCell(new Phrase(string, new Font(alphaNumericFont, Integer.valueOf(fontSize))));
        	else /*if the string has japanese letters on it*/
        		cell = new PdfPCell(new Phrase(string, new Font(japaneseFont, Integer.valueOf(fontSize)))); 
        	
            table.addCell(cell); 
        } 
        
        document.add(table); 
        document.close(); 
        
        response.setContentType("application/pdf"); 
        response.setHeader("Content-Disposition", "attachment; filename=\"" + event + ".pdf\""); 
        
        OutputStream outputStream = response.getOutputStream(); 
        outputStream.write(byteOut.toByteArray());
        outputStream.flush(); 
        outputStream.close(); 
    } 
    
    /**
     * �肳�ꂽ�����񂪁A���p�p����(�L���܂�)���ۂ���Ԃ��܂��B
     *
     * @param value �����ΏۂƂȂ镶����
     * @return true:���p�p�����ł���(�������͑Ώە������Ȃ�), false:���p�p�����łȂ�
     */
    
    public static Boolean isHalfWidthAlphanumeric(String value) {
        if ( value == null || value.length() == 0 )
            return true;
        int len = value.length();
        byte[] bytes = value.getBytes();
        
        if ( len != bytes.length )
            return false;
        
        return true;
    }


} 