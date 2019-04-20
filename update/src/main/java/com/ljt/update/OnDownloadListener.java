package com.ljt.update;

public interface OnDownloadListener {

    void startLoad(Long all);
    void loaddingLoad(Long progress);
    void completeLoad(String path);
    void failLoad(String exception);
    void failRequest(String exception);

}
