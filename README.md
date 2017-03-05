# busy-example

This is an example of how to implement [busy](https://github.com/viajobien/busy) with minimal options.

You can run this project with:

```sh
$ sbt run
```

Then you can make requests to the application.  
Here are some examples:

| curl | httpie | Notes |
|:-:|:-:|---|
| curl -i -X GET 'http://localhost:9000/photos' | http GET 'http://localhost:9000/photos' | GET request to https://jsonplaceholder.typicode.com/photos |
| curl -i -X GET 'http://localhost:9000/photos/4000' | http GET 'http://localhost:9000/photos/4000' |  GET request to https://jsonplaceholder.typicode.com/photos/4000 |
| curl -i -X GET 'http://localhost:9000/noroute' | http GET 'http://localhost:9000/noroute' | There is no route for "/noroute", so the response is a 502 error with {"error": "route not found"} as response |
| curl -i -X GET 'http://localhost:9000/ip/8.8.8.8' -H 'provider: ipinfo' | http GET 'http://localhost:9000/ip/8.8.8.8' provider:ipinfo | Using the header "provider", this makes a GET to http://ipinfo.io/8.8.8.8 |
| curl -i -X GET 'http://localhost:9000/ip/8.8.8.8' -H 'provider: ip-api' | http GET 'http://localhost:9000/ip/8.8.8.8' provider:ip-api | Using the header "provider", this makes a GET to http://ip-api.com/json/8.8.8.8 |

The last two has a different json as response, is up to you to get the data tranformation to get the same response.
