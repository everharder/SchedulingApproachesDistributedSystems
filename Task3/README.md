


## Task 3: "Definition of Optimization Constraints"

### Goals of the exercise:

* Defining simple SAT constraints
* Getting to know OpenDSE's constraint definition interface

### Part 0 - Setup

* Clone (or pull) the [GitHub repository](https://github.com/uibk-dps-teaching/SchedulingApproachesDistributedSystems) storing the exercise tasks
* Run the command _gradle build eclipse_ from within the _Task 3_ directory to build the project and set up the Eclipse workspace

### Part 1 - Example of optimization constraints

The _example_ package contains an implementation of the constraint example used in the lecture. The definition of the constraints is done in the class _ExampleMappingEncoding.java_. The example can be run by running the _launches/exampleLaunch.launch_ file.

* Have a look at the way the constraint definition works
* Run the example launch with different settings for the used constraints in the _ExampleEncoding_ module and examine how the constraints affect the optimization
 
### Part 2 - Defining mapping constraints

#### Story

Disillusioned with Distopistan's authoritary system, Bob joins the resistance. Due to his competence with orchestration, his task there is to map different resistance applications to the available cloud and edge resources. Since the government has a firm grip on Distopsitan's cloud infrastructure, it is hereby particularly important to make sure that the secret tasks of the resistance run only on the more secure (yet also more expensive) edge devices. 

#### Use Case Description

*Specification*

In order to practice for his new task, Bob has impemented a program to provide him with different problem specifications (used by adding the _HomeworkSpec_ module to the exploration). With this, it is possible to easily create different problem specifications.

*Evaluation*

Due to the resource scarcity of the resistance, it is important to find ways to perform their tasks cost-efficiently. Since, at least at the beginning, Bob wants to mainly concentrate on ensuring the secrecy of the tasks, a simplistic cost evaluator will suffice.

*Constraints*

Following constraints must be enforced to create valid orchestrations:

* Any task annotated as a secret must not be executed on cloud resources
* If two tasks exchange messages and are both annotated as secrets, they both must be executed within the same region
* Due to their restricted capacity, at most 2 tasks can be executed on a single edge resource
* Each task has to be mapped onto at least one resource

#### Task Objectives:

- Implement the constraints given above as evaluators (within the class _HwConstraintEvaluator_; use the launch file _hwEvaluatorLaunch.launch_).
- Complete the provided code of the _HomeworkMappingEncoding.class_ to formulate the SAT constraints enforcing mappings which are valid w.r.t. the above description (launch file: _homeworkLaunch.launch_).
- Compare the ways the optimization runs when the constraints are implemented as part of the evaluation on the one and as SAT constraints on the other hand. How do the different solutions scale with an increasing problem size?




