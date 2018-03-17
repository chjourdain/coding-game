import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Rank : 8912
 **/
class Player {
    public static boolean bostremained = true;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int step = 0;

        int previousAngle = 0;
        String previousThrust = "0";
        Position previousObjectif = null;
        List<Position> objectifs = new LinkedList<>();


        // game loop
        while (true) {
            //COLLECTING DATA
            step = step++;
            int x = in.nextInt();
            int y = in.nextInt();
            Position currentObjectif = new Position(in.nextInt(), in.nextInt());
            int nextCheckpointDist = in.nextInt();
            int nextCheckpointAngle = in.nextInt();
            int opponentX = in.nextInt();
            int opponentY = in.nextInt();
            if (!currentObjectif.equals(previousObjectif)) {
                objectifs.add(0, currentObjectif);
            }

            //PROCESSING
            int directionX = currentObjectif.getX();
            int directionY = currentObjectif.getY();
            System.err.println("paremeters nextCheckpointX=" + directionX + "  nextCheckpointY=" + directionY
                    + " nextCheckpointDist=" + nextCheckpointDist + " nextCheckpointAngle=" + nextCheckpointAngle);

            String thrust = calculateThrust(directionX, directionY, nextCheckpointDist, nextCheckpointAngle);
            thrust = skidModeration(thrust, previousAngle, nextCheckpointAngle, nextCheckpointDist, previousThrust);
            System.err.println(directionX + " " + directionY + " " + thrust);
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
            return "20";
        }

        return thrust;
    }

    public static String calculateThrust(int nextCheckpointX, int nextCheckpointY, int nextCheckpointDist,
                                         int nextCheckpointAngle) {
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


}

//MODEL
class Position {
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
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

    @Override
    public String toString() {
        return "P{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public int distance(Position position) {
        return (int) Math.sqrt((this.x - position.getX()) * (this.x - position.getX()) * +(this.y - position.getY()) * (this.y - position.getY()));
    }
}
