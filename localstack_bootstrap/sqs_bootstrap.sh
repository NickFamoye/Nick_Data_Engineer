##!/usr/bin/env bash
#
#set -euo pipefail
#
#echo "configuring sqs"
#
## https://gugsrs.com/localstack-sqs-sns/
#HOSTNAME_EXTERNAL=localhost
#DEFAULT_REGION=us-east-1
#
#
#echo "================================ Starting SQS via localstack...====================================="
#
#get_all_queues() {
#    awslocal --endpoint-url=http://${HOSTNAME_EXTERNAL}:4566 sqs list-queues
#}
#
#
#create_queue() {
#    local QUEUE_NAME_TO_CREATE=$1
#    awslocal --endpoint-url=http://${HOSTNAME_EXTERNAL}:4566 sqs create-queue --queue-name ${QUEUE_NAME_TO_CREATE}
#}
#
#get_all_topics() {
#    awslocal --endpoint-url=http://${HOSTNAME_EXTERNAL}:4566 sns list-topics
#}
#
#create_topic() {
#    local TOPIC_NAME_TO_CREATE=$1
#    awslocal --endpoint-url=http://${HOSTNAME_EXTERNAL}:4566 sns create-topic --output text --name ${TOPIC_NAME_TO_CREATE}
#}
#
#link_queue_and_topic() {
#    local TOPIC_ARN_TO_LINK=$1
#    local QUEUE_ARN_TO_LINK=$2
#    awslocal --endpoint-url=http://${HOSTNAME_EXTERNAL}:4566 sns subscribe --topic-arn ${TOPIC_ARN_TO_LINK} --protocol sqs --notification-endpoint ${QUEUE_ARN_TO_LINK}
#}
#
#guess_queue_arn_from_name() {
#    local QUEUE_NAME=$1
#    echo "arn:aws:sns:${DEFAULT_REGION}:${LOCALSTACK_DUMMY_ID}:$QUEUE_NAME"
#}
#
#QUEUE_NAME="First_queue"
#TOPIC_NAME="First_topic"
#
#echo "creating topic $TOPIC_NAME"
#TOPIC_ARN=$(create_topic ${TOPIC_NAME})
#echo "created topic: $TOPIC_ARN"
#
#echo "creating queue $QUEUE_NAME"
#QUEUE_URL=$(create_queue ${QUEUE_NAME})
#echo "created queue: $QUEUE_URL"
#QUEUE_ARN=$(guess_queue_arn_from_name $QUEUE_NAME)
#
#echo "linking topic $TOPIC_ARN to queue $QUEUE_ARN"
#LINKING_RESULT=$(link_queue_and_topic $TOPIC_ARN $QUEUE_ARN)
#echo "linking done:"
#echo "$LINKING_RESULT"
#
#echo "all topics are:"
#echo "$(get_all_topics)"
#
#echo "all queues are:"
#echo "$(get_all_queues)"
#echo "========== Listing profile =========="
#
#aws configure list --profile= localstack
#
##set -euo pipefail
#
## enable debug
## set -x
#
#
echo "configuring sqs"
echo "================================ Starting SQS via localstack...====================================="
LOCALSTACK_HOST=localhost
DEFAULT_REGION=us-east-1


# https://docs.aws.amazon.com/cli/latest/reference/sqs/create-queue.html
create_queue() {
  local QUEUE_NAME_TO_CREATE=$1
  awslocal --endpoint-url=http://${LOCALSTACK_HOST}:4566 sqs create-queue --queue-name ${QUEUE_NAME_TO_CREATE} --region ${DEFAULT_REGION} --attributes VisibilityTimeout=30
}

create_queue "queue2"
create_queue "queue1"