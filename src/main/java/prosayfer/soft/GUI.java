package prosayfer.soft;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
  private static final int HEIGHT = 500;
  private static final int WIDTH = 500;
  private static final String MANHATTAN = "Manhattan";
  private static final String CHEBYSHEV = "Chebyshev";
  private static final String CLEAR = "clear";

  private JPanel rootPanel;
  private JPanel buttonPanel;
  private DrawPanel workPanel;
  private JButton manhattanButton;
  private JButton chebyshevButton;
  private JButton clearButton;
  private ActionListener actionListener;

  public GUI() {
    super("clustering");

    actionListener = new ActionProcessing();
    manhattanButton = new JButton(MANHATTAN);
    chebyshevButton = new JButton(CHEBYSHEV);
    clearButton = new JButton(CLEAR);

    manhattanButton.setActionCommand(MANHATTAN);
    chebyshevButton.setActionCommand(CHEBYSHEV);
    clearButton.setActionCommand(CLEAR);

    manhattanButton.addActionListener(actionListener);
    chebyshevButton.addActionListener(actionListener);
    clearButton.addActionListener(actionListener);

    buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(manhattanButton);
    buttonPanel.add(chebyshevButton);
    buttonPanel.add(clearButton);
    buttonPanel.setBounds(50, 425, 400, 50);

    workPanel = new DrawPanel();
    workPanel.setBounds(50, 25, 400, 400);

    rootPanel = new JPanel();
    rootPanel.setLayout(null);
    rootPanel.add(buttonPanel);
    rootPanel.add(workPanel);
    add(rootPanel);

    pack();
    setSize(WIDTH, HEIGHT);
    setVisible(true);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  class ActionProcessing implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
      switch (ae.getActionCommand()) {
        case MANHATTAN:
          workPanel.clustering(true);
          break;
        case CHEBYSHEV:
          workPanel.clustering(false);
          break;
        case CLEAR:
          workPanel.clear();
          break;
        default:
          break;
      }
    }
  }
}
