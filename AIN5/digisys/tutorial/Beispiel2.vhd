LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY blink IS
   PORT(rst:  IN  std_logic;  -- reset, low active
        clk:  IN  std_logic;  -- clock, rising edge active
        led:  OUT std_logic); -- led, high active
END blink;

ARCHITECTURE verhalten OF blink IS
   
   CONSTANT RSTDEF: std_logic := '0';
   CONSTANT SYSFRQ: real      := 100.0e6;
   CONSTANT LEDFRQ: real      := 1.0;
   CONSTANT CNTMAX: natural   := natural(SYSFRQ/LEDFRQ);
   
   SIGNAL cnt: integer RANGE 0 TO CNTMAX-1;
   
BEGIN

   -- this creates a 1 hy led blink frequency, as the led is on for half 
   -- of the time and off for the other, completing a full cycle in 1 second
   p1: PROCESS (rst, clk) IS
   BEGIN

      -- when rst is 0, the counter resets and the LED turns off
      IF rst=RSTDEF THEN
         cnt <= 0;
         led <= '0';

      ELSIF rising_edge(clk) THEN
         -- MOD is used to limit the counter to the range 0 to CNTMAX-1
         cnt <= (cnt + 1) MOD CNTMAX;
         -- the LED is toggled when the counter reaches half of max
         IF cnt=CNTMAX/2-1 THEN
            led <= '1';
         ELSIF cnt=CNTMAX-1 THEN
            led <= '0';
         END IF;
      END IF;
   END PROCESS;

END verhalten;
