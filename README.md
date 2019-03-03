# offer-api
API for offer management

PORT: 8080


POST 
/offer
Body (JSON):
{
    "description": "description",
    "price": 3,
    "currencyCode": "GBP",
    "expiration": "2019-03-03 13:30",
    "canceled": true
}


PUT
/offer/{id}
Body (JSON):
{
    "description": "description",
    "price": 3,
    "currencyCode": "GBP",
    "expiration": "2019-03-03 13:30",
    "canceled": true
}


PATCH
/offer/{id}
Body (JSON):
{
    "price": 7.13,
    "canceled": false,
}


GET
/offer/{id}


GET
/offer/all


DELETE
/offer/{id}
