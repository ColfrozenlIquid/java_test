
# Java_Test Answers

The task is to develop a set of classes for recursively scanning a directory, its subdirectories and included files. The sizes, dimensions and compressions rates are to be computed and stored (in memory) for all directories and picture files. For directories it must be possible to compute the sum of the file sizes in the directory and its subdirectories, the average image dimensions and average compression rates.



## Screenshots

Below is a screenshot of sample output of running the program in its home directory


![App Screenshot](https://i.imgur.com/aVlL0PJ.png)


## Optimizations

Currently the code is not set up very well, even though it manages to achieve its intended function.

Currently scalability is a main concern as adding new functionality would require refactoring multiple methods.


![App Screenshot](https://i.imgur.com/H0szBW4.png)

Currently all nodes are of the same (TreeNode) Type. This limits the amount of abstraction and inheritance, as different nodes cannot be of inherited types.

A better solution would be to rewrite the TreeNode model whereby all Nodes are their own Types and Implement a more abstract TreeNode model, such that all nodes implement the TreeNode functionality while maintaining their own Data Types. This would allow for calling more abstract methods on the whole Model like printData(), which would call a custom method on all Node types to allow for better expandability.

![App Screenshot](https://i.imgur.com/UPPRPj2.png)

Furthermore the expandability is pretty tedious as custom methods have to de developed for reading out data from various file types, like PNG files for example. Implementing already existing frameworks for this Task would greatly reduce code clutter and improve code safety and occurance of potential errors while reading bytestreams.
## Lessons Learned

Creating this project I learned a great deal about Java in general. I gained greater insight into  recursion, Graph and Tree Data models, Bytestreams, working with File Standards and Inheritance in OOP.

