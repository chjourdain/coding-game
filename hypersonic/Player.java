import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse the standard input
 * according to the problem statement.
 **/

//TODO list : ignore boxe that will explode
// pick up item
// do calculation thank to items
// differenciate path for bomb and for movement

class Player {
    static int width;
    static int height;
    static int myId;
    //parameter

    static final int BOXE_PROPAGATION = 2;
    static final int BOXE_VALUE = 40;
    static final int BOXE_DIMINUTION = 2;
    static final int PLAYER_PROPAGATION = 24;
    static final int PLAYER_VALUE = 24;
    static final int PLAYER_DIMINUTION = 1;

    static final int MIN_BOMB = 70;

    static int current_max;


    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        width = in.nextInt();
        height = in.nextInt();
        myId = in.nextInt();

        // game loop
        while (true) {
            // loop var
            List<Position> boxes = new ArrayList<>();
            char[][] map = new char[width][height];
            //  Position me = null;
            //  Position myBomb = null;
            BomberMan[] bombermans = new BomberMan[4];
            List<Bomb> bombs = new ArrayList<>();


            // collecting data
            for (int i = 0; i < height; i++) {
                String row = in.next(); // . floor 0 box
                char[] line = row.toCharArray();
                for (int x = 0; x < line.length; x++) {
                    map[x][i] = line[x];
                    if ('.' != line[x]) {
                        boxes.add(new Position(x, i));
                    }
                }
            }
            int entities = in.nextInt();
            for (int i = 0; i < entities; i++) {
                int entityType = in.nextInt(); // player 0 bomb 1
                int owner = in.nextInt(); // 0 me other 1
                int x = in.nextInt();
                int y = in.nextInt();
                int param1 = in.nextInt();
                int param2 = in.nextInt();

                switch (entityType) {
                    case 0:
                        bombermans[owner] = new BomberMan(x, y, param1, param2);
                        break;
                    case 1:
                        bombs.add(new Bomb(x, y, param1, param2));
                        break;
                    case 2:
                        break;
                }
            }

            List<Position> willExploseAera = findExplodingAera(bombs);

            // Playing

            int[][] boxeValue = boxes.stream()//.peek(b -> System.err.println("box "+b))
                    .filter(b -> !willExploseAera.contains(b))
                    .map(p -> findMatrice(map, p.x, p.y, BOXE_VALUE, BOXE_DIMINUTION, BOXE_PROPAGATION))
                    //  .peek(x -> printMatrice(x))
                    .reduce(new int[width][height], Player::addMatrice);
            int[][] playerValue = findRoundMatrice(map, bombermans[myId].pos.x, bombermans[myId].pos.y, PLAYER_VALUE, PLAYER_DIMINUTION, PLAYER_PROPAGATION);
            playerValue[bombermans[myId].pos.x][bombermans[myId].pos.y] = PLAYER_VALUE;
            int[][] path = addMatrice(playerValue, boxeValue);

            bombs.forEach(b -> {
                eraseMatrice(path, b.pos.x, b.pos.y, 0, BOXE_PROPAGATION);
                path[b.pos.x][b.pos.y] = 0;
            });

            Position toGo = findMax(path);

            String decision = "MOVE";

            if (bombermans[myId].bombs > 0 && (path[bombermans[myId].pos.x][bombermans[myId].pos.y] >= MIN_BOMB || path[bombermans[myId].pos.x][bombermans[myId].pos.y] > current_max - 2 * BOXE_DIMINUTION)) {
                decision = "BOMB";
            }
            System.err.println("go to " + toGo);
            System.err.println("current max is :" + current_max);
            printMatrice(path);
            System.out.println(decision + " " + toGo.x + " " + toGo.y);
        }
    }

    private static Position findMax(int[][] mat) {
        current_max = 0;
        Position pos = null;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (mat[x][y] > current_max) {
                    current_max = mat[x][y];
                    pos = new Position(x, y);
                }
            }
        }
        return pos;
    }

    private static int[][] addMatrice(int[][] mat1, int[][] mat2) {
        int[][] sum = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                sum[x][y] = mat1[x][y] + mat2[x][y];
            }
        }
        return sum;
    }

    private static int[][] findMatrice(char[][] map, int positionX, int positionY, int value, int diminution, int propagation) {
        int[][] matrice = new int[width][height];
        propagate(matrice, map, 1, 0, positionX, positionY, value, diminution, propagation);
        propagate(matrice, map, 0, -1, positionX, positionY, value, diminution, propagation);
        propagate(matrice, map, -1, 0, positionX, positionY, value, diminution, propagation);
        propagate(matrice, map, 0, 1, positionX, positionY, value, diminution, propagation);
        return matrice;
    }

    private static void propagate(int[][] matrice, char[][] map, int directionX, int directionY, int positionX, int positionY, int value, int diminution, int propagation) {
        for (int step = 1; step <= propagation; step++) {
            int valueX = positionX + directionX * (step);
            int valueY = positionY + directionY * (step);
            if (valueX >= 0 && valueX < width && valueY >= 0 && valueY < height && map[valueX][valueY] == '.') {
                int valueT = value - step * diminution;
                matrice[valueX][valueY] = valueT;
            }
        }
    }

    private static int[][] eraseMatrice(int[][] matrice, int positionX, int positionY, int value, int propagation) {
        erase(matrice, 1, 0, positionX, positionY, value, propagation);
        erase(matrice, -1, 0, positionX, positionY, value, propagation);
        erase(matrice, 0, 1, positionX, positionY, value, propagation);
        erase(matrice, 0, -1, positionX, positionY, value, propagation);
        return matrice;
    }

    private static void erase(int[][] matrice, int directionX, int directionY, int positionX, int positionY, int value, int propagation) {
        for (int step = 1; step <= propagation; step++) {
            int valueX = positionX + directionX * (step);
            int valueY = positionY + directionY * (step);
            if (valueX >= 0 && valueX < width && valueY >= 0 && valueY < height) {
                matrice[valueX][valueY] = value;
            }
        }
    }

    private static void addPointInDirection(List<Position> positions, int directionX, int directionY, int positionX, int positionY, int propagation) {
        for (int step = 1; step <= propagation; step++) {
            int valueX = positionX + directionX * (step);
            int valueY = positionY + directionY * (step);
            if (valueX >= 0 && valueX < width && valueY >= 0 && valueY < height) {
                positions.add(new Position(valueX, valueY));
            }
        }
    }

    private static void printMatrice(int[][] mat) {
        for (int y = 0; y < height; y++) {
            String row = "";
            for (int x = 0; x < width; x++) {
                row += mat[x][y] + "  ";
            }
            System.err.println(row);
        }
    }


    private static int[][] findRoundMatrice(char[][] map, int positionX, int positionY, int value, int diminution, int propagation) {
        int[][] matrice = new int[width][height];
        propagateRoundX(matrice, map, -1, 1, positionX, positionY, value, diminution, propagation);
        propagateRoundX(matrice, map, 1, -1, positionX, positionY, value, diminution, propagation);
        propagateRoundY(matrice, map, 1, 1, positionX, positionY, value, diminution, propagation);
        propagateRoundY(matrice, map, -1, -1, positionX, positionY, value, diminution, propagation);
        return matrice;
    }

    private static void propagateRoundX(int[][] matrice, char[][] map, int directionX, int directionY, int positionX, int positionY, int value, int diminution, int propagation) {
        for (int step = 1; step <= propagation; step++) {
            for (int stepY = 0; stepY < step; stepY++) {
                int valueX = positionX + directionX * (step - stepY);
                int valueY = positionY + directionY * (stepY);
                if (valueX >= 0 && valueX < width && valueY >= 0 && valueY < height && map[valueX][valueY] == '.') {
                    int valueT = value - step * diminution;
                    matrice[valueX][valueY] = valueT;
                }
            }
        }
    }

    private static void propagateRoundY(int[][] matrice, char[][] map, int directionX, int directionY, int positionX, int positionY, int value, int diminution, int propagation) {
        for (int step = 1; step <= propagation; step++) {
            for (int stepX = 0; stepX < step; stepX++) {
                int valueX = positionX + directionX * (stepX);
                int valueY = positionY + directionY * (step - stepX);
                if (valueX >= 0 && valueX < width && valueY >= 0 && valueY < height && map[valueX][valueY] == '.') {
                    int valueT = value - step * diminution;
                    matrice[valueX][valueY] = valueT;
                }
            }
        }
    }

    private static List<Position> findExplodingAera(List<Bomb> bombs) {
        List<Position> explosion = new ArrayList<>();
        bombs.forEach(
                b -> {
                    explosion.add(b.pos);
                    addPointInDirection(explosion, 1, 0, b.pos.x, b.pos.y, b.range);
                    addPointInDirection(explosion, -1, 0, b.pos.x, b.pos.y, b.range);
                    addPointInDirection(explosion, 0, 1, b.pos.x, b.pos.y, b.range);
                    addPointInDirection(explosion, 0, -1, b.pos.x, b.pos.y, b.range);
                }
        );
        return explosion;
    }
}

class Position {
    int x;
    int y;

    @Override
    public String toString() {
        return "Pos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}

class Bomb {
    Position pos;
    int range;
    int countdown;

    public Bomb(int x, int y, int param1, int param2) {
        this.pos = new Position(x, y);
        this.range = param2;
        this.countdown = param1;
    }
}

class BomberMan {
    Position pos;
    int bombs;
    int rangeUp;

    public BomberMan(int x, int y, int param1, int param2) {
        this.pos = new Position(x, y);
        this.bombs = param1;
        this.rangeUp = param2;
    }
}