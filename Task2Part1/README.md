


## Task 2: "Modelling and Optimization using OpenDSE"

### Goals of the exercise:

* Getting to know the [OpenDSE](https://github.com/FedorSmirnov89/opendse) optimization framework
* Getting to know OpenDSE's implementation of the system model, as well as convenience functionalities for, e.g., visualization
* Implementing a timing and a cost evaluator and integrating it into an optimization

### Part 0.1 - Setup.

1. Clone the exercise project from its GitHub Repository
2. Build the project---i.e. download the project dependencies defined in the  _build.gradle_  file and compile the project---using gradle:

> `gradle build`  

3. (Optional, in case you use Eclipse) Configure the Eclipse project using gradle:  

> `gradle eclipse`
        
### Part 0.2 - OpenDSE Tutorial.
1. Read the sections 1. through 3. of the [OpenDSE tutorial](http://opendse.sourceforge.net/documentation/1.7/tutorial.xhtml).
 Try to understand how the concepts discussed in the lecture (in particular the system model) are implemented within the framework.
2. Familiarize yourself with the Java classes used for the implementation of a Specification.

_Note:_ The exercise project contains the specification files used for the tutorial in the _specs_ folder, as well as a configuration for running the first step of the tutorial (_configs/tutorial.xml_) and a launch file to start the framework with this configuration (_launches/openDseTutorial.launch_). You can run it by right-clicking the launch file and choosing the _Run as -> openDseTutorial_ option. In order to use the specifications corresponding to the other steps of the tutorial, please adjust the file path in the _Input_ module of the configuration.

### Part 1 - Modeling an orchestration problem.
1. Review the description of the customer monitoring system provided in the lecture script (Lecture 1 pp. 11-14) for details concerning the application and the infrastructure of the application. 

2. Implement the methods for the creation of the specification components within the class  _SpecificationGenerator.java_ (package _at.uibk.dps.dsB.task2.part1_), so that its _generate()_ method returns a specification modeling the customer monitoring application from the lecture.

3. Have a look at the class _ShopMonitoringSpecification.java_ class (package _at.uibk.dps.dsB.task2.part1_) and use it to visualize the graph of the specification created in the previous step, store the specification as a file. Try to load a specification file as a Java Specification object.


