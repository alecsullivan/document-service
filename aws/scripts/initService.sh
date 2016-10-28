#!/bin/bash
echo "Attempting to initialize document-service..."

echo "Updating .conf file..."

INSTANCE=`ec2-metadata | grep instance-id | cut -d ' '  -f2`
REGION=`ec2-metadata | grep placement | cut -d ' '  -f2 | sed 's/.$//'`
ENV=`ec2-describe-tags --region $REGION --filter "resource-type=instance" --filter "resource-id=$INSTANCE" --filter "key=Environment" | cut -d$'\t' -f5`
echo "Located the Environment tag: $ENV"

APP_NAME=`ls /var/document-service/document-service*.jar | cut -d '/' -f4 | sed 's/.jar//g'`
echo "JAVA_OPTS=\"\$JAVA_OPTS -Dspring.profiles.active=$ENV -Deureka.client.region=$REGION\"" > /var/document-service/$APP_NAME.conf
echo "Created/updated JAVA_OPTS in $APP_NAME.conf."

if [ ! -L /etc/init.d/document-service ]
then
  echo "Creating document-service."
  ln -s /var/document-service/*.jar /etc/init.d/document-service
else
  echo "document-service already exists."
fi