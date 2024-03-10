package com.dl.officialsite.bot.event;

import com.dl.officialsite.bot.model.Message;

public class NotifyMessageFactory {

    public static final String SOCIAL_MEDIA = "Website: https://dapplearning.org/\n" +
        "GitHub:  https://github.com/Dapp-Learning-DAO/Dapp-Learning-DAO\n" +
        "Twitter:  https://twitter.com/Dapp_Learning\n" +
        "YouTube: https://www.youtube.com/@DappLearning\n";

    public static Message testMessage(String testMsg) {
        return Message.build(String.format("Test Message -----> %s\n%s", testMsg, SOCIAL_MEDIA));
    }

    public static Message welcomeUserMessage(String nickName) {
        return Message.build(String.format("Welcome %s join Dapp-Learning, introduce yourself briefly.\n%s", nickName, SOCIAL_MEDIA));
    }

    public static Message bountyMessage(String nickName, String title) {
        String message = String.format(
            "➖➖➖➖➖➖➖➖➖➖➖\n" +
                "👏Create New Bounty👏\n" +
                "Creator:\t\t%s\n" +
                "Bounty Name:\t\t%s\n" +
                "➖➖➖➖➖➖➖➖➖➖➖", nickName, title);
        return Message.build(message);
    }

    public static Message sharingMessage(String title, String nickName, String theme, String formatDate) {
        String message = String.format(
            "➖➖➖➖➖➖➖➖➖➖➖\n" +
                "%s\n" +
                "Creator:\t\t%s\n" +
                "Share Name:\t\t%s\n " +
                "Share Date:\t\t%s\n" +
                "➖➖➖➖➖➖➖➖➖➖➖", title, nickName, theme, formatDate);
        return Message.build(message);
    }

    public static Message joinTeamMessage(String nickName, String teamName) {
        String message = String.format(
            "➖➖➖➖➖➖➖➖➖➖➖\n" +
                "👏New Member Join Team👏\n" +
                "MemberName:\t\t%s\n" +
                "Team Name:\t\t%s\n" +
                "Please team admin to approve.\n" +
                "➖➖➖➖➖➖➖➖➖➖➖", nickName, teamName);
        return Message.build(message);
    }

    public static Message exitTeamMessage(String nickName, String teamName) {
        String message = String.format(
            "➖➖➖➖➖➖➖➖➖➖➖\n" +
                "🥀🥀Member Exit Team🥀🥀\n" +
                "MemberName:\t\t%s\n" +
                "Team Name:\t\t%s\n" +
                "➖➖➖➖➖➖➖➖➖➖➖", nickName, teamName);
        return Message.build(message);
    }

}