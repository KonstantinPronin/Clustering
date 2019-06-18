package prosayfer.soft;

import java.awt.*;
import java.util.ArrayList;

public class WorkSpace {

  /** Класс точка на двумерном пространстве */
  private class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    /** Расстояние Манхэттона */
    public int manhattanDistance(int x, int y) {
      return Math.abs(this.x - x) + Math.abs(this.y - y);
    }
    /** Расстояние Чебышева */
    public int chebyshevDistance(int x, int y) {
      return Math.max(Math.abs(this.x - x), Math.abs(this.y - y));
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }
  }

  /** Кластер */
  private class Cluster {
    private Color color;
    private int x;
    private int y;
    private ArrayList<Point> clusterPoints;

    public Cluster(int x, int y, Color clr) {
      this.x = x;
      this.y = y;
      color = clr;
      clusterPoints = new ArrayList<>();
    }

    public void addToCluster(Point obj) {
      clusterPoints.add(obj);
    }

    public void clearClusterPoints() {
      clusterPoints.clear();
    }

    // Перерасчет координат центра
    public boolean centerRecount() {
      int xSum = 0;
      int ySum = 0;
      int xOld = x;
      int yOld = y;

      if (clusterPoints.isEmpty()) {
        return false;
      }

      for (Point i : clusterPoints) {
        xSum += i.getX();
        ySum += i.getY();
      }

      x = xSum / clusterPoints.size();
      y = ySum / clusterPoints.size();

      return x - xOld != 0 || y - yOld != 0;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    public void drawClusterPoints() {
      for (Point tmp : clusterPoints) {
        parentPanel.drawPoint(tmp.getX(), tmp.getY(), color);
      }
    }
  }

  private static final int MAX_DIST = 1000;
  private ArrayList<Point> points;
  private ArrayList<Cluster> clusters;
  private DrawPanel parentPanel;

  public WorkSpace(DrawPanel panel) {
    parentPanel = panel;
    points = new ArrayList<>();
    clusters = new ArrayList<>();
  }

  // Создание точки
  public void addPoint(int x, int y) {
    points.add(new Point(x, y));
  }
  // Создание кластера
  public void addCluster(int x, int y, Color clr) {
    clusters.add(new Cluster(x, y, clr));
  }

  // Кластеризация
  public void clustering(boolean metricType) {
    boolean flag;
    do {
      flag = false;
      for (Cluster tmp : clusters) {
        tmp.clearClusterPoints();
      }
      for (Point tmp : points) {
        int minDist = MAX_DIST;
        int nearestCluster = 0;

        for (int i = 0; i < clusters.size(); ++i) {
          int tmpDist;

          if (metricType) {
            tmpDist = tmp.manhattanDistance(clusters.get(i).getX(), clusters.get(i).getY());
          } else {
            tmpDist = tmp.chebyshevDistance(clusters.get(i).getX(), clusters.get(i).getY());
          }

          if (tmpDist < minDist) {
            minDist = tmpDist;
            nearestCluster = i;
          }
        }
        clusters.get(nearestCluster).addToCluster(tmp);
      }

      parentPanel.clearSpace();
      for (Cluster tmp : clusters) {
        parentPanel.drawCluster(tmp.getX(), tmp.getY(), tmp.color);
        tmp.drawClusterPoints();
        if (tmp.centerRecount()) flag = true;
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    } while (flag);
  }
}
