# AstonDataBaseCRUD

## Description
#### Simple CRUD web application to create groups, topics and messages in them
#### Group can have many topics and topics can belong to several groups
#### Programming language - pure JAVA 17
#### Database - Postgres 15
#### Database is initialized after starting the context
#### For JUnit Tests used library TestContainers (postgres:15)

## Start app
#### 1. database connection settings in file app.properties
#### 2. "docker compose up" - start database docker container (use adminer optional)
#### 3. build project
#### 4. deploy crud_app.war (use servlet container (TomCat 10))
#### 5. example url : http://localhost:8080/crud_app/

## REST API

## Group
### GET : /crud_app/groups/ - get all groups
### GET : /crud_app/groups/{id} - get group by id
### POST : /crud_app/groups/ - create new group
#### Json body example
#### {"name":"new group name"}
### PUT : /crud_app/groups/ - update group
#### Json body example
#### {"id":groupId,"name":"group name update"}
### DELETE : /crud_app/groups/{id} - delete group by id

## Topic
### GET : /crud_app/topics/ - get all topics
### GET : /crud_app/topics/group/{id} - get all topic by group id
### GET : /crud_app/topics/{id} - get topic by id
### POST : /crud_app/topics/ - create new topic
#### Json body example
#### {"groupId":groupId,"name":"new topic name"}
### PUT : /crud_app/topics/ - update topic
#### Json body example
#### {"id":topicId,"groupId":groupId,"name":"topic name update"}
### DELETE : /crud_app/topics/{id} - delete topic by id

## Message
### GET : /crud_app/messages/topic/{id} - get all messages topic by topic id
### GET : /crud_app/messages/{id} - get message by id
### POST : /crud_app/messages/ - create new message
#### Json body example
#### {"topicId":topicId,"title":"new message title","body":"new message text"}
### PUT : /crud_app/messages/ - update message
#### Json body example
#### {"id":messageId,"topicId":newTopicId,"title":"message title update","body":"message text update"}
### DELETE : /crud_app/messages/{id} - delete message by id