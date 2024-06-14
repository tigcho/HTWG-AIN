# Questions 

There are now two simulators related to this chapter. The first,
`fork.py`, is a simple tool to show what a process tree looks like
when processes are created and destroyed. Read more about it
[here](README-fork.md).

The second is a program, `generator.py`, that creates real C programs
that use `fork()`, `wait()`, and `exit()` to show how `fork` works in
running programs. Read more about it [here](README-generator.md).

1. Run `./fork.py -s 10` and see which actions are taken. Can you
   predict what the process tree looks like at each step?

Output:
```sh
Process Tree:
                               a

Action: a forks b
                               a
                               └── b
Action: a forks c
                               a
                               ├── b
                               └── c
Action: c EXITS
                               a
                               └── b
Action: a forks d
                               a
                               ├── b
                               └── d
Action: a forks e
                               a
                               ├── b
                               ├── d
                               └── e
```

---------------------------------------

2. Run the simulator with a large number of actions (e.g., `-a 100`) 
   and vary the fork percentage from 0.1 to 0.9. What do you
   think the resulting final process trees will look like as the 
   percentage changes?

- smol number: boring. 
  large number: wow, so much depth! :hearteyes:

--------------------------------------

3. Now, switch the output by using the -t flag (e.g., run `./fork.py -t`). 
   Given a set of process trees, can you tell which actions were taken?

Output:
```sh
Process Tree:
                               a

Action?
a forks b
                               a
                               └── b
Action?
a forks c
                               a
                               ├── b
                               └── c
Action?
c forks d
                               a
                               ├── b
                               └── c
                                   └── d
Action?
a forks e
                               a
                               ├── b
                               ├── c
                               │   └── d
                               └── e
Action?
e forks f
                               a
                               ├── b
                               ├── c
                               │   └── d
                               └── e
                                   └── f
```

-------------------------------------

4. One interesting thing to note is what happens when a child exits;
   what happens to its children in the process tree? To study this, let’s
   create a specific example: `./fork.py -A a+b,b+c,c+d,c+e,c-`.
   This example has process ’a’ create ’b’, which in turn creates ’c’,
   which then creates ’d’ and ’e’. However, then, ’c’ exits. What do
   you think the process tree should like after the exit? What if you
   use the -R flag?

- I assumed they would move up one process but I was wrong. ’d’ and ’e’
  become the children of the initial process ’a’. My assumption is 
  satisfied with the -R flag.

Output with -R flag:
```sh
Process Tree:
                               a

Action: a forks b
                               a
                               └── b
Action: b forks c
                               a
                               └── b
                                   └── c
Action: c forks d
                               a
                               └── b
                                   └── c
                                       └── d
Action: c forks e
                               a
                               └── b
                                   └── c
                                       ├── d
                                       └── e
Action: c EXITS
                               a
                               └── b
                                   ├── d
                                   └── e
```

-----------------------------------

5.  Run `./fork.py -F` and see if you can write down the final 
    tree by looking at the series of actions generated.

Output:
```sh
Process Tree:
                               a

Action: a forks b
Action: b EXITS
Action: a forks c
Action: c EXITS
Action: a forks d

                        Final Process Tree?
			                   a
                               └── d
```

---------------------------------

6. Finally, use both -t and -F together. This shows the final process
   tree, but then asks you to fill in the actions that took place. By looking 
   at the tree, can you determine the exact actions that took place?
   In which cases can you tell? In which can’t you tell?

- You can generally guess the actions which lead to the final tree, but 
  only after assuming that no process exited. If something exits, you can't
  tell if a process forks and exits and forks again.
