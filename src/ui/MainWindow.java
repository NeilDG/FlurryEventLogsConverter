package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import file.FilePathHolder;
import file.OpenFileFilter;
import logic.ConverterHandler;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.FlowLayout;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import java.awt.Color;

public class MainWindow {

	private static MainWindow sharedInstance = null;
	public static MainWindow getSharedInstance() {
		return sharedInstance;
	}
	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
					sharedInstance = window;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel titlePanel = new JPanel();
		frame.getContentPane().add(titlePanel);
		titlePanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("Flurry Event Logs Converter");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		titlePanel.add(lblNewLabel_2);
		
		JPanel loadingPanel = new JPanel();
		frame.getContentPane().add(loadingPanel);
		loadingPanel.setLayout(new GridLayout(2, 3, 0, 0));
		
		JLabel lblInputFile = new JLabel("Input File:");
		loadingPanel.add(lblInputFile);
		
		JLabel lblNameHereInput = new JLabel("Name here");
		loadingPanel.add(lblNameHereInput);
		
		JButton btnInputOpen = new JButton("Open...");
		btnInputOpen.addActionListener(new InputButtonListener());
		loadingPanel.add(btnInputOpen);
		
		JLabel lblOutputFile = new JLabel("Output File:");
		loadingPanel.add(lblOutputFile);
		
		JLabel labelNameHereOutput = new JLabel("Name here");
		loadingPanel.add(labelNameHereOutput);
		
		JButton btnOutputOpen = new JButton("Open...");
		btnOutputOpen.addActionListener(new OutputButtonListener());
		loadingPanel.add(btnOutputOpen);
		
		JPanel convertPanel = new JPanel();
		frame.getContentPane().add(convertPanel);
		convertPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
		
		JButton btnConvert = new JButton("Convert");
		btnConvert.addActionListener(new ConvertButtonListener());
		convertPanel.add(btnConvert);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel progressLabel = new JLabel("PROGRESS");
		progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(progressLabel);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setValue(60);
		progressBar.setBackground(Color.GRAY);
		panel.add(progressBar);
		
		ProgressBarHandler.initialize(progressBar, progressLabel);
		ConverterHandler.initialize();
	}
	
	public class InputButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			chooser.removeChoosableFileFilter(chooser.getFileFilter());
			chooser.addChoosableFileFilter(new OpenFileFilter("csv", "Comma separated values"));
			int result = chooser.showOpenDialog(MainWindow.sharedInstance.frame);
			
			if (result == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = chooser.getSelectedFile();
			    FilePathHolder.getInstance().setInputFilePath(selectedFile.getAbsolutePath());
			    System.out.println("Selected input file: " + selectedFile.getAbsolutePath());
			}
		}
	}
	
	public class OutputButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			int result = chooser.showOpenDialog(MainWindow.sharedInstance.frame);
			
			if (result == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = chooser.getSelectedFile();
			    FilePathHolder.getInstance().setOutputFilePath(selectedFile.getAbsolutePath());
			    System.out.println("Selected output file: " + selectedFile.getAbsolutePath());
			}
		}
	}
	
	public class ConvertButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ConverterHandler.getInstance().execute();
		}
		
	}
}
