package com.cjourdain;

import com.cjourdain.hypersonic.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PlayerTest {

    PrintStream mock = Mockito.mock(PrintStream.class);
    @Captor
    ArgumentCaptor<String> answer = new ArgumentCaptor<>();

    @Before
    public void init() {

    }

    @Test
    public void should_walk_on_corner_to_maximize_boxes_to_explose() {
        String arg1 = "13 11 0";
        String arg2 = "...2.0.0.2... .X2X.X.X.X2X. 1..021.120..1 0X.X.X.X.X.X0 .1.1.2.2.1.1. 0X0X0X.X0X0X0 .1.1.2.2.1.1. 0X.X.X.X.X.X0 1..021.120..1 .X2X.X.X.X2X. ...2.0.0.2... 2 0 0 0 0 1 3 0 1 12 10 1 3";
        Player.printer = mock;
        Player.in = new Scanner(arg1 + " " + arg2);
        try {
            Player.main(new String[0]);
        } catch (Exception e) {

        }
        Mockito.verify(mock).println(answer.capture());
        assertEquals("MOVE 2 0", answer.getValue());
    }

    @Test
    public void drop_a_bomb_on_corner() {
        String arg1 = "13 11 0";
        String arg2 = "...2.0.0.2... .X2X.X.X.X2X. 1..021.120..1 0X.X.X.X.X.X0 .1.1.2.2.1.1. 0X0X0X.X0X0X0 .1.1.2.2.1.1. 0X.X.X.X.X.X0 1..021.120..1 .X2X.X.X.X2X. ...2.0.0.2... 2 0 0 2 0 1 3 0 1 10 10 1 3 ";
        Player.printer = mock;
        Player.in = new Scanner(arg1 + " " + arg2);
        try {
            Player.main(new String[0]);
        } catch (Exception e) {

        }
        Mockito.verify(mock).println(answer.capture());
        assertEquals("BOMB 0 1", answer.getValue());
    }

    @Test
    public void should_walk_to_plaace_a_3_box_bomb() {
        String arg1 = "13 11 0";
        String arg2 = ".....0.0.2... .X.X.X.X.X2X. 1..021.120..1 0X.X.X.X.X.X0 .1.1.2.2.1.1. 0X0X0X.X0X0X0 .1.1.2.2.1.1. 0X.X.X.X.X.X0 1..021.120..1 .X2X.X.X.X.X. ...2.0.0..... 6 0 0 2 1 1 3 0 1 10 9 1 3 1 0 0 1 5 3 1 1 12 9 5 3 2 0 9 10 2 2 2 0 3 0 2 2 ";
        Player.printer = mock;
        Player.in = new Scanner(arg1 + " " + arg2);
        try {
            Player.main(new String[0]);
        } catch (Exception e) {

        }
        Mockito.verify(mock).println(answer.capture());
        assertEquals("MOVE 2 4", answer.getValue());
    }


    @Test
    public void should_stay_hidden_in_the_corner() {
        String arg1 = "13 11 0";
        String arg2 = "...2.0.0.2... .X2X.X.X.X2X. 1..021.120..1 0X.X.X.X.X.X0 .1.1.2.2.1.1. 0X0X0X.X0X0X0 .1.1.2.2.1.1. 0X.X.X.X.X.X0 1..021.120..1 .X2X.X.X.X2X. ...2.0.0.2... 4 0 0 0 0 0 3 0 1 12 10 0 3 1 0 2 0 7 3 1 1 10 10 7 3 "
                + "...2.0.0.2... .X2X.X.X.X2X. 1..021.120..1 0X.X.X.X.X.X0 .1.1.2.2.1.1. 0X0X0X.X0X0X0 .1.1.2.2.1.1. 0X.X.X.X.X.X0 1..021.120..1 .X2X.X.X.X2X. ...2.0.0.2... 4 0 0 0 1 0 3 0 1 12 9 0 3 1 0 2 0 6 3 1 1 10 10 6 3 "
                + "...2.0.0.2... .X2X.X.X.X2X. 1..021.120..1 0X.X.X.X.X.X0 .1.1.2.2.1.1. 0X0X0X.X0X0X0 .1.1.2.2.1.1. 0X.X.X.X.X.X0 1..021.120..1 .X2X.X.X.X2X. ...2.0.0.2... 4 0 0 0 1 0 3 0 1 12 9 0 3 1 0 2 0 5 3 1 1 10 10 5 3 "
                + "...2.0.0.2... .X2X.X.X.X2X. 1..021.120..1 0X.X.X.X.X.X0 .1.1.2.2.1.1. 0X0X0X.X0X0X0 .1.1.2.2.1.1. 0X.X.X.X.X.X0 1..021.120..1 .X2X.X.X.X2X. ...2.0.0.2... 4 0 0 0 1 0 3 0 1 12 9 0 3 1 0 2 0 4 3 1 1 10 10 4 3 "
                + "...2.0.0.2... .X2X.X.X.X2X. 1..021.120..1 0X.X.X.X.X.X0 .1.1.2.2.1.1. 0X0X0X.X0X0X0 .1.1.2.2.1.1. 0X.X.X.X.X.X0 1..021.120..1 .X2X.X.X.X2X. ...2.0.0.2... 4 0 0 0 1 0 3 0 1 12 9 0 3 1 0 2 0 3 3 1 1 10 10 3 3 "
                + "...2.0.0.2... .X2X.X.X.X2X. 1..021.120..1 0X.X.X.X.X.X0 .1.1.2.2.1.1. 0X0X0X.X0X0X0 .1.1.2.2.1.1. 0X.X.X.X.X.X0 1..021.120..1 .X2X.X.X.X2X. ...2.0.0.2... 4 0 0 0 1 0 3 0 1 12 9 0 3 1 0 2 0 2 3 1 1 10 10 2 3 "
                + "...2.0.0.2... .X2X.X.X.X2X. 1..021.120..1 0X.X.X.X.X.X0 .1.1.2.2.1.1. 0X0X0X.X0X0X0 .1.1.2.2.1.1. 0X.X.X.X.X.X0 1..021.120..1 .X2X.X.X.X2X. ...2.0.0.2... 4 0 0 0 1 0 3 0 1 12 9 0 3 1 0 2 0 1 3 1 1 10 10 1 3 ";

        Player.printer = mock;
        Player.in = new Scanner(arg1 + " " + arg2);
        try {
            Player.main(new String[0]);
        } catch (Exception e) {

        }
        Mockito.verify(mock, Mockito.times(7)).println(answer.capture());
        answer.getAllValues().forEach(
                x -> assertEquals("MOVE 0 1", x)
        );
    }

    @Test
    public void should_stay_hidden_in_the_corner2() {
        String arg1 = "13 11 0";
        String arg2 = ".......0.2... .X.X.X.X.X2X. ...0.1.120..1 0X.X.X.X.X.X0 .....2.2.1.1. 0X.X0X.X0X.X0 .1.1.2.2..... 0X.X.X.X.X.X0 1..021.1.0... .X2X.X.X.X.X. ...2.0....... 13 0 0 2 7 2 3 0 1 11 10 1 3 1 1 11 8 1 3 1 1 12 9 7 3 1 0 2 6 8 3 2 0 0 2 1 1 2 0 12 8 1 1 2 0 8 8 2 2 2 0 3 4 1 1 2 0 1 4 1 1 2 0 11 6 1 1 2 0 9 6 1 1 2 0 4 2 2 2 "
        + ".......0.2... .X.X.X.X.X2X. ...0.1.120..1 0X.X.X.X.X.X0 .....2.2.1.1. 0X.X0X.X0X.X0 .1.1.2.2..... 0X.X.X.X.X.X0 1..021.1..... .X2X.X.X.X.X. ...2.0....... 11 0 0 2 8 2 3 0 1 10 10 2 3 1 1 12 9 6 3 1 0 2 6 7 3 2 0 0 2 1 1 2 0 8 8 2 2 2 0 3 4 1 1 2 0 1 4 1 1 2 0 11 6 1 1 2 0 9 6 1 1 2 0 4 2 2 2 "
        + ".......0.2... .X.X.X.X.X2X. ...0.1.120..1 0X.X.X.X.X.X0 .....2.2.1.1. 0X.X0X.X0X.X0 .1.1.2.2..... 0X.X.X.X.X.X0 1..021.1..... .X2X.X.X.X.X. ...2.0....... 11 0 0 1 8 2 3 0 1 9 10 2 3 1 1 12 9 5 3 1 0 2 6 6 3 2 0 0 2 1 1 2 0 8 8 2 2 2 0 3 4 1 1 2 0 1 4 1 1 2 0 11 6 1 1 2 0 9 6 1 1 2 0 4 2 2 2 "
        + ".......0.2... .X.X.X.X.X2X. ...0.1.120..1 0X.X.X.X.X.X0 .....2.2.1.1. 0X.X0X.X0X.X0 .1.1.2.2..... 0X.X.X.X.X.X0 1..021.1..... .X2X.X.X.X.X. ...2.0....... 11 0 0 1 8 2 3 0 1 8 10 2 3 1 1 12 9 4 3 1 0 2 6 5 3 2 0 0 2 1 1 2 0 8 8 2 2 2 0 3 4 1 1 2 0 1 4 1 1 2 0 11 6 1 1 2 0 9 6 1 1 2 0 4 2 2 2 "
        + ".......0.2... .X.X.X.X.X2X. ...0.1.120..1 0X.X.X.X.X.X0 .....2.2.1.1. 0X.X0X.X0X.X0 .1.1.2.2..... 0X.X.X.X.X.X0 1..021.1..... .X2X.X.X.X.X. ...2.0....... 11 0 0 2 8 2 3 0 1 7 10 2 3 1 1 12 9 3 3 1 0 2 6 4 3 2 0 0 2 1 1 2 0 8 8 2 2 2 0 3 4 1 1 2 0 1 4 1 1 2 0 11 6 1 1 2 0 9 6 1 1 2 0 4 2 2 2 ";


        Player.printer = mock;
        Player.in = new Scanner(arg1 + " " + arg2);
        try {
            Player.main(new String[0]);
        } catch (Exception e) {

        }
        Mockito.verify(mock, Mockito.times(7)).println(answer.capture());
        answer.getAllValues().forEach(
                x -> assertEquals("MOVE 1 8", x)
        );
    }

    @Test
    public void should_place_a_second_bomb_even_if_both_bomb_is_on_your_path() {
        String arg1 = "13 11 0";
        String arg2 = ".....0.0.2... .X.X.X.X.X2X. ...021.120..1 0X.X.X.X.X.X0 .1.1.2.2.1.1. 0X0X0X.X0X0X0 .1.1.2.2.1.1. 0X.X.X.X.X.X0 1..021.120... .X2X.X.X.X.X. ...2.0.0..... 6 0 0 4 0 2 3 0 1 10 6 2 3 1 0 2 4 3 3 1 1 8 10 3 3 2 0 0 2 1 1 2 0 12 8 1 1 ";
        Player.printer = mock;
        Player.in = new Scanner(arg1 + " " + arg2);
        try {
            Player.main(new String[0]);
        } catch (Exception e) {

        }
        Mockito.verify(mock).println(answer.capture());
        assertTrue(answer.getValue().startsWith("BOMB"));

    }
}
