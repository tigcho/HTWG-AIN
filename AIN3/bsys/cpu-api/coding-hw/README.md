# Questions

1. Write a program that calls fork(). Before calling fork(), have the
   main process access a variable (e.g., x) and set its value to some-
   thing (e.g., 100). What value is the variable in the child process?
   What happens to the variable when both the child and parent change
   the value of x?

- Parent process before changing x: 100
  Parent process after changing x: 300
  Child process before changing x: 100
  Child process after changing x: 200

  They have the same initial value, but change to different values after.

---------------------------------

2. 
