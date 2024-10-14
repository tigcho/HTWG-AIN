LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY beispiel2_tb IS
   -- empty
END beispiel2_tb;

ARCHITECTURE verhalten OF beispiel2_tb IS

   CONSTANT RSTDEF: std_logic := '0';
   CONSTANT FRQDEF: real := 100.0e6;
   CONSTANT tcyc: time :=  1 sec / FRQDEF;

   COMPONENT blink IS
      PORT(rst:  IN  std_logic;  -- reset, low active
           clk:  IN  std_logic;  -- clock, rising edge active
           led:  OUT std_logic); -- led, high active
   END COMPONENT;

   SIGNAL rst: std_logic := RSTDEF;
   SIGNAL clk: std_logic := '0';
   SIGNAL led: std_logic := '0';

BEGIN
   rst <= RSTDEF, NOT RSTDEF AFTER 100 ns;
   clk <= not clk AFTER tcyc/2;
   
   dut: blink
   PORT MAP(rst => rst,
            clk => clk,
            led => led);

END verhalten;