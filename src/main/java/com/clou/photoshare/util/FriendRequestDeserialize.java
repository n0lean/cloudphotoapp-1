package com.clou.photoshare.util;


import com.clou.photoshare.model.FriendRequest;
import com.clou.photoshare.model.FriendsRequestBuilder;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import sun.jvm.hotspot.memory.FreeChunk;

import java.io.IOException;

public class FriendRequestDeserialize extends JsonDeserializer<FriendRequest> {

    @Override
    public FriendRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        FriendRequest fr = new FriendsRequestBuilder()
                                .fromUserId(node.get("fromUserId").asText())
                                .toUserId(node.get("toUserId").asText())
                                .buildFriendRequest();
        return fr;

    }
}
