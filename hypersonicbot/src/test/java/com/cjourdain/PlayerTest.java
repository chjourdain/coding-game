package com.cjourdain;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.PrintStream;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PlayerTest {

    PrintStream mock = Mockito.mock(PrintStream.class);
    ArgumentCaptor<String> answer =  ArgumentCaptor.forClass(String.class);


    @Before
    public void init () throws InterruptedException {
        answer =  ArgumentCaptor.forClass(String.class);
        mock = Mockito.mock(PrintStream.class);
        System.out.println(" //////////////////////////////////////////////////////////////////////////////////////// ");
    }

    @After
    public void after() {
        Player.purge();
    }

    @Test
    public void should_walk_on_corner_to_maximize_boxes_to_explose() {
        String arg1 = "13 11 0";
        String arg2 = "...2.0.0.2... .X2X.X.X.X2X. 1..021.120..1 0X.X.X.X.X.X0 .1.1.2.2.1.1. 0X0X0X.X0X0X0 .1.1.2.2.1.1. 0X.X.X.X.X.X0 1..021.120..1 .X2X.X.X.X2X. ...2.0.0.2... 2 0 0 0 0 1 3 0 1 12 10 1 3";
        Player player = new Player();
        Player.printer = mock;
        Player.in = new Scanner(arg1 + " " + arg2);
        try {
            Player.main(new String[]{arg1,arg2});
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
            Player.main(new String[]{arg1,arg2});
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
            Player.main(new String[]{arg1,arg2});
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
            Player.main(new String[]{arg1,arg2});
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
            Player.main(new String[]{arg1,arg2});
        } catch (Exception e) {

        }
        Mockito.verify(mock, Mockito.times(5)).println(answer.capture());
        answer.getAllValues().forEach(
                x -> assertEquals("MOVE 1 8", x)
        );
    }


    @Test
    public void should_stay_hidden_in_the_corner3() {
        String arg1 = "13 11 1";
        String arg2 = ".....2.2..... .X.X.X.X.X.X. 2..........02 .X2X.X.X.X2X. .0100...0010. .X.X1X.X1X.X. .0100...0010. .X2X1X.X.X.X. 20........... .X.X1X.X.X.X. ..2..2....... 11 0 1 3 8 3 5 0 2 5 2 2 6 1 1 4 8 8 5 2 0 2 0 2 2 2 0 8 1 1 1 2 0 10 7 2 2 2 0 8 7 1 1 2 0 6 4 2 2 2 0 6 6 2 2 2 0 4 3 1 1 2 0 4 1 1 1 "
      +  ".....2.2..... .X.X.X.X.X.X. 2..........02 .X2X.X.X.X2X. .0100...0010. .X.X1X.X1X.X. .0100...0010. .X2X1X.X.X.X. 20........... .X.X1X.X.X.X. ..2..2....... 11 0 1 2 8 3 5 0 2 4 2 2 6 1 1 4 8 7 5 2 0 2 0 2 2 2 0 8 1 1 1 2 0 10 7 2 2 2 0 8 7 1 1 2 0 6 4 2 2 2 0 6 6 2 2 2 0 4 3 1 1 2 0 4 1 1 1 "
      +  ".....2.2..... .X.X.X.X.X.X. 2..........02 .X2X.X.X.X2X. .0100...0010. .X.X1X.X1X.X. .0100...0010. .X2X1X.X.X.X. 20........... .X.X1X.X.X.X. ..2..2....... 12 0 1 2 9 3 5 0 2 3 2 1 6 1 1 4 8 6 5 1 2 4 2 8 6 2 0 2 0 2 2 2 0 8 1 1 1 2 0 10 7 2 2 2 0 8 7 1 1 2 0 6 4 2 2 2 0 6 6 2 2 2 0 4 3 1 1 2 0 4 1 1 1 "
      +  ".....2.2..... .X.X.X.X.X.X. 2..........02 .X2X.X.X.X2X. .0100...0010. .X.X1X.X1X.X. .0100...0010. .X2X1X.X.X.X. 20........... .X.X1X.X.X.X. ..2..2....... 12 0 1 2 9 3 5 0 2 2 2 1 6 1 1 4 8 5 5 1 2 4 2 7 6 2 0 2 0 2 2 2 0 8 1 1 1 2 0 10 7 2 2 2 0 8 7 1 1 2 0 6 4 2 2 2 0 6 6 2 2 2 0 4 3 1 1 2 0 4 1 1 1 "
      +  ".....2.2..... .X.X.X.X.X.X. 2..........02 .X2X.X.X.X2X. .0100...0010. .X.X1X.X1X.X. .0100...0010. .X2X1X.X.X.X. 20........... .X.X1X.X.X.X. ..2..2....... 12 0 1 2 9 3 5 0 2 2 1 1 6 1 1 4 8 4 5 1 2 4 2 6 6 2 0 2 0 2 2 2 0 8 1 1 1 2 0 10 7 2 2 2 0 8 7 1 1 2 0 6 4 2 2 2 0 6 6 2 2 2 0 4 3 1 1 2 0 4 1 1 1 "
      +  ".....2.2..... .X.X.X.X.X.X. 2..........02 .X2X.X.X.X2X. .0100...0010. .X.X1X.X1X.X. .0100...0010. .X2X1X.X.X.X. 20........... .X.X1X.X.X.X. ..2..2....... 12 0 1 2 9 3 5 0 2 2 1 1 6 1 1 4 8 3 5 1 2 4 2 5 6 2 0 2 0 2 2 2 0 8 1 1 1 2 0 10 7 2 2 2 0 8 7 1 1 2 0 6 4 2 2 2 0 6 6 2 2 2 0 4 3 1 1 2 0 4 1 1 1 ";
        Player.printer = mock;
        Player.in = new Scanner(arg1 + " " + arg2);
        try {
            Player.main(new String[]{arg1,arg2});
        } catch (Exception e) {

        }
        Mockito.verify(mock, Mockito.times(6)).println(answer.capture());
        answer.getAllValues().forEach(
                x -> assertEquals("MOVE 2 9", x)
        );


    }

    @Test
    public void should_place_a_second_bomb_even_if_both_bomb_is_on_your_path() {

        String arg1 = "13 11 0";
        String arg2 = ".....0.0.2... .X.X.X.X.X2X. ...021.120..1 0X.X.X.X.X.X0 .1.1.2.2.1.1. 0X0X0X.X0X0X0 .1.1.2.2.1.1. 0X.X.X.X.X.X0 1..021.120... .X2X.X.X.X.X. ...2.0.0..... 6 0 0 4 0 2 3 0 1 10 6 2 3 1 0 2 4 3 3 1 1 8 10 3 3 2 0 0 2 1 1 2 0 12 8 1 1 ";
        Player.printer = mock;
        Player.in = new Scanner(arg1 + " " + arg2);
        try {
            Player.main(new String[]{arg1,arg2});
        } catch (Exception e) {

        }
        Mockito.verify(mock).println(answer.capture());
        assertTrue(answer.getValue().startsWith("BOMB"));

    }

    @Test
    public void should_pick_bomb_item_and_place_a_third_bomb() {

        String arg1 = "13 11 0";
        String arg2 = "..11.2.2.11.. .X.X.X1X.X1X. ....1...12... .X.X.X0X.X2X. 2..0.0.0.0..2 .X2X0X.X0X2X. 2..0.0.0.0..2 .X2X.X0X.X.X. ...21...1.... .X1X.X1X.X.X. ..11.2.2.11.. 7 0 0 2 3 0 3 0 1 10 9 2 4 1 0 0 3 3 3 1 0 2 4 8 3 2 0 3 2 2 2 2 0 2 1 1 1 2 0 10 7 2 2 "
        + "..11.2.2.11.. .X.X.X1X.X1X. ....1...12... .X.X.X0X.X2X. 2..0.0.0.0..2 .X2X0X.X0X2X. 2..0.0.0.0..2 .X2X.X0X.X.X. ...21...1.... .X1X.X1X.X.X. ..11.2.2.11.. 7 0 0 2 2 0 3 0 1 10 8 2 4 1 0 0 3 2 3 1 0 2 4 7 3 2 0 3 2 2 2 2 0 2 1 1 1 2 0 10 7 2 2 "
        + "..11.2.2.11.. .X.X.X1X.X1X. ....1...12... .X.X.X0X.X2X. 2..0.0.0.0..2 .X2X0X.X0X2X. 2..0.0.0.0..2 .X2X.X0X.X.X. ...21...1.... .X1X.X1X.X.X. ..11.2.2.11.. 5 0 0 3 2 1 3 0 1 10 7 3 4 1 0 0 3 1 3 1 0 2 4 6 3 2 0 2 1 1 1 "
        + "..11.2.2.11.. .X.X.X1X.X1X. ....1...12... .X.X.X0X.X2X. ...0.0.0.0..2 .X2X0X.X0X2X. 2..0.0.0.0..2 .X2X.X0X.X.X. ...21...1.... .X1X.X1X.X.X. ..11.2.2.11.. 5 0 0 3 2 2 3 0 1 10 6 3 4 1 0 2 4 5 3 2 0 2 1 1 1 2 0 0 4 2 2 ";
        Player.printer = mock;
        Player.in = new Scanner(arg1 + " " + arg2);
        try {
            Player.main(new String[]{arg1,arg2});
        } catch (Exception e) {

        }
        Mockito.verify(mock, Mockito.times(4)).println(answer.capture());

        List<String> moves = answer.getAllValues();

        assertEquals("MOVE 3 2", moves.get(0));
        assertEquals("MOVE 3 2", moves.get(1));
        assertEquals("MOVE 3 2", moves.get(2));

        assertTrue(moves.get(3).startsWith("BOMB"));
    }

    @Test
    public void should_place_a_bomb_on_a_three_box_spot() {

        String arg1 = "13 11 0";
        String arg2 = "..11.2.2.11.. .X.X.X1X.X1X. ........12... .X.X.X0X.X2X. .....0.0.0..2 .X.X0X.X0X.X. 2..0.0.0..... .X2X.X0X.X.X. ...21........ .X1X.X1X.X.X. ..11.2.2.11.. 9 0 0 4 3 2 3 0 1 10 6 3 4 1 1 12 10 1 4 1 0 2 6 4 3 1 0 4 4 8 3 2 0 2 1 1 1 2 0 10 5 2 2 2 0 4 2 1 1 2 0 8 8 1 1 "
       + "..11.2.2.11.. .X.X.X1X.X1X. ........12... .X.X.X0X.X2X. .....0.0.0..2 .X.X0X.X0X.X. 2..0.0.0..... .X2X.X0X.X.X. ...21........ .X1X.X1X.X.X. ..11.2.2.1... 7 0 0 4 2 2 4 0 1 10 5 5 4 1 0 2 6 3 3 1 0 4 4 7 3 2 0 2 1 1 1 2 0 8 8 1 1 2 0 10 10 1 1 "
       + "..11.2.2.11.. .X.X.X1X.X1X. ........12... .X.X.X0X.X2X. .....0.0.0..2 .X.X0X.X0X.X. 2..0.0.0..... .X2X.X0X.X.X. ...21........ .X1X.X1X.X.X. ..11.2.2.1... 7 0 0 5 2 2 4 0 1 10 4 5 4 1 0 2 6 2 3 1 0 4 4 6 3 2 0 2 1 1 1 2 0 8 8 1 1 2 0 10 10 1 1 "
       + "..11.2.2.11.. .X.X.X1X.X1X. ........12... .X.X.X0X.X2X. .....0.0.0..2 .X.X0X.X0X.X. 2..0.0.0..... .X2X.X0X.X.X. ...21........ .X1X.X1X.X.X. ..11.2.2.1... 8 0 0 6 2 2 4 0 1 10 5 4 4 1 0 2 6 1 3 1 0 4 4 5 3 1 1 10 4 8 4 2 0 2 1 1 1 2 0 8 8 1 1 2 0 10 10 1 1 ";
        Player.printer = mock;
        Player.in = new Scanner(arg1 + " " + arg2);
        try {
            Player.main(new String[]{arg1,arg2});
        } catch (Exception e) {

        }
        Mockito.verify(mock, Mockito.times(4)).println(answer.capture());

        List<String> moves = answer.getAllValues();

        assertEquals("MOVE 6 2", moves.get(0));
        assertEquals("MOVE 6 2", moves.get(1));
        assertEquals("MOVE 6 2", moves.get(2));

        assertTrue(moves.get(3).startsWith("BOMB"));
    }


    @Test
    public void should_aggro_my_oponnent_blocked_by_a_bomb() throws InterruptedException {

        String arg1 = "13 11 0";
        String arg2 = "...1.....1... .X.X.X.X.X.X. ............. .X.X.X.X.X.X. ..........12. .X.X.X.X.X.X. ..........120 .X.X.X.X.X.X. ............. .X.X.X.X.X.X. ...1.....1... 6 0 0 8 2 1 4 0 1 8 3 5 6 1 1 9 6 1 6 1 0 6 0 5 4 1 1 9 4 1 6 2 0 3 6 2 2 ";
        Player.printer = mock;
        Player.in = new Scanner(arg1 + " " + arg2);
        try {
            Player.main(new String[]{arg1,arg2});
        } catch (Exception e) {

        }
        Mockito.verify(mock).println(answer.capture());
        System.out.println("size :: " + answer.getAllValues().size());
        System.out.println("answer = " + answer.getValue());
        assertTrue(answer.getValue().startsWith("BOMB"));
    }

    @Test
    public void should_aggro_my_oponent_nothing_else_to_do() throws InterruptedException {

        String arg1 = "13 11 0";
        String arg2 = "............. .X.X.X.X.X.X. ............. .X.X.X.X.X.X. ............. .X.X.X.X.X.X. ............. .X.X.X.X.X.X. ............. .X.X.X.X.X.X. ...1.....1... 7 0 0 8 8 1 4 0 1 8 5 7 8 1 0 6 10 1 4 2 0 9 0 1 1 2 0 3 0 1 1 2 0 11 4 2 2 2 0 11 6 2 2  YYY    ";
        Player.printer = mock;
        Player.in = new Scanner(arg1 + " " + arg2);
        try {
            Player.main(new String[]{arg1,arg2});
        } catch(NoSuchElementException e) {

        } catch (Exception e) {
            assertTrue(false);
        }
        Mockito.verify(mock).println(answer.capture());
        assertTrue(answer.getValue().startsWith("BOMB"));

    }

    @Test
    public void should_go_left_or_rigth_to_avoid_being_perpendicular_to_bomb(){
        String arg1 = "13 11 0";
        String arg2 = "............. .X.X.X.X.X.X. ............. .X2X.X.X.X.X. .0.........0. .X.X.X.X1X.X. .01.......10. .X.X.X.X.X.X. ............. .X.X.X.X.X.X. .....2....... 16 0 1 8 8 6 7 0 2 4 4 1 7 1 1 8 10 3 7 1 2 2 4 7 7 2 0 10 7 2 2 2 0 4 7 1 1 2 0 4 9 1 1 2 0 0 2 2 2 2 0 2 7 2 2 2 0 0 8 2 2 2 0 2 10 2 2 2 0 5 0 2 2 2 0 10 3 2 2 2 0 12 2 2 2 2 0 4 5 1 1 2 0 10 4 1 1 ";
    }

    @Test
    public void should_bomb_but_then_go_left_to_avoid_dying(){
        String arg1 = "13 11 0";
        String arg2 = "...0..1..0... .X.X0X.X0X.X. 0....2.2...10 .X.X.X.X.X1X. 2.0...22.10.2 .X.X.X.X.X.X. 2.01.2.2.10.2 .X.X.X.X.X1X. .1.........10 .X.X.X.X0X.X. .........0... 11 0 0 4 3 2 3 0 1 12 10 1 3 0 2 10 0 1 3 1 0 4 6 4 3 2 0 1 2 1 1 2 0 2 3 1 1 2 0 3 4 1 1 2 0 2 7 1 1 2 0 6 6 2 2 2 0 7 8 2 2 2 0 6 10 1 1 ";

    }

    public void  sould_pick_up_two_item_then_bomb_boxes_next_to_them() {
        String arg1 = "13 11 0";
        String arg2 = "..012...210.. .X0X1X.X.X.X. ....12....... 1X.X.X.X.X.X1 2...........2 .X.X.X.X.X.X. 2...........2 1X.X.X.X0X.X. .....2.2..... .X.X1X.X1X.X. ..0122.221... 12 0 0 3 2 1 3 0 1 6 1 0 3 0 2 1 2 0 4 1 1 6 8 2 3 1 2 2 4 4 4 1 2 2 2 7 4 2 0 8 8 1 1 2 0 4 8 1 1 2 0 12 7 1 1 2 0 8 1 1 1 2 0 7 0 2 2 2 0 5 0 2 2 ";
        String arg2 = "..012...210.. .X0X1X.X.X.X. ....12....... 1X.X.X.X.X.X1 2...........2 .X.X.X.X.X.X. 2...........2 1X.X.X.X0X.X. .....2.2..... .X.X1X.X1X.X. ..0122.221... 12 0 0 3 2 1 3 0 1 6 0 0 3 0 2 0 2 0 4 1 1 6 8 1 3 1 2 2 4 3 4 1 2 2 2 6 4 2 0 8 8 1 1 2 0 4 8 1 1 2 0 12 7 1 1 2 0 8 1 1 1 2 0 7 0 2 2 2 0 5 0 2 2 ";
        String arg2 = "..012...210.. .X0X1X.X.X.X. ....12....... 1X.X.X.X.X.X1 2...........2 .X.X.X.X.X.X. 2...........2 1X.X.X.X0X.X. ............. .X.X1X.X1X.X. ..0122.221... 12 0 0 3 2 1 3 0 1 5 0 2 3 0 2 0 1 0 4 1 2 2 4 2 4 1 2 2 2 5 4 2 0 8 8 1 1 2 0 4 8 1 1 2 0 12 7 1 1 2 0 8 1 1 1 2 0 7 0 2 2 2 0 7 8 2 2 2 0 5 8 2 2 ";
        String arg2 = "..012...210.. .X0X1X.X.X.X. ....12....... 1X.X.X.X.X.X1 2...........2 .X.X.X.X.X.X. 2...........2 1X.X.X.X0X.X. ............. .X.X1X.X1X.X. ..0122.221... 12 0 0 3 2 1 3 0 1 6 0 2 3 0 2 0 0 0 4 1 2 2 4 1 4 1 2 2 2 4 4 2 0 8 8 1 1 2 0 4 8 1 1 2 0 12 7 1 1 2 0 8 1 1 1 2 0 7 0 2 2 2 0 7 8 2 2 2 0 5 8 2 2 ";

        String arg2 = "..012...210.. .X.X1X.X.X.X. .....2....... 1X.X.X.X.X.X1 ............2 .X.X.X.X.X.X. 2...........2 1X.X.X.X0X.X. ............. .X.X1X.X1X.X. ..0122.221... 11 0 1 7 0 2 3 0 2 0 0 2 4 2 0 8 8 1 1 2 0 4 8 1 1 2 0 12 7 1 1 2 0 8 1 1 1 2 0 7 8 2 2 2 0 5 8 2 2 2 0 0 4 2 2 2 0 4 2 1 1 ";


//        "MOVE 5 0" "MOVE 7 0"
//        "MOVE 5 0" or "MOVE 7 0"
//        "MOVE 7 0"
//        "MOVE 7 0"
//                "BOMB";


    }

}
