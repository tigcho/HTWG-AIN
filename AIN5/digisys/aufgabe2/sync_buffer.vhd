LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
USE ieee.std_logic_unsigned.ALL;

ENTITY sync_buffer IS
    GENERIC(RSTDEF:  std_logic := '1');
    PORT(rst:    IN  std_logic;  -- reset, RSTDEF active
        clk:    IN  std_logic;  -- clock, rising edge
        en:     IN  std_logic;  -- enable, high active
        swrst:  IN  std_logic;  -- software reset, RSTDEF active
        din:    IN  std_logic;  -- data bit, input
        dout:   OUT std_logic;  -- data bit, output
        redge:  OUT std_logic;  -- rising  edge on din detected
        fedge:  OUT std_logic); -- falling edge on din detected
END sync_buffer;

ARCHITECTURE sync_buffer_behavior OF sync_buffer IS
    SIGNAL buffer1, buffer2, buffer3: std_logic;
    -- SIGNAL counter: std_logic_vector(14 DOWNTO 0);
    SIGNAL din_reg: std_logic_vector(4 DOWNTO 0);
    SIGNAL din_filtered: std_logic;
    SIGNAL en_buffer: std_logic;

BEGIN
    -- Two D-Flip-Flops in series
    PROCESS (clk, rst)
    BEGIN
        IF rst = RSTDEF THEN
            buffer1 <= '0';
            buffer2 <= '0';
        ELSIF rising_edge(clk) THEN
            IF swrst = RSTDEF THEN
                buffer1 <= '0';
                buffer2 <= '0';
            ELSE
                buffer1 <= din;
                buffer2 <= buffer1;
            END IF;
        END IF;
    END PROCESS;

    -- Hysteresis
    PROCESS (clk, rst)
    BEGIN
        IF rst = RSTDEF THEN
            din_reg <= (OTHERS => '0');
            din_filtered <= '0';
            en_buffer <= '0';
        ELSIF rising_edge(clk) THEN
            IF swrst = RSTDEF THEN
                din_reg <= (OTHERS => '0');
                din_filtered <= '0';
                en_buffer <= '0';
            ELSE
                IF (en = '1') AND (en_buffer = '1') THEN
                    en_buffer <= '0';
                    --din_reg <= din_reg(30 DOWNTO 0) & buffer2;
                    --IF (din_reg(31 DOWNTO 16) = x"FFFF") THEN
                    --    din_filtered <= '1';
                    --ELSIF (din_reg(31 DOWNTO 16) = x"0000") THEN
                    --    din_filtered <= '0';
                    --END IF;
                    IF buffer2 = '1' THEN
                        din_reg <= din_reg + 1;
                    ELSE 
                        din_reg <= din_reg - 1;
                    END IF;
                    IF din_reg = "11111" THEN
                        din_reg <= "11110";
                        din_filtered <= '1';
                    ELSIF din_reg = "00000" THEN
                        din_reg <= "00001";
                        din_filtered <= '0';
                    END IF;
                ELSIF (en = '0') THEN
                    en_buffer <= '1';
                END IF;
            END IF;
        END IF;
    END PROCESS;

    -- Edge detection
    PROCESS (clk, rst)
    BEGIN
        IF rst = RSTDEF THEN
            buffer3 <= '0';
        ELSIF rising_edge(clk) THEN
            IF swrst = RSTDEF THEN
                buffer3 <= '0';
            ELSE
                buffer3 <= din_filtered;
            END IF;
        END IF;
    END PROCESS;

    dout <= din_filtered;
    redge <= din_filtered AND NOT buffer3;
    fedge <= NOT din_filtered AND buffer3;

END sync_buffer_behavior;