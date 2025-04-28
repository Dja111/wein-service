# wein-service

This service is based on the microservice architectur.

## Description

The wein-service provides RESTful CRUD (Create, Read, Update, Delete) operations for managing wein models. This service adheres strictly to REST principles, ensuring simplicity and compatibility.

## Purpose 

This project serves as an application that can be deployed on Kubernetes clusters (e.g., Minikube, EKS) using a CI/CD pipeline powered by ArgoCD. To keep things lightweight and efficient, data is stored using an H2 in-memory database during development.

The service also comes with its own [wein-service-env](https://github.com/Dja111/wein-service-env) repository, which contains the Helm configuration files needed to deploy the application on Minikube.

Additionally, within the Deployment folder, you will find an architecture diagram showcasing the CI/CD pipeline's structure.

## Tools and Technologies

- Java Spring Boot for building the microservice.

- Gradle for dependency management and builds.

- Helm as the package manager for Kubernetes.

- Prometheus & Grafana for monitoring and observability.

## Local Setup
Developers often prefer testing their applications on local clusters before deploying them to production.
This repository includes a local setup configuration. Navigate to the Deployment/local directory, where you'll find a .sh script to automate local deployment.

## Prerequisites
To successfully run the deployment script, ensure the following software is installed on your machine:
- Docker
- Minikube
- Helm
- ArgoCD 

The application will be deployed using Helm, with the Helm charts stored in the wein-service-env repository. Once deployed, the cluster and application will be monitored using Prometheus and Grafana, solving the observability challenge.

## Deployment
- Navigate to the Deployment/local directory.
- Execute the .sh script to automate the deployment locally:

```js
./run.sh
```

## Observability
The observability stack consists of:

- Prometheus: For metrics collection and alerting.

- Grafana: For building dashboards and visualizing metrics.

These tools provide insight into the cluster and application performance.

You can add the following Grafana Dashboard:

- [Spring Boot & Endpoint Metrics 2.0](https://grafana.com/grafana/dashboards/17053-spring-boot-statistics-endpoint-metrics/)
- [Spring Boot 3.x Statistics](https://grafana.com/grafana/dashboards/19004-spring-boot-statistics/)

## Maintainer
This project is maintains by Jospin Aurèle Donfack

## Contact
aurel.donfak@gmail.com
