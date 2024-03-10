#!/bin/bash
######################### Not finished yet #########################

# Define the output YAML file name
output_file=../src/main/java/resources/application-prod.yml
data_json=$(curl -s https://api.telegram.org/bot$TELEGRAM_BOT_TOKEN/getUpdates)

# Start writing the static part of the YAML to the output file
cat > $output_file <<EOF
bot:
  telegram:
    botToken: \${TG_BOT_TOKEN}
    timeoutInSeconds: \${TG_BOT_TIMEOUT:10}
    groupList:
      - groupId: \${TG_GROUP_ID}
        topicOrChannelList:
          - name: "general"
            threadOrTopicId: "1"
EOF

# Extract message_thread_id and forum_topic_created.name, remove duplicates, and append to the output file
echo ${data_json} | jq -r '.result[] | select(.message.message_thread_id != null) | "\(.message.message_thread_id) \(.message.reply_to_message.forum_topic_created.name)"'  | sort -u | while read -r line; do
    # Extract thread id and topic name
    thread_id=$(echo "$line" | awk '{print $1}')
    topic_name=$(echo "$line" | cut -d ' ' -f 2-)

    # transform topic_name
    # 1. to lower case
    # 2. replace space with '_'
    topic_name="${topic_name// /_}"
    topic_name="${topic_name,,}"

    # Append to the output file
    cat >> $output_file <<EOF
          - name: "$topic_name"
            threadOrTopicId: "$thread_id"
EOF
done

echo "YAML file generated: $output_file"
