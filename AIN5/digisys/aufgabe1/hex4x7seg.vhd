LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
USE ieee.numeric_std.ALL;

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
   SIGNAL counter: std_logic_vector(13 DOWNTO 0);
--   SIGNAL mod4_counter: std_logic_vector(1 DOWNTO 0);
   SIGNAL phase: std_logic_vector(3 DOWNTO 0);
   SIGNAL mux_out: std_logic_vector(3 DOWNTO 0);
   SIGNAL seg_out: std_logic_vector(7 DOWNTO 1);
   SIGNAL dp_out: std_logic_vector(3 DOWNTO 0);
   SIGNAL en: std_logic;
   SIGNAL carry_out: std_logic;
   signal prev_carry_out: std_logic;
   CONSTANT N: integer := 4;
   TYPE segment_table is array (0 to 15) of std_logic_vector(7 downto 1);
   constant SEGMENTS: segment_table := (
      "0111111", -- 0
      "0000110", -- 1
      "1011011", -- 2
      "1001111", -- 3
      "1100110", -- 4
      "1101101", -- 5
      "1111101", -- 6
      "0000111", -- 7
      "1111111", -- 8
      "1101111", -- 9
      "1110111", -- A
      "1111100", -- b
      "0111001", -- C
      "1011110", -- d
      "1111001", -- E
      "1110001"  -- F
   );

BEGIN

   -- Modulo-2**14-Zaehler
   process(clk, rst)
   begin
      if rst = RSTDEF then
         counter <= (OTHERS => '0');
         carry_out <= '0';
      elsif rising_edge(clk) then
         counter <= std_logic_vector(unsigned(counter) + 1);
         carry_out <= counter(13);
         prev_carry_out <= carry_out;
      end if;
   end process;

   en <= carry_out and not prev_carry_out; -- Use the MSB as enable signal
   
   -- Modulo-4-Zaehler
--   process(clk, rst)
--   begin
--      if rst = RSTDEF then
--         mod4_counter <= "00";
--      elsif rising_edge(clk) then
--         mod4_counter <= std_logic_vector(unsigned(mod4_counter) + 1);
--      end if;
--   end process;


   -- 1-aus-4-Dekoder als Phasengenerator
   process(clk, rst)
   begin
      if rst = RSTDEF then
         phase <= "0000"; -- No digit lit during reset
      elsif rising_edge(clk) then
         if en = '1' then
            if phase = "0000" then
               phase <= "0001"; -- Start cycling when enabled
            else
               phase <= phase(2 downto 0) & phase(3); -- Rotate left
            end if;
         end if;
      end if;
   end process;

       
   -- 1-aus-4-Multiplexer
   mux_out <= data(3 downto 0) when phase = "0001" else
      data(7 downto 4) when phase = "0010" else
      data(11 downto 8) when phase = "0100" else
      data(15 downto 12) when phase = "1000" else
      (OTHERS => '0');

   dp <= dp_out(0) when phase = "0001" else
      dp_out(1) when phase = "0010" else
      dp_out(2) when phase = "0100" else
      dp_out(3) when phase = "1000" else
      '0';
      
   -- 7-aus-4-Dekoder
   process(mux_out)
   begin
      seg_out <= SEGMENTS(to_integer(unsigned(mux_out)));
   end process;

   -- Decimal Point
--  process(clk)
--   begin
--      for i in 0 to 3 loop
--         if dpin(i) = '1' then
--            dp_out(i) <= '1';
--         else
--            dp_out(i) <= '0';
--         end if;
--      end loop;
--   end process;

   dp_out <= dpin;
            
   ena <= phase;
   seg <= seg_out;

END struktur;