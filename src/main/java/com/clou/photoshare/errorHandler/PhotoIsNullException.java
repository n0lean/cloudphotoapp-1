package com.clou.photoshare.errorHandler;

import com.clou.photoshare.model.Photo;

public class PhotoIsNullException extends RuntimeException{
    public PhotoIsNullException(Photo photo){
        super("Photo id or OwnerId is null " + photo);
    }
}
