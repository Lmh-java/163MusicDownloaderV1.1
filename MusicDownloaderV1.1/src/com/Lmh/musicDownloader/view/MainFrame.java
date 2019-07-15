package com.Lmh.musicDownloader.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.http.ParseException;

import com.Lmh.musicDownloader.main.IdCrawler;
import com.Lmh.musicDownloader.model.Music;
import com.Lmh.musicDownloader.util.DataUtil;
import com.Lmh.musicDownloader.util.DownloadUtil;
import com.Lmh.musicDownloader.util.PostUtil;
//import com.sun.java.swing.plaf.windows.resources.windows;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

public class MainFrame extends Thread {

	JFrame frmvBylmh;
	private final JPanel mainPanel = new JPanel();
	private static JTextField id;
	private static JScrollPane jsp;
	private static DataUtil du;
	private static DownloadUtil downloader;
	private JFileChooser fileChooser;

	private static JTextField path;
	private static JTextField cookie;
	private static JTextArea logArea;
	public final static int WAIT_FOR_INPUT = 0;
	// TODO 让状态计数器生效(待测试)
	public final static int DOWNLOADING = 1;
	public static int state = WAIT_FOR_INPUT;
	// 统计文本框被点击的次数，如果第一次被点击就清空文本框
	private static int cookieClick = 0;
	private static int pathClick = 0;
	private static boolean isFileChooserUsed = false;
	private int idClick = 0;
	private boolean isAutoComplete = false;
	private String regex = "[\\\\]";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Notice notice = new Notice();
					notice.showNotice();
					MainFrame window = new MainFrame();
					window.frmvBylmh.setVisible(true);
				} catch (Exception e) {
//					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "系统初始化时发生致命错误，错误代码：010", "警告", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	public synchronized static void log(String message) {
		logArea.append(message + '\n');
		logArea.update(logArea.getGraphics());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmvBylmh = new JFrame();
		frmvBylmh.setTitle("\u7F51\u6613\u4E91\u97F3\u4E50\u4E0B\u8F7D\u5668v1.1 By Lmh");
		frmvBylmh.setBounds(100, 100, 470, 430);
		frmvBylmh.setResizable(false);
		frmvBylmh.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmvBylmh.getContentPane().setLayout(null);
		mainPanel.setBounds(0, 0, 454, 391);
		frmvBylmh.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		

		id = new JTextField();
		id.setText("\u5728\u6B64\u586B\u5165\u83B7\u53D6\u6B4C\u5355id");
		id.setToolTipText("\u5728\u6B64\u586B\u5165\u83B7\u53D6\u6B4C\u5355id");
		id.setBounds(97, 79, 137, 21);
		mainPanel.add(id);
		id.setColumns(10);

		JLabel lblV = new JLabel("\u7F51\u6613\u4E91\u97F3\u4E50\u4E0B\u8F7D\u5668 v1.1");
		lblV.setBounds(120, 10, 247, 30);
		lblV.setFont(new Font("����", Font.BOLD | Font.ITALIC, 18));
		mainPanel.add(lblV);

		JLabel label = new JLabel(
				"\u672C\u8F6F\u4EF6\u4EC5\u4F9B\u5B66\u4E60\u548C\u7814\u7A76\uFF0C\u4E00\u5207\u8FDD\u6CD5\u884C\u4E3A\u4E0E\u4F5C\u8005\u65E0\u5173");
		label.setBounds(134, 360, 290, 21);
		mainPanel.add(label);

		JLabel lblid = new JLabel("\u6B4C\u5355id\uFF1A");
		lblid.setFont(new Font("����", Font.PLAIN, 14));
		lblid.setBounds(42, 82, 56, 15);
		mainPanel.add(lblid);

		JLabel label_1 = new JLabel("\u50A8\u5B58\u8DEF\u5F84\uFF08\u6587\u4EF6\u5939\uFF09\uFF1A");
		label_1.setFont(new Font("����", Font.PLAIN, 14));
		label_1.setBounds(10, 110, 142, 15);
		mainPanel.add(label_1);

		path = new JTextField();
		path.setText("选择文件夹做为存储路径，以\"/\"分割");
		path.setToolTipText("选择文件夹做为存储路径，以\"/\"分割");
		path.setColumns(10);
		path.setBounds(162, 107, 197, 21);
		mainPanel.add(path);

		JButton download = new JButton("\u786E\u5B9A\u4E0B\u8F7D");
		download.setBounds(244, 78, 93, 23);
		download.setFocusPainted(false);
		mainPanel.add(download);

		logArea = new JTextArea();
		// logArea.setBounds(76, 160, 348, 197);
		logArea.setEditable(false);
		logArea.setFocusable(false);
		// mainPanel.add(logArea);
		jsp = new JScrollPane(logArea);
		jsp.setBounds(76, 160, 348, 197);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mainPanel.add(jsp);
		// logArea.append("thank you");

		JLabel label_2 = new JLabel("\u65E5\u5FD7\u4FE1\u606F");
		label_2.setBounds(10, 256, 54, 15);
		mainPanel.add(label_2);

		JLabel lbAuthor = new JLabel("By Lmh");
		lbAuthor.setBounds(318, 35, 54, 15);
		mainPanel.add(lbAuthor);

		JLabel label_3 = new JLabel("\u4F8B\u5982 2254104559");
		label_3.setBounds(347, 82, 100, 15);
		mainPanel.add(label_3);

		// JLabel pathExample = new JLabel("\u4F8B\u5982\uFF1AD:/1");
		// pathExample.setBounds(347, 110, 77, 15);
		// mainPanel.add(pathExample);

		JLabel lblcookie = new JLabel("\u8bbf\u95ee\u0063\u006f\u006f\u006b\u0069\u0065\uff08\u9009\u586b\u0029");
		lblcookie.setFont(new Font("����", Font.PLAIN, 14));
		lblcookie.setBounds(10, 135, 142, 15);
		mainPanel.add(lblcookie);

		JButton chooseFile = new JButton("...");
		chooseFile.setBounds(365, 107, 37, 20);
		chooseFile.setFocusPainted(false);
		mainPanel.add(chooseFile);

		cookie = new JTextField();
		cookie.setText("如果无法下载，可以适当添加cookie代码");
		cookie.setToolTipText("\u5C06\u7528\u4F5C\u9690\u853D\uFF0C\u9632\u6B62\u670D\u52A1\u5668\u53D1\u73B0");
		cookie.setBounds(155, 132, 256, 21);
		mainPanel.add(cookie);
		cookie.setColumns(10);

		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setApproveButtonText("确定");

		// 获取上次配置文件
		du = new DataUtil();
		String[] map = du.getOptions();
		if (map != null) {
			path.setText(map[0]);
			cookie.setText(map[1]);
			isAutoComplete = true;
		}

		chooseFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = fileChooser.showOpenDialog(null);
				System.out.println(choice);
				if (choice == JFileChooser.APPROVE_OPTION) {
					String selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
					System.out.println(selectedPath);
					//系统兼容性使用jdk自带的函数获取系统标识符
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(selectedPath);
					isFileChooserUsed = true;
					if (m.find()) {
						selectedPath = selectedPath.replaceAll(regex, "/");
					}
					path.setText(selectedPath);
					System.out.println(selectedPath);
				}
			}

		});

		download.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 先保证文件路径的合法性
				String finalPath = path.getText();
				Pattern p = Pattern.compile(regex);
				Matcher fpMatcher = p.matcher(finalPath);
				if (fpMatcher.find()) {
					finalPath = finalPath.replaceAll(regex, "/");
				}
				// 保证cookie的合法性
				String finalCookie = cookie.getText();
				if (finalCookie.contains("如果无法下载，可以适当添加cookie代码")) {
					finalCookie = "";
					// 如果cookie没有改变则不填
				}
				if (cookieClick==0) {
					finalCookie = "";
				}
//				System.out.println(finalCookie);
				try {
					if (state == WAIT_FOR_INPUT) {
						File file = new File(finalPath);
						Pattern reg = Pattern.compile("^\\d{9}|\\d{10}$");
						Matcher idMatcher = reg.matcher(id.getText());
						if (idMatcher.find()) {
							if (file.canExecute() && file.isDirectory()) {
								ArrayList<Music> name = IdCrawler.crawl(id.getText(), cookie.getText());
								int result = JOptionPane.showConfirmDialog(null, "是否下载该歌单内的" + name.size() + "首歌曲到本地？",
										"网易云音乐下载器", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
								if (result == JOptionPane.YES_OPTION) {
									log("[提示]开始下载歌单：" + id.getText() + "\n");
									state = DOWNLOADING;
									ArrayList<Music> url = PostUtil.doPost(name, cookie.getText());
									// DownloadUtil.downLoad(url,
									// path.getText());
									downloader = new DownloadUtil(url, finalPath);
									downloader.start();
									// state = WAIT_FOR_INPUT;
									// TODO 语句块转移到多线程
									log("[提示]下载歌单：" + id.getText() + "完成" + "\n");
								}
							} else {
								JOptionPane.showMessageDialog(null, "文件夹不可达，请更换");
							}
						} else {
							JOptionPane.showMessageDialog(null, "请输入十位或九位纯数字，以表示歌单id");
						}
					} else {
						JOptionPane.showMessageDialog(null, "请等待当前任务完成后再开始新的任务");
						return;
					}
				} catch (ParseException e1) {
					// e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "程序发生未知错误，请联系作者。代码：001", "警告", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e1) {
					// e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "程序发生错误，请检查cookie填写是否正确。代码：002", "警告",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e1) {
					// e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "程序发生未知错误，请联系作者。代码：003", "警告", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		// TODO 当点击时清空文本框内容
		cookie.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				cookieClick++;
				if (cookieClick == 1 && isAutoComplete == false) {
					// 第一次点击
					cookie.setText("");
				}
				System.out.println(cookieClick);

			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});

		path.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				pathClick++;
				if (pathClick == 1 && isAutoComplete == false) {
					path.setText("");
				}
				System.out.println(pathClick);
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});

		id.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				idClick++;
				if (idClick == 1) {
					id.setText("");
				}
				System.out.println(idClick);
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});

		frmvBylmh.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				
			}
			@Override
			public void windowClosing(WindowEvent e) {
				du.saveLog();
				du.saveOptions();
			}

			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}

		});
		//设置风格
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			JOptionPane.showMessageDialog(null, "程序加载界面风格出错", "警告", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static StringBuilder getLog() {
		// System.out.println(logArea.getText());
		return new StringBuilder(logArea.getText());
	}

	public static String getMusicId() {
		return id.getText();
	}

	public static String getPath() {
		if (pathClick == 0 && isFileChooserUsed == false) {
			return "";
		} else
			return path.getText();
	}

	public static String getCookie() {
		if (cookieClick == 0) {
			return "";
		} else {
			return cookie.getText();
		}
	}
}
