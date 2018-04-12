import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Auto-generated code below aims at helping you parse the standard input
 * according to the problem statement.
 **/

//TODO list :
// mveout of other player plans
// be safer on movement
// item block explosion
// reduce negative effect of bomb in fonction of there futur explosion, in case of moving do not avoid bomb that will explose not really
 //soon
class Player {
    static int width;
    static int height;
    static int myId;
    //parameter

    static BomberMan[] bombermans = new BomberMan[4];

    static final AvoidingPosition avoidingPosition = new AvoidingPosition();

    static final int BOXE_PROPAGATION_STANDARD = 2;
    static final int BOXE_VALUE = 40;
    static final int BOXE_DIMINUTION = 0;
    static final int PLAYER_PROPAGATION = 24;
    static final int PLAYER_VALUE = 24;
    static final int PLAYER_DIMINUTION = 1;

    static final int BOMB_ITEM_NOMINAL = 80;
    static final int RANGE_ITEM_NOMINAL = 40;

    static final int MIN_BOMB = 100;

    static int current_max;
    static char[][] map;
    static List<Bomb> bombs;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        width = in.nextInt();
        height = in.nextInt();
        myId = in.nextInt();

        // game loop
        while (true) {
            Instant start = Instant.now();
            // loop var
            List<Position> boxes = new ArrayList<>();
            map = new char[width][height];
            //  Position me = null;
            //  Position myBomb = null;
            bombs = new ArrayList<>();
            List<Position> bombItems = new ArrayList<>();
            List<Position> rangeUpItems = new ArrayList<>();


            // collecting data
            for (int i = 0; i < height; i++) {
                String row = in.next(); // . floor 0 box
                char[] line = row.toCharArray();
                for (int x = 0; x < line.length; x++) {
                    map[x][i] = line[x];
                    if ('.' != line[x] && 'X' != line[x]) {
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
                        if (param1 == 1) {
                            rangeUpItems.add(new Position(x, y));
                        } else {
                            bombItems.add(new Position(x, y));
                        }
                        break;
                }
            }

            System.err.println("ME :" + bombermans[myId]);


            List<Position> willExploseAera = findExplodingAera(bombs, 9);
            List<Position> willExploseAeraIn1Or2 = findExplodingAera(bombs, 2);

            int boxePorpagation = bombermans[myId].range - 1;

            // Playing

            int[][] boxeValue = boxes.stream()
                    .filter(b -> !willExploseAera.contains(b))
                    .map(p -> findMatrice(map, p.x, p.y, BOXE_VALUE, BOXE_DIMINUTION, boxePorpagation))
                    .reduce(new int[width][height], Player::addMatrice);
            int[][] playerValue = /*(map, bombermans[myId].pos.x, bombermans[myId].pos.y, PLAYER_VALUE, PLAYER_DIMINUTION, PLAYER_PROPAGATION);
            playerValue[bombermans[myId].pos.x][bombermans[myId].pos.y] = PLAYER_VALUE;*/
                    pathExploring(height + width, PLAYER_VALUE, PLAYER_DIMINUTION, bombermans[myId].pos);
            int[][] path = addMatrice(playerValue, boxeValue);
            for (Position explose : willExploseAera) {
                path[explose.x][explose.y] = -1000;
            }
            avoidingPosition.processAvoiding(path);
            Position toGo;

            String decision = "MOVE";

            if (bombermans[myId].bombs > 0 && (path[bombermans[myId].pos.x][bombermans[myId].pos.y] >= MIN_BOMB || path[bombermans[myId].pos.x][bombermans[myId].pos.y] == current_max)) {
                decision = "BOMB";
                Bomb newBomb = new Bomb(bombermans[myId].pos.x, bombermans[myId].pos.y, 8, bombermans[myId].range);
                for (Position x : findExplodingAera(Arrays.asList(newBomb), 10)) {
                    path[x.x][x.y] = -1000;
                }
                path = addValueAround(path, bombermans[myId].pos, 200);
                toGo = findMax(path);
                System.err.println("wanna bomb, but is it safe ?");
                if (current_max < 0) {
                    System.err.println("FUCKING NOT SAFE");
                    decision = "MOVE";
                    avoidingPosition.add(bombermans[myId].pos.x, bombermans[myId].pos.y, 5);
                } else {
                    bombs.add(newBomb);
                    int[][] nextPlayerValue =
                            pathExploring(4, PLAYER_VALUE, PLAYER_DIMINUTION, guessingNextPosition(bombermans[myId].pos, toGo));

                    for (Position explose : findExplodingAera(bombs, 10)) {
                        path[explose.x][explose.y] = -1000;
                    }

                    System.err.println("guessing my next mat will be :");

                    printMatrice(nextPlayerValue);
                    findMax(nextPlayerValue);
                    if (current_max < 10) {
                        System.err.println("NOT SAFE");
                        decision = "MOVE";
                        avoidingPosition.add(bombermans[myId].pos.x, bombermans[myId].pos.y, 5);
                    }
                }

            } else {
                path = addBombItemValue(path, bombItems);
                path = addRangeUpItemValue(path, rangeUpItems);
                toGo = findMax(path);
            }
            System.err.println("go to " + toGo);
            System.err.println("current max is :" + current_max);

            printMatrice(path);

            toGo = goAvoidingPosition(toGo, bombermans[myId].pos, willExploseAeraIn1Or2);
            System.out.println(decision + " " + toGo.x + " " + toGo.y);
            System.err.println("calculation took :  " + Duration.between(start, Instant.now()));
            avoidingPosition.reduceCount();
        }
    }

    private static Position guessingNextPosition(Position mine, Position toGO) {
        return goAvoidingPosition(toGO, mine, Collections.emptyList());
    }

    private static Position findMax(int[][] mat) {
        current_max = -100000;
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


    private static int[][] addValueAround(int[][] mat, Position position, int value) {
        int upperBound = position.y - 1;
        int lowerBound = position.y + 1;
        int leftBound = position.x - 1;
        int rightBound = position.x + 1;

        if (upperBound >= 0) {
            for (int x = 0; x < width; x++) {
                mat[x][upperBound] += value;
            }
        }

        if (lowerBound < height) {
            for (int x = 0; x < width; x++) {
                mat[x][lowerBound] += value;
            }
        }

        if (leftBound >= 0) {
            for (int y = 0; y < height; y++) {
                mat[leftBound][y] += value;
            }
        }

        if (rightBound < width) {
            for (int y = 0; y < height; y++) {
                mat[rightBound][y] += value;
            }
        }

        return mat;
    }

    private static int[][] addBombItemValue(int[][] mat, List<Position> bombsItem) {
        int value;
        switch (bombermans[myId].bombs) {
            case 0:
                value = BOMB_ITEM_NOMINAL;
                break;
            case 1:
                value = BOMB_ITEM_NOMINAL / 2;
                break;
            default:
                value = 0;
        }
        bombsItem.forEach(
                b -> {
                    mat[b.x][b.y] = value + mat[b.x][b.y];
                }
        );
        return mat;
    }

    private static int[][] addRangeUpItemValue(int[][] mat, List<Position> rangeUpItem) {
        int value;
        switch (bombermans[myId].range) {
            case 3:
                value = RANGE_ITEM_NOMINAL;
                break;
            default:
                value = RANGE_ITEM_NOMINAL / 2;
        }
        rangeUpItem.forEach(
                b -> mat[b.x][b.y] += value
        );
        return mat;
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
        propagateStopAtBoxes(matrice, map, 1, 0, positionX, positionY, value, diminution, propagation);
        propagateStopAtBoxes(matrice, map, 0, -1, positionX, positionY, value, diminution, propagation);
        propagateStopAtBoxes(matrice, map, -1, 0, positionX, positionY, value, diminution, propagation);
        propagateStopAtBoxes(matrice, map, 0, 1, positionX, positionY, value, diminution, propagation);
        return matrice;
    }

    private static void propagateStopAtBoxes(int[][] matrice, char[][] map, int directionX, int directionY, int positionX, int positionY, int value, int diminution, int propagation) {
        for (int step = 1; step <= propagation; step++) {
            int valueX = positionX + directionX * (step);
            int valueY = positionY + directionY * (step);
            if (valueX >= 0 && valueX < width && valueY >= 0 && valueY < height && map[valueX][valueY] == '.') {
                int valueT = value - step * diminution;
                matrice[valueX][valueY] = valueT;
            } else {
                break;
            }
        }
    }


    private static void addPointInDirection(List<Position> positions, int directionX, int directionY, int positionX, int positionY, int propagation) {
        for (int step = 1; step <= propagation; step++) {
            int valueX = positionX + directionX * (step);
            int valueY = positionY + directionY * (step);
            if (valueX >= 0 && valueX < width && valueY >= 0 && valueY < height) {
                positions.add(new Position(valueX, valueY));
                if (map[valueX][valueY] != '.') {
                    break;
                }
            }
        }
    }

    private static Position goAvoidingPosition(Position toGo, Position myPosition, List<Position> toAvoid) {
        Position vecteur = Position.vecteur(myPosition, toGo);
        Position pathByY = (vecteur.y == 0) ? toGo : new Position(myPosition.x, myPosition.y + vecteur.y / Math.abs(vecteur.y));
        Position pathByX = (vecteur.x == 0) ? toGo : new Position(myPosition.x + vecteur.x / Math.abs(vecteur.x), myPosition.y);

        if (!toAvoid.contains(myPosition) &&
                !toAvoid.contains(pathByX) &&
                !toAvoid.contains(pathByY)) {
            //its safe to make the computer auto compile path
            System.err.println("safe going");
            return toGo;
        } else {
            if (!toAvoid.contains(pathByX) && map[pathByX.x][pathByX.y] == '.') {
                System.err.println("going by X");
                return pathByX;
            } else if (!toAvoid.contains(pathByY) && map[pathByY.x][pathByY.y] == '.') {
                System.err.println("going by Y");
                return pathByY;
            } else if (!toAvoid.contains(myPosition)) {
                System.err.println("Stoping everything");
                return myPosition;
            }
            System.err.println("I'm screwed");
        }
        return toGo;
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
        upperloop:
        for (int step = 1; step <= propagation; step++) {
            for (int stepY = 0; stepY < step; stepY++) {
                int valueX = positionX + directionX * (step - stepY);
                int valueY = positionY + directionY * (stepY);
                if (valueX >= 0 && valueX < width && valueY >= 0 && valueY < height && map[valueX][valueY] == '.') {
                    int valueT = value - step * diminution;
                    matrice[valueX][valueY] = valueT;
                } else {
                    return;
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

    private static List<Position> findExplodingAera(List<Bomb> bombs, int maxExplodeIn) {
        List<Position> explosion = new ArrayList<>();
        bombs.stream().filter(b -> b.countdown <= maxExplodeIn).forEach(
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


    private static int[][] pathExploring(int deep, int value, int decrease, Position start) {
        System.err.println("I'm am at " + start);
        int[][] matrice = new int[width][height];
        for (int[] row : matrice)
            Arrays.fill(row, -1000);
        List<List<Move>> calculation = new ArrayList<>();
        Move initialMove = new Move(start, value, new ArrayList<>());
        calculation.add(Arrays.asList(initialMove));
        for (int step = 0; step < deep; step++) {
            List<Move> stepMove = new ArrayList<>();
            if (calculation.get(step).isEmpty()) {
                break;
            }
            calculation.get(step).forEach(
                    m -> stepMove.addAll(goAround(m, m.value, decrease))
            );
            calculation.add(stepMove);
        }

        for (List<Move> moves : calculation) {
            for (Move move : moves) {
                if (matrice[move.now.x][move.now.y] < move.value) {
                    matrice[move.now.x][move.now.y] = move.value;
                }
            }
        }
        return matrice;
    }

    private static List<Move> goAround(Move start, int value, int decrease) {
        Position up = new Position(start.now.x, start.now.y + 1);
        Position right = new Position(start.now.x + 1, start.now.y);
        Position down = new Position(start.now.x, start.now.y - 1);
        Position left = new Position(start.now.x - 1, start.now.y);
        return Stream.of(up, down, right, left)
                .filter(pos -> pos.x >= 0 && pos.x < width
                        && pos.y >= 0 && pos.y < height
                        && map[pos.x][pos.y] == '.'
                        && !start.previousPositions.contains(pos) && !bombs.stream().map(b -> b.pos).collect(Collectors.toList()).contains(pos))
                .map(pos -> new Move(pos, value - decrease, start.previousPositions))
                .collect(Collectors.toList());

    }
}

class Move {
    Position now;
    List<Position> previousPositions;
    int value;

    public Move(Position now, int value, List<Position> previousPositions) {
        this.now = now;
        this.value = value;
        this.previousPositions = new ArrayList<>(previousPositions);
        this.previousPositions.add(now);
    }

    @Override
    public String toString() {
        return "Move{" +
                "now=" + now +
                ", from=" + previousPositions +
                ", value=" + value +
                '}';
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

    public static Position vecteur(Position from, Position to) {
        return new Position(to.x - from.x, to.y - from.y);
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

    @Override
    public String toString() {
        return "Bomb{" +
                "pos=" + pos +
                ", range=" + range +
                ", countdown=" + countdown +
                '}';
    }
}

class BomberMan {
    Position pos;
    int bombs;
    int range;

    public BomberMan(int x, int y, int param1, int param2) {
        this.pos = new Position(x, y);
        this.bombs = param1;
        this.range = param2;
    }

    @Override
    public String toString() {
        return "BomberMan{" +
                "pos=" + pos +
                ", bombs=" + bombs +
                ", range=" + range +
                '}';
    }
}

class AvoidingPosition {
    List<PositonCount> list = new ArrayList<>();

    void add(int x, int y, int count) {
        list.add(new PositonCount(new Position(x, y), count));
    }

    void processAvoiding(int[][] mat) {
        list.forEach(posC -> mat[posC.pos.x][posC.pos.y] += -1000);
    }

    void reduceCount() {
        list = list.stream().map(x -> {
            x.count--;
            return x;
        }).filter(x -> x.count > 0).collect(Collectors.toList());
    }

}

class PositonCount {
    Position pos;
    int count;

    public PositonCount(Position pos, int count) {
        this.pos = pos;
        this.count = count;
    }
}