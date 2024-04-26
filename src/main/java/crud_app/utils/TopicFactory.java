package crud_app.utils;


import crud_app.dto.TopicDto;
import crud_app.entity.Group;
import crud_app.entity.Topic;

/**
 * topic factory class
 */
public class TopicFactory {
    /**
     * method for create topic object
     *
     * @param topicDto topicDto object
     * @return topic
     */
    public Topic getTopicMessage(TopicDto topicDto) {
        Group group = new Group();
        group.setId(topicDto.getGroupId());
        return new Topic(
                topicDto.getId(),
                topicDto.getName(),
                group);
    }
}
