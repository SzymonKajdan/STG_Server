package com.shareThegame.STG.Repository;

public
interface EmailSender {
    void sendEmail(String to, String subject, String content);

}
