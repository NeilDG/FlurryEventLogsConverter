package ui;

import java.awt.BorderLayout;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import config.ConverterConfig;
import config.ConverterConfig.OriginType;
import file.FilePathHolder;
import file.OpenFileFilter;
import logic.ConverterHandler;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JCheckBox;

public class Mainframe extends JFrame {

	private JPanel contentPane;
	private JLabel lblNameHereInput;
	private JLabel labelNameHereOutput;

	/**
	 * Create the frame.
	 */
	public Mainframe() {
		this.setResizable(false);
		this.setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		this.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		this.initialize();
		
		ConverterConfig.setConfig(OriginType.FROM_AWS);
	}
	
	public void initialize() {
		JPanel titlePanel = new JPanel();
		this.contentPane.add(titlePanel);
		titlePanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("Flurry Event Logs Converter");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		titlePanel.add(lblNewLabel_2);
		
		JPanel loadingPanel = new JPanel();
		this.contentPane.add(loadingPanel);
		loadingPanel.setLayout(new GridLayout(2, 3, 0, 0));
		
		JLabel lblInputFile = new JLabel("Input File:");
		loadingPanel.add(lblInputFile);
		
		this.lblNameHereInput = new JLabel("No file selected");
		loadingPanel.add(lblNameHereInput);
		
		JButton btnInputOpen = new JButton("Open...");
		btnInputOpen.addActionListener(new InputButtonListener(this));
		loadingPanel.add(btnInputOpen);
		
		JLabel lblOutputFile = new JLabel("Output File:");
		loadingPanel.add(lblOutputFile);
		
		this.labelNameHereOutput = new JLabel("No save destination");
		loadingPanel.add(labelNameHereOutput);
		
		JButton btnOutputOpen = new JButton("Open...");
		btnOutputOpen.addActionListener(new OutputButtonListener(this));
		loadingPanel.add(btnOutputOpen);
		
		JPanel convertPanel = new JPanel();
		this.contentPane.add(convertPanel);
		convertPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		JButton btnConvert = new JButton("Convert");
		btnConvert.addActionListener(new ConvertButtonListener());
		convertPanel.add(btnConvert);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		
		JRadioButton checkFlurryBtn = new JRadioButton("From Flurry");
		checkFlurryBtn.addActionListener(ToggleButtonListenerGroup.getSharedInstance().getFlurryToggleListener());
		panel_1.add(checkFlurryBtn);
		
		JRadioButton checkAWSBtn = new JRadioButton("From AWS Redshift");
		checkAWSBtn.addActionListener(ToggleButtonListenerGroup.getSharedInstance().getRedShiftToggleListener());
		checkAWSBtn.setSelected(true);
		panel_1.add(checkAWSBtn);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(checkFlurryBtn);
		buttonGroup.add(checkAWSBtn);
		
		JPanel panel = new JPanel();
		this.contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel progressLabel = new JLabel("PROGRESS");
		progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(progressLabel);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setValue(60);
		progressBar.setBackground(Color.GRAY);
		panel.add(progressBar);
		
		ProgressBarHandler.initialize(progressBar, progressLabel);
	}
	
	public class InputButtonListener implements ActionListener {

		private Mainframe parentFrame;
		public InputButtonListener(Mainframe parentFrame) {
			// TODO Auto-generated constructor stub
			this.parentFrame = parentFrame;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			chooser.removeChoosableFileFilter(chooser.getFileFilter());
			chooser.addChoosableFileFilter(new OpenFileFilter("csv", "Comma separated values"));
			int result = chooser.showOpenDialog(this.parentFrame);
			
			if (result == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = chooser.getSelectedFile();
			    FilePathHolder.getInstance().setInputFilePath(selectedFile.getAbsolutePath());
			    System.out.println("Selected input file: " + selectedFile.getAbsolutePath());
			    
			    this.parentFrame.lblNameHereInput.setText(selectedFile.getName());
			}
		}
	}
	
	public class OutputButtonListener implements ActionListener {

		private Mainframe parentFrame;
		public OutputButtonListener(Mainframe parentFrame) {
			// TODO Auto-generated constructor stub
			this.parentFrame = parentFrame;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			//chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.removeChoosableFileFilter(chooser.getFileFilter());
			chooser.addChoosableFileFilter(new OpenFileFilter("csv", "Comma separated values"));
			
			int result = chooser.showSaveDialog(this.parentFrame);
			
			if (result == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = chooser.getSelectedFile();
			    String absolutePath = selectedFile.getAbsolutePath();
			    
			    if(!absolutePath.toLowerCase().contains(".csv")) {
			    	absolutePath += ".csv";
			    }
			    FilePathHolder.getInstance().setOutputFilePath(absolutePath);
			    System.out.println("Selected output file: " + absolutePath);
			    
			    this.parentFrame.labelNameHereOutput.setText(selectedFile.getName());
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
