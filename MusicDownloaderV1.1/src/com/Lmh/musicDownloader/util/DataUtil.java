package com.Lmh.musicDownloader.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.Lmh.musicDownloader.view.MainFrame;

public class DataUtil {

	File logFile = new File("log.txt");
	Calendar c = Calendar.getInstance(); // 用于获取时间
	FileWriter logfw = null;
	PrintWriter logpw = null;

	File opFile = new File("options.xml");
	FileWriter opfw = null;
	PrintWriter oppw = null;

	public DataUtil() {

		try {
			logfw = new FileWriter(logFile, true);
			logpw = new PrintWriter(logfw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TODO 上次下载选项的存储 输出日志的保存

	public void saveLog() {
		StringBuilder sb = MainFrame.getLog();
		logpw.println("------------------" + c.get(Calendar.YEAR) + "年" +(c.get(Calendar.MONTH)+1)+"月"+ c.get(Calendar.DATE) + "日"
				+ c.get(Calendar.HOUR_OF_DAY) + "时" + c.get(Calendar.MINUTE) + "分" + c.get(Calendar.SECOND)+"秒"
				+ "------------");
		// 打印日志
		logpw.println(sb);
		logpw.println("------------------------------------------------------");
		logpw.flush();
	}

	public void saveOptions() {
		try {
			//TODO dom4j写入xml
			opfw = new FileWriter(opFile, false);
			oppw = new PrintWriter(opfw);
			StringBuilder sb = new StringBuilder();
			sb.append("<options>");
			sb.append("<path>"+MainFrame.getPath()+"</path>");
			sb.append("<cookie>"+MainFrame.getCookie()+"</cookie>");
			sb.append("</options>");
			oppw.println(sb);
			oppw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String[] getOptions() {
		
		String[] result = new String[2];
		
		if (opFile.exists() && opFile.isFile()) {
			
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();

				Document doc = builder.parse(opFile);

				Element root = doc.getDocumentElement();//获取xml根标签
				Element path = (Element)root.getElementsByTagName("path").item(0);
				result[0] = path.getTextContent();
				
				Element cookie = (Element)root.getElementsByTagName("cookie").item(0);
				result[1] = cookie.getTextContent();
				
				System.out.println(result[0]+"/n"+result[1]);
				
			} catch (ParserConfigurationException e) {
//				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "获取配置文件错误，错误代码005", "警告", JOptionPane.ERROR_MESSAGE);
			} catch (SAXException e) {
//				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "获取配置文件错误，错误代码006", "警告", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
//				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "获取配置文件错误，错误代码007", "警告", JOptionPane.ERROR_MESSAGE);
			}

			if(result[0].equals("")&&result[1].equals("")){
				return null;
			}else{
				return result;
			}
		} else {
			return null;
		}
	}

}
