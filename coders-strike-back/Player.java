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

        int previousAngle = 0;
        String previousThrust = "0";


        // game loop
        while (true) {
            int x = in.nextInt();
            int y = in.nextInt();
            int nextCheckpointX = in.nextInt();
            int nextCheckpointY = in.nextInt();
            int nextCheckpointDist = in.nextInt();
            int nextCheckpointAngle = in.nextInt();
            int opponentX = in.nextInt();
            int opponentY = in.nextInt();

            int directionX = nextCheckpointX;
            int directionY = nextCheckpointY;
            System.err.println("paremeters nextCheckpointX=" + nextCheckpointX + "  nextCheckpointY=" + nextCheckpointY
                    + " nextCheckpointDist=" + nextCheckpointDist + " nextCheckpointAngle=" + nextCheckpointAngle);

            String thrust = calculateThrust(nextCheckpointX, nextCheckpointY, nextCheckpointDist, nextCheckpointAngle);
            thrust = skidModeration(thrust, previousAngle, nextCheckpointAngle, nextCheckpointDist, previousThrust);
            System.err.println(directionX + " " + directionY + " " + thrust);
            System.out.println(directionX + " " + directionY + " " + thrust);
            previousAngle = nextCheckpointAngle;
            previousThrust = thrust;

        }
    }

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
            return String.valueOf((int)(Math.abs(nextCheckpointAngle)* -0.6 + 140));
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
