LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
USE ieee.std_logic_unsigned.ALL;

ENTITY hex4x7seg IS
   GENERIC(RSTDEF: std_logic := '0');
   PORT(rst:   IN  std_logic;                       -- reset,           active RSTDEF
        clk:   IN  std_logic;                       -- clock,           rising edge
        data:  IN  std_logic_vector(15 DOWNTO 0);   -- data input,      active high
        dpin:  IN  std_logic_vector( 3 DOWNTO 0);   -- 4 decimal point, active high
        ena:   OUT std_logic_vector( 3 DOWNTO 0);   -- 4 digit enable  signals,                active high
        seg:   OUT std_logic_vector( 7 DOWNTO 1);   -- 7 connections to seven-segment display, active high
        dp:    OUT std_logic);                      -- decimal point output,                   active high
END hex4x7seg;

ARCHITECTURE struktur OF hex4x7seg IS
   CONSTANT reg: std_logic_vector := "11111111111111"; -- initial value for the counter 
   SIGNAL cnt: std_logic_vector(13 DOWNTO 0);          -- modulo-2**14 counter (16383)
   SIGNAL en: std_logic := '0';                        -- enable signal for the 4-digit display

   CONSTANT N: integer := 4;
   SIGNAL cnt4: integer RANGE 0 TO N-1 := 0;           -- modulo-4 counter
BEGIN

   -- Modulo-2**14-Zaehler
   -- use LFSR like in lecture slides
   p1: PROCESS(rst, clk) IS
   BEGIN
      IF rst = RSTDEF THEN
         cnt <= (OTHERS => '0');
         en <= '0';
      ELSIF rising_edge(clk) THEN
         IF cnt = (OTHERS => '0') THEN
            cnt <= reg;
         ELSE

            -- Polynom: x^14 + x^13 + x^12 + x^2 + 1 
            -- shift the entire register by one position to the right
            cnt(13 DOWNTO 1) <= cnt(12 DOWNTO 0);
            -- calculate the new value for the leftmost bit
            cnt(0) <= cnt(13) XOR cnt(12) XOR cnt(11) XOR cnt(1);

         if cnt = reg THEN
            en <= '1';
         ELSE
            en <= '0';
         END IF;
      END IF;
   END PROCESS p1;
   
   -- Modulo-4-Zaehler
   p2: PROCESS(rst, clk) IS
   BEGIN
      IF rst = RSTDEF THEN
         cnt4 <= 0;
      ELSIF rising_edge(clk) THEN -- clk switches from 0 to 1
         IF en = '1' THEN         -- only keep counting if en is set
            IF cnt = N-1 THEN     -- if cnt is 3, reset 
               cnt <= 0;
            ELSE
               cnt <= cnt + 1;
            END IF;
         END IF;
      END IF;
   END PROCESS p2;

   -- 1-aus-4-Dekoder als Phasengenerator
       
   -- 1-aus-4-Multiplexer

   -- 7-aus-4-Dekoder

   -- 1-aus-4-Multiplexer

END struktur;
