import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class Challenge7Test {
    @Test
    public void testNotSoComplexHash() {
        Challenge7 challenge7 = new Challenge7();

        final String card = "Subject: Boat;From: Charlie;To: Desmond;---03W000000S0e0000Xzzwue08BzQz0Z0DzzzzzzRzzzzzez_zz---Penny's boat :)";
        byte[] expected = new byte[] {122, 103, 95, 92, -123, -89, -78, 14, 44, -8, 99, 56, 86, 75, -50, 1};
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(challenge7.notSoComplexHash(card)));

        final String originalCard = "Subject: Boat;From: Charlie;To: Desmond;------Not Penny's boat";
        expected = new byte[] {122, 103, 95, 92, -123, -89, -78, 14, 44, -8, 99, 56, 86, 75, -50, 1};
        System.out.println(Arrays.toString("Subject: Boat;From: Charlie;To: Desmond;------Not Penny's boat".getBytes()));
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(challenge7.notSoComplexHash(originalCard)));

        final String modifiedCard = "Subject: Boat;From: Charlie;To: Desmond;------Penny's boat :)";
        expected = new byte[] {116, -75, -120, 30, -118, 89, -101, 86, 26, 76, 33, 3, 30, -41, -48, -9};
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(challenge7.notSoComplexHash(modifiedCard)));
    }

    @Test
    public void testCrackMessage() {
        Challenge7 challenge7 = new Challenge7();
        final String originalCard = "Subject: Boat;From: Charlie;To: Desmond;------Not Penny's boat";
        final String modifiedCard = "Subject: Boat;From: Charlie;To: Desmond;------Penny's boat :)";
        String expected = "03W000000S0e0000Xzzwue08BzQz0Z0DzzzzzzRzzzzzez_zz";
        Assert.assertEquals(expected, challenge7.crackMessage(originalCard, modifiedCard));
    }

    @Test
    public void testCrackMessage_EmptyMEssage() {
        Challenge7 challenge7 = new Challenge7();
        String originalCard = "------";
        String modifiedCard = "------";
        String expected = "";
//        Assert.assertEquals(expected, challenge7.crackMessage(originalCard, modifiedCard));

        originalCard = "a------";
        modifiedCard = "b------";
        expected = "z";
        Assert.assertEquals(expected, challenge7.crackMessage(originalCard, modifiedCard));
    }

    @Test
    public void testBytesToReachOriginal() {
        Challenge7 challenge7 = new Challenge7();
        for (byte i = 48; i < 123; i++) {
            byte[] bytes = challenge7.bytesToReachOriginal((byte) 97, (byte) 98, i);
            System.out.println(Arrays.toString(bytes));
        }
        byte[] bytes = challenge7.bytesToReachOriginal((byte) 97, (byte) 98, (byte) 122);
        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void testNextCrack() {
        Challenge7 challenge7 = new Challenge7();
        Assert.assertEquals("0", challenge7.nextCrack(""));
        Assert.assertEquals("00", challenge7.nextCrack("z"));
        Assert.assertEquals("01", challenge7.nextCrack("00"));
        Assert.assertEquals("10", challenge7.nextCrack("0z"));
        Assert.assertEquals("11", challenge7.nextCrack("10"));
        Assert.assertEquals("12", challenge7.nextCrack("11"));
        Assert.assertEquals("20", challenge7.nextCrack("1z"));
        Assert.assertEquals("1", challenge7.nextCrack("0"));
        Assert.assertEquals("1", challenge7.nextCrack("0"));
        Assert.assertEquals("012B", challenge7.nextCrack("012A"));
    }

    @Test
    public void testNotSoComplexHashForIndex() {
        Challenge7 challenge7 = new Challenge7();
        String text = "Subject: Boat;From: Charlie;To: Desmond;------Not Penny's boat";
        Assert.assertEquals(122, challenge7.notSoComplexHashForIndex(text, 0));
        Assert.assertEquals(103, challenge7.notSoComplexHashForIndex(text, 1));
        Assert.assertEquals(95, challenge7.notSoComplexHashForIndex(text, 2));
        Assert.assertEquals(92, challenge7.notSoComplexHashForIndex(text, 3));
        Assert.assertEquals(-123, challenge7.notSoComplexHashForIndex(text, 4));
        Assert.assertEquals(-89, challenge7.notSoComplexHashForIndex(text, 5));
        Assert.assertEquals(-78, challenge7.notSoComplexHashForIndex(text, 6));
        Assert.assertEquals(14, challenge7.notSoComplexHashForIndex(text, 7));
        Assert.assertEquals(44, challenge7.notSoComplexHashForIndex(text, 8));
        Assert.assertEquals(-8, challenge7.notSoComplexHashForIndex(text, 9));
        Assert.assertEquals(99, challenge7.notSoComplexHashForIndex(text, 10));
        Assert.assertEquals(56, challenge7.notSoComplexHashForIndex(text, 11));
        Assert.assertEquals(86, challenge7.notSoComplexHashForIndex(text, 12));
        Assert.assertEquals(75, challenge7.notSoComplexHashForIndex(text, 13));
        Assert.assertEquals(-50, challenge7.notSoComplexHashForIndex(text, 14));
        Assert.assertEquals(1, challenge7.notSoComplexHashForIndex(text, 15));
        Assert.assertEquals(122, challenge7.notSoComplexHashForIndex(text, 16));
    }

    @Test
    public void testNotSoComplexHashUptoIndex() {
        Challenge7 challenge7 = new Challenge7();
        String text = "Subject: Boat;From: Charlie;To: Desmond;------Not Penny's boat";
        byte[] expected = new byte[] {122, 103, 95, 92, -123, -89, -78, 14, 44, -8, 99, 56, 86, 75, -50, 1};
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(challenge7.notSoComplexHashUptoIndex(text, 15)));
        expected = new byte[] {122};
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(challenge7.notSoComplexHashUptoIndex(text, 0)));
        expected = new byte[] {122, 103, 95, 92, -123};
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(challenge7.notSoComplexHashUptoIndex(text, 4)));
        expected = new byte[] {122, 103, 95, 92, -123, -89, -78, 14, 44, -8, 99, 56, 86, 75, -50, 1};
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(challenge7.notSoComplexHashUptoIndex(text, 16)));
    }
}
