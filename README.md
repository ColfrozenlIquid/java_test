
# Java_Test Answers

The task is to develop a set of classes for recursively scanning a directory, its subdirectories and included files. The sizes, dimensions and compressions rates are to be computed and stored (in memory) for all directories and picture files. For directories it must be possible to compute the sum of the file sizes in the directory and its subdirectories, the average image dimensions and average compression rates.



## Screenshots

Below is a screenshot of sample output of running the program in its home directory that contains various fies and subfolders to show that recursion works


![App Screenshot](https://i.imgur.com/lyVh6pj.png)


## Optimizations
Compared to the initial commit of the program, varies improvements have been made in the way the program is designed from the ground up. The main method has been reduced to a bare minimum and new classes have been introduced to improve scalability and readability. Enumerations and Interfaces have been introduced to avoid duplicate methods.

Below is the initial Data structure concept involving nodes and inheritance
![App Screenshot](https://i.imgur.com/H0szBW4.png)

[Initial Commit]

Nodes are of the same (TreeNode) Type. This limits the amount of abstraction and inheritance, as different nodes cannot be of inherited types.

A better solution would be to rewrite the TreeNode model whereby all Nodes are their own Types and Implement a more abstract TreeNode model, such that all nodes implement the TreeNode functionality while maintaining their own Data Types. This would allow for calling more abstract methods on the whole Model like printData(), which would call a custom method on all Node types to allow for better expandability.

[Updated Commit]

Initially all nodes were of the same TreeNode type. Now all nodes have their own concrete class that inherits certain traits from an abstract Node class and implement an INode interface. This allows for storing various different Objects in each node and expands the functionality that can be added to the model

![App Screenshot](https://i.imgur.com/ytxIPRd.png)

## Lessons Learned

Creating this project I learned a great deal about Java in general. I gained greater insight into  recursion, Graph and Tree Data models, Bytestreams, working with File Standards and Inheritance in OOP.

