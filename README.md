Hierarchy Adapter Kata
=============

Problem description
-------------
Given two "parallel" type hierarchies, write a conversion layer. The provided implementation which is inspired by multiple real life exemples has several problems :

- The adapter ( EventDomainFactory ) forces its users to work with the root type of each hierarchies only, if a usage needs a more precise type, a specific implementation will have to be added
- The visitor pattern is implemented in a way which creates a circular dependency between the factory package and the persistent package
- The visitor's code is not very DRY : 12 methods named visit (4 in EventModelVisitor, 4 in EventEntityFactory, 4 in EventModelFactory) with only type variations

Not very satisfying.

The event hierarchy is specially designed to show various problems in adaptation layers: 

- it has multiple levels of abstract types and concrete types at the same level as abtract types (Event, Sms, Xmpp)
- it has concrete types extending other concrete types (Event...File, Event...Audio)
- transformation of some of the concrete types require additional dependencies which are not shared by the rest of the transformations. 

Entry points to the code 
-------------

I suggest you start by having a look at `factory.EventDomainFactoryTest` and work to fix the TODOs I have placed all over the code. You may want to explore both hierarchies the two roots are : `api.event.domain.Event` and `persistent.EventEntity`

The transformation from EventEntityFile to EventModelFile requires a custom helper. In this exercise the helper can seem contrived but it represents constraints I have seen occur in projects before. 