# Jobs Receita.

Repositório da aplicação responsável pelo execução de Jobs


## Paramentros de configuracao no pom.xml
## É necessario ter os caminhos e nome do arquivo para ser executado

## Comando de execução

Tem que ser excutado no Target:

cd target

Em seguida execute comando, segue exemplo nome do meu arquivo é receita.csv:

Importante ter job 01 nomedoarquivo

java -jar receita-batch-0.0.1.jar 01 receita.csv

		##REC_OUT: D:/local/home/rec/out 
		##REC_IN: D:/local/home/rec/in
		##REC_ERRO: D:/local/home/rec/erro
		##REC_PROCESS: D:/local/home/rec/process 
		##REC_NAME_FILE_IN: receita.csv 
		##REC_NAME_FILE_OUT: receitaOut.csv 
