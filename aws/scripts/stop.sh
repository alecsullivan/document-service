#!/bin/bash
echo "Attempting to stop document-service..."
if [ -L /etc/init.d/document-service ]
then
  echo "Stopping document-service."
  service document-service stop
else
  echo "No service to stop."
fi