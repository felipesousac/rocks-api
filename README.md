
# Engenharia de software - Desafio Claudio Felipe Leite Sousa

## Problema a ser resolvido

O usuário da aplicação precisa acessar uma planilha no Google Sheets e simplificar o cálculo de média dos alunos de sua turma com base em seus critérios de aprovação. As seguintes regras com base em suas médias finais devem ser respeitadas:

* Média < 5 - **Reprovado por nota**
* Média <= 5 < 7 - **Exame final**
* Média >= 7 - **Aprovado**
* Número de faltas > 25% de aulas - **Reprovado por falta**

Além das regras de nota, as seguintes condições devem ser respeitadas: 

* Caso a situação do aluno seja diferente de "Exame Final", preencha o campo "Nota para  Aprovação Final" com 0.
* Arredondar o resultado para o próximo número inteiro (aumentar) caso necessário.

## Resoluções

No meu entendimento do problema, há duas formas de resolver essa tarefa, uma utilizando a tecnologia nativa do Google Sheets, o Apps Script. E a segunda é integrando a Google Sheets Api com uma aplicação escrita em uma linguagem de minha escolha, a escolhida para este desafio foi Java.

### Solução com Apps Script

Primeiro entre na planilha pública contendo os dados dos alunos.

* [A planilha pode ser acessada por esse link](https://docs.google.com/spreadsheets/d/1YLp32Pl0S9jdgSuag3jnKnN_LN8MKezK5fMjjrB2vx4/edit#gid=0) 

Após acessar a planilha:

* Na aba extensões, acessar "Apps Script"
* Na nova janela que será aberta, entrar na opção "Editor"
* Certifique-se que o arquivo "basics.gs" esteja selecionado
* Clique em executar

Após a execução do mesmo, volte para a aba da planilha e veja as situações dos alunos inseridas dinamicamente pelo script.
