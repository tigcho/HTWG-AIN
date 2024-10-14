LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
USE work.lfsr_lib.ALL;

ENTITY blink IS
  PORT(rst:  IN  std_logic;  -- reset, low active
       clk:  IN  std_logic;  -- clock, rising edge active
       led:  OUT std_logic); -- led, high active
END blink;

ARCHITECTURE verhalten OF blink IS
   
  CONSTANT RSTDEF: std_logic := '0';  
  CONSTANT LENDEF: natural   := 4;
  CONSTANT POLY:   std_logic_vector(LENDEF DOWNTO 0) := "10011"; -- Polynom: x^4 + x^1 + 1
 
   SIGNAL ref: std_logic_vector(LENDEF-1 DOWNTO 0);
   SIGNAL reg: std_logic_vector(LENDEF-1 DOWNTO 0);
   
BEGIN

   led <= '1';
      
   PROCESS (rst, clk) IS
   BEGIN
      IF rst=RSTDEF THEN
         ref <= (OTHERS => '1');
      ELSIF rising_edge(clk) THEN
         ref    <= lfsr(ref, POLY, '0');
      END IF;
   END PROCESS;

   PROCESS (rst, clk) IS
   BEGIN
      IF rst=RSTDEF THEN
         reg <= (OTHERS => '1');
      ELSIF rising_edge(clk) THEN
         reg(3) <= reg(2);
         reg(2) <= reg(1);
         reg(1) <= reg(0) XOR reg(3);
         reg(0) <= reg(3);
      END IF;
   END PROCESS;
   
END verhalten;
