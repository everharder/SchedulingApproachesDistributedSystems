


## Task 4: "Application Dynamism"

### Goals of the exercise:

* Getting to know the impact that application dynamism has on scheduling


### Story

Due to the commitment of Distopistan's resistance (and, in particular their extremely well-designed and secure communication infrastrucutre), its repressive government is finally overthrown, ushering in a time of prosprerity and freedom for Distopistan (which is renamed in Freedom Republic (FreeRep)).
Happy with the outcome and enthusiastic for the new possibilities, Bob returns to his passion of orchestrating distributed applications. Just at this moment, FreeRep's democratically elected government decides, with broad approval of the people, to install the _Life Improvement Wizard 4000 (LIW4000)_, the latest innovation in technology for Big Data aggregation and personalized life-improvement applications (incidentally, it is offered by the same company as the PIW3000, but there is definitely no connection at all between the two products). 

After getting an initial impression for the task, Bob quickly realizes that the orchestration task he is facing now is significantly different from the tasks he was dealing with in Distopistan: After the abandonment of Distopistan's predictable (although ineffective) planned economy and repressive rules for public places, FreeRep's exhibits a booming economy and a vibrant nightlife, so that it becomes impossible to plan with the fix predictions for parameters such as the number of people in a video frame. With more and more startups switching to cloud services, it is also no longer possible to assume a fix number of available cloud resources or freedom from interference on the communication links.

Can you help Bob investigate the implications of these changes and adjust the scheduling to the new situation?


### Use Case Description

The specification of the LIW4000 is provided in the file _specs/liw4000.xml_.

*Application and Architecture*

Upon requesting the documents describing the details of the LIW4000 application, the its developers inform Bob that, due to the high demand for the product, they unfortunately currently cannot provide him with its manual. The good news, however, is that, although being not related to each other at all, the PIW3000, on a very abstract level, offers enogh similarity to the LIW4000 to use the same manual he used for the orchestration of the PIW3000. 

(4th-wall-breaking-bottom-line: you can use the same description of the architecture and application as in the second part of Task 2. The only thing which has changed is the Property provider, which now provides different property values upon each request.)



### Task Notes

- All assumptions from the second part of Task 2 still apply
- The project of Task 4 also has the same structure w.r.t. the configuration and launch files as the project in Task 2.

### Task Objectives:

- Transfer the evaluators created for Task 2 into the project you are using for Task 4
  DONE
- Make sure that the evaluator does not break in the new situation (it should, e.g., be able to handle situations where a parameter like the number of recognized cars is 0)
  DONE
- Are the results of the optimization performed with this evaluator valid/useful? Why?/Why not?
  YES, if you include the additions from the next objective (assuming that the currency FreeRep Library is widely accepted)
  otherwise the evaluations reassemble mere snapshots, because the number of recognized people, cars and available cloud-resource instances changes randomly. 
- Adjust the evaluators so that they provide a more reliable evaluation of the (a) expected costs and the (b) worst-case makespan.
  DONE
  Although this dramatically increases the optimizers runtime, the evaluated results have much higher quality.
  Another approach would have been to capture some numbers for recognized people / cars and available cloud-resources during the first generation and using them throughout (in order to have fairer evaluation),
  but the chosen solution is closer to real-life application and therefore preferable (in my opinion).
  
 




