#!/usr/bin/env bash

set -euo pipefail

# Farben für Ausgaben
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # Keine Farbe

info() {
  echo -e "${GREEN}[INFO]${NC} $*"
}

warn() {
  echo -e "${YELLOW}[WARN]${NC} $*"
}

error() {
  echo -e "${RED}[ERROR]${NC} $*"
}

# --- Skript beginnt ---

info "Deleting the cluster with profile 'multi-node' if it exists..."
if ! minikube delete -p multi-node; then
    warn "Cluster 'multi-node' does not exist. Continuing..."
fi

info "Creating a new Minikube cluster with 2 nodes..."
minikube start --nodes 2 -p multi-node --addons metrics-server --addons ingress

info "Adding Helm repos..."
helm repo add argo https://argoproj.github.io/argo-helm
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update

info "Installing ArgoCD..."
helm install argocd argo/argo-cd --version 7.8.28 -n argocd --create-namespace

info "Waiting for ArgoCD pods to be ready..."
kubectl rollout status deployment/argocd-server -n argocd --timeout=120s || warn "ArgoCD server rollout timeout!"

info "Listing ArgoCD components:"
kubectl get all -n argocd

info "Installing Prometheus stack..."
helm install prometheus prometheus-community/kube-prometheus-stack

info "Waiting for Prometheus pods to be ready..."
kubectl rollout status deployment/prometheus-kube-prometheus-operator --timeout=120s || warn "Prometheus operator rollout timeout!"

info "Retrieving Grafana admin password:"
kubectl get secret prometheus-grafana -o jsonpath="{.data.admin-password}" | base64 --decode; echo

info "Deploying application via ArgoCD..."
kubectl apply -f https://raw.githubusercontent.com/Dja111/apps-gitops/refs/heads/main/init.yaml

info "Waiting for wein-service pods to be ready..."
kubectl rollout status deployment/weinservice --timeout=120s || warn " Wein-service rollout timeout!"

#info "Waiting for application components to initialize..."
#sleep 30  # oder: `kubectl wait` auf bestimmte Deployments setzen

info "Retrieving ArgoCD initial admin password..."
PWD=$(kubectl get -n argocd secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d)
info "ArgoCD initial password is: $PWD"

info "Port-forwarding ArgoCD server (localhost:8080 -> ArgoCD)..."
kubectl port-forward -n argocd svc/argocd-server 8080:443 > /dev/null 2>&1 &
ARGOCD_PID=$!

info "Logging into ArgoCD CLI..."
sleep 5  # Warten, bis Port-Forward steht
argocd login localhost:8080 --username admin --password "$PWD" --insecure

info "Listing ArgoCD applications:"
argocd app list

info "Port-forwarding Grafana (localhost:3000 -> Grafana)..."
kubectl port-forward svc/prometheus-grafana 3000:80 > /dev/null 2>&1 &
GRAFANA_PID=$!

# --- Unterschiedliches Verhalten je nach OS ---

OS_TYPE=$(uname)
info "Detected OS: $OS_TYPE"

if [[ "$OS_TYPE" == "Darwin" ]]; then
    info "Running 'minikube tunnel' for macOS..."
    minikube tunnel -p multi-node
else
    info "Running curl to access the application on non-macOS system..."
    curl --resolve "localhost:80:$( minikube ip -p multi-node )" -i http://localhost/weins  || error "Failed to reach http://localhost/weins"
fi

# Aufräumen (optional)
#trap 'kill $ARGOCD_PID $GRAFANA_PID 2>/dev/null || true' EXIT
