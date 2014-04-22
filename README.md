Preventing-Useless-Checkpoints
==============================

Implementation of Preventing-Useless-Checkpoints using Helary  Algorithm

Implemented as part of Course work Advanced Operating Systems in The University of Texas at Dallas in Spring 2014
Under the prof Neeraj Mittal.

Project Description:
In this project, you have to implement a communication-based distributed algorithm for avoiding
useless checkpoints.
To that end, first implement a distributed application that takes two parameters: mean inter
message transmission time (MTT) and mean inter independent checkpointing time (ICT). The first
parameter denotes the mean time between two successive message transmissions by a node. The second
parameter denotes the mean time between two successive independent checkpoints by a node. Assume
that inter message transmission time at a node is exponentially distributed with mean MTT, and inter
independent checkpointing time is uniformly distributed in the range [0.5 · ICT, 1.5 · ICT]. You can
assume that the communication topology is a complete graph.
Next, implement Helary et al.’s algorithm for preventing a checkpoint from becoming useless, and
evaluate its performance (in terms of number of forced checkpoints) experimentally. This algorithm is
based on logical clock and was discussed in the class.
Measure the variation in the number of forced checkpoints taken by nodes with the increase in:
(i) the mean inter message transmission time, and (ii) the mean inter independent checkpointing time.
Show the results using a graph. Note that you should only vary one parameter at a time. Also, each
data point in a graph should be obtained by averaging over several runs.


Project is entirely designed in the Java
SCTP protocol is used for socket programming
heavy multi threading is used while implementation.

For any more questions or details contact author @ 
Neilesh Chorakhalikar : cns.nilesh@gmail.com
www.neileshc.co.nr
