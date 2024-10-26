
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
   SIGNAL counter: std_logic_vector(13 DOWNTO 0) := (OTHERS => '0');
   SIGNAL mod4_counter: std_logic_vector(1 DOWNTO 0) := (OTHERS => '0');
   SIGNAL phase: std_logic_vector(3 DOWNTO 0) := (OTHERS => '0');
   SIGNAL mux_out: std_logic_vector(3 DOWNTO 0);
   SIGNAL seg_out: std_logic_vector(7 DOWNTO 1);
BEGIN

   -- Modulo-2**14-Zaehler
   process(clk, rst)
   begin
      if rst = RSTDEF then
         counter <= (OTHERS => '0');
      elsif rising_edge(clk) then
         if counter = "11111111111111" then
            counter <= (OTHERS => '0');
         else
            counter <= counter + 1;
         end if;
      end if;
   end process;
   
   -- Modulo-4-Zaehler
   process(clk, rst)
   begin
      if rst = RSTDEF then
         mod4_counter <= (OTHERS => '0');
      elsif rising_edge(clk) then
         if mod4_counter = "11" then
            mod4_counter <= (OTHERS => '0');
         else
            mod4_counter <= mod4_counter + 1;
         end if;
      end if;
   end process;

   -- 1-aus-4-Dekoder als Phasengenerator
   process(mod4_counter)
   begin
      case mod4_counter is
         when "00" => phase <= "0001";
         when "01" => phase <= "0010";
         when "10" => phase <= "0100";
         when "11" => phase <= "1000";
         when others => phase <= "0000";
      end case;
   end process;
       
   -- 1-aus-4-Multiplexer
   process(mod4_counter, data)
   begin
      case mod4_counter is
         when "00" => mux_out <= data(3 DOWNTO 0);
         when "01" => mux_out <= data(7 DOWNTO 4);
         when "10" => mux_out <= data(11 DOWNTO 8);
         when "11" => mux_out <= data(15 DOWNTO 12);
         when others => mux_out <= (OTHERS => '0');
      end case;
   end process;

   -- 7-aus-4-Dekoder
   process(mux_out)
   begin
      case mux_out is
         when "0000" => seg_out <= "1111110"; -- 0
         when "0001" => seg_out <= "0110000"; -- 1
         when "0010" => seg_out <= "1101101"; -- 2
         when "0011" => seg_out <= "1111001"; -- 3
         when "0100" => seg_out <= "0110011"; -- 4
         when "0101" => seg_out <= "1011011"; -- 5
         when "0110" => seg_out <= "1011111"; -- 6
         when "0111" => seg_out <= "1110000"; -- 7
         when "1000" => seg_out <= "1111111"; -- 8
         when "1001" => seg_out <= "1111011"; -- 9
         when "1010" => seg_out <= "1110111"; -- A
         when "1011" => seg_out <= "0011111"; -- b
         when "1100" => seg_out <= "1001110"; -- C
         when "1101" => seg_out <= "0111101"; -- d
         when "1110" => seg_out <= "1001111"; -- E
         when "1111" => seg_out <= "1000111"; -- F
         when others => seg_out <= "0000000"; -- default
      end case;
   end process;

   seg <= seg_out;

END struktur;