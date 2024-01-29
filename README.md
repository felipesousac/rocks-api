
# Engenharia de software - Desafio Claudio Felipe Leite Sousa

### Problema a ser resolvido

O usuário da aplicação precisa simplificar o cálculo de média dos alunos de sua turma com base em seus critérios de aprovação. As seguintes regras com base em suas médias finais devem ser respeitadas:

* Média < 5 - **Reprovado por nota**
* Média <= 5 < 7 - **Exame final**
* Média >= 7 - **Aprovado**
* Número de faltas > 25% de aulas - **Reprovado por falta**

Além das regras de nota, as seguintes condições devem ser respeitadas: 

* Caso a situação do aluno seja diferente de "Exame Final", preencha o campo "Nota para  Aprovação Final" com 0.
* Arredondar o resultado para o próximo número inteiro (aumentar) caso necessário.
