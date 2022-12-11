URL Base da aplicação:

https://easyjobgoapp.herokuapp.com/easyjobgo/v1/

URL Referentes ao Usuario:

Para trazer todos so usuarios (GET):
https://easyjobgoapp.herokuapp.com/easyjobgo/v1/user/all

Para trazer um usuario pelo email dele (GET):
https://easyjobgoapp.herokuapp.com/easyjobgo/v1/user/{username}

Para trazer um usuario pela categoria de servico dele (GET):
https://easyjobgoapp.herokuapp.com/easyjobgo/v1/user/{serviceCategoryId}

Para trazer um usuario por uma descricao (quando pesquisar alguma coisa) (GET):
https://easyjobgoapp.herokuapp.com/easyjobgo/v1/user/{descricao}

Para trazer salvar um novo usuario (POST):
https://easyjobgoapp.herokuapp.com/easyjobgo/v1/user/registration

pelo corpo da requisicao (content type: multipart/form-data)

file (imagem de perfil): faz o upload da imagem direto
user:
{
	"cpf": "12345678910",
	"firstName": "qualquer",
	"lastName": "User",
	"username": "teste@gmail.com",
	"password": "teste123",
	"numberDDD": "00",
	"number": "000000000",
	"birthdate": "0000-00-00",
	"cep": "00000000",
	"addressDistrict": "Admin District",
	"addressPubPlace": "Admin Public Place",
	"addressComp": "Admin Complement",
	"city": "Admin City",
	"fedUnit": "AD",
	"ratingValue": 5.0,
	"provideService": false,
	"statusId": 2,
	"roleId": 2
}
days:
[
	{"dayId": 1},
	{"dayId": 2},
	{"dayId": 3}
]

Para atualizar os dados de um usuario (PUT):
https://easyjobgoapp.herokuapp.com/easyjobgo/v1/user/alteration

file (imagem de perfil): faz o upload da imagem direto
user:
{
	"numberDDD": "00",
	"number": "000000000",
	"cep": "00000000",
	"addressDistrict": "Admin District",
	"addressPubPlace": "Admin Public Place",
	"addressComp": "Admin Complement",
	"city": "Admin City",
	"fedUnit": "AD",
	"provideService": false,
    "serviceCategoryId": 1,
    "subserviceCategoryId": 2,
}
days:
[
	{"dayId": 1},
	{"dayId": 2},
	{"dayId": 3}
]



