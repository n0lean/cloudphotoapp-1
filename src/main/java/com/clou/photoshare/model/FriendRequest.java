package com.clou.photoshare.model;


import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.clou.photoshare.util.FriendRequestDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;


@JsonDeserialize(using = FriendRequestDeserialize.class)
@DynamoDBTable(tableName = "FriendRequest")
@PropertySource("classpath:application.properties")
public class FriendRequest {
    private String fromUserId;
    private String toUserId;
    private String status;
    private Date timeStamp;

    public class FriendRequestId implements Serializable {

        private String fromUserId;
        private String toUserId;

        @DynamoDBHashKey
        public String getFromUserId() {
            return this.fromUserId;
        }

        public void setFromUserId(String fromUserId) {
            this.fromUserId = fromUserId;
        }

        @DynamoDBRangeKey
        public String getToUserId() {
            return this.toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId =  toUserId;
        }

    }


    @Id
    private FriendRequestId friendRequestId;


    public FriendRequest () { }

    public FriendRequest (String fromUserId,
                          String toUserId,
                          String status,
                          Date timeStamp) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    @DynamoDBHashKey(attributeName = "FromUserId")
    @DynamoDBIndexRangeKey(attributeName = "FromUserId", globalSecondaryIndexName = "toUserIndex")
    public String getFromUserId() {return this.fromUserId;}

    public void setFromUserId(String fromUserId) { this.fromUserId = fromUserId; }

    @DynamoDBRangeKey(attributeName = "ToUserId")
    @DynamoDBIndexHashKey(attributeName = "ToUserId", globalSecondaryIndexName = "toUserIndex")
    public String getToUserId() { return this.toUserId; }

    public void setToUserId(String toUserId) { this.toUserId = toUserId; }

    @DynamoDBAttribute(attributeName = "Status")
    public String getStatus() { return this.status; }

    public void setStatus(String status) { this.status = status; }

    @DynamoDBAttribute(attributeName = "TimeStamp")
    public Date getTimeStamp() { return this.timeStamp; }

    public void setTimeStamp(Date timeStamp) { this.timeStamp = timeStamp; }


}
