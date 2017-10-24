Compile all java files.<br>
```javac *.java```

The Connect Four program has several different command-line arguments that you can use to control how the game is played. By default, the two players are human-controlled. You can choose which AI modules to use by using the `-p1` and `-p2` options to select the AIModules to use as the first and second player. For example, to let the `minimax` player against the `alphabeta` player, you could use:<br>
```java Main -p1 minimax -p2 alphabeta```
