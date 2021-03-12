# Wikipaths
This is a project from my CS201 Data Structures class. The project uses several algorithms to find the shortest path through wikipedia pages. (For example, how many clicks does it take to get from the Duran Duran wikipedia page to the Canada Goose wikipedia page?). 

### Instructions
Run the PathFinder file. 
- First compile: `javac PathFinder.java`. 
- Then, there are two ways to run the program. 
  - The first is `java PathFinder articles.tsv links.tsv startVertex endVertex`. 
  - The second is `java PathFinder articles.tsv links.tsv startVertex intermediateVertex endVertex`. 
- When entering articles (vertices) of more than one word, surround the word with quotations and replace spaces with underscores. For example, I would enter `java PathFinder articles.tsv links.tsv "Duran_Duran" "Canada_Goose"`.

Note: if running on repl.it, the Run button is configured not to work, must enter the commands manually.

[![Run on Repl.it](https://repl.it/badge/github/Ave-Wat/Wikipaths)](https://repl.it/github/Ave-Wat/Wikipaths)
