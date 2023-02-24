package DZ4;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class LiAlgorithm {
    public static void main(String[] args) {

        MapPrinter mp = new MapPrinter();
        Ui ui = new Ui(mp, new ConsoleView());
        int[][] m = new int[10][10];

        mp.mapColor(m);
        // точка начала

        var mg = new MapGenerator();

        System.out.println(
                new MapPrinter().rawData(
                        mg.getMap()));

        ui.setCatPosition(mg.getMap());

        Point2D c1 = ui.getCatPos();
        // System.out.println(c1);
        // количество выходов -2
        ui.setExitPos(2, mg.getMap());
        ArrayList<Point2D> exitlist = ui.getExitPos();

        // список выходов
        // System.out.println(exitlist);

        // System.out.println(
        // new MapPrinter().rawData(
        // mg.getMap()));
        // start
        // var c = new Point2D(5, 3);
        // finish
        // var f = new Point2D(8, 9);

        var lee = new WaveAlgorithm(mg.getMap());
        lee.Colorize(c1);

        int[][] mapnew = lee.CloneLi(mg.getMap());

        // int [][] maplee = mg.getMap();

        // System.out.println(
        // new MapPrinter().rawData(
        // mg.getMap())

        // );

        // mg.map[c.x][c.y] = -2;
        mg.setCat(c1);

        for (int i = 0; i < 2; i++) {

            mg.setExit(exitlist.get(i));

        }

        // mg.map[f.x][f.y] = -3;
        System.out.println(
                new MapPrinter().rawData(
                        mapnew)

        );

        System.out.println();
        System.out.println();
        System.out.println(new MapPrinter().mapColor(mg.map));

        // System.out.println(exitlist.get(1));

        FindRoad fr = new FindRoad();
        ArrayList<Point2D> findroad = fr.getRoad(mapnew, c1, exitlist);
        // System.out.println(findroad);

    }
}

interface View {
    void print(String str);
}

class ConsoleView implements View {

    @Override
    public void print(String str) {
        System.out.println(str);
    }
}

class Ui {
    Scanner in = new Scanner(System.in);
    Point2D catPos = new Point2D(0, 0);
    ArrayList<Point2D> exitPos = new ArrayList<Point2D>();
    MapPrinter mapPrinter;
    View view;

    public Ui(MapPrinter printer, View view) {
        mapPrinter = printer;
        this.view = view;
    }

    public Point2D getCatPos() {
        return catPos;
    }

    public void setCatPosition(int[][] map) {

        boolean flag = true;
        while (flag) {

            System.out.println("Введите координаты входа");

            try {
                Scanner sc = new Scanner(System.in);
                catPos = new Point2D(sc.nextInt(), sc.nextInt());

                if (map[catPos.x][catPos.y] == -1) {
                    System.out.println("Здесь стена");
                    continue;

                }
            } catch (InputMismatchException in) {
                System.out.println("Ввод неверен");
                continue;

            } catch (ArrayIndexOutOfBoundsException in) {
                System.out.println("координаты вне диапазона массива");
                continue;
            }

            flag = false;
        }
    }

    public ArrayList<Point2D> getExitPos() {
        return exitPos;
    }

    public void setExitPos(int count, int[][] map) {
        Point2D exitpoint = new Point2D(0, 0);
        boolean flag = true;

        for (int i = 0; i < count; i++) {
            flag = true;
            while (flag) {

                System.out.println("Введите координаты выходов");

                try {
                    Scanner sc = new Scanner(System.in);
                    exitpoint = new Point2D(sc.nextInt(), sc.nextInt());

                    if (map[exitpoint.x][exitpoint.y] == -1) {
                        System.out.println("Здесь стена");

                        continue;
                    }
                } catch (InputMismatchException in) {
                    System.out.println("Ввод неверен");

                    continue;
                } catch (ArrayIndexOutOfBoundsException in) {
                    System.out.println("координаты вне диапазона массива");
                    continue;
                }
                // System.out.println(exitpoint);

                flag = false;
            }
            exitPos.add(exitpoint);

        }
    }

}

class Point2D {
    int x, y;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("x: %d  y: %d", x, y);
    }

    @Override
    public boolean equals(Object obj) {
        Point2D t = (Point2D) obj;
        return this.x == t.x && this.y == t.y;
    }
}

class MapGenerator {
    int[][] map;

    public MapGenerator() {
        int[][] map = {
                { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
                { -1, 00, 00, 00, -1, 00, 00, 00, 00, 00, 00, 00, 00, 00, -1 },
                { -1, 00, 00, 00, 00, 00, 00, -1, 00, 00, 00, 00, 00, 00, -1 },
                { -1, 00, 00, 00, -1, 00, 00, -1, 00, 00, 00, 00, 00, 00, -1 },
                { -1, 00, 00, 00, -1, 00, -1, -1, -1, -1, 00, 00, 00, 00, -1 },
                { -1, 00, 00, 00, -1, 00, -1, 00, 00, -1, 00, 00, 00, 00, -1 },
                { -1, -1, -1, 00, -1, 00, -1, 00, 00, -1, 00, 00, 00, 00, -1 },
                { -1, 00, 00, 00, -1, 00, -1, 00, 00, -1, -1, -1, 00, 00, -1 },
                { -1, 00, 00, 00, -1, 00, 00, 00, 00, -1, 00, 00, 00, 00, -1 },
                { -1, 00, 00, 00, -1, 00, 00, 00, 00, -1, 00, 00, 00, 00, -1 },
                { -1, 00, 00, 00, -1, -1, -1, -1, -1, -1, 00, 00, 00, 00, -1 },
                { -1, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, -1 },
                { -1, 00, 00, 00, -1, -1, -1, -1, -1, -1, -1, 00, 00, 00, -1 },
                { -1, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, -1 },
                { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }
        };

        this.map = map;
    }

    public int[][] getMap() {
        return map;
    }

    public void setCat(Point2D pos) {
        map[pos.x][pos.y] = -2;
    }

    public void setExit(Point2D pos) {
        map[pos.x][pos.y] = -3;
    }
}

class MapPrinter {

    public String rawData(int[][] map) {

        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {

                sb.append(String.format("%5d", map[row][col]));

            }
            sb.append("\n");
        }
        for (int i = 0; i < 3; i++) {
            sb.append("\n");
        }

        return sb.toString();
    }

    public String mapColor(int[][] map) {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                switch (map[row][col]) {
                    case 0:
                        sb.append(" e ");
                        break;
                    case -1:
                        sb.append("-1");
                        break;
                    case -2:
                        sb.append("-2");
                        break;
                    case -3:
                        sb.append("-3");
                        break;
                    default:
                        sb.append("  ");
                        break;
                }
            }
            sb.append("\n");
        }
        for (int i = 0; i < 3; i++) {
            sb.append("\n");
        }
        return sb.toString();
    }
}

class WaveAlgorithm {
    int[][] map;

    public WaveAlgorithm(int[][] map) {
        this.map = map;

    }

    public void Colorize(Point2D startPoint) {
        Queue<Point2D> queue = new LinkedList<Point2D>();
        queue.add(startPoint);
        map[startPoint.x][startPoint.y] = 1;

        while (queue.size() != 0) {
            Point2D p = queue.remove();

            if (map[p.x - 1][p.y] == 0) {
                queue.add(new Point2D(p.x - 1, p.y));
                map[p.x - 1][p.y] = map[p.x][p.y] + 1;
            }
            if (map[p.x][p.y - 1] == 0) {
                queue.add(new Point2D(p.x, p.y - 1));
                map[p.x][p.y - 1] = map[p.x][p.y] + 1;
            }
            if (map[p.x + 1][p.y] == 0) {
                queue.add(new Point2D(p.x + 1, p.y));
                map[p.x + 1][p.y] = map[p.x][p.y] + 1;
            }
            if (map[p.x][p.y + 1] == 0) {
                queue.add(new Point2D(p.x, p.y + 1));
                map[p.x][p.y + 1] = map[p.x][p.y] + 1;
            }
        }
    }

    public int[][] CloneLi(int[][] map) {
        int[][] cloneMap = new int[map.length][map[0].length];
        for (int i = 0; i < map.length; i++)
            for (int j = 0; j < map[i].length; j++)
                cloneMap[i][j] = map[i][j];
        return cloneMap;
    }

   
}

class FindRoad {
    int[][] map;
    Point2D cat_Pos;
    ArrayList<Point2D> exits;

    public ArrayList<Point2D> getRoad(int[][] map, Point2D cat_Pos, ArrayList<Point2D> exits) {
        ArrayList<Point2D> road = new ArrayList<>();
        Point2D currentpos;
        currentpos = exits.get(0);
        

        for (int i = 0; i < exits.size(); i++) {
            road.clear();
            currentpos = exits.get(i);
            road.add(new Point2D(currentpos.x, currentpos.y));
            System.out.println("путь " +  i);
            if (currentpos.x == cat_Pos.x & currentpos.y == cat_Pos.y) {
                System.out.println("Точка начала совпадает с концом");
                continue;

            }

            // (currentpos.x !=cat_Pos.x) & ( currentpos.y !=cat_Pos.y)
            while (map[currentpos.x][currentpos.y] != map[cat_Pos.x][cat_Pos.y]) {
                if (map[currentpos.x - 1][currentpos.y] < map[currentpos.x][currentpos.y]
                        & map[currentpos.x - 1][currentpos.y] != -1) {
                    
                    road.add(new Point2D(currentpos.x - 1, currentpos.y));
                    currentpos.x = currentpos.x - 1;
                    // System.out.println();
                }
                if (map[currentpos.x][currentpos.y - 1] < map[currentpos.x][currentpos.y]
                        & map[currentpos.x][currentpos.y - 1] != -1) {
                    
                    road.add(new Point2D(currentpos.x, currentpos.y - 1));
                    currentpos.y = currentpos.y - 1;

                }
                if (map[currentpos.x + 1][currentpos.y] < map[currentpos.x][currentpos.y]
                        & map[currentpos.x + 1][currentpos.y] != -1) {
                    
                    road.add(new Point2D(currentpos.x + 1, currentpos.y));
                    currentpos.x = currentpos.x + 1;

                }
                if (map[currentpos.x][currentpos.y + 1] < map[currentpos.x][currentpos.y]
                        & map[currentpos.x][currentpos.y + 1] != -1) {
                    
                    road.add(new Point2D(currentpos.x, currentpos.y + 1));
                    currentpos.y = currentpos.y + 1;

                }

            }
            System.out.println(road);
        }

        return road;
    }

}
