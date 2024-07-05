# Projeto de Inteligência Artificial: Resolvendo o Problema do Triplo com IDA*

Este projeto foi desenvolvido no âmbito da disciplina de Inteligência Artificial, onde utilizei o algoritmo IDA* para resolver um problema específico: calcular o custo mínimo para transformar um número \( x \) no seu triplo utilizando apenas três operações:
- Somar 1 (custo 1)
- Subtrair 1 (custo 2)
- Multiplicar por 2 (custo 3)

## Descrição do Problema

O objetivo do problema é determinar o custo mínimo para transformar um número \( x \) no seu triplo utilizando as operações descritas. 

## Implementação do Algoritmo IDA*

O algoritmo IDA* (Iterative Deepening A*) foi escolhido para resolver este problema devido à sua capacidade de encontrar soluções ótimas em problemas de busca com custos, utilizando um processo de busca em profundidade iterativo combinado com uma função heurística para estimar o custo mínimo restante até a solução.

## Como Utilizar

Para utilizar o projeto:
- Insira o número \( x \) como entrada para o programa.
- O programa calculará e imprimirá o custo mínimo para transformar \( x \) no seu triplo, junto com a sequência de operações necessárias.

## Tecnologias Utilizadas

- Linguagem de Programação: Java
- Algoritmo: IDA*
