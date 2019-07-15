package com.Lmh.musicDownloader.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Notice{
	
	private JFrame noticeFrame = null;
	private JPanel panel = null;
	private JTextArea content = null;
	private JButton enter = null;
	private JButton cancel = null;
	private JScrollPane jsp = null;
	private boolean isAgree = false;
	
	public Notice(){
		
		noticeFrame = new JFrame("使用须知");
		panel = new JPanel();
		content = new JTextArea();
		enter = new JButton("确定");
		cancel = new JButton("取消");
		jsp = new JScrollPane(content);
		
		init();
	}
	
	public void init(){
			
		noticeFrame.setVisible(true);
		noticeFrame.setBounds(100, 100, 470, 430);
		noticeFrame.setResizable(false);
		noticeFrame.setLayout(null);
		noticeFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		noticeFrame.getContentPane().setLayout(null);
		noticeFrame.getContentPane().add(panel);	
		noticeFrame.setAlwaysOnTop(true);
		panel.setBounds(0, 0, 454, 391);
		panel.setLayout(null);
		
		content.setEditable(false);
		content.setFocusable(false);
		content.setFont(new Font("微软雅黑",Font.BOLD, 20));
		jsp.setBounds(25, 20, 400, 300);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp.setVisible(true);
		panel.add(jsp);
		content.setText("使用须知：\n"
				+ "本软件仅供学习用途，并且不会公开核心算法，如发生违法行为均与作者无关\n"
				+ "本软件为免费软件，请支持原版，但由于种种原因，本软件无法开源，请大家不要进行反编译活动\n"
				+ "作者：Lmh  本软件参数加密算法：由csdn上的大神提供，经过作者调试后选用，特别感谢\n"
				+ "有意合作者或软件bug反馈请联系作者，无事勿扰，qq邮箱：3545228620@qq.com\n"
				+ "后期有时间的话还会更新：加快下载速度等，敬请期待v3.0版本\n"
				+ "version:1.1正式版	完工日期：2019.1.29");
		
		enter.setBounds(100, 345, 70, 30);
		enter.setToolTipText("同意所述，自愿遵守，进入软件");
		enter.setFocusPainted(false);
		panel.add(enter);
		
		cancel.setBounds(300, 345, 70, 30);
		cancel.setToolTipText("做不到，自动退出");
		cancel.setFocusPainted(false);
		panel.add(cancel);
		
		enter.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				noticeFrame.setVisible(false);
				isAgree = true;
			}
			
		});
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				isAgree = false;
			}
		});
	}
	
	public void showNotice(){
		noticeFrame.setVisible(true);
	}
	
	public boolean getIsAgree(){
		return isAgree;
	}
	
}
