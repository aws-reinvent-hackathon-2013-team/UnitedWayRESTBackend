#!/bin/bash
curl -X POST -H "Content-Type: application/json" -d "{\"latitude\": 40.30177, \"longitude\": -122.42512}" http://myfirstelasticbeans-env-dynamo.elasticbeanstalk.com/points/test1

