#!/usr/bin/env bash

set -ex

echo "Delete the cluster with the profile 'multi-node'"
if ! minikube delete -p multi-node; then
    echo "Cluster 'multi-node' does not exist. Continuing..."
fi

echo "Create a cluster with 2 nodes"
minikube start --nodes 2 -p multi-node --addons metrics-server

echo "add argocd Repo"
helm repo add argo https://argoproj.github.io/argo-helm

echo "Install argoCD"
helm install argocd argo/argo-cd --version 7.8.28 -n argocd --create-namespace

echo "List all components"
kubectl get all -n argocd

echo "add Prometheus for monotoring"
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts

echo "update repository"
helm repo update

echo "Install Prometheus"
helm install prometheus prometheus-community/kube-prometheus-stack

echo "show Grafana Password"
kubectl get secret prometheus-grafana -o jsonpath="{.data.admin-password}" | base64 --decode; echo

echo "Deploy the application to ArgoCD"
kubectl create -f application.yaml

echo "wait 50s for creating all the necessary element"
sleep 50

echo "Get ArgoCD initial password"
PWD=$(kubectl get -n argocd secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d)
echo "The initial password for ArgoCD is: $PWD"

echo "Expose the ArgoCD server"
kubectl port-forward -n argocd svc/argocd-server 8080:443 > port-forward.log 2>&1 &

echo "Login to ArgoCD"
argocd login localhost:8080 --username admin --password $PWD --insecure

echo "List all applications in ArgoCD"
argocd app list

#echo "Retrieve Minikube IP"
#IP=$(minikube ip -p multi-node)
#echo "Access the Service at: http://$IP"

echo "expose the Grafana service"
kubectl port-forward svc/prometheus-grafana 3000:80 &

echo "wait 50s for creating all the necessary element"
sleep 50

echo "To access to the App wein-service local on the browser, add to the url the path: /weins, e.g: http://127.0.0.1:51294/weins"
minikube service weinservice --url
