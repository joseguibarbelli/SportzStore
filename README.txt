Loja de esportes

Esse projeto é um sistema simples de pedidos feito em Java para o trabalho de POO.

Dá pra escolher produtos, informar o cliente e finalizar com Pix ou cartão. O Pix tem 5% de desconto. Também da pra consultar os pedidos depois.

Integrantes

-José Guilherme
-João Guilherme Lima
-João Vitor Moraes
-Guilherme Seguro

O que usamos

A classe abstrata `Produto` guarda os dados comuns de `Roupa` e `Equipamento`, evitando repetir código. Cada uma implementa o método abstrato `getDescricao()`.
O pedido tem uma lista de produtos usando `ArrayList`. Os atributos são privados pra controlar as alterações.
A interface `FormaPagamento` permite calcular o valor com Pix ou cartão. Isso e as descrições dos produtos mostram o polimorfismo.
Tambem fizemos os três extras: tratamento de erros, salvamento dos objetos em `pedidos.dat` e consulta de endereço pela API ViaCEP.
A consulta de CEP precisa de internet. Pra retirar na loja basta deixar o CEP vazio.