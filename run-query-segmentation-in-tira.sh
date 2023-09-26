#!/usr/bin/bash

tira-run \
	--image webis/query-segmentation:application-1.0 \
	--input-dir data/${1}/ \
	--output-dir data/${1}-cache/ \
	--allow-network true \
	--command 'java -jar /query-segmentation-application.jar --input $inputDataset --cache $inputDataset --output $outputDir --segmentation-approach wt-snp-baseline hyb-b hyb-a wiki-based wt-baseline naive hyb-i'

tira-run \
	--image webis/query-segmentation:application-1.0 \
	--input-dir data/${1}/ \
	--output-dir data/${1}-cache/ \
	--command 'java -jar /query-segmentation-application.jar --input $inputDataset --cache $outputDir --output $outputDir --segmentation-approach wt-snp-baseline hyb-b hyb-a wiki-based wt-baseline naive hyb-i'

