package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class GeneticMinimaApplication {
	private final static String WINDOW_LABEL = "Genetic Minima";

	private JFrame frame;
	private GraphPanel graphPanel;
	private OptionsPanel optionsPanel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GeneticMinimaApplication window = new GeneticMinimaApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GeneticMinimaApplication() {
		initializeApplication();
	}

	private void initializeApplication() {
		initFrame();
	}

	private void initFrame() {
		frame = new JFrame(WINDOW_LABEL);
		frame.setBounds(0, 0, 1000, 680);
		frame.getContentPane().setLayout(new BorderLayout());
		graphPanel = new GraphPanel();
		frame.add(graphPanel, BorderLayout.CENTER);
		optionsPanel = new OptionsPanel(graphPanel);
		frame.add(optionsPanel, BorderLayout.SOUTH);
	}

}