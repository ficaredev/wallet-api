pull:
	git pull origin develop

git:
	bash git-cli.sh

up:
	docker-compose -f docker-compose.yml up -d

down:
	docker-compose -f docker-compose.yml down
