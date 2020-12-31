


## Task 2: "Modelling and Optimization using OpenDSE"

### Goals of the exercise:

* Getting to know the [OpenDSE](https://github.com/FedorSmirnov89/opendse) optimization framework
* Getting to know OpenDSE's implementation of the system model, as well as convenience functionalities for, e.g., visualization
* Implementing a timing and a cost evaluator and integrating it into an optimization

### Part 2 - Optimized Scheduling of a Distributed  Application

#### Story

The repressive government of Distopistan has decided to improve its grip upon its citizens by acquiring the _Privacy Intrusion Wizard 3000 (PIW3000)_, the latest innovation in surveillance technology. Bob, a simple Distopistan engineer, is charged with finding a scheduling of the PIW3000 with the available resources, which combines a short makespan (to quickly detect subversive elements) with low costs for the Distopistan elite. Should he fail, Bob has to fear dire consequences. Can you help him find a good scheduling?

#### Use Case Description

The specification of the PIW3000 is provided in the file _specs/piw3000.xml_.

*Application*

After recording and subsequently preprocessing the video, an object recognition algorithm detects all cars and people present in the current frame. For each detected car, the PIW3000 runs an additional algorithm to interpret its licence plate and then queries the vehicle data base to find out whether the car owner is wanted by the authorities. Similarly, each image part with a detected person is used as input for a facial recognition algorithm to then query an identity data base. At the end of this procedure, the PID3000 decides whether there is cause to trigger an alarm or not. 

*Architecture*

For the implementation of the PID3000, Bob can choose between a smart and a normal camera, which can be connected to a router either via cable or via a wireless connection. Moreover, the tasks of the PID3000 can be implemented on different types of resources offered by 2 different cloud providers.

#### (Anti-immersive) Task Notes

Element Properties:

For a more convenient access to the relevant element properties, the project features a dependency to _PropertyProviderStatic.jar_ (whose name is **definitely not** a foreshadowing of a future task). This library contains the _PropertyService.java_, which can be used to access the properties of the specification elements, and the _PropertyProviderStatic.java_, which can be used to query other relevant parameters. You can find the source code for the _PropertyService.java_ and the interface for the _PropertyProviderStatic.java_ in the sources folder of the project.


Makespan Calculation:

- You can assume that exactly one resource type is chosen for every task
- Due to Distopistan's backwardness, their cloud technology is not yet mature, so that only a limited number of instances is available for each resource type (it can be obtained via the PropertyProvider), restricting the potential for task parallelization.
- The tasks annotated with _ITERATIVE\_PEOPLE_ or _ITERATIVE\_CARS_ have to be executed once for each instance of the respective object in the processed frame (this number can also be obtained from the PropertyProvider)

Cost Calculation:

- Fog and Edge resources are bought (cost = prize), while cloud resources are leased (cost = prize * time).

#### Task Objectives:

- Complete the provided skeletton code to implement a makespan and costs optimization of the provided application.
- Run the optimization and discuss the obtained results. How does the most expensive/fastest solution look like? Does it make sense? 




