# CYKAlgorithm
This is a CYK parsing algorithm to check if an string can be generated.
It makes use of gramar tables.

The first input asks you how many productions the gramar will have (Integer).

Then, you have to write the string to be verified if it can be produced.

Followed by asking you for the productions (the ammount you specified on the firs input), which has to be writen in the normal Chomsky form, like S->AB, S->a, S->A, S->AB|a.

You can see the CYK table generated for the algorythm in the console.


**Example:**

Ammount of productions (Cantidad de producciones): 4

String to evaluate (Cadena a evaluar): baaba

Productions
<ol>
<li>S->AB|BC</li>
<li>A->BA|a</li>
<li>B->CC|b</li>
<li>C->AB|a</li>
</ol>

That generates the following CYK table:
|0|1|2|3|4|
|---|---|---|---|---|
|B|AS| | |SAC|
||AC|B|B|SCA|
|| |AC|SC|B|
|| | |B|AS|
|| | | |AC|

And prints the following parse tree:

![Parse tree img](https://github.com/MiltonEBR/CYKAlgorithm/blob/main/Screenshot_128.png)

