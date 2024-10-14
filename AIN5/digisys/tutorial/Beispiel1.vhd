-- blink2 is the name of the entity
-- Entities are the top level of a VHDL file
-- They define the inputs and outputs
ENTITY blink2 IS

   -- the entity has one output called LED
   -- it is a single bit output of type bit
   PORT(LED: OUT BIT);
END blink2;

-- the architecture is the implementation of the entity
ARCHITECTURE verhalten OF blink2 IS
BEGIN
   
   PROCESS BEGIN
      -- this is an infinite loop
      LOOP
         LED <= '0';
         WAIT FOR 500 ms;
         LED <= '1';
         WAIT FOR 500 ms;
      END LOOP;
   END PROCESS;

END verhalten;
