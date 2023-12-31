# docker build -t webis/query-segmentation:application-1.0 .
FROM webis/query-segmentation:application-1.0-dev

ADD pom.xml /query-segmentation-application/
ADD assembly.xml /query-segmentation-application/
ADD src /query-segmentation-application/src
ADD data/webis-qsec-10-training-set-queries.txt /query-segmentation-application/data/

RUN cd /query-segmentation-application \
	&& mvn clean install \
	&& mvn clean install \
	&& mv /query-segmentation-application/target/query-segmentation-application-1.0-SNAPSHOT-jar-with-all-dependencies.jar /query-segmentation-application.jar

