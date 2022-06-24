# CreditLineApi
Tribal Coding Challenge - Backend

For this challange I used bucket4j to limit the rate of usage. Use data base H2 to persist the data.
Used also an MVC arquitecture with DTO class to transfer the object to the client and DAO interface to comunicate with the database using JPA. A package to handle commom exceptions, a package with an utilitie class and static methods. Also a Service layer to keep the business logic.
For Unit tests used Junit and MOckito (spring-boot-starter-test)

The use of the API, can be done sending a body like the option:
{
"foundingType": "SME",
"cashBalance": 435.30,
"monthlyRevenue": 4235.45,
"requestedCreditLine": 100,
"requestedDate": "2021-07-19T16:32:59.860Z"
}

If you don't want to use the field requestDate you can do so. The API will provide the Local Date time.

If you don't send any or worng type of value in any of the fields: foundingType, cashBalance, monthlyRevenue, requestedCreditLine; you will receive a error mensage like follow:
{
    "message": "cashBalance::can't be null",
    "error": "NotNull"
}

Every return of the API has the headers X-RateLimit-Remaining if the HttpStatus is OK (200) and X-RateLimit-Reset if the HttpStatus is TOO_MANY_REQUEST 429.
If you receive an error with code 429 you will receive a body like follow:
{
    "message": "Number of hits exceeded. Try Later",
    "creditLineAuthorized": 0
}

If the Credit Line was Accepted you will receive:
{
    "message": "The Credit Line was accept!",
    "creditLineAuthorized": 1647.09
}

If the Credit Line is not accepted:
{
    "message": "The Credit Line was NOT accept!",
    "creditLineAuthorized": 0
}
