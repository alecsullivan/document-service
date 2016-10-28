#!/bin/bash
echo "Attempting to stop id-service..."
if [ -L /etc/init.d/id-service ]
then
  echo "Stopping id-service."
  service id-service stop
else
  echo "No service to stop."
fi