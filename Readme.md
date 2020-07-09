# Polyglot persistence example with Spring Boot, PostgreSQL, Elasticsearch, and RabbitMQ

Proof of concept to achieve a system with dual database persistence and post processing with RabbitMQ consumers. The persistence layer is based on PostgreSQL and ElasticSearch, using 
the relational model with the document-based persistence achieved with Elasticsearch. Additional processing was done using RabbitMQ
allowing a decoupled log system, and a simple mechanism to extract "important info" when an article is stored in the system.


## Requirement
* Java 8
* Docker
* Elasticsearch
* Head: Elasticsearch Chrome Extension
* PostgreSQL
* RabbitMQ

## Note
Download ElasticSearch of 2.4.x if you are using Spring Boot 1.5.x. If you don't use correct versions then you should
get following error:
```
java.lang.IllegalStateException: Received message from unsupported version: [2.0.0] minimal compatible version is: [5.0.0]
```
As we are using Spring Boot to 2.x.x, you should download/run elasticsearch 6.8.3 version. If not you might face following issue:
```
failed to load elasticsearch nodes : org.elasticsearch.client.transport.NoNodeAvailableException: 
None of the configured nodes are available: [{#transport#-1}{uWHhZacNR9mbfojQOayyAg}{127.0.0.1}{127.0.0.1:9300}]
```
## Running the sample app

* Install and run Elastic Server 6.8.3
```
docker run -d -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:6.8.3
```
* Open browser and hit `localhost:9200` and you should see below response
```
{
  "name" : "2unlfLY",
  "cluster_name" : "docker-cluster",
  "cluster_uuid" : "IP7WnDZNS2CRCtKJWZrssw",
  "version" : {
    "number" : "6.8.3",
    "build_flavor" : "default",
    "build_type" : "docker",
    "build_hash" : "0c48c0e",
    "build_date" : "2019-08-29T19:05:24.312154Z",
    "build_snapshot" : false,
    "lucene_version" : "7.7.0",
    "minimum_wire_compatibility_version" : "5.6.0",
    "minimum_index_compatibility_version" : "5.0.0"
  },
  "tagline" : "You Know, for Search"
}
```

* Install and run RabbitMQ

For Windows, use this [link](https://www.rabbitmq.com/install-windows-manual.html) to install and run RabbitMQ as a service.
For future release, the entire application will be dockerized 

* Install PostgreSQL 

You could use some available resource to guide you to install PostgreSQL. This [link](https://www.postgresqltutorial.com/install-postgresql/) 
is an example about how to achieve it 

* Run Spring Boot application using below command
```
mvn spring-boot:run
```

* Open in your browser the Swagger endpoint to check the capabilities of the system

```
http://localhost:8080/swagger-ui.html
```

