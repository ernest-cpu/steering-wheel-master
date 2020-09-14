package userInterface;

import javax.swing.*;

import org.json.JSONObject;

import execution.ExecutionConfiguration;
import execution.Manager;

import static execution.Constants.JSON_CONFIG_PATH;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;

public class Menu extends JPanel{

	private static final long serialVersionUID = 1L;
	private static final String INPUT_FILE = "input_file";
	private static final String AGGREGATION_ORDER = "aggregation_order";
	private static final String DEADZONE_ENHANCEMENT = "deadzone_enhancement";
	private static final String DEADZONE_CORRECTION_ONLY = "deadzone_correction_only";
	private static final Dimension MENU_DIMENSION = new Dimension(642, 320);
	JButton previewButton;
	JButton generateCsvButton;
	JButton generateLutButton;
	JButton fileBrowserButton;
	JButton autoButton;
	JCheckBox deadZoneCorrectionOnly;
	JTextField inputFileText;
	JSlider aggregationSlider;
	JSlider deadZoneEnhancement;
	JSONObject config;


	public void showMenu(org.json.JSONObject inputConfig){
		this.config = inputConfig;
		// Create frame with title Wheel Check Data Aggregator
		JFrame frame= new JFrame(); 
		frame.setTitle("Wheel Check Data Aggregator");
		frame.setMinimumSize(MENU_DIMENSION);

		// Panel to define the layout
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		JPanel headingPanel = new JPanel();
		JLabel headingLabel = new JLabel("Settings:");
		headingLabel.setFont(new Font(headingLabel.getFont().getFontName(), 2, 16));
		headingPanel.add(headingLabel);

		// Panel to define the layout
		JPanel layoutPanel = new JPanel(new GridBagLayout());
		// Constraints for the layout
		GridBagConstraints constr = new GridBagConstraints();
		constr.insets = new Insets(8, 8, 8, 8);     
		constr.anchor = GridBagConstraints.WEST;

		// Declare Text fields
		JLabel inputFileLabel = new JLabel("Input file:");
		inputFileText = new JTextField();
		inputFileText.setPreferredSize(new Dimension(236, 22));
		inputFileText.setText(config.getString(INPUT_FILE));
		
		// Link label	
		final String linkLabel = "Open documentation";
		final JLabel documentationLink = new JLabel(linkLabel);
		documentationLink.setForeground(Color.BLUE);
		documentationLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		// Add Listener for the link	
		documentationLink.addMouseListener(new MouseAdapter() {
			 
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        try {            
		            Desktop.getDesktop().browse(new URI("https://github.com/Luke460/wheel-check-data-aggregator"));  
		        } catch (IOException | URISyntaxException e1) {
		            e1.printStackTrace();
		        }
		    }
		    
            @Override
            public void mouseExited(MouseEvent e) {
            	documentationLink.setText(linkLabel);
            }
 
            @Override
            public void mouseEntered(MouseEvent e) {
            	documentationLink.setText("<html><a href=''>" + linkLabel + "</a></html>");
            }
		
		});
		
		deadZoneCorrectionOnly = new JCheckBox();
		deadZoneCorrectionOnly.setText("Dead zone correction only");
		deadZoneCorrectionOnly.setSelected(inputConfig.getBoolean(DEADZONE_CORRECTION_ONLY));

		// Add positions label in the slider
		Hashtable<Integer, JLabel> position = new Hashtable<Integer, JLabel>();
		for(int i = 0; i<=10; i++) {
			position.put(i, new JLabel(i+""));
		}

		JLabel aggregationLabel = new JLabel("Aggregation order:");
		aggregationSlider = new JSlider(0, 10, config.getInt(AGGREGATION_ORDER));
		aggregationSlider.setPreferredSize(new Dimension(244, 44));
		aggregationSlider.setMajorTickSpacing(5);
		aggregationSlider.setMinorTickSpacing(1);
		aggregationSlider.setPaintTicks(true);
		aggregationSlider.setPaintLabels(true);   		         
		aggregationSlider.setLabelTable(position); 

		JLabel deadZoneEnhancementLabel = new JLabel("Dead zone enhancement:");
		deadZoneEnhancement = new JSlider(0, 10, config.getInt(DEADZONE_ENHANCEMENT));
		deadZoneEnhancement.setPreferredSize(new Dimension(244, 44));
		deadZoneEnhancement.setMajorTickSpacing(5);
		deadZoneEnhancement.setMinorTickSpacing(1);
		deadZoneEnhancement.setPaintTicks(true);
		deadZoneEnhancement.setPaintLabels(true);
		deadZoneEnhancement.setPaintTrack(true);
		deadZoneEnhancement.setLabelTable(position); 

		// create event listener for the buttons
		PerformListener performListener = new PerformListener();

		// Declare File Manager Button
		fileBrowserButton = new JButton("File browser");

		constr.gridx=0;
		constr.gridy=0;
		layoutPanel.add(inputFileLabel, constr);

		constr.gridx=1;
		layoutPanel.add(inputFileText, constr);

		constr.gridx=2;
		layoutPanel.add(fileBrowserButton, constr);
		fileBrowserButton.addActionListener(performListener);

		constr.gridx=0; constr.gridy++;
		layoutPanel.add(aggregationLabel, constr);
		constr.gridx=1;
		layoutPanel.add(aggregationSlider, constr);

		autoButton = new JButton("Auto");
		autoButton.addActionListener(performListener);

		constr.gridx=2;
		layoutPanel.add(autoButton, constr);

		constr.gridx=0; constr.gridy++;
		layoutPanel.add(deadZoneEnhancementLabel, constr);
		constr.gridx=1;
		layoutPanel.add(deadZoneEnhancement, constr);
		constr.gridx=2;
		layoutPanel.add(documentationLink, constr);
		
		constr.gridx=1; constr.gridy++;
		layoutPanel.add(deadZoneCorrectionOnly, constr);
		
		constr.gridx=0; constr.gridy++;	
		previewButton = new JButton("Preview");
		previewButton.addActionListener(performListener);

		generateCsvButton = new JButton("Generate csv");
		generateCsvButton.addActionListener(performListener);

		generateLutButton = new JButton("Generate lut");
		generateLutButton.addActionListener(performListener);

		Dimension buttonDimension = new Dimension(160, 30);
		fileBrowserButton.setPreferredSize(buttonDimension);
		previewButton.setPreferredSize(buttonDimension);
		generateCsvButton.setPreferredSize(buttonDimension);
		generateLutButton.setPreferredSize(buttonDimension);

		// Add label and button to panel
		constr.gridx=0; constr.gridy++;
		constr.gridwidth = 3;
		constr.anchor = GridBagConstraints.WEST;
		layoutPanel.add(previewButton, constr);
		constr.anchor = GridBagConstraints.CENTER;
		layoutPanel.add(generateCsvButton, constr);
		constr.anchor = GridBagConstraints.EAST;
		layoutPanel.add(generateLutButton, constr);

		mainPanel.add(headingPanel);
		mainPanel.add(layoutPanel);

		// Add panel to frame
		frame.add(mainPanel);
		frame.pack();
		frame.setSize(MENU_DIMENSION);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void updateConfig(org.json.JSONObject config) {
		config.put(AGGREGATION_ORDER, aggregationSlider.getValue());
		config.put(INPUT_FILE, inputFileText.getText());
		config.put(DEADZONE_ENHANCEMENT, deadZoneEnhancement.getValue());
		config.put(DEADZONE_CORRECTION_ONLY, deadZoneCorrectionOnly.isSelected());
		try {
			Files.write(Paths.get(JSON_CONFIG_PATH), config.toString().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class PerformListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			updateConfig(config);
			ExecutionConfiguration exConf = new ExecutionConfiguration();
			Object src = e.getSource();
			if (src == previewButton){
				exConf.setShowPreview(true);
				Manager.execute(config, exConf);
			} else if(src == generateCsvButton){
				exConf.setShowPreview(true);
				exConf.setSaveCSV(true);
				Manager.execute(config, exConf);
			} else if(src == generateLutButton){
				exConf.setShowPreview(true);
				exConf.setSaveLUT(true);
				Manager.execute(config, exConf);
			} else if(src == autoButton) {
				exConf.setAutoCalcAggregationOder(true);
				exConf = Manager.execute(config, exConf);
				aggregationSlider.setValue(exConf.getAggregationOrder());
				deadZoneEnhancement.setValue(exConf.getDeadZoneEnhancement());
			} else if(src == fileBrowserButton){
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new CsvFileFilter());
				int n = fileChooser.showOpenDialog(Menu.this);
				if (n == JFileChooser.APPROVE_OPTION) {
					File f = fileChooser.getSelectedFile();	         
					inputFileText.setText(f.getPath());
				}
			}
		}
	}

}