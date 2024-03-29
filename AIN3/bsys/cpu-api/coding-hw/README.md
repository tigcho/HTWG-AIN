# Questions

1. Write a program that calls fork(). Before calling fork(), have the
   main process access a variable (e.g., x) and set its value to some-
   thing (e.g., 100). What value is the variable in the child process?
   What happens to the variable when both the child and parent change
   the value of x?

- They have the same initial value, but change to different values after.
  This is because the child process gets a copy of the parent process and
  they are independent of each other. 

```sh
Parent process before changing x: 100
Parent process after changing x: 300
Child process before changing x: 100
Child process after changing x: 200
```

---------------------------------

2.  Write a program that opens a file (with the open() system call)
    and then calls fork() to create a new process. Can both the child
    and parent access the file descriptor returned by open()? What
    happens when they are writing to the file concurrently, i.e., at the
    same time?

- Both of them can access the file descriptor returned by open(). When they
  write to the file concurrently, the output can't be predetermined. This is because
  they are independent of each other and the OS scheduler decides which process
  to run at a given time.

---------------------------------

3. Write another program using fork(). The child process should print
   "hello"; the parent process should print "goodbye". You should try to
   ensure that the child process always prints first; can you do this
   without calling wait() in the parent?

- Make the parent process sleep for a while before printing "goodbye" (?)

---------------------------------

4. Write a program that calls fork() and then calls some form of exec()
   to run the program /bin/ls. See if you can try all of the variants of
   exec(), including execl(), execle(), execlp(), execv(), execvp(), and
   execvpe(). Why do you think there are so many variants of the same
   basic call?

- Look them up [here](https://www.man7.org/linux/man-pages/man3/exec.3.html).
  They are almost identical, but their arguments differ from each other.

---------------------------------

5. Now write a program that uses wait() to wait for the child process
   to finish in the parent. What does wait() return? What happens if
   you use wait() in the child?

- If I use it in the child, it returns `-1` because there is no child process
  to wait for. In the parent process, it returns the child's PID.

---------------------------------

6. Write a slight modification of the previous program, this time 
   using waitpid() instead of wait(). When would waitpid() be
   useful?

- It's useful when you want to wait on a child process with a specific PID.

---------------------------------

7. Write a program that creates a child process, and then in the child
   closes standard output (STDOUT FILENO). What happens if the child
   calls printf() to print some output after closing the descriptor?

- Nothing happens.
