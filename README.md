Computer Simulation Workshop 01
==============

Workshop 01 of Class Computer Simulation - March 2017

Questionary
----------------------------

### Generation Pseudo-Random Number 

Implement the minimum standard generator (GEM), seen in class. Don't use generators provides by the chossen programming language.

### Goodness of fit 

With the GEM crated in the previoud point generate two files:

- GEM1000.txt 1000 generated pseudo-random numbers.
- GEM10000.txt 10000 generated pseudo-random numbers.

* Implement the χ² test and Poker 2 and 3 decimales test with confidence α = 0,1. In the execution the table must be shown, 
where indicated:
	* Class
	* EF (Expeted Frequency)
	* OF (Observed Frequency)
	* Error ((EF-OF)²/EF)

Apply to both cases. In total there are 3 tests for 1000 and 10000 data.

Select another generator: that of your chossen programming language or an external database and Analyze the results by comparing them.


Development Information
----------------------------
This Workshop was developed in Java, in NetBeans IDE 8.0.2 in Windows and Linux


How to Run
----------------------------
Open the project in NetBeans and uncomment the lines in the main function for execute.
The function that execute everithing is: 

gem.printInformation(a,b,c);

where:
* a is the test that you want to run (1: Chi Squared, 2: Poker 2, 3: Poker 3)
* b is the generator that you want to use (0: GEM, 1: Java)
* c is the number of pseudo-random numbers that you want to generate (1.000 or 10.000)


Info
----------------------------
[GitHub Repository](https://github.com/geralch/2017-03_computer_simulation_workshop01)
| [GitHub Profile](https://github.com/geralch)
| [Linkeid](https://www.linkedin.com/in/geraldinecaicedohidalgo)
| [ResearchGate](https://www.researchgate.net/profile/Geraldine_Caicedo)
| [Twitter](https://twitter.com/chougeral)
| [WebPage](https://agilenerdnote.tumblr.com/)