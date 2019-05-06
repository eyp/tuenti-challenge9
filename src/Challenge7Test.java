import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class Challenge7Test {
    @Test
    public void testNotSoComplexHash() {
        Challenge7 challenge7 = new Challenge7("------", "------");

        final String card = "Subject: Boat;From: Charlie;To: Desmond;---03W000000S0e0000Xzzwue08BzQz0Z0DzzzzzzRzzzzzez_zz---Penny's boat :)";
        byte[] expected = new byte[]{122, 103, 95, 92, -123, -89, -78, 14, 44, -8, 99, 56, 86, 75, -50, 1};
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(challenge7.notSoComplexHash(card)));

        final String originalCard = "Subject: Boat;From: Charlie;To: Desmond;------Not Penny's boat";
        expected = new byte[]{122, 103, 95, 92, -123, -89, -78, 14, 44, -8, 99, 56, 86, 75, -50, 1};
        System.out
                .println(Arrays.toString("Subject: Boat;From: Charlie;To: Desmond;------Not Penny's boat".getBytes()));
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(challenge7.notSoComplexHash(originalCard)));

        final String modifiedCard = "Subject: Boat;From: Charlie;To: Desmond;------Penny's boat :)";
        expected = new byte[]{116, -75, -120, 30, -118, 89, -101, 86, 26, 76, 33, 3, 30, -41, -48, -9};
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(challenge7.notSoComplexHash(modifiedCard)));
    }

    @Test
    public void testHash() {
        //System.out.printf("43 %% 16: %d", (43%16));
        //System.out.printf("18 %% 16: %d", (18%16));
        Assert.assertEquals((byte) 45, (byte) (57 + 122 + 122));

        final String originalCard = "Subject: Boat;From: Charlie;To: Desmond;------Not Penny's boat";
        final String modifiedCard = "Subject: Boat;From: Charlie;To: Desmond;------Penny's boat :)";
        Challenge7 challenge7 = new Challenge7(originalCard, modifiedCard);
        final String card = "Subject: Boat;From: Charlie;To: Desmond;---03W000000S0e0000Xzzwue08BzQz0Z0DzzzzzzRzzzzzez_zz---Penny's boat :)";
        challenge7.printHashBytes(card);

    }

    @Test
    public void testHasSameHash() {
        final String originalCard = "Subject: Boat;From: Charlie;To: Desmond;------Not Penny's boat";
        Challenge7 challenge7 = new Challenge7(originalCard, "------");
        Assert.assertFalse(challenge7.hasSameHash("", challenge7.notSoComplexHash(originalCard)));
        Assert.assertTrue(challenge7.hasSameHash(originalCard, challenge7.notSoComplexHash(originalCard)));

        Assert.assertFalse(challenge7.hasSameHash("b---000000000000Xzzzzzzzzzzzzzzzzzzzzzzzzzzzzz---",
                                                  challenge7.notSoComplexHash("a------")));
    }

    @Test
    public void testCrackMessage() {
        final String originalCard = "Subject: Boat;From: Charlie;To: Desmond;------Not Penny's boat";
        final String modifiedCard = "Subject: Boat;From: Charlie;To: Desmond;------Penny's boat :)";
        Challenge7 challenge7 = new Challenge7(originalCard, modifiedCard);
        String expected = "03W000000S0e0000Xzzwue08BzQz0Z0DzzzzzzRzzzzzez_zz";
        Assert.assertEquals(expected, challenge7.crackMessage());
    }

    @Test
    public void testCrackMessage_EmptyMessage() {
        String originalCard = "------";
        String modifiedCard = "------";
        Challenge7 challenge7 = new Challenge7(originalCard, modifiedCard);
        String expected = "";
        Assert.assertEquals(expected, challenge7.crackMessage());
    }

    @Test
    public void testCrackMessage_SimpleMessage() {
        String originalCard = "------";
        String modifiedCard = "------a";
        Challenge7 challenge7 = new Challenge7(originalCard, modifiedCard);
        String expected = "000000000000Xzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
        Assert.assertEquals(expected, challenge7.crackMessage());
    }

    @Test
    public void testNextCrack() {
        Challenge7 challenge7 = new Challenge7("------", "------");
        Assert.assertEquals("0", challenge7.incrementCrack(""));
        Assert.assertEquals("00", challenge7.incrementCrack("z"));
        Assert.assertEquals("01", challenge7.incrementCrack("00"));
        Assert.assertEquals("10", challenge7.incrementCrack("0z"));
        Assert.assertEquals("11", challenge7.incrementCrack("10"));
        Assert.assertEquals("12", challenge7.incrementCrack("11"));
        Assert.assertEquals("20", challenge7.incrementCrack("1z"));
        Assert.assertEquals("1", challenge7.incrementCrack("0"));
        Assert.assertEquals("1", challenge7.incrementCrack("0"));
        Assert.assertEquals("012B", challenge7.incrementCrack("012A"));
        Assert.assertEquals("000", challenge7.incrementCrack("zz"));
        Assert.assertEquals("zz", challenge7.incrementCrack("zy"));
        Assert.assertEquals("100", challenge7.incrementCrack("0zz"));
    }

    @Test
    public void testNotSoComplexHashForIndex() {
        String text = "Subject: Boat;From: Charlie;To: Desmond;------Not Penny's boat";
        Challenge7 challenge7 = new Challenge7("------", "------");
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
        String text = "Subject: Boat;From: Charlie;To: Desmond;------Not Penny's boat";
        byte[] expected = new byte[]{122, 103, 95, 92, -123, -89, -78, 14, 44, -8, 99, 56, 86, 75, -50, 1};
        Challenge7 challenge7 = new Challenge7("------", "------");
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(challenge7.notSoComplexHashUptoIndex(text, 15)));
        expected = new byte[]{122};
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(challenge7.notSoComplexHashUptoIndex(text, 0)));
        expected = new byte[]{122, 103, 95, 92, -123};
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(challenge7.notSoComplexHashUptoIndex(text, 4)));
        expected = new byte[]{122, 103, 95, 92, -123, -89, -78, 14, 44, -8, 99, 56, 86, 75, -50, 1};
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(challenge7.notSoComplexHashUptoIndex(text, 16)));
    }
}
