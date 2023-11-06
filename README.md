![Fiapark](https://github.com/samuelclinton/fiapark/blob/main/img/pill.png)

O projeto Fiapark proposto para a pós-graduação [FIAP](https://www.fiap.com.br/) em [Arquitetura e desenvolvimento Java](https://postech.fiap.com.br/curso/arquitetura-desenvolvimento-java) consiste em uma API de gerenciamento de estacionamentos de uma cidade fictícia. A aplicação como um todo é composta por essa API e dois microsserviços, um de gerenciamento e recibos e outro de notificação, que se comunicam de maneira assíncrona via mensageria utilizando o RabbitMQ. O projeto roda totalmente dockerizado em containers e pode ser executado utilizando o docker compose para testes e avaliações.

## Índice

1. [Tecnologias](#tecnologias)
2. [Ferramentas](#ferramentas)
3. [Instalação](#instalação)
5. [Documentação](#documentação)
6. [Jornada de desenvolvimento](#jornada)
7. [Autores](#autores)
8. [Licença](#licença)

## Tecnologias

As tecnologias utilizadas durante o desenvolvimento do projeto.

#### [Java 17](https://docs.oracle.com/en/java/javase/17/docs/api/)
Linguagem de programação escolhida para implementar a lógica do sistema, garantindo confiabilidade, segurança e escalabilidade.

#### [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
Utilizado como framework para agilizar o desenvolvimento da API, oferecendo recursos como injeção de dependências, mapeamento de URLs, tratamento de requisições HTTP e muito mais.

#### [Docker](https://www.docker.com/docs/)
Plataforma que facilita a criação, implantação e execução de aplicativos em contêineres. Com o Docker, é possível empacotar a aplicação e suas dependências em um contêiner para fácil distribuição e implantação.

#### [RabbitMQ](https://www.rabbitmq.com/documentation.html)
Plataforma de mensageria de código aberto altamente escalável e robusta, utilizada para comunicação assíncrona entre diferentes partes de um sistema distribuído. O RabbitMQ oferece suporte a filas de mensagens, garantindo a entrega confiável e a comunicação entre os componentes do sistema.

#### [MySQL](https://dev.mysql.com/doc/)
Banco de dados utilizado para armazenar e gerenciar as informações relacionadas a eletrodomésticos, endereços e pessoas. O MySQL é conhecido por sua confiabilidade e ampla utilização, oferecendo suporte para aplicações de grande porte.

#### [Maven](https://maven.apache.org/guides/index.html)
Gerenciador de dependências utilizado para facilitar a configuração e o gerenciamento das bibliotecas e dependências do projeto.

#### [Git](https://git-scm.com/doc)
Sistema de controle de versão utilizado para rastrear alterações no código-fonte, facilitando o trabalho em equipe, o versionamento e a colaboração no projeto.

#### [Lombok](https://projectlombok.org/features/)
Biblioteca que permite reduzir a quantidade de código boilerplate, como getters, setters e construtores, através de anotações, melhorando a produtividade do desenvolvimento.

#### [ModelMapper](https://modelmapper.org/getting-started/)
Biblioteca utilizada para mapear automaticamente objetos de uma classe para outra, facilitando a conversão de DTOs (Data Transfer Objects) para entidades e vice-versa.

## Ferramentas

#### [Intellij IDEA](https://www.jetbrains.com/pt-br/idea/)
Ambiente de desenvolvimento integrado (IDE) utilizado para escrever, depurar e testar o código-fonte do projeto. O Intellij IDEA oferece recursos avançados de produtividade, facilitando o desenvolvimento em Java e agilizando o processo de construção da API.

#### [Postman](https://www.postman.com/)
Plataforma de colaboração e testes de API que permite enviar e receber solicitações HTTP, testar os endpoints da API, verificar as respostas e realizar a depuração de forma eficiente.

#### [GitHub](https://github.com/)
Plataforma de hospedagem de repositórios de controle de versão Git, utilizada para armazenar e gerenciar o código-fonte do projeto. O GitHub permite o trabalho colaborativo, controle de versões, rastreamento de alterações e facilita a integração com ferramentas de desenvolvimento.

## Instalação
Estes são os passos para a execução do projeto num ambiente local, utilizando o docker compose.
1. Clone este repositório git na sua máquina local
   ``` 
   git clone https://github.com/samuelclinton/fiapark.git
   ```
   
2. Caso o Docker não esteja instalado no seu computador, siga os passos da [documentação oficial](https://docs.docker.com/engine/install/).
   
4. Não é necessário fazer a build de cada imagem dos serviços, eles já estão disponíveis no Docker Hub, porém caso queira, cada diretório tem seu Dockerfile que pode ser usado para criar uma imagem com o seguinte comando:
   ``` 
   docker build -t [nome-da-imagem] [caminho-ate-Dockerfile]
   ```
   
   É recomendado seguir o padrão de nome fiapark-api, fiapark-recibos e fiapark-notificacoes, pois caso altere, o arquivo `compose.yml` precisará ser alterado também.
   
6. Abra um Powershell (ou equivalente no seu OS) no diretório que o arquivo `compose.yml` estiver localizado e execute o comando:
   ``` 
   docker compose up -d
   ```
   
7. Aguarde alguns segundos para que todos os containers sejam executados e após aguardar pode se verificar se os containers estão rodando com o comando:
   ``` 
   docker container ls -a
   ```
   
8. Com todos os serviços rodando o endereço das APIs relevantes serão `localhost:8080` para a fiapark-api e `localhost:8081` para o fiapark-recibos, porém as requisições do arquivo postman desse repositório já deve estar configuradas para que todos os testes possam ser feitos.
   
10. Para o teste de notificações por e-mail, saiba que eles serão enviados 15 minutos antes do término para o caso de estacionamentos do tipo FIXO, e para os tipos DINAMICO e FIXO assim que o status passar para FINALIZADO um recibo também será enviado por e-mail, então é recomendado cadastrar um e-mail válido para os testes.

## Documentação
A documentação no padrão Open Api 3 (Swagger) estará disponível para consulta assim que os containers estiverem sendo executados corretamente no endereço `/docs.html` de ambos os serviços fiapark-api e fiapark-recibos.

## Jornada

A jornada dessa fase do Tech Challenge começou com uma mudança na API que ao meu ver foi positivo, pois realmente nos força a não fazer tanto refactor e pensar mais na disciplina da fase, pra mim isso serviu muito bem e posso dizer que a fase teve um efeito grande em como vou desenvolver daqui pra frente, pensando em containers e sistemas distribuidos sempre que viável e aplicável.

Comecei fazendo um monolito que funcionasse de acordo com as funções necessárias, e com ele funcionando pude ver o domínio da aplicação clararemente e verificar que algumas partes podiam funcionar separadamente e de forma assíncrona.
Como o principal requisito dessa fase era escalabilidade, monolito surgiram os 3 serviços desse repositório: fiapark-api (gerenciamento dos estacionamentos, condutores e veículos), fiapark-recibos (gerenciamento dos recibos, que eu vejo como a parte fiscal no caso e gostei de deixar isso num banco separado) e fiapark-notificacoes (gerenciamento de notificacoes para os condutores).

Agora com três serviços, sugiu a necessidade de comunicação entre eles, e escolhi utilizar mensageria ao invés de comunicação direta via HTTP. Isso evitaria por exemplo a perda de mensagens e falhas devido a indisponibilidade de um serviço ou outro.
No caso dos recibos, pensando como um documento fiscal (tratei os recibos como se fosse uma nota fiscal simplificada) escolhi usar o RabbitMQ por conta da grande quantidade de documentação disponível e fácil integração com Spring Boot.
Pra esse projeto aprendi e implementei também o conceito de Dead Letter Queue, que é uma fila separada para mensagens que não tiveram sucesso no processamento e precisam ser avaliadas e se for o caso, reintegradas na fila principal. O que deu mais segurança ainda pra mensagens importantes, como a de salvar o recibo.

Com a comunicação resolvida, parti pra persistência de dados e escolhi continuar com bancos de dados relacionais para o gerenciamento de estacionamentos, condutores e recibos.
Tomei essa decisão pela confiabilidade e integridade que um banco SQL oferece. Achei que ainda não fazia sentido utilizar NoSQL nesse projeto (seria só por usar).

Pra solucionar o envio de notificações por e-mail inicialmente escolhi o AWS SES, porém com a burocracia de configurar o domínio de envio (é necessário um domínio personalizado, que eu até configurei para o meu pessoal samuelclinton.com) porém vieram outras dificuldades, como o fato de estar em modo sandbox e somente enviar e-mails pra destinatários confirmados, sendo necessário solicitar acesso a produção. Resolvi implementar o envio de e-mails utilizando SMTP usando o suporte do Spring Boot pra e-mails e criar também uma conta do gmail somente pra isso.

Com os serviços desenvolvidos, eu os Dockerizei individualmente e criei o arquivo compose.yml que iria subir tudo que fosse necessário pra aplicação rodar, para meus proprios testes e futuramente para a correção do Tech Challenge. Porém surgiram erros de inicialização devido a ordem de execução e dependências que eu resolvi com uma ferramenta ótima chamada [docker-compose-wait](https://github.com/ufoscout/docker-compose-wait), que funcionou maravilhosamente bem pra definir a dependência entre os containers e até a múltiplos containers.

Finalmente, com tudo funcionando, disponibilizei as imagens dos serviços em repositórios no [Docker Hub](https://hub.docker.com/u/samuelclinton) pra facilitar a subida do projeto com apenas um comando `compose up`, sem necessidade de buildar cada serviço manualmente. Deixei assim mesmo os `Dockerfile` para referência e pra caso seja necessário gerar uma nova imagem de algum dos serviços novamente.

No fim, decidi por manter tudo rodando localmente pois não me senti confortável em deixar uma aplicação complexa rodando na AWS com meu cartão de crédito atrelado, por tempo indeterminado, pois não conseguiria garantir a data de correção do projeto. E como a API não contava com nenhum sistema anti-abusos, os serviços poderiam sair da cota do free-tier ou gerar grandes custos devido a quantidade de processamento.
No cenário que idealizei, esse seria o fluxo simplificado da implantação na AWS.

![Fluxo AWS](https://github.com/samuelclinton/fiapark/blob/main/img/aws.png)

## Autores

- [Samuel Clinton](https://www.linkedin.com/in/samuelclinton)

## Licença

[Licença MIT](https://opensource.org/license/mit/): permite o uso, a modificação e a distribuição do software sem restrições significativas.
