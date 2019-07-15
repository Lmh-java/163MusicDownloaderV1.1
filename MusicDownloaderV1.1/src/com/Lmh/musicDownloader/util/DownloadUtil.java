package com.Lmh.musicDownloader.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.swing.JOptionPane;

import com.Lmh.musicDownloader.model.Music;
import com.Lmh.musicDownloader.view.MainFrame;

public class DownloadUtil extends Thread{
	
	private ArrayList<Music> musicUrl = null;
	private String path = null;
	
	public DownloadUtil(ArrayList<Music> musicUrl,String path){
		this.path = path;
		this.musicUrl = musicUrl;
	}
	
	public synchronized void download() {

		// MainFrame.log("[提示]开始下载歌单：" + MainFrame.getId().getText()+"\n");
		InputStream is = null;
		ByteArrayOutputStream os = null;
		byte[] buffer = new byte[1024 * 1024];
		FileOutputStream fos = null;
		HttpURLConnection con = null;

		for (int x = 0; x < musicUrl.size(); x++) {
			try {

				URL url = new URL(musicUrl.get(x).getUrl());
				con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(5 * 1000);
				con.setRequestMethod("GET");
				// if(con==null){
				// return;
				// }
				is = con.getInputStream();
				os = new ByteArrayOutputStream();

				int len = -1;
				while ((len = is.read(buffer)) != -1) {
					os.write(buffer, 0, len);
				}

				byte[] data = os.toByteArray();
				// if(musicUrl.get(x).getName().contains('('))
				// String newname = musicUrl.get(x).s
				// String name = musicUrl.get(x).getName();
				// if(name.contains(" \" ")){
				// name.replaceAll("\"", "");
				// }
				// if(name.contains("“")){
				// name.replaceAll("“", "");
				// }
				
				String str = musicUrl.get(x).getName();
				String regEx = "[\\\\/:*?\"<>|]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(str);
				// System.out.println(m.find());
				if (m.find()) {
					// MainFrame.log("[提示]歌曲"+str+"因为系统文件名问题无法写入");
					// continue;
					str = str.replaceAll("[\\\\/:*?\"<>|]", "");
					musicUrl.get(x).setName(str);
				}

				fos = new FileOutputStream(new File(path + "/" + musicUrl.get(x).getName() + ".mp3"));
				fos.write(data);

				fos.close();
				os.flush();
				os.close();
				is.close();
				con.disconnect();
				MainFrame.log("[提示]歌曲" + musicUrl.get(x).getName() + "下载完成");
			} catch (MalformedURLException e) {
				MainFrame.log("[提示]歌曲" + musicUrl.get(x).getName() + "资源缺失，无法下载");
				// continue;
			} catch (IOException e) {
				// e.printStackTrace();
				MainFrame.log("[警告]文件" + musicUrl.get(x).getName() + "写入错误");
				// continue;
			}
		}
		 MainFrame.log("[提示]下载歌单：" + MainFrame.getMusicId()+"完成\n");
		 MainFrame.state = MainFrame.WAIT_FOR_INPUT;
	}
	public void run(){
		download();
	}
}
