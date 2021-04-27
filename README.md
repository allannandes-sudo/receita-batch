# Jobs Receita.

Repositório da aplicação responsável pelo execução de Jobs


## Paramentros de configuracao no pom.xml
## É necessario ter os caminhos e nome do arquivo para ser executado

## Comando de execução

mvn clean install -DskipTests

Tem que ser excutado no Target:

cd target

Em seguida execute comando, segue exemplo nome do meu arquivo é receita.csv:

Importante ter job 01 nomedoarquivo

java -jar receita-batch-0.0.1.jar 01 receita.csv

		##REC_OUT: D:/local/home/rec/out (Diretorio de saida do arquivo)
		##REC_IN: D:/local/home/rec/in   (Diretorio de entrada do arquivo)
		##REC_ERRO: D:/local/home/rec/erro (Diretorio de erro do arquivo, caso ocorra erro no processo)
		##REC_PROCESS: D:/local/home/rec/process (Diretorio de arquivo processados)
		##REC_NAME_FILE_IN: receita.csv (nome do arquivo de entreda)
		##REC_NAME_FILE_OUT: receitaOut.csv (nome do arquivo de saida)
