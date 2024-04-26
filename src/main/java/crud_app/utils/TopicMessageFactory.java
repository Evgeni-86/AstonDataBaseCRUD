package crud_app.utils;

import crud_app.dto.TopicMessageDto;
import crud_app.entity.Topic;
import crud_app.entity.TopicMessage;

/**
 * topic message factory class
 */
public class TopicMessageFactory {
    /**
     * method for create topic message object
     *
     * @param topicMessageDto topicMessageDto object
     * @return topic message
     */
    public TopicMessage getTopicMessage(TopicMessageDto topicMessageDto) {
        Topic topic = new Topic();
        topic.setId(topicMessageDto.getTopicId());
        return new TopicMessage(
                topicMessageDto.getId(),
                topicMessageDto.getTitle(),
                topicMessageDto.getBody(),
                topic);
    }
}
