# training-senang-pay-02
Contains sample code related to training Senang Pay.

# Liquibase
liquibase --changeLogFile=db/master.changelog.yaml --url=jdbc:postgresql://localhost:5432/training_senang_pay --username=postgres --password=postgres generate-changelog

liquibase --changeLogFile=db/master.changelog.yaml --url=jdbc:postgresql://localhost:5432/training_senang_pay --username=postgres --password=postgres update

liquibase --changeLogFile=db/master.changelog.yaml --url=jdbc:postgresql://localhost:5432/training_senang_pay --username=postgres --password=postgres  rollback --tag=DP-0001-1
