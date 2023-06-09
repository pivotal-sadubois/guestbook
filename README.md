# Guestbook - Demo Application for Tanzu Applicaiton Platform (TAP)
This Project has been designed as a demo application for the Tanzu Applicaiton Platform (TAP). The Demo currently only 
implements the User Profile Management as part of the Guestbook application and is basing on a
the user-profile-database, user-profile-backend and the user-profile-ui. 

## Applicaiton Components
Applicaiton Components
- user-profile-database - PostgrSQL Database backend as persistent storage for the user profiles.
- user-profile-backend - Is a spring boot REST API service and acts as a backend for the User Profile Management Service
- user-profile-ui - Is an AngularJS Frontent for the Guestbook User Profile Management and interacts directly with the user-profile-backend

## Setup Development Environment
### Fork the guestbook Git Repository 
By creating a fork of the original GIT Repository allows you to edit the content in your GitHub environment without doing pull requests on the original repsitory.
- Navigate to https://github.com/pivotal-sadubois/guestbook/fork in your browser
- Select an Owner (specifiy your GitHub account)
- Select 'Copy the main branch only'
- press 'Create fork'

## Clone the Git Repsitory to your local laptop/workstation
By cloning the GIT repository with the Github API Key, allows to make 'git pull' without asking for a password. The GITHUB_HOME can be adjusted as needed if $HOME/Development is not your development folder.
```
export GITHUB_APIKEY=<your-github-user>
export GITHUB_USER=<github-api-key>
export GITHUB_HOME=$HOME/Development

git -C $GITHUB_HOME clone https://$GITHUB_APIKEY@github.com/$GITHUB_USER/guestbook.git
```

## Visual Studio Code (VScode) Setup

git -C /tmp clone https://github.com/pivotal-sadubois/$APPNAME.git

# --- CREATE DEVELOPER NAMESPACE ---
cd $HOME/tanzu-demo-hub/scripts && ./tap-create-developer-namespace.sh $NAMESPACE 

# --- SETUP POSGRESS DB ---
kubectl delete secret regsecret -n $NAMESPACE > /dev/null 2>&1
kubectl create secret --namespace=$NAMESPACE docker-registry regsecret \
   --docker-server=https://registry.tanzu.vmware.com \
   --docker-username=$TDH_REGISTRY_VMWARE_USER \
   --docker-password=$TDH_REGISTRY_VMWARE_PASS

cd /tmp/$APPNAME/user-profile-database
kubectl -n $APPNAME create -f postgres-service-binding.yaml 2> /dev/null
kubectl -n $APPNAME create -f postgres-class.yaml 2> /dev/null
kubectl -n $APPNAME create -f postgres.yaml 


# VSCode Import TAP Settings 
vscode-settings-guestbook.json















# ---------------------

# Introduction blockchain-api
blockchain-api provides you an out-of-the-box application setup to implement your business logic. It is based on the
commonly known 3-layered application architecture in where the package `api` provides the presentation layer, `domain` provides 
the services and business domain and finally the `data` package provides you the capability to persist your domain.

It is leveraging Spring Boot as a technology stack, which provides:
- a way to implement REST(ful) API using Spring Web annotations
- generation of the OpenAPI definition based on your code
- data persistence using Spring Data JPA (now PostgreSQL is supported, but other databases can be easily added)
- an Inversion of Control Container to wire together your classes at running without the need to write tightly-coupled code

The application contains example code implementing REST API to write and read customer profile information to and from 
database. This example is intended to showcase best practices around using Spring Boot and it's libraries as well as
different types of tests which can be utilized to verify different parts of an application.

## Prerequisites
In order to further develop this application the following tools needs to be setup:
- Java Development Kit (https://bell-sw.com/)
- Visual Studio Code or IntelliJ IDEA as Integrated Development Environment (IDE)
- Tanzu Developer Tools plugin for mentioned IDE
- Docker Desktop to execute integration tests or run the application locally

# Local
## Build
In order to compile the production code:
```bash
./mvnw clean compile
```


After that it is a good habit to compile the test classes and execute those tests to see if your application is still behaving as you would expect:
```bash
./mvnw verify
```


## Start and interact
Spring Boot has its own integrated Web Server (Apache Tomcat (https://tomcat.apache.org/)). In order 
to start the application a PostgreSQL instance should be running.

Running a PostgreSQL instance can easily be done by using `docker-compose`:
```bash
docker-compose up -d
```

Launch application using profile `local`:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```


### OpenApi Definition
You can access the API docs using `curl`:

```bash
curl http://localhost:8080/api-docs  
```

### Create customer profile

You can access the `customer-profiles` API endpoint using `curl`:

```bash
curl -X POST -H 'Content-Type: application/json' http://localhost:8080/api/customer-profiles/ -d '{"firstName": "Joe", "lastName": "Doe", "email": "joe.doe@test.org"}'
```

### Get customer profile
Use the `id` received by previous POST call.
```bash
curl -X GET http://localhost:8080/api/customer-profiles/{id}
```

# Deployment
## Tanzu Application Platform (TAP)
Using the `config/workload.yaml` it is possible to build, test and deploy this application onto a
Kubernetes cluster that is provisioned with Tanzu Application Platform (https://tanzu.vmware.com/application-platform).

As with the local deployment a PostgreSQL instance needs to be available at the cluster.
When using VMware Tanzu SQL with Postgres for Kubernetes (https://tanzu.vmware.com/sql and https://docs.vmware.com/en/VMware-Tanzu-SQL-with-Postgres-for-Kubernetes/index.html),
one could apply for an instance, and it will be automatically provisioned.

> Note: please define the storage class to be used for the PostgreSQL storage.

```bash
kubectl apply -f config/postgres.yaml
```

The `workload.yaml` contains a reference to the PostgreSQL instance.

> Note: if your postgres instance is in another namespace than your developer namespace, add the following to the workload.yaml:
```metadata:
       annotations:
         serviceclaims.supplychain.apps.x-tanzu.vmware.com/extensions: '{"kind":"ServiceClaimsExtension","apiVersion":"supplychain.apps.x-tanzu.vmware.com/v1alpha1", "spec":   {"serviceClaims":{"db":{"namespace":"<database namespace>"}}}}'
```

Before deploying your application a Tekton Pipeline responsible for the testing step shall be created in your application
namespace. Please execute following command.

```bash
kubectl apply -f config/test-pipeline.yaml
```


### Tanzu CLI
Using the Tanzu CLI one could apply the workload using the local sources:
```bash
tanzu apps workload apply \
  --file config/workload.yaml \
  --namespace <namespace> --source-image <image-registry> \
  --local-path . \
  --yes \
  --tail
````

Note: change the namespace to where you would like to deploy this workload. Also define the (private) image registry you
are allowed to push the source-image, like: `docker.io/username/repository`.

### Visual Studio Code Tanzu Plugin
When developing local but would like to deploy the local code to the cluster the Tanzu Plugin could help.
By using `Tanzu: Apply` on the `workload.yaml` it will create the Workload resource with the local source (pushed to an image registry) as
starting point.

# How to proceed from here?
Having the application locally running and deployed to a cluster you could add your domain logic, related persistence and new RESTful controller.
