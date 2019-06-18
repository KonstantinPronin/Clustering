package prosayfer.soft;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class DrawPanel extends JPanel {
  private static final int PIXEL_SIZE = 4;
  private static final Color[] COLOR_LIST = {
    Color.BLACK, Color.CYAN, Color.GRAY,
    Color.BLUE, Color.GREEN, Color.MAGENTA,
    Color.ORANGE, Color.RED, Color.YELLOW
  };
  private WorkSpace workSpace;
  private int clustersNumber;

  public DrawPanel() {
    Border bd = BorderFactory.createLineBorder(Color.BLACK);
    setBorder(bd);

    MouseHandler mouseHandler = new MouseHandler(this);
    addMouseListener(mouseHandler);
    addMouseMotionListener(mouseHandler);

    workSpace = new WorkSpace(this);
    clustersNumber = 0;
  }

  public void clustering(boolean metricType) {
    workSpace.clustering(metricType);
  }

  public void drawCluster(int x, int y, Color clr) {
    Graphics2D g2d = (Graphics2D) getGraphics();
    g2d.setColor(clr);
    g2d.fillOval(x, y, PIXEL_SIZE + 2, PIXEL_SIZE + 2);
  }

  public void drawPoint(int x, int y, Color clr) {
    Graphics2D g2d = (Graphics2D) getGraphics();
    g2d.setColor(clr);
    g2d.fillRect(x, y, PIXEL_SIZE, PIXEL_SIZE);
  }

  public void clearSpace() {
    Graphics2D g2d = (Graphics2D) getGraphics();
    g2d.setColor(getBackground());

    g2d.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
  }

  public void clear() {
    repaint();
    workSpace = new WorkSpace(this);
    clustersNumber = 0;
  }

  public class MouseHandler extends MouseAdapter {
    private DrawPanel parentPanel;

    public MouseHandler(DrawPanel panel) {
      parentPanel = panel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
      int x1 = e.getX();
      int y1 = e.getY();
      Graphics2D g2d = (Graphics2D) parentPanel.getGraphics();

      if (e.getButton() == MouseEvent.BUTTON1) {
        workSpace.addPoint(x1, y1);
        g2d.fillRect(x1, y1, PIXEL_SIZE, PIXEL_SIZE);
      }
      if (e.getButton() == MouseEvent.BUTTON3) {
        if (clustersNumber < COLOR_LIST.length) {
          workSpace.addCluster(x1, y1, COLOR_LIST[clustersNumber]);
          clustersNumber++;
        }
        g2d.fillOval(x1, y1, PIXEL_SIZE + 2, PIXEL_SIZE + 2);
      }
    }
  }
}
