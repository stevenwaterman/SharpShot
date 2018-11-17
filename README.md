# sharpshot
An esoteric visual programming language developed by Steven Lowes, James Chalk, and Ivan Donat Pupovac.

Name is a reference to C# and shot, as in bullets being shot.

Put Nodes on a two-dimensional grid to represent constants, branching and conditionals, whereas integers being shot and moved around are the arguments.

# Nodes we have got

- IO - sends out a stream of the input given by use; prints output
- Arithmetic - add, subtract, multiply or divide most recent two numbers (divide does first / second)
- Branching - control bullet direction, create more copies
- Conditionals - change bullet direction if true (>0 and =0)
- Random - output a random number value from 0 to incoming bullet's value
