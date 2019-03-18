
# Microservice
This micorservice will:
-watch a configured path in order to detect new files uploaded to it
-if the file is a .zip it will check integrity and if its ok, will unzip it to a configured path
-offer an endpoint for makin it check the folder for all new files and fire full ingest
-offer an endpoint which will return failed files that need to be retried

Params:
-in path
-output path (unzipped files)
-retry interval


