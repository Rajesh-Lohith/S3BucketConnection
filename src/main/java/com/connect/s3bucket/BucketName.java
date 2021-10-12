package com.connect.s3bucket;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    TODO_IMAGE("waimagebucket");
    private final String bucketName;
}