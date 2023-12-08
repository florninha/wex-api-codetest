# api-test

This is a small project developed in 5 business days by me for the
recruitment process in wex company.

## Start project
Requirements:
1. Docker installed
### Development
#### Intellij
In the gradle sidebar:
1. Tasks > build > build
2. Tasks > application > bootRun 

The application will be up and running, and can be used and debugged.
### Production
The project can be started using docker-compose.
1. Go to the docker folder
2. And run
```
docker compose up
```

The application will be up and running
## Project Usage
The project contains 2 main tasks:
1. Store Purchase on Database;
2. Retrieve the purchase with converted currency

### Store Purchase on Database
In order to store a purchase it is needed to execute a POST request to the application, such as:
```
http://url:8080/store
e.g
http://localhost:8080/store
```
With a body containing the following:
``` JSON
{
    "description":"Description",
    "transactionDate": "2023-09-30",
    "amountUSD":"2.00"
}
```
| NOTE: The date format needs to be yyyy-MM-dd
| NOTE: The description should contain less than 51 characters
| NOTE: The amount must be a positive value

If the request is successful it will return the Id of the purchase:
```
Purchase Transaction created! Id:<id>
e.g
Purchase Transaction created! Id:3
```
### Retrieve the purchase with converted currency

In order to retrieve the purchase converted in the currencies available at Treasury Reporting Rates of Exchange API
all that is needed is the purchase Id. Then you can execute a GET request such as the following:
```
http://localhost:8080/retrieveById/<id>
e.g
http://localhost:8080/retrieveById/2
```
The application will return a JSON with the purchase and its value converted in all currencies, such as below:
```JSON
[
    {
        "id": 2,
        "description": "Special1",
        "transactionDate": "2023-09-29",
        "amountUSD": 2.0,
        "currency": "Afghani",
        "exchangeRate": "86.75",
        "convertedAmount": "173.50"
    },
    {
        "id": 2,
        "description": "Special1",
        "transactionDate": "2023-09-29",
        "amountUSD": 2.0,
        "currency": "Chavito",
        "exchangeRate": "1.00",
        "convertedAmount": "2.00"
    },
    {
        "id": 2,
        "description": "Special1",
        "transactionDate": "2023-09-29",
        "amountUSD": 2.0,
        "currency": "Colon",
        "exchangeRate": "539.65",
        "convertedAmount": "1079.30"
    },
    {
        "id": 2,
        "description": "Special1",
        "transactionDate": "2023-09-29",
        "amountUSD": 2.0,
        "currency": "Dirham",
        "exchangeRate": "10.21",
        "convertedAmount": "20.42"
    },
    {
        "id": 2,
        "description": "Special1",
        "transactionDate": "2023-09-29",
        "amountUSD": 2.0,
        "currency": "Dollar",
        "exchangeRate": "1.36",
        "convertedAmount": "2.71"
    }
]
```

### Project Observations

1. The plugin ('com.avast.gradle.docker-compose') used to scale docker up and down whenever gradle build and bootRun has
a bug. This bug was not resolved by the plugin creators. This means that whenever a bootRun is interrupted (stopped) the 
Docker container is not scaled down and needs to be manually stopped/deleted. This does not occur for build task.