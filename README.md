# CS-F222-Discrete-Structures-for-Computer-Science

Problem Statement: 
Consider a graph is represented using adjacency matrix,write a program which checks whether the graph is a rooted tree

Explanation:
1. The program is written in Java

2. The input file name and output file name are taken from the command line arguments. The format for running this program from Command Prompt is as follows : 

<executable file name> <input file name> <output file name>
For example -
aq36_2011A1PS485P  testcase1_Q09.txt  output.txt

3. The given text file is converted into a 2 Dimensional array which represents the adjacency matrix.
Then the following 6 conditions were checked to see whether the provided graph is a rooted tree or not.
Whether all the entries are either 0 or 1
Whether all the diagonal elements are 0
Whether there are more than one root which implies there are k- components
Whether the number of vertices = number of edges + 1
Whether there are any lone points
Whether the graph is connected

4. Finally the result is written into the output file in the given format
