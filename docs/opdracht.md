6 DECEMBER 2018

De context van de opdracht is een Java AWT/Swing/FX applicatie die in staat is om getallen te sorteren met behulp van verschillende algoritmes, namelijk:
* Bubble sort (https://en.wikipedia.org/wiki/Bubble_sort)
* Quick sort (https://en.wikipedia.org/wiki/Quicksort)
* Optioneel: een derde algoritme naar keuze.

Het doel is om deze algoritmes geparalleliseerd uit te voeren met behulp van een instelbaar aantal threads.

Visualiseer een lijst van X getallen (bijv: 100) die gesorteerd moeten worden. Het ontwerp van de UI is vormvrij. 
Zorg ervoor dat de gebruiker de gebruikte sorteermethode kan instellen, alsmede het aantal threads waarmee gesorteerd wordt. 
Simuleer verder dat de benodigde cpu-tijd voor het vergelijken van twee getallen wordt verlengd met een instelbaar aantal milliseconden 
(Maak gebruik van Thread.Sleep()).

Zorg ervoor dat de gebruiker visuele output krijgt tijdens het sorteren, maar waak ervoor dat niet alle cpu tijd gaat zitten in het updaten van UI. Dit kun je doen door gedurende het sorteren op gezette tijden een refresh uit te voeren (bijv: 10x per seconde).

Schrijf een unit test die valideert dat alle getallen die initieel in de lijst stonden er naar het sorteren nog steeds in staan, en schrijf een unit test die valideert dat de lijst na het sorteren daadwerkelijk gesorteerd is. Zorg ervoor dat de applicatie meerdere keren achter elkaar kan worden uitgevoerd.

Opleveren doe je in source code vorm, alsmede een JAR met daarin alle benodigdheden om te kunnen runnen.

Samengevat:

Sorteeralgoritmes:
1. bubblesort, 2. quicksort, 3. optional

Inputs:
* type algoritme
* aantal threads
* variabele sleep time

2 unit tests:
* verify that sorting works
* verify that input array contains same numbers as output array

opleveren: code + jar