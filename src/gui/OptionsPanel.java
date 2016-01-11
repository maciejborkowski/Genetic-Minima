package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import engine.Genetic;
import engine.GeneticOptions;

@SuppressWarnings("serial")
public class OptionsPanel extends JPanel {
	private final GraphPanel graphPanel;
	private final GridBagConstraints constraints = new GridBagConstraints();

	private JTextField from = new JTextField("0");
	private JTextField to = new JTextField("10");
	private JTextField populationSize = new JTextField("500");
	private JTextField sectionNumber = new JTextField("100");
	private JTextField iterations = new JTextField("100");

	private JCheckBox showAllSections = new JCheckBox("All sections");

	private JButton runButton = new JButton("Run");

	public OptionsPanel(GraphPanel graphPanel) {
		this.graphPanel = graphPanel;
		setLayout(new GridBagLayout());

		constraints.gridy = 0;
		constraints.gridx = 0;

		addLabel("From");
		addComponent(from);
		newLine();
		addLabel("To");
		addComponent(to);
		newLine();
		addLabel("Population");
		addComponent(populationSize);
		newLine();
		addLabel("Sections");
		addComponent(sectionNumber);
		newLine();
		addLabel("Iterations");
		addComponent(iterations);
		addComponent(showAllSections);

		runButton.addActionListener(new RunActionListener());
		addComponent(runButton);
	}

	private void addLabel(String value) {
		JLabel label = new JLabel(value);
		addComponent(label);
	}

	private void addComponent(Component component) {
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.9;
		component.setPreferredSize(new Dimension(120, 20));
		add(component, constraints);
		constraints.gridx++;
	}

	private void newLine() {
		constraints.gridy++;
		constraints.gridx = 0;
	}

	private final class RunActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				GeneticOptions options = new GeneticOptions();
				options.from = Integer.parseInt(from.getText());
				options.to = Integer.parseInt(to.getText());
				options.populationSize = Integer.parseInt(populationSize.getText());
				options.sectionNumber = Integer.parseInt(sectionNumber.getText());
				options.iterations = Integer.parseInt(iterations.getText());
				Genetic genetic = new Genetic(options);
				try {
					genetic.live(options.iterations);
					graphPanel.drawGraph(genetic.best, showAllSections.isSelected());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
