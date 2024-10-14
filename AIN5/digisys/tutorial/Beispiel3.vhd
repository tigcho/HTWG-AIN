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
   -- cntmax is only half of the period, because the led is toggled 
   -- at the end of the period
   CONSTANT CNTMAX: natural   := natural(SYSFRQ/LEDFRQ)/2;
   
   SIGNAL cnt: integer RANGE 0 TO CNTMAX-1;
   SIGNAL dff: std_logic;

BEGIN

   -- uses a signal to assign to led outside of the process
   led <= dff;

   p1: PROCESS (rst, clk) IS
   BEGIN
      IF rst=RSTDEF THEN
         cnt <= 0;
         dff <= '0';
      ELSIF rising_edge(clk) THEN
         IF cnt=CNTMAX-1 THEN
            dff <= NOT dff;
            cnt <= 0;
         ELSE
            cnt <= cnt + 1;
         END IF;
      END IF;
   END PROCESS;

END verhalten;
