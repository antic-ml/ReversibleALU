import java.math.BigDecimal;

/**
 * This is a *reversible* prototype ALU. What that means is, the mathematical
 * operations it performs can be reversed. This is desired, for mathematical
 * reasons. I expect a number of useful, practical applications from this
 * kind of ALU. I suspect that one can use this ALU's architecture to perform
 * massively parallel execution of mathematical operations.
 *
 * To perform computations with sums all the ALU does is *count* out the solution. There
 * is no fancy logic being used here. The internal operation amounts to nothing more
 * than the manipulation of two counters and a register.
 *
 * The accumulator stores the result of the last mathematical operation. In
 * the case of division, any remainder is stored in the remainder register.
 * The A and B registers are loaded with the numbers you wish to perform
 * a mathematical operation on. *Do NOT use them for ANY other purpose.*.
 * The mathematical operations are: ADD, SUBTRACT, DIVIDE and MULTIPLY.
 * Which covers everything you might want to do.
 *
 * There will be NO floating point unit. The decimals will be handled
 * by circuitry that implements fixed arithmetic. If you use floating
 * point, then you deserve everything that happens to you. So I guess
 * there will be a Fixed Arithmetic Unit.
 *
 * The method of computation is very slow, but it is *accurate*
 * and that is the point and you can see the computation working by examining the value
 * of the counters A and B and the accumulator.
 *
 * Load registers A and B with the parameters in the constructor. Create an instance and
 * call run() to see it work.
 *
 * Project Status: Completed.
 *
 * Wednesday, 20th January 2021
 * ----------------------------
 * - Add works forwards and in reverse.
 * - Subtract works forwards and in reverse.
 * - Multiply works forwards and in reverse.
 * - Divide. Hell, this one is hard. Doesn't work yet.
 *
 * Thursday, 21st January 2021
 * ---------------------------
 * - Divide forward works but the output is confusing. The result of a division is given by the
 *      // accumulator and the remainder. Division is problematic and I am going to leave it as
 *      // something for you to work out. I  am not particularly interested in it at the moment.
 * - Divide in reverse. Again, I am leaving this for you to do. Best of luck, because I can't work it out.
 *      // I assume it is just the inverse of a forwards division. Actually, it better had be the inverse.
 *
 * Satan, or what eh? :D
 *
 * @author Mario Gianota 19 January 2021 (gianotamario@gmail.com)
 */
public class ALU implements Runnable {
    int registerA;      // Load this with operand 1
    int registerB;      // Load this with operand 2
    int accumulator = 0;// Holds result of the computation. Contents are over-written by the ALU.

    int counterA;       // Used to perform the computation. Counters are externally read-only.
    int counterB;       // Used to perform the computation. Counters are externally read-only.
    int iteration = 0;  // Holds the number of iterations of the last computation. Externally read-only.

    boolean signFlag;   // The sign of a result. Externally, read-only.
    boolean topHeavy;   // Signals if a sum is top heavy, or not. For example, 8/4 is top heavy 4/8 is not.
    int remainder;      // Holds the remainder, if any of a division.
    int dotPosition;    // The position of the decimal point *after* a division. The position is the number of places to
                        // move left from the right hand-side of the number. E.g, 0.5 is dotPosition 1.

    private static final boolean LOG = true; // Turn on logging to System.out

    /**
     * Make a new ALU that will perform the following operations:
     *  a + b
     *  a - b
     *  a * b
     *  a / b
     *
     * @param a Value of register A
     * @param b Value of register B
     */
    public ALU(int a, int b) {
        registerA = a;
        registerB = b;
    }

    /**
     * Creates a new ALU with internal registers A and B initialized to zero.
     * @see #setRegisterA(int)
     * @see #setRegisterB(int)
     */
    public ALU() {

    }

    /**
     * Set the value of internal register A.
     * @param number
     */
    public void setRegisterA(int number) {
        registerA = number;
    }

    /**
     * Set the value of internal register B.
     *
     * @param number
     */
    public void setRegisterB(int number) {
        registerB = number;
    }

    /**
     * Returns the number held in the accumulator.
     *
     * @return accumulator amount
     */
    public int getAccumulator() {
        return accumulator;
    }

    /**
     * Returns the remainder of a division.
     * @return remainder
     */
    public int getRemainder() {
        return remainder;
    }

    /**
     * Return the position of the decimal point after a division operation. The
     * value is the number of places to move left through the number.
     * @return dot position
     */
    public int getDotPosition() {
        return dotPosition;
    }

    private void out(String s) {
        if( LOG )
            System.out.print(s);
    }

    /**
     * Runs all four mathematical operations forwards and in reverse on the numbers
     * loaded into registers A and B. The output is to the terminal.
     *
     */
    public void run() {
        iteration = 0;
        counterA = registerA;
        counterB = registerB;
        accumulator = 0;
        out("\n");
        out("Performing operation: " + registerA + " + " + registerB + "\n");
        addForwards();

        iteration = 0;
        out("\n");
        out("*** Reversing add operation. ***\n");
        addBackwards();

        iteration = 0;
        //counterA = registerA;
        //counterB = registerB;
        signFlag = registerB > registerA ? true : false;
        accumulator = 0;
        out("\n");
        out("Performing operation: " + registerA + " - " + registerB + "\n");
        subtractForwards();

        iteration = 0;
        //signFlag = false;
        out("\n");
        out("Reversing calculation: " + registerA + " - " + registerB + "\n");
        subtractBackwards();

        iteration = 0;
        accumulator = 0;
        out("\n");
        out("Performing operation: " + registerA + " * " + registerB + "\n");
        multiplyForwards();

        iteration = 0;
        out("\n");
        out("Reversing calculation: " + registerA + " * " + registerB+"\n");
        multiplyBackwards();

        iteration = 0;
        //counterA = registerA;
        //counterB = registerB;

        remainder = 0;
        topHeavy = registerA > registerB ? true : false;
        out("\n");
        out("Performing operation: " + registerA + " / " + registerB + "\n");
        divideForwards();
    }

    private void addForwards() {

        while( true ) {
            iteration++;
            out(iteration + ": " + "Counter A = " + counterA + " Counter B = " + counterB + " Accumulator = " + accumulator + "\n");
            if( counterA != 0 ) {
                accumulator++;
                counterA--;
            }
            if( counterB != 0 ) {
                accumulator++;
                counterB--;
            }


            if( counterA == 0 && counterB == 0) {
                break;
            }
        }

        // print result
        iteration++;
        out(iteration + ": " + "Counter A = " + counterA + " Counter B = " + counterB + " Accumulator = " + accumulator + "\n");
        out("*** Calculation completed in " + iteration + " iterations. ***\n");

    }
    private void addBackwards() {
        while( true ) {
            iteration++;
            out(iteration + ": " + "Counter A = " + counterA + " Counter B = " + counterB + " Accumulator = " + accumulator+"\n");
            if( counterA != registerA ) {
                accumulator--;
                counterA++;
            }
            if( counterB != registerB ) {
                accumulator--;
                counterB++;
            }


            if( counterA == registerA && counterB == registerB) {
                break;
            }
        }

        // print result
        iteration++;
        out(iteration + ": " + "Counter A = " + counterA + " Counter B = " + counterB + " Accumulator = " + accumulator + "\n");
        out("*** Calculation reversed in " + iteration + " iterations. ***\n");

    }

    private void subtractForwards() {

        while( true ) {
            iteration++;
            out(iteration + ": " + "Counter A = " + counterA + " Counter B = " + counterB + " Accumulator = " + accumulator + "\n");
            if( signFlag ) {
                if( counterA != 0) {
                    accumulator--;
                    counterA--;
                }
                if( counterB != 0 ) {
                    accumulator++;
                    counterB--;
                }
                if( counterA == 0 && counterB == 0)
                    break;
            } else {
                if( counterA != 0) {
                    accumulator++;
                    counterA--;
                }
                if( counterB != 0 ) {
                    accumulator--;
                    counterB--;
                }
                if( counterA == 0 && counterB == 0 )
                    break;
            }
        }
        // print result
        iteration++;
        out(iteration + ": " + "Counter A = " + counterA + " Counter B = " + counterB + " Accumulator = " + accumulator + " Sign Flag = " + signFlag + "\n");
        out("*** Calculation completed in " + iteration + " iterations. ***\n");

    }
    private void subtractBackwards() {
        iteration = 0;

        while( true ) {
            iteration++;
            out(iteration + ": " + "Counter A = " + counterA + " Counter B = " + counterB + " Accumulator = " + accumulator + "\n");
            if( signFlag ) {
                if( counterA != registerA) {
                    accumulator++;
                    counterA++;
                }
                if( counterB != registerB ) {
                    accumulator--;
                    counterB++;
                }
                if( counterB == registerB)
                    break;
            } else {
                if( counterA != registerA) {
                    accumulator--;
                    counterA++;
                }
                if( counterB != registerB ) {
                    accumulator++;
                    counterB++;
                }
                if( counterA == registerA)
                    break;
            }
        }
        // print result
        iteration++;
        out(iteration + ": " + "Counter A = " + counterA + " Counter B = " + counterB + " Accumulator = " + accumulator + " Sign Flag = " + signFlag + "\n");
        out("*** Calculation reversed in " + iteration + " iterations ***\n");

    }
    private void multiplyForwards() {
        // Multiplication is repeated addition. Obviously.

        // Heaven help you if you change the contents of register A whilst the ALU is
        // computing a forward multiplication.
        for (int i = 0; i < registerA; i++) {
            counterB = registerB;
            while (true) {
                iteration++;
                out(iteration + ": " + "Counter A = " + counterA + " Counter B = " + counterB + " Accumulator = " + accumulator + "\n");
                if (counterB != 0) {
                    accumulator++;
                    counterB--;
                }


                if (counterB == 0) {
                    break;
                }
            }
        }

        // print result
        iteration++;
        out(iteration + ": " + "Counter A = " + counterA + " Counter B = " + counterB + " Accumulator = " + accumulator + "\n");
        out("*** Calculation completed in " + iteration + " iterations. ***\n");

    }
    private void multiplyBackwards() {
        for (int i = 0; i < registerA; i++) {
            counterB = 0;
            while (true) {
                iteration++;
                out(iteration + ": " + "Counter A = " + counterA + " Counter B = " + counterB + " Accumulator = " + accumulator + "\n");
                if (counterB != registerB) {
                    accumulator--;
                    counterB++;
                }


                if (counterB == registerB) {
                    break;
                }
            }
        }

        // print result
        iteration++;
        out(iteration + ": " + "Counter A = " + counterA + " Counter B = " + counterB + " Accumulator = " + accumulator + "\n");
        out("*** Calculation reversed in " + iteration + " iterations. ***\n");

    }
    private void divideForwards() {
        // Division is repeated subtraction. Obviously.
        counterA = registerA;
        counterB = registerB;
        dotPosition = 0;

        while( counterA < counterB ) {
            for (int j = 0; j < 10; j++) {
                counterA = registerA;
                while (true) {
                    iteration++;
                    out(iteration + ": " + "Counter A = " + counterA + " Counter B = " + counterB + " Accumulator = " + accumulator + "\n");
                    if (counterA != 0) {
                        accumulator++;
                        counterA--;
                    }


                    if (counterA == 0) {
                        break;
                    }
                }
            }

            //}
            counterA = accumulator;
            dotPosition++;
            accumulator = 0;

        }

        // Do division
        while( true ) {
            iteration++;
            out(iteration + ": " + "Counter A = " + counterA +
                    " Counter B = " + counterB +
                    " Accumulator = " + accumulator +
                    " Remainder = " + remainder +
                    " Dot position = " + dotPosition + "\n");
            if( counterA != 0 ) {
                counterA--;
                counterB--;
            }

            if( counterB == 0 ) {
                accumulator++;
                counterB = registerB;
                remainder = counterA;
            }
            if( counterA == 0 ) {
                break;
            }
        }

        // print result
        iteration++;
        out(iteration + ": " + "Counter A = " + counterA +
                " Counter B = " + counterB +
                " Accumulator = " + accumulator +
                " Remainder = " + remainder +
                " Dot position = " + dotPosition + "\n");
        out("*** Calculation completed in " + iteration + " iterations. ***\n");

    }
    public static void main(String[] args) {
        ALU adder = new ALU(4,2);
        adder.run();
    }
}
