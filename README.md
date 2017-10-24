Compile all java files.<br>
```javac *.java```

The Connect Four program has several different command-line arguments that you can use to control how the game is played. By default, the two players are human-controlled. You can choose which AI modules to use by using the `-p1` and `-p2` options to select the AIModules to use as the first and second player. For example, to pit the `minimax` player against the `alphabeta` player, you could use:<br>
```java Main -p1 minimax -p2 alphabeta```
Any unspecified players will be filled in with human players.

You can also customize how much time is available to the AI players. By default, each computer has 500 ms to think and this is what we'll use in the competition. However, you can use the `-t` option to change this. Use the `--help` option to learn more about the options available to you.
