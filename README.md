Hierarchy Adapter Kata
=============

Problem description
-------------
Given two "parallel" type hierarchies, write a conversion layer.

The event hierarchy is specially designed to show various problems in adaptation layers:

- it has multiple levels of abstract types and concrete types at the same level as abtract types (Event, Sms, Xmpp)
- it has concrete types extending other concrete types (Event...File, Event...Audio)
- transformation of some of the concrete types requires additional dependencies which are not shared by the rest of
the transformations.

I will add various solutions in branches, I suggest you do not look at the solutions before having thought about the
problem yourself.

Requirements
-------------

To compile and run this kata, you will need at least

* Java 7+
* Maven
* An internet connection for the initial dependency download


Contributing
-------------

Feel free to contribute, either by
* submitting a pull request to improve the kata starting point (EventStore would be
a good place to start ;) )
* submitting a pull request to improve an existing solution
* by opening an issue with a link to a solution branch that you developed and would like to see included in the
repository. Such solution branches will be reviewed before being integrated.