# ReversibleALU
An ALU that can reverse the operation of the basic mathematical operators.

 This is a *reversible* prototype ALU. What that means is, the mathematical
 operations it performs can be reversed. This is desired, for mathematical
 reasons. I expect a number of useful, practical applications from this
 kind of ALU. I suspect that one can use this ALU's architecture to perform
 massively parallel execution of mathematical operations.
 
 To perform computations with sums all the ALU does is *count* out the solution. There
 is no fancy logic being used here. The internal operation amounts to nothing more
 than the manipulation of two counters and a register.
 
 The accumulator stores the result of the last mathematical operation. In
 the case of division, any remainder is stored in the remainder register.
 The A and B registers are loaded with the numbers you wish to perform
 a mathematical operation on. *Do NOT use them for ANY other purpose.*.
 The mathematical operations are: ADD, SUBTRACT, DIVIDE and MULTIPLY.
 Which covers everything you might want to do.
 
 There will be NO floating point unit. The decimals will be handled
 by circuitry that implements fixed arithmetic. If you use floating
 point, then you deserve everything that happens to you. So I guess
 there will be a Fixed Arithmetic Unit.
 
 The method of computation is very slow, but it is *accurate*
 and that is the point and you can see the computation working by examining the value
 of the counters A and B and the accumulator.
 
 Load registers A and B with the parameters in the constructor. Create an instance and
 call run() to see it work.

 # Project Status: Completed.
 
 ## Wednesday, 20th January 2021
 
 - Add works forwards and in reverse.
 - Subtract works forwards and in reverse.
 - Multiply works forwards and in reverse.
 - Divide. Hell, this one is hard. Doesn't work yet.

 ## Thursday, 21st January 2021
 - Divide forward works but the output is confusing. The result of a division is given by the
   accumulator and the remainder. Division is problematic and I am going to leave it as
   something for you to work out. I  am not particularly interested in it at the moment.
- Divide in reverse. Again, I am leaving this for you to do. Best of luck, because I can't work it out.
  I assume it is just the inverse of a forwards division. Actually, it better had be the inverse.

Satan, or what eh? :D

@author Mario Gianota 19 January 2021 (gianotamario@gmail.com)
