
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
USE ieee.std_logic_unsigned.ALL;

ENTITY std_counter IS
   GENERIC(RSTDEF: std_logic := '1';
           CNTLEN: natural   := 4);
   PORT(rst:   IN  std_logic;  -- reset,           RSTDEF active
        clk:   IN  std_logic;  -- clock,           rising edge
        en:    IN  std_logic;  -- enable,          high active
        inc:   IN  std_logic;  -- increment,       high active
        dec:   IN  std_logic;  -- decrement,       high active
        load:  IN  std_logic;  -- load value,      high active
        swrst: IN  std_logic;  -- software reset,  RSTDEF active
        cout:  OUT std_logic;  -- carry,           high active
        din:   IN  std_logic_vector(CNTLEN-1 DOWNTO 0);
        dout:  OUT std_logic_vector(CNTLEN-1 DOWNTO 0));
END std_counter;

--
-- Functiontable
-- rst clk swrst en  load dec inc | Aktion
----------------------------------+-------------------------
--  V   -    -    -    -   -   -  | cnt := 000..0, asynchronous reset
--  N   r    V    -    -   -   -  | cnt := 000..0, synchronous reset
--  N   r    N    0    -   -   -  | no change
--  N   r    N    1    1   -   -  | cnt := din, parallel load
--  N   r    N    1    0   1   -  | cnt := cnt - 1, decrement
--  N   r    N    1    0   0   1  | cnt := cnt + 1, increment
--  N   r    N    1    0   0   0  | no change
--
-- Legend:
-- V = valid, = RSTDEF
-- N = not valid, = NOT RSTDEF
-- r = rising egde
-- din = data input of the counter
-- cnt = value of the counter
--

ARCHITECTURE std_counter_behaviour OF std_counter IS
    CONSTANT overflow: std_logic_vector(CNTLEN-1 DOWNTO 0) := (OTHERS => '1');
    CONSTANT underflow: std_logic_vector(CNTLEN-1 DOWNTO 0) := (OTHERS => '0');
    SIGNAL cnt: std_logic_vector(CNTLEN-1 DOWNTO 0) := (OTHERS => '0');
    SIGNAL carry_out: std_logic := '0';
BEGIN
    PROCESS (rst, clk)
    BEGIN
        -- Asynchronous reset
        IF rst = RSTDEF THEN
            cnt <= (OTHERS => '0');
            carry_out <= '0';
        ELSIF rising_edge(clk) THEN
            -- Synchronous reset
            IF swrst = RSTDEF THEN
                cnt <= (OTHERS => '0');
                carry_out <= '0';
            ELSIF en = '1' THEN
                -- Load value
                IF load = '1' THEN
                    cnt <= din;
                    carry_out <= '0';
                -- Decrement
                ELSIF dec = '1' THEN
                    IF cnt = underflow THEN
                        cnt <= (OTHERS => '1');  -- Wrap around
                        carry_out <= '1';
                    ELSE
                        cnt <= cnt - 1;
                        carry_out <= '0';
                    END IF;
                -- Increment
                ELSIF inc = '1' THEN
                    IF cnt = overflow THEN
                        cnt <= (OTHERS => '0');  -- Wrap around
                        carry_out <= '1';
                    ELSE
                        cnt <= cnt + 1;
                        carry_out <= '0';
                    END IF;
                END IF;
            END IF;
        END IF;
    END PROCESS;

    -- Output assignments
    dout <= cnt;
    cout <= carry_out;

END std_counter_behaviour;