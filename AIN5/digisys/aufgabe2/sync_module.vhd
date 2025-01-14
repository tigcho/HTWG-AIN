
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
USE ieee.std_logic_unsigned.ALL;

ENTITY sync_module IS
   GENERIC(RSTDEF: std_logic := '1');
   PORT(rst:   IN  std_logic;  -- reset, active RSTDEF
        clk:   IN  std_logic;  -- clock, risign edge
        swrst: IN  std_logic;  -- software reset, active RSTDEF
        BTN1:  IN  std_logic;  -- push button -> load
        BTN2:  IN  std_logic;  -- push button -> dec
        BTN3:  IN  std_logic;  -- push button -> inc
        load:  OUT std_logic;  -- load,      high active
        dec:   OUT std_logic;  -- decrement, high active
        inc:   OUT std_logic); -- increment, high active
END sync_module;

ARCHITECTURE sync_module_behavior OF sync_module IS
    COMPONENT sync_buffer IS
        GENERIC(RSTDEF: std_logic := '1');
        PORT(
            rst:    IN  std_logic;
            clk:    IN  std_logic;
            en:     IN  std_logic;
            swrst:  IN  std_logic;
            din:    IN  std_logic;
            dout:   OUT std_logic;
            redge:  OUT std_logic;
            fedge:  OUT std_logic
        );
    END COMPONENT;

    SIGNAL slow_clk: std_logic;
    SIGNAL counter: std_logic_vector(14 DOWNTO 0);
    SIGNAL load_sync, dec_sync, inc_sync: std_logic;
    SIGNAL load_redge, dec_redge, inc_redge: std_logic;
    SIGNAL load_fedge, dec_fedge, inc_fedge: std_logic;
BEGIN

    -- Clock divider
    PROCESS (clk, rst)
    BEGIN
        IF rst = RSTDEF THEN
            counter <= (OTHERS => '0');
            slow_clk <= '0';
        ELSIF rising_edge(clk) THEN
            IF counter = "111111111111111" THEN
                counter <= (OTHERS => '0');
                slow_clk <= NOT slow_clk;
            ELSE
                counter <= counter + 1;
            END IF;
        END IF;
    END PROCESS;

    sync_buffer_load: sync_buffer
        GENERIC MAP (
            RSTDEF => RSTDEF
        )
        PORT MAP (
            rst => rst,
            clk => clk,
            en => slow_clk,
            swrst => swrst,
            din => BTN1,
            dout => load_sync,
            redge => load_redge,
            fedge => load_fedge
        );

    sync_buffer_dec: sync_buffer
        GENERIC MAP (
            RSTDEF => RSTDEF
        )
        PORT MAP (
            rst => rst,
            clk => clk,
            en => slow_clk,
            swrst => swrst,
            din => BTN2,
            dout => dec_sync,
            redge => dec_redge,
            fedge => dec_fedge
        );

    sync_buffer_inc: sync_buffer
        GENERIC MAP (
            RSTDEF => RSTDEF
        )
        PORT MAP (
            rst => rst,
            clk => clk,
            en => slow_clk,
            swrst => swrst,
            din => BTN3,
            dout => inc_sync,
            redge => inc_redge,
            fedge => inc_fedge
        );

    load <= load_redge;
    dec  <= dec_fedge;
    inc  <= inc_fedge;

END sync_module_behavior;