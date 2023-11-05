#!/usr/bin/env python3
from tira.rest_api_client import Client
import shutil
tira = Client()


truth_ground_dir = tira.download_dataset('ir-lab-jena-leipzig-wise-2023', 'training-20231104-training', truth_dataset=True)
shutil.copyfile(truth_ground_dir + '/queries.jsonl', 'ir-lab-sose-training/input/queries.jsonl')

ground_truth_dir = tira.download_dataset('ir-lab-jena-leipzig-wise-2023', 'validation-20231104-training', truth_dataset=True)
shutil.copyfile(truth_ground_dir + '/queries.jsonl', 'ir-lab-sose-validation/input/queries.jsonl')

