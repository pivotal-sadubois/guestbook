# Newsletter Applicaiton
This Project has been designed as a demo application for the Tanzu Applicaiton Platform (TAP). The Demo currently only
implements the User Profile Management as part of the Guestbook application and is basing on a
the guestbook-user-db, guestbook-user-svc and the guestbook-ui.

The picture below shows the architecture of the Newsletter Application:
![guestbook-architecture](images/newsletter.jpg)

Applicaiton Components
- newsletter-ui - Is an AngularJS Frontent for the Guestbook User Profile Management and interacts directly with the user-profile-backend
- user-service - Is a spring boot REST API service and acts as a backend for the User Profile Management Service
- user-db - PostgrSQL Database backend as persistent storage for the user profiles.
- mailing-service - The mailing-service is responsible for sending the newsletter to the recipients provided by the user-service app

## Newsletter User Interface (newsletter-ui)
The Newsletter User Interface is a Web Frontent basing on AngularJS that allows user to signup to the Newsletter. The Interface allows users to 
register themself to the Newsleter Service and modify or delete their data records afterwards. The Newsleter User Interfaces interacts with the 
API provided Newsleter User Service to abstract direct connection to the Newsletter User Database. 

## Newsletter User Service (newsletter-user-service)
The Newsletter User Service is the Backend of the Newsletter User Interface and provides an interface (RestFULL) for the application components 
(newletter-ui or the mailing-service) to interact with cwits API. The the Newsletter User Service uses a PostgreSQL database backend to store 
persistent user data records. This abstraction allows better to controll who is acessing the service and new service features can be implemented 
by introducting a new API Version. As well its planned to integrate a circuit breaker in the NEwsleter User Service in a fulure version to prent 
the service from overloading.

## Newsletter User Database (newsletter-user-db)
Tje Newsletter User Database acts as backend for the Newsletter User Service and is basing on a PostgreSQL Database running in a container with 
the same Kubernetes Namespace as the application. The Database is deployed and Managed trough the PostgreSQL Operator running in the default namespace
of the same cluster. Morge information about the VMware PostgreSQL Operator can be seen in the documentation 
[VMware SQL with Postgres for Kubernetes Operator](https://docs.vmware.com/en/VMware-SQL-with-Postgres-for-Kubernetes/2.0/vmware-postgres-k8s/GUID-install-operator.html).

## Newsletter Sailing Service (newsletter-mailing-service)

Deployment Scenarios
The Guestbook application can be deployed local on a Workstation / Laptop which usually the first choise of a developer with the limitation
that backensystems (database etc.) requires to be simulated as well as other procects API's which can not be accessed on a local environent. The second 
deployment option is on top of kubernetes cluster deployed with Tanzu Applicaiton Platform (TAP) that automaticly generate a supply chain depending 
on the applicaiotn needs. 
- Deploy on the local Laptop/Workstation
- Deploy on Kubernetes with Tanzu Applicaiton Platform (TAP)

## Setup Development Environment
### Create GitHub API Key
- Navigate to https://github.com/settings/profile
- Navigate to 'Developer Settings' in the Side Bar at the bottom
- Select 'Personal access tokens' and 'Token (classic)'
- Select 'Generate new Token (classic)"
- Enter a Name and Select Access for 'Repo' then hit 'Generate Token'

