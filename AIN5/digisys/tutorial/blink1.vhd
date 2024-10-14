
ENTITY blink1 IS
   PORT(LED: OUT BIT);
END blink1;

ARCHITECTURE verhalten OF blink1 IS
   SIGNAL tmp: BIT := '0';
BEGIN
   
   tmp <= NOT tmp AFTER 500 ms;
   LED <= tmp;

END verhalten;