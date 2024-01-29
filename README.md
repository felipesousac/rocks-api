
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
* Certifique-se que o arquivo "Exodia.gs" esteja selecionado
* Clique em executar

Após a execução do mesmo, volte para a aba da planilha e veja as situações dos alunos inseridas dinamicamente pelo script.

### Solução com Java/Spring Boot

Antes de prosseguir, apagar os dados inseridos pela solução anterior para averiguarem o funcionamento dessa solução.

Pelas regras de autenticação da própria Api da Google, eu preciso adicionar manualmente os e-mails permitidos para concluirem com sucesso a autenticação necessária do teste dessa aplicação. Então antes de completarem os requisitos abaixo, preciso de um e-mail para liberar o acesso de vocês.

Pré-requisitos: Java 17 e Gradle

```bash
# Clonar repositório
git clone git@github.com:felipesousac/rocks-api.git

# Certificar que está dentro da pasta clonada e executar o projeto
gradlew bootRun

# Após executar o projeto no servidor local, acessar a seguinte url
http://localhost:8080/data
```

Essa url irá enviar uma requisição do tipo GET a Api escrita em java que está integrada ao Google Sheets Api, fazendo com que a planilha contendo os dados dos aluno seja editada pela própria aplicação.

Voltando para a planilha os dados já estarão atualizados conforme as regras do desafio.

#### Considerações dessa solução

A camada Controller da aplicação faz uma requisição do tipo GET ao ser acessado o endereço "/data", nessa requisição é chamada a camada Service, que por sua vez, faz chamada com a camada Utils da aplicação, essa camada conta com os métodos de autenticação, leitura e gravação de dados encontrados na [documentação do Google Sheets Api](https://developers.google.com/sheets/api/guides/concepts?hl=pt-br), além do mais o método que calcula as médias dos alunos também se encontra nesse arquivo.

Ao ler a descrição do desafio com o link contendo a documentação da Google, acreditei que foi um convite para ser desenvolvida uma aplicação fora da plataforma do Google Sheets, assim, essa resolução "extra" foi implementada.
