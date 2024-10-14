LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY blink IS
   PORT(rst:  IN  std_logic;  -- reset, low active
        clk:  IN  std_logic;  -- clock, rising edge active
        led:  OUT std_logic); -- led, high active
END blink;

ARCHITECTURE verhalten OF blink IS
   
   CONSTANT RSTDEF: std_logic := '0';
   CONSTANT CNTMAX1: natural  :=  5000;
   CONSTANT CNTMAX2: natural  := 10000;
   
   SIGNAL dff1: std_logic;
   SIGNAL dff2: std_logic;
   SIGNAL cnt1: integer RANGE 0 TO CNTMAX1-1;
   SIGNAL cnt2: integer RANGE 0 TO CNTMAX2-1;

BEGIN
   
   led <= dff2;
      
   p1: PROCESS (rst, clk) IS
   BEGIN
      IF rst=RSTDEF THEN
         dff1 <= '0';
         cnt1 <= 0;
      ELSIF rising_edge(clk) THEN
         dff1 <= '0';
         IF cnt1=CNTMAX1-1 THEN
            dff1 <= '1';
            cnt1 <= 0;
         ELSE
            cnt1 <= cnt1 + 1;
         END IF;
      END IF;
   END PROCESS;

   p2: PROCESS (rst, clk) IS
   BEGIN
      IF rst=RSTDEF THEN
         dff2 <= '0';
         cnt2 <= 0;
      ELSIF rising_edge(clk) THEN
         IF dff1='1' THEN
            IF cnt2=CNTMAX2-1 THEN
               dff2 <= not dff2;
               cnt2 <= 0;
            ELSE
               cnt2 <= cnt2 + 1;
            END IF;
         END IF;
      END IF;
   END PROCESS;

END verhalten;