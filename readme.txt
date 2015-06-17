QUERY SEGMENTATION
==================

CONFIGURATION
-------------

This project requires two files containing titles of wikipedia articles:

- /media/storage1/data-in-progress/query-segmentation/data/wikititle.txt
  [contains wikipedia title to be considered for query segmentation]
- /media/storage1/data-in-progress/query-segmentation/data/stop-wikititle.txt
  [contains wikipedia title which will not be considered for query segmentation]


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