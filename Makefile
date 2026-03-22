.PHONY: help up down build logs health clean
.DEFAULT_GOAL := help

help: ## Show this help
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "  %-10s %s\n", $$1, $$2}'

up: ## Start all services (build + detached)
	docker compose up --build -d

down: ## Stop all services
	docker compose down

build: ## Build images without starting
	docker compose build

logs: ## Tail logs for all services
	docker compose logs -f

health: ## Check actuator health endpoint
	@curl -sf http://localhost:8080/actuator/health | python3 -m json.tool || echo "Health endpoint not reachable — is the stack running? (make up)"

clean: ## Stop all services and remove volumes
	docker compose down -v
