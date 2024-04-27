package crud_app.repository;

import crud_app.entity.Topic;

import java.util.List;

/**
 * this is topic repository interface
 */
public interface TopicRepository {
    /**
     * method save new topic in database
     *
     * @param topic topic for save
     * @return saved topic
     */
    Topic createTopic(Topic topic);

    /**
     * method update topic in database
     *
     * @param topic topic for update
     * @return updated topic
     */
    Topic updateTopic(Topic topic);

    /**
     * method read topic from database by id
     *
     * @param topicId topic id in database
     * @return topic from database
     */
    Topic getTopic(int topicId);

    /**
     * method removed topic from database by id
     *
     * @param topicId topic id in database
     * @return removed topic
     */
    boolean removeTopic(int topicId);

    /**
     * method return list topic by group id
     *
     * @param groupId group id in database
     * @return topic list
     */
    List<Topic> getAllTopicGroup(int groupId);

    /**
     * method return all topics
     *
     * @return topic list
     */
    List<Topic> getAllTopic();
}
