package com.clou.photoshare.util;


import com.clou.photoshare.model.FriendRequest;
import com.clou.photoshare.model.FriendRequestBuilder;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class FriendRequestDeserialize extends JsonDeserializer<FriendRequest> {

    @Override
    public FriendRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        System.out.println("DEBUG: ");
        System.out.println(node.toString());
        FriendRequest fr = new FriendRequestBuilder()
                .fromUserId(node.get("fromUserId").asText())
                .toUserId(node.get("toUserId").textValue())
                .buildFriendRequest();
        return fr;

    }
}