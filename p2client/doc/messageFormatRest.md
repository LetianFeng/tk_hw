## Message Format for REST Service:

#### request with data range

    HTTP METHOD: GET
     
    URL: http://localhost:9999/booking//availableService/{startDate}/{endDate}
    
    startDate, endDate Format: yyyy-mm-dd


#### return available service

    [
        {
            "serviceId": 1,
            "serviceName": "single rooms",
            "price": 20.99,
            "isRoom": true,
            "description": "rooms for one person",
            "availableAmount": 8
        },
        {
            "serviceId": 2,
            "serviceName": "double rooms",
            "price": 30.99,
            "isRoom": true,
            "description": "rooms for two persons",
            "availableAmount": 11
        },
        {
            "serviceId": 3,
            "serviceName": "rent car",
            "price": 20,
            "isRoom": false,
            "description": "rent for a car",
            "availableAmount": 3
        },
        {
            "serviceId": 4,
            "serviceName": "breakfast",
            "price": 3,
            "isRoom": false,
            "description": "reserve the breakfast",
            "availableAmount": 2147483647
        }
    ]
    
#### booking request

    HTTP METHOD: POST
    
    URL: http://localhost:9999/booking/bookingEntry/
    
    BODY:

    [
      {
        "serviceId": 1,
        "email": "test@example.com",
        "date":
        {
          "year": 2016,
          "month": 12,
          "day": 10
        }
      },
      {
        "serviceId": 3,
        "email": "test@example.com",
        "date":
        {
          "year": 2016,
          "month": 12,
          "day": 11
        }
      }
    ]
    
#### booking response

##### successful

    {
        "bookingState": true
    }

##### failed

    {
        "bookingState": false,
        "failedService": "rent car"
    }

## Library:

- REST Service: jersey-bundle-1.19.1
- Convert Objects to (from) JSON: gson-2.8.0
