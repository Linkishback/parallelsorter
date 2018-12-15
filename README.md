# Parallel Sorter
Parallel Sorter is een applicatie die ik heb geschreven in opdracht van Hogeschool Inholland. Met deze applicatie kunnen verschillende sorteeralgoritmes worden uitgevoerd op een variabel aantal threads.

![alt text](https://i.imgur.com/YXH4fIq.gif)

## Features
* Sorteert een array van ints. (size: 10 of 100)
* Met de eerste slider kan het aantal threads worden ingesteld.
* Met de tweede slider kan de delay ingesteld worden (milliseconds). 
* Met Start-knop start het gekozen sorteeralgoritme, deze kan vervolgens gestopt worden met de Stop-knop.
* Met de Shuffle-knop word de huidige array geshuffeld.
* Met de fixed array knoppen kan de lengte van de array worden ingesteld. Ook zullen deze knoppen altijd dezelfde array terug geven. (handig voor benchmarks) 

## Algoritmes
* Bubble sort (Odd–even sort)
* Quick sort
* Merge Sort

## Gebouwd met
* IntelliJ Community 2018.3
* JDK 11
* JUnit 5.3

## Bronnen
* Algorithmist - sorting algorithms: http://www.algorithmist.com/index.php/Sorting
* slideshow about parallel sorting: https://www.slideshare.net/GARIMASHAKYA1/parallel-sorting-algorithms
* java multithreading: https://www.tutorialspoint.com/java/java_multithreading.htm
* Zie de map docs voor nog meer bronmateriaal.