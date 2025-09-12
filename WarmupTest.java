import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class WarmupTest {
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }
    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testMain() {
        for (int i = 1; i <= 8; i++) {
            outContent.reset();
            String input = "5\n6\n" + i + "\n";
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            assertDoesNotThrow(() -> SP_lr1.main(new String[]{}));
            assertTrue(outContent.toString().contains("30"));
        }
        outContent.reset();
        String input = "5\n6\n9\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        assertDoesNotThrow(() -> SP_lr1.main(new String[]{}));
        assertTrue(outContent.toString().contains("Операции не существует"));
    }

    @Test
    void testMulIterative() {
        assertEquals(20, SP_lr1.mulIterative(4, 5));
        assertEquals(-20, SP_lr1.mulIterative(4, -5));
        assertEquals(0, SP_lr1.mulIterative(0, 5));
        assertEquals(0, SP_lr1.mulIterative(6, 0));
        assertEquals(0, SP_lr1.mulIterative(0, 0));
        assertEquals(35, SP_lr1.mulIterative(-5, -7));
        assertEquals(-63, SP_lr1.mulIterative(-9, 7));
    }

    @Test
    void testMulRecursiveBit() {
        assertEquals(64, SP_lr1.mulRecursiveBit(8, 8));
        assertEquals(-14, SP_lr1.mulRecursiveBit(2, -7));
        assertEquals(0, SP_lr1.mulRecursiveBit(0, 2133));
        assertEquals(0, SP_lr1.mulRecursiveBit(0, 0));
        assertEquals(0, SP_lr1.mulRecursiveBit(5, 0));
        assertEquals(80, SP_lr1.mulRecursiveBit(-40, -2));
        assertEquals(-42, SP_lr1.mulRecursiveBit(-6, 7));
    }

    @Test
    void testMulBit() {
        assertEquals(24, SP_lr1.mulBit(4, 6));
        assertEquals(-2, SP_lr1.mulBit(1, -2));
        assertEquals(0, SP_lr1.mulBit(0, 5));
        assertEquals(0, SP_lr1.mulBit(3, 0));
        assertEquals(0, SP_lr1.mulBit(0, 0));
        assertEquals(35, SP_lr1.mulBit(-5, -7));
        assertEquals(-63, SP_lr1.mulBit(-9, 7));
    }

    @Test
    void testMulBigInteger() {
        assertEquals(BigInteger.valueOf(100), SP_lr1.mulBigInteger(2, 50));
        assertEquals(BigInteger.valueOf(-20), SP_lr1.mulBigInteger(4, -5));
        assertEquals(BigInteger.valueOf(0), SP_lr1.mulBigInteger(0, 5));
        assertEquals(BigInteger.valueOf(0), SP_lr1.mulBigInteger(0, 0));
        assertEquals(BigInteger.valueOf(0), SP_lr1.mulBigInteger(55, 0));
        assertEquals(BigInteger.valueOf(35), SP_lr1.mulBigInteger(-5, -7));
        assertEquals(BigInteger.valueOf(-15), SP_lr1.mulBigInteger(-5, 3));
    }

    @Test
    void testMulLogarithm() {
        assertEquals(20, SP_lr1.mulLogarithm(4, 5));
        assertEquals(-20, SP_lr1.mulLogarithm(4, -5));
        assertEquals(0, SP_lr1.mulLogarithm(0, 5));
        assertEquals(0, SP_lr1.mulLogarithm(6, 0));
        assertEquals(0, SP_lr1.mulLogarithm(0, 0));
        assertEquals(35, SP_lr1.mulLogarithm(-5, -7));
        assertEquals(63, SP_lr1.mulLogarithm(9, 7));
        assertThrows(ArithmeticException.class, () -> SP_lr1.mulLogarithm(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    @Test
    void testMulSquareFormula() {
        assertEquals(28, SP_lr1.mulSquareFormula(4, 7));
        assertEquals(-6000, SP_lr1.mulSquareFormula(300, -20));
        assertEquals(0, SP_lr1.mulSquareFormula(7, 0));
        assertEquals(0, SP_lr1.mulSquareFormula(0, 0));
        assertEquals(0, SP_lr1.mulSquareFormula(0, 320));
        assertEquals(42, SP_lr1.mulSquareFormula(-6, -7));
        assertEquals(63, SP_lr1.mulSquareFormula(9, 7));
        assertThrows(ArithmeticException.class, () -> SP_lr1.mulSquareFormula(Integer.MAX_VALUE, Integer.MAX_VALUE));
        assertThrows(ArithmeticException.class, () -> SP_lr1.mulSquareFormula(Integer.MIN_VALUE, Integer.MIN_VALUE));
    }

    @Test
    void testMulKaratsuba() {
        assertEquals(20L, SP_lr1.mulKaratsuba(4L, 5L));
        assertEquals(-20L, SP_lr1.mulKaratsuba(4L, -5L));
        assertEquals(20L, SP_lr1.mulKaratsuba(-4L, -5L));
        assertEquals(-20L, SP_lr1.mulKaratsuba(-4L, 5L));
        assertEquals(0L, SP_lr1.mulKaratsuba(0L, 5L));
        assertEquals(0L, SP_lr1.mulKaratsuba(8L, 0L));
        assertEquals(0L, SP_lr1.mulKaratsuba(0L, 0L));
        assertEquals(123456789L * 987654321L, SP_lr1.mulKaratsuba(123456789L, 987654321L));
    }

    @Test
    void testMulParallel() throws ExecutionException, InterruptedException {
        assertEquals(20, SP_lr1.mulParallel(4, 5));
        assertEquals(-20, SP_lr1.mulParallel(4, -5));
        assertEquals(0, SP_lr1.mulParallel(0, 5));
        assertEquals(0, SP_lr1.mulParallel(4, 0));
        assertEquals(0, SP_lr1.mulParallel(0, 0));
        assertEquals(35, SP_lr1.mulParallel(-5, -7));
        assertEquals(63, SP_lr1.mulParallel(9, 7));
    }

}
