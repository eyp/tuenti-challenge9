import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class Challenge7Test {
    @Test
    public void testNotSoComplexHash() {
        final String card = "Subject: Boat;From: Charlie;To: Desmond;---03W000000S0e0000Xzzwue08BzQz0Z0DzzzzzzRzzzzzez_zz---Penny's boat :)";
        Challenge7 challenge7 = new Challenge7();
        byte[] expected = new byte[] {122, 103, 95, 92, -123, -89, -78, 14, 44, -8, 99, 56, 86, 75, -50, 1};
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(challenge7.notSoComplexHash(card)));

        final String originalCard = "Subject: Boat;From: Charlie;To: Desmond;------Not Penny's boat";
        expected = new byte[] {122, 103, 95, 92, -123, -89, -78, 14, 44, -8, 99, 56, 86, 75, -50, 1};
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(challenge7.notSoComplexHash(originalCard)));

        final String modifiedCard = "Subject: Boat;From: Charlie;To: Desmond;------Penny's boat :)";
        expected = new byte[] {116, -75, -120, 30, -118, 89, -101, 86, 26, 76, 33, 3, 30, -41, -48, -9};
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(challenge7.notSoComplexHash(modifiedCard)));
    }
}
