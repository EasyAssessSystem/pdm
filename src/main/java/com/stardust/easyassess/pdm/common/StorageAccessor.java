package com.stardust.easyassess.pdm.common;

import java.io.InputStream;

public interface StorageAccessor {
    String put(String bucketName, String fileKey, InputStream inputStream);
    void delete();
}
