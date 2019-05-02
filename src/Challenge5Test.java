import org.junit.Assert;
import org.junit.Test;

public class Challenge5Test {
    @Test
    public void testDecryptText() {
        Challenge5 challenge5 = new Challenge5();
        Assert.assertEquals("I MISS YOU. G", challenge5.decryptText('G', "P .PFF IQOZ J".toCharArray()));
        Assert.assertEquals("I MISS YOU TOO. B", challenge5.decryptText('B', "J 6JZZ GKH FKK8 4".toCharArray()));
    }

    @Test
    public void testGetCoordinates() {
        Challenge5 challenge5 = new Challenge5();
        Assert.assertEquals(new Challenge5.Coordinates(4, 3), challenge5.getCoordinates('B'));
        Assert.assertEquals(new Challenge5.Coordinates(4, 2), challenge5.getCoordinates('G'));
        Assert.assertEquals(new Challenge5.Coordinates(9, 0), challenge5.getCoordinates('0'));
        Assert.assertEquals(new Challenge5.Coordinates(0, 0), challenge5.getCoordinates('1'));
        Assert.assertEquals(new Challenge5.Coordinates(9, 3), challenge5.getCoordinates('-'));
    }

    @Test
    public void testDifference() {
        Challenge5 challenge5 = new Challenge5();
        Challenge5.Coordinates charJ = challenge5.getCoordinates('J');
        Challenge5.Coordinates charG = challenge5.getCoordinates('G');
        Assert.assertEquals(new Challenge5.Coordinates(2, 0), challenge5.difference(charJ, charG));

        Challenge5.Coordinates char4 = challenge5.getCoordinates('4');
        Challenge5.Coordinates charB = challenge5.getCoordinates('B');
        Assert.assertEquals(new Challenge5.Coordinates(-1, -3), challenge5.difference(char4, charB));
    }

    @Test
    public void testDecryptChar() {
        Challenge5 challenge5 = new Challenge5();
        Assert.assertEquals('G', challenge5.decryptChar('J', new Challenge5.Coordinates(2, 0)));
    }
}
