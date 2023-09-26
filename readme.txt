QUERY SEGMENTATION
==================

This project uses a development container.
See the documentation of your IDE to check how you can use a dev container, e.g., https://code.visualstudio.com/docs/devcontainers/containers

To run a query segmentation with the existing docker image, please install tira (pip3 install tira) and execute:

```
./run-query-segmentation-in-tira.sh robust04/disks45-nocr-trec-robust-2004-20230209-training
```

To build the project in a docker image, please run:

docker build -t webis/query-segmentation:application-1.0 .

SUMMARY
-------

The following query segmentation approaches are implemented:

- stein2010j-naive.txt
- stein2011e-wiki-based.txt
- stein2012q-hybrid-acc.txt [HYB-A im paper]
- stein2012q-hybrid-ir-none-stein11e.txt [HYB-I im paper]
- stein2012q-hybrid-ir-none-wt.txt [HYB-B im paper]
- stein2012q-wt-baseline.txt
- stein2012q-wt-snp-baseline.txt 

QUERY SEGMENTATION (Legacy)
===========================

CONFIGURATION
-------------

This project requires two files containing titles of wikipedia articles:

- /media/storage1/data-in-progress/query-segmentation/data/wikititle.txt
  [contains wikipedia title to be considered for query segmentation]
- /media/storage1/data-in-progress/query-segmentation/data/stop-wikititle.txt
  [contains wikipedia title which will not be considered for query segmentation]


The log files will be written to:
- /media/storage1/data-in-progress/query-segmentation/logs/
Please make sure that these directory exists.

