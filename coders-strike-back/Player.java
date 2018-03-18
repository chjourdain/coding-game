import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Rank : 13232
 **/
class Player {
    public static boolean bostremained = true;
    static boolean usingDirectionPrediction = false;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int step = 0;

        int previousAngle = 0;
        String previousThrust = "0";
        Position previousObjectif = null;
        List<Position> objectifs = new LinkedList<>();
        Set<Position> gameObjectifs = new HashSet<>();

        // game loop
        while (true) {
            //COLLECTING DATA
//            step = step++;
            usingDirectionPrediction = false;
            int x = in.nextInt();
            int y = in.nextInt();
            Position currentObjectif = new Position(in.nextInt(), in.nextInt());
            int nextCheckpointDist = in.nextInt();
            int nextCheckpointAngle = in.nextInt();
            int opponentX = in.nextInt();
            int opponentY = in.nextInt();
            if (!currentObjectif.equals(previousObjectif)) {
                //objectifs.add(0, currentObjectif);
                gameObjectifs.add(currentObjectif);
            }

            //PROCESSING
            Position direction = calculateDirection(gameObjectifs, currentObjectif, previousObjectif, nextCheckpointDist, nextCheckpointAngle);
            int directionX = direction.x;
            int directionY = direction.y;
            System.err.println("paremeters nextCheckpointX=" + currentObjectif.x + "  nextCheckpointY=" + currentObjectif.y
                    + " nextCheckpointDist=" + nextCheckpointDist + " nextCheckpointAngle=" + nextCheckpointAngle);

            String thrust = calculateThrust(directionX, directionY, nextCheckpointDist, nextCheckpointAngle);
            thrust = skidModeration(thrust, previousAngle, nextCheckpointAngle, nextCheckpointDist, previousThrust);
            System.out.println(directionX + " " + directionY + " " + thrust);

            // SAVE DATA FOR NEXT STEP
            previousAngle = nextCheckpointAngle;
            previousThrust = thrust;
            previousObjectif = currentObjectif;
        }
    }

    // INTELIGENCE
    private static String skidModeration(String thrust, int previousAngle, int nextCheckpointAngle,
                                         int nextCheckpointDist, String previousThrust) {
        if (Math.abs(nextCheckpointAngle) >= Math.abs(previousAngle) && previousAngle != 0 && "100".equals(thrust) && "100".equals(previousThrust)
                && nextCheckpointDist < 2500 && Math.abs(nextCheckpointAngle) > 17) {
            System.err.println("using skid régulation");
            return "25";
        }

        return thrust;
    }

    //TODO ajout d'une limitation au bon angle
    public static Position calculateDirection(Set<Position> mapObjectives, Position currentObjectif, Position previousObjectif, int distance, int angle) {
        if (distance > 1100 || mapObjectives.size() < 3 || angle > 10) {
            return currentObjectif;
        } else
            System.err.println("predicting next ojectif");
        return baryCentreOtherObjectif(mapObjectives, Arrays.asList(currentObjectif, previousObjectif));
    }

    public static String calculateThrust(int nextCheckpointX, int nextCheckpointY, int nextCheckpointDist,
                                         int nextCheckpointAngle) {
        if (usingDirectionPrediction) {
            return "40";
        }
        if ((nextCheckpointAngle > 80 && nextCheckpointAngle < 180)
                || (nextCheckpointAngle < -80 && nextCheckpointAngle > -180)) {
            return String.valueOf((int) (Math.abs(nextCheckpointAngle) * -0.6 + 140));
        } else if (nextCheckpointAngle > 180 || nextCheckpointAngle < -180) {
            return "40";
        } else if (bostremained && nextCheckpointAngle == 0 && nextCheckpointDist > 7000) {
            bostremained = false;
            return "BOOST";
        } else {
            return "100";
        }
    }

    public static String calculateThrustBad(int nextCheckpointX, int nextCheckpointY, int nextCheckpointDist,
                                            int nextCheckpointAngle) {
        if (nextCheckpointAngle > 90 || nextCheckpointAngle < -90) {
            System.err.println("using medium thrust");
            return String.valueOf((int) (Math.abs(nextCheckpointAngle) * -0.9 + 181));
        } else if (bostremained && nextCheckpointAngle == 0 && nextCheckpointDist > 5500) {
            System.err.println("using BOOST");
            bostremained = false;
            return "BOOST";
        } else {
            System.err.println("using full thrust");
            return String.valueOf((int) (Math.abs(nextCheckpointAngle) * -0.05 + 100));
        }
    }

    private static Position baryCentreOtherObjectif(Set<Position> objectifs, List<Position> objectifsExcluded) {
        int sumX = objectifs.stream().mapToInt(x-> x.x).sum() - objectifsExcluded.stream().mapToInt(x-> x.x).sum();
        int sumY = objectifs.stream().mapToInt(x -> x.y).sum() - objectifsExcluded.stream().mapToInt(x-> x.y).sum();
        return new Position(sumX / (objectifs.size()- objectifsExcluded.size()), sumY / (objectifs.size()-objectifsExcluded.size()));
    }

}

//MODEL
class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return 2*x + y;
    }

    @Override
    public String toString() {
        return "P{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public int distance(Position position) {
        return (int) Math.sqrt((this.x - position.x) * (this.x - position.x) * +(this.y - position.y) * (this.y - position.y));
    }
}
